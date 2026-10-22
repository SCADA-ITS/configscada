-- 
-- Table: master.road_types
--
INSERT INTO master.road_types (road_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'MOTORWAY', 'MOTORWAY', 'LBL_ROAD_TYPE_MOTORWAY', null, true, true, '2020-01-01 00:00:00.000', '2020-01-01 00:00:00.000'),
(2, 'TOLL ROAD', 'TOLL ROAD', 'LBL_ROAD_TYPE_TOLL_ROAD', null, true, true, '2020-01-01 00:00:00.000', '2020-01-01 00:00:00.000'),
(3, 'NATIONAL HIGHWAY', 'NATIONAL HIGHWAY', 'LBL_ROAD_TYPE_NATIONAL_HIGHWAY', null, true, true, '2020-01-01 00:00:00.000', '2020-01-01 00:00:00.000'),
(4, 'COUNTRY ROAD', 'COUNTRY ROAD', 'LBL_ROAD_TYPE_COUNTRY_ROAD', null, true, true, '2020-01-01 00:00:00.000', '2020-01-01 00:00:00.000');

-- 
-- Table: master.road_impacts;
--

INSERT INTO master.road_impacts (road_impact_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'DEFAULT', 'DEFAULT', 'LBL_ROAD_IMPACT_DEFAULT', null, true, true, '2020-01-01 00:00:00.000', '2020-01-01 00:00:00.000'),
(2, 'UNKNOWN', 'UNKNOWN', 'LBL_ROAD_IMPACT_UNKNOWN', null, true, true, '2020-01-01 00:00:00.000', '2020-01-01 00:00:00.000');