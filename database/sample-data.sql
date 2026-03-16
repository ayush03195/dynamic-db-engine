-- =========================
-- COUNTRY DATA
-- =========================
INSERT INTO country (name, created_at) VALUES
                                           ('India', now()),
                                           ('USA', now());


-- =========================
-- REGION DATA
-- =========================
INSERT INTO region (name, country_id, created_at) VALUES
                                                      ('North', 1, now()),
                                                      ('South', 1, now()),
                                                      ('California', 2, now()),
                                                      ('Texas', 2, now());


-- =========================
-- PLANT DATA
-- =========================
INSERT INTO plant (name, region_id, created_at) VALUES
                                                    ('Plant A', 1, now()),
                                                    ('Plant B', 2, now()),
                                                    ('Plant C', 3, now()),
                                                    ('Plant D', 4, now());


-- =========================
-- LOCATION DATA
-- =========================
INSERT INTO location (name, plant_id, created_at) VALUES
                                                      ('Warehouse A', 1, now()),
                                                      ('Warehouse B', 2, now()),
                                                      ('Warehouse C', 3, now()),
                                                      ('Warehouse D', 4, now());


-- =========================
-- SUPPLIER DATA
-- =========================
INSERT INTO supplier (name, location_id, created_at) VALUES
                                                         ('ABC Supplier', 1, now()),
                                                         ('XYZ Supplier', 2, now()),
                                                         ('Global Parts Ltd', 3, now()),
                                                         ('Industrial Components', 4, now());


-- =========================
-- PART DATA
-- =========================
INSERT INTO part (name, supplier_id, created_at) VALUES
                                                     ('Bolt', 1, now()),
                                                     ('Nut', 2, now()),
                                                     ('Gear', 3, now()),
                                                     ('Shaft', 4, now());


-- =========================
-- MANUFACTURING DETAILS
-- =========================
INSERT INTO manufacturing_detail (name, part_id, created_at) VALUES
                                                                 ('Forging Process', 1, now()),
                                                                 ('Thread Cutting', 2, now()),
                                                                 ('Heat Treatment', 3, now()),
                                                                 ('Surface Finishing', 4, now());