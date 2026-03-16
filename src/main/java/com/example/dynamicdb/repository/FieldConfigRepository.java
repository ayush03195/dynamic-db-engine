package com.example.dynamicdb.repository;

import com.example.dynamicdb.entity.config.FieldConfig;
import com.example.dynamicdb.entity.config.TableConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldConfigRepository extends BaseRepository<FieldConfig, Long> {

    List<FieldConfig> findByTableConfig(TableConfig tableConfig);

}