-- =========================
-- TABLE CONFIG
-- =========================
INSERT INTO table_config (table_name, entity_name) VALUES
                                                       ('country', 'Country'),
                                                       ('region', 'Region'),
                                                       ('plant', 'Plant'),
                                                       ('location', 'Location'),
                                                       ('supplier', 'Supplier'),
                                                       ('part', 'Part'),
                                                       ('manufacturing_detail', 'ManufacturingDetail');

INSERT INTO field_config (table_config_id, column_key, entity_field_name, content_type)
VALUES
    (1, 'name', 'name', 'TEXT');

INSERT INTO field_config (table_config_id, column_key, entity_field_name, content_type)
VALUES
    (2, 'name', 'name', 'TEXT'),
    (2, 'country', 'country', 'DROPDOWN');

INSERT INTO field_config (table_config_id, column_key, entity_field_name, content_type)
VALUES
    (3, 'name', 'name', 'TEXT'),
    (3, 'region', 'region', 'DROPDOWN');

INSERT INTO field_config (table_config_id, column_key, entity_field_name, content_type)
VALUES
    (4, 'name', 'name', 'TEXT'),
    (4, 'plant', 'plant', 'DROPDOWN');

INSERT INTO field_config
(table_config_id,column_key,entity_field_name,content_type,table_view)
VALUES
    (5,'name','name','TEXT',NULL),
    (5,'location','location','DROPDOWN','supplier_location_view');

INSERT INTO field_config
(table_config_id,column_key,entity_field_name,content_type,table_view)
VALUES
    (6,'name','name','TEXT',NULL),
    (6,'supplier','supplier','DROPDOWN','part_supplier_view');

INSERT INTO field_config
(table_config_id,column_key,entity_field_name,content_type,table_view)
VALUES
    (7,'name','name','TEXT',NULL),
    (7,'part','part','DROPDOWN','manufacturing_part_view');

