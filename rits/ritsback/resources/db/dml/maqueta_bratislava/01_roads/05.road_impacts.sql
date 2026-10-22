-- 
-- Table: master.road_impacts;
--
INSERT INTO master.road_impacts (road_impact_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(10, 'PISTA_IZQ', 'Pista izquierda afectada', 'LBL_ROAD_IMPACT_LEFT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 'PISTA_DER', 'Pista derecha afectada', 'LBL_ROAD_IMPACT_RIGHT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'PISTA_IZQ_CEN', 'Pistas izquierda y central afectadas', 'LBL_ROAD_IMPACT_LEFT_CENTER_LANES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'PISTA_DER_CEN', 'Pistas derecha y central afectadas', 'LBL_ROAD_IMPACT_RIGHT_CENTER_LANES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



