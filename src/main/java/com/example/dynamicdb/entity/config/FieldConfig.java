package com.example.dynamicdb.entity.config;

import com.example.dynamicdb.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "field_config")
@Getter
@Setter
public class FieldConfig extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_config_id", nullable = false)
    private TableConfig tableConfig;

    @Column(name = "column_key", nullable = false)
    private String columnKey;

    @Column(name = "entity_field_name", nullable = false)
    private String entityFieldName;

    @Column(name = "content_type")
    private String contentType;   // TEXT / DROPDOWN

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "table_view")
    private String tableView;
}