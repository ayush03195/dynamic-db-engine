-- ======================================================
-- SUPPLIER LOCATION DROPDOWN VIEW
-- Combines Supplier → Location → Plant → Region → Country
-- ======================================================

CREATE OR REPLACE VIEW supplier_location_view AS
SELECT
    s.id AS id,
    s.name || ' - ' || l.name || ' - ' || p.name || ' - ' || r.name || ' - ' || c.name AS label
FROM supplier s
         LEFT JOIN location l ON s.location_id = l.id
         LEFT JOIN plant p ON l.plant_id = p.id
         LEFT JOIN region r ON p.region_id = r.id
         LEFT JOIN country c ON r.country_id = c.id;



-- ======================================================
-- PART SUPPLIER DROPDOWN VIEW
-- Combines Part → Supplier → Location → Plant → Region
-- ======================================================

CREATE OR REPLACE VIEW part_supplier_view AS
SELECT
    p.id AS id,
    p.name || ' - ' || s.name || ' - ' || l.name || ' - ' || pl.name || ' - ' || r.name AS label
FROM part p
         LEFT JOIN supplier s ON p.supplier_id = s.id
         LEFT JOIN location l ON s.location_id = l.id
         LEFT JOIN plant pl ON l.plant_id = pl.id
         LEFT JOIN region r ON pl.region_id = r.id;



-- ======================================================
-- MANUFACTURING DETAIL DROPDOWN VIEW
-- Combines Manufacturing → Part → Supplier
-- ======================================================

CREATE OR REPLACE VIEW manufacturing_part_view AS
SELECT
    m.id AS id,
    m.name || ' - ' || p.name || ' - ' || s.name AS label
FROM manufacturing_detail m
         LEFT JOIN part p ON m.part_id = p.id
         LEFT JOIN supplier s ON p.supplier_id = s.id;