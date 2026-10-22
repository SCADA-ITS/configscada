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
(2, 'UNKNOWN', 'Unknown', 'LBL_ROAD_IMPACT_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'PISTA_IZQ', 'Pista izquierda afectada', 'LBL_ROAD_IMPACT_LANE_1', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'PISTA_CENT', 'Pista central afectada', 'LBL_ROAD_IMPACT_LANE_2', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 'PISTA_DER', 'Pista derecha afectada', 'LBL_ROAD_IMPACT_LANE_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'PISTA_IZQ_CEN', 'Pistas izquierda y central afectadas', 'LBL_ROAD_IMPACT_LANE_1_2', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'PISTA_IZQ_DER', 'Pistas izquierda y derecha afectadas', 'LBL_ROAD_IMPACT_LANE_1_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'PISTA_DER_CEN', 'Pistas derecha y central afectadas', 'LBL_ROAD_IMPACT_LANE_2_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(123, 'CALZADA', 'Toda la calzada afectada', 'LBL_ROAD_IMPACT_LANE_1_2_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100, 'RAMAL_CALZADA', 'Todo el ramal', 'LBL_ROAD_IMPACT_LANE_1_2_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 'RAMAL_PISTA_IZQ', 'Pista izquierda del ramal', 'LBL_ROAD_IMPACT_LANE_1_2_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 'RAMAL PISTA_DER', 'Pista derecha del ramal', 'LBL_ROAD_IMPACT_LANE_1_2_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



