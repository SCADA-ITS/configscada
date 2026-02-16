-- 
-- Table: master.road_impacts;
--
INSERT INTO master.road_impacts (road_impact_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(10, 'PISTA_IZQ', 'Pista izquierda afectada', 'LBL_ROAD_IMPACT_LEFT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'ARCEN_IZQ', 'Arcén izquierdo afectado', 'LBL_ROAD_IMPACT_LEFT_SHOULDER', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'PISTA_DER', 'Pista derecha afectada', 'LBL_ROAD_IMPACT_RIGHT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 'ARCEN_DER', 'Arcén derecha afectada', 'LBL_ROAD_IMPACT_RIGHT_SHOULDER', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'CALZADA', 'Toda la calzada afectada', 'LBL_ROAD_IMPACT_ALL_LANES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



