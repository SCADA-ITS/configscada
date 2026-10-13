-- 
-- Table: master.road_impacts;
--
INSERT INTO master.road_impacts (road_impact_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(10, 'PISTA_IZQ', 'Pista izquierda afectada', 'LBL_ROAD_IMPACT_LEFT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'PISTA_CENT', 'Pista central afectada', 'LBL_ROAD_IMPACT_CENTER_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 'PISTA_DER', 'Pista derecha afectada', 'LBL_ROAD_IMPACT_RIGHT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'PISTA_IZQ_CEN', 'Pistas izquierda y central afectadas', 'LBL_ROAD_IMPACT_LEFT_CENTER_LANES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'PISTA_IZQ_DER', 'Pistas izquierda y derecha afectadas', 'LBL_ROAD_IMPACT_LEFT_RIGHT_LANES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'PISTA_DER_CEN', 'Pistas derecha y central afectadas', 'LBL_ROAD_IMPACT_RIGHT_CENTER_LANES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(123, 'CALZADA', 'Toda la calzada afectada', 'LBL_ROAD_IMPACT_ALL_LANES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100, 'RAMAL_CALZADA', 'Todo el ramal', 'LBL_ROAD_IMPACT_ALL_BRANCH', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 'RAMAL_PISTA_IZQ', 'Pista izquierda del ramal', 'LBL_ROAD_IMPACT_BRANCH_LEFT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 'RAMAL PISTA_DER', 'Pista derecha del ramal', 'LBL_ROAD_IMPACT_BRANCH_RIGHT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



