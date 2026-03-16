-- =========================
-- COUNTRY
-- =========================
CREATE TABLE country (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         created_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         created_by VARCHAR(50),
                         updated_by VARCHAR(50)
);

-- =========================
-- REGION
-- =========================
CREATE TABLE region (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        country_id BIGINT,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP,
                        created_by VARCHAR(50),
                        updated_by VARCHAR(50),

                        CONSTRAINT fk_region_country
                            FOREIGN KEY(country_id)
                                REFERENCES country(id)
);

-- =========================
-- PLANT
-- =========================
CREATE TABLE plant (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       region_id BIGINT,
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP,
                       created_by VARCHAR(50),
                       updated_by VARCHAR(50),

                       CONSTRAINT fk_plant_region
                           FOREIGN KEY(region_id)
                               REFERENCES region(id)
);

-- =========================
-- LOCATION
-- =========================
CREATE TABLE location (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          plant_id BIGINT,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP,
                          created_by VARCHAR(50),
                          updated_by VARCHAR(50),

                          CONSTRAINT fk_location_plant
                              FOREIGN KEY(plant_id)
                                  REFERENCES plant(id)
);

-- =========================
-- SUPPLIER
-- =========================
CREATE TABLE supplier (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          location_id BIGINT,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP,
                          created_by VARCHAR(50),
                          updated_by VARCHAR(50),

                          CONSTRAINT fk_supplier_location
                              FOREIGN KEY(location_id)
                                  REFERENCES location(id)
);

-- =========================
-- PART DETAILS
-- =========================
CREATE TABLE part (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      supplier_id BIGINT,
                      created_at TIMESTAMP,
                      updated_at TIMESTAMP,
                      created_by VARCHAR(50),
                      updated_by VARCHAR(50),

                      CONSTRAINT fk_part_supplier
                          FOREIGN KEY(supplier_id)
                              REFERENCES supplier(id)
);

-- =========================
-- MANUFACTURING DETAILS
-- =========================
CREATE TABLE manufacturing_detail (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(100) NOT NULL,
                                      part_id BIGINT,
                                      created_at TIMESTAMP,
                                      updated_at TIMESTAMP,
                                      created_by VARCHAR(50),
                                      updated_by VARCHAR(50),

                                      CONSTRAINT fk_manufacturing_part
                                          FOREIGN KEY(part_id)
                                              REFERENCES part(id)
);

-- =========================
-- TABLE CONFIG
-- =========================
CREATE TABLE table_config (
                              id BIGSERIAL PRIMARY KEY,
                              table_name VARCHAR(100) UNIQUE NOT NULL,
                              entity_name VARCHAR(100) NOT NULL
);

-- =========================
-- FIELD CONFIG
-- =========================
CREATE TABLE field_config (
                              id BIGSERIAL PRIMARY KEY,
                              table_config_id BIGINT,
                              column_key VARCHAR(100),
                              entity_field_name VARCHAR(100),
                              content_type VARCHAR(50),
                              table_view VARCHAR(100),

                              CONSTRAINT fk_fieldconfig_tableconfig
                                  FOREIGN KEY(table_config_id)
                                      REFERENCES table_config(id)
);