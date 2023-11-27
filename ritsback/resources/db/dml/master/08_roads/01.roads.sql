-- 
-- Table: master.road_types
--
INSERT INTO master.road_types (road_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'MOTORWAY', 'MOTORWAY', 'LBL_ROAD_TYPE_MOTORWAY', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'TOLL ROAD', 'TOLL ROAD', 'LBL_ROAD_TYPE_TOLL_ROAD', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'NATIONAL HIGHWAY', 'NATIONAL HIGHWAY', 'LBL_ROAD_TYPE_NATIONAL_HIGHWAY', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'COUNTRY ROAD', 'COUNTRY ROAD', 'LBL_ROAD_TYPE_COUNTRY_ROAD', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.road_impacts;
--
INSERT INTO master.road_impacts (road_impact_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'DEFAULT', 'Default', 'LBL_ROAD_IMPACT_DEFAULT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'UNKNOWN', 'Unknown', 'LBL_ROAD_IMPACT_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: static.management_areas
--
INSERT INTO static.management_areas  (management_area_id, alias, description,  enabled, visible, created_at, updated_at) VALUES 
(2000, 'root', 'root', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
