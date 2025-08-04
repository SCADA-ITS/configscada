-- 
-- Table: master.road_impacts;
--
INSERT INTO master.road_impacts (road_impact_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(100, 'Circulación condicionada', 'Circulación condicionada', 'LBL_ROAD_IMPACT_CIRC_CONDI', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(200, 'Circulación irregular', 'Circulación irregular', 'LBL_ROAD_IMPACT_CIRC_IRR', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(300, 'Circulación difícil', 'Circulación difícil', 'LBL_ROAD_IMPACT_CIRC_DIF', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(400, 'Circulación interrumpida', 'Circulación interrumpida', 'LBL_ROAD_IMPACT_CIRC_INT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);