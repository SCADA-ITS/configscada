-- 
-- Table: master.road_impacts;
--
INSERT INTO master.road_impacts (road_impact_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(10, 'ĽAVÁ STOPA', 'Ovplyvnená ľavá stopa', 'LBL_ROAD_IMPACT_LEFT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 'PRAVÁ STOPA', 'Ovplyvnená pravá stopa', 'LBL_ROAD_IMPACT_RIGHT_LANE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'ĽAVÁ STREDNÁ DRÁHA', 'Ovplyvnený ľavý a stredný pruh', 'LBL_ROAD_IMPACT_LEFT_CENTER_LANES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'PRAVÁ STREDNÁ DRÁHA', 'Ovplyvnený pravý a stredný pruh', 'LBL_ROAD_IMPACT_RIGHT_CENTER_LANES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



