package com.example.dynamicdb.repository;

import com.example.dynamicdb.entity.config.TableConfig;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableConfigRepository extends BaseRepository<TableConfig, Long> {

    Optional<TableConfig> findByTableName(String tableName);

}