package com.example.dynamicdb.serviceImpl;

import com.example.dynamicdb.dto.DynamicTableResponse;
import com.example.dynamicdb.entity.config.FieldConfig;
import com.example.dynamicdb.entity.config.TableConfig;
import com.example.dynamicdb.registry.RepositoryRegistry;
import com.example.dynamicdb.repository.FieldConfigRepository;
import com.example.dynamicdb.repository.TableConfigRepository;
import com.example.dynamicdb.repository.ViewQueryRepository;
import com.example.dynamicdb.service.DynamicTableService;
import com.example.dynamicdb.util.EntityConstants;
import com.example.dynamicdb.util.FieldMetadata;
import com.example.dynamicdb.util.MetadataCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation responsible for dynamically fetching
 * table data based on metadata configuration.
 *
 * This service performs:
 *  - Dynamic entity resolution
 *  - Dynamic repository resolution
 *  - Pagination
 *  - Reflection-based field mapping
 *  - View-based dropdown resolution
 *  - Metadata caching to optimize reflection
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicTableServiceImpl implements DynamicTableService {

    private final TableConfigRepository tableConfigRepository;
    private final FieldConfigRepository fieldConfigRepository;
    private final RepositoryRegistry repositoryRegistry;
    private final ViewQueryRepository viewQueryRepository;

    /**
     * Fetch dynamic table data using metadata configuration.
     *
     * @param tableName logical table name from table_config
     * @param page      page number starting from 0
     * @param size      requested page size
     * @return DynamicTableResponse containing pagination metadata and data rows
     */
    @Override
    @Transactional(readOnly = true)
    public DynamicTableResponse getTableData(String tableName, int page, int size) {

        log.info("Fetching data for table: {}, page: {}, size: {}", tableName, page, size);

        int safeSize = Math.min(size, 100);

        TableConfig tableConfig = tableConfigRepository
                .findByTableName(tableName)
                .orElseThrow(() -> {
                    log.error("Invalid table name requested: {}", tableName);
                    return new RuntimeException("Invalid table name: " + tableName);
                });

        List<FieldConfig> fieldConfigs =
                fieldConfigRepository.findByTableConfig(tableConfig);

        Map<String, FieldConfig> fieldConfigMap =
                fieldConfigs.stream()
                        .collect(Collectors.toMap(FieldConfig::getEntityFieldName, fc -> fc));

        try {

            String entityClassName =
                    EntityConstants.ENTITY_PACKAGE + tableConfig.getEntityName();

            log.debug("Resolving entity class: {}", entityClassName);

            Class<?> entityClass = Class.forName(entityClassName);

            JpaRepository<?, ?> repository =
                    repositoryRegistry.getRepository(entityClass);

            PageRequest pageable = PageRequest.of(page, safeSize);

            Page<?> pageResult = repository.findAll(pageable);

            List<?> records = pageResult.getContent();

            log.debug("Fetched {} records for table: {}", records.size(), tableName);

            List<FieldMetadata> metadata = MetadataCache.get(entityClass);

            if (metadata == null) {
                log.debug("Cache miss for entity: {}. Building metadata.", entityClass.getSimpleName());
                metadata = buildMetadata(entityClass, fieldConfigMap);
                MetadataCache.put(entityClass, metadata);
            } else {
                log.debug("Cache hit for entity: {}", entityClass.getSimpleName());
            }

            // Pre-fetch all dropdown data once outside the record loop
            // This prevents N+1 queries when processing multiple records
            Map<String, List<Map<String, Object>>> dropdownCache = new HashMap<>();

            for (FieldMetadata meta : metadata) {
                if (meta.isDropdown() && !dropdownCache.containsKey(meta.getViewName())) {
                    log.debug("Pre-fetching dropdown for view: {}", meta.getViewName());
                    dropdownCache.put(
                            meta.getViewName(),
                            viewQueryRepository.fetchDropdown(meta.getViewName())
                    );
                }
            }

            List<Map<String, Object>> response = new ArrayList<>();

            for (Object record : records) {

                Map<String, Object> row = new LinkedHashMap<>();

                for (FieldMetadata meta : metadata) {

                    Field field = meta.getField();

                    Object value = field.get(record);

                    if (meta.isDropdown()) {
                        row.put(field.getName(), dropdownCache.get(meta.getViewName()));
                    } else {
                        row.put(field.getName(), value);
                    }
                }

                response.add(row);
            }

            log.info("Successfully built response for table: {}", tableName);

            return new DynamicTableResponse(
                    page,
                    safeSize,
                    pageResult.getTotalElements(),
                    response
            );

        } catch (ClassNotFoundException e) {
            log.error("Entity class not found for table: {}", tableName, e);
            throw new IllegalStateException("Entity class not found for table: " + tableName, e);

        } catch (IllegalAccessException e) {
            log.error("Reflection access error for table: {}", tableName, e);
            throw new IllegalStateException("Reflection access error for table: " + tableName, e);
        }
    }

    /**
     * Builds field metadata for an entity class.
     * Result is cached in MetadataCache to avoid repeated reflection scanning.
     *
     * @param entityClass    entity class to analyze
     * @param fieldConfigMap field configuration mapping from database
     * @return list of FieldMetadata
     */
    private static List<FieldMetadata> buildMetadata(Class<?> entityClass,
                                                     Map<String, FieldConfig> fieldConfigMap) {

        List<FieldMetadata> metadataList = new ArrayList<>();

        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {

            String fieldName = field.getName();

            if (isAuditField(fieldName)) {
                continue;
            }

            try {
                field.setAccessible(true);
            } catch (Exception e) {
                log.warn("Cannot access field: {} on entity: {}. Skipping.",
                        fieldName, entityClass.getSimpleName());
                continue;
            }

            FieldConfig fieldConfig = fieldConfigMap.get(fieldName);

            boolean dropdown =
                    fieldConfig != null && fieldConfig.getTableView() != null;

            String viewName =
                    dropdown ? fieldConfig.getTableView() : null;

            metadataList.add(new FieldMetadata(field, dropdown, viewName));
        }

        return metadataList;
    }

    /**
     * Filters out audit fields that should not be returned in API responses.
     *
     * @param fieldName name of the field to check
     * @return true if the field is an audit field
     */
    private static boolean isAuditField(String fieldName) {

        return fieldName.equals("createdAt")
                || fieldName.equals("updatedAt")
                || fieldName.equals("createdBy")
                || fieldName.equals("updatedBy");
    }
}