package com.example.dynamicdb.entity.config;

import com.example.dynamicdb.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "table_config")
@Getter
@Setter
public class TableConfig extends BaseEntity {

    @Column(name = "table_name", nullable = false, unique = true)
    private String tableName;

    @Column(name = "entity_name", nullable = false)
    private String entityName;
}