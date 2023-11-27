-- 
-- Table: static.management_areas
--
INSERT INTO static.management_areas  (management_area_id, alias, description,  enabled, visible, created_at, updated_at) VALUES 
(1, 'AP7 - AUSOL I', 'AP7 - AUSOL I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'AP7 - AUSOL II', 'AP7 - AUSOL II', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.roads
--
INSERT INTO conf.roads (road_id, road_type_id, alias, description, management_area_id) VALUES 
(1, 2, 'AP7 - AUSOL I', 'AP7 - AUSOL I', 1),
(2, 2, 'AP7 - AUSOL II', 'AP7 - AUSOL II', 2);


