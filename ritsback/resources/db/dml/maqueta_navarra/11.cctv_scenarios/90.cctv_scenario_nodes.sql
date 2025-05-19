
-- 
-- Table: config.cctv_scenario_nodes
--
INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, enabled, visible) VALUES
(1, 'CAMERAS', 'CAMERAS', NULL, true, true),
	(2, 'ESCENARIO 1', 'ESCENARIO 1', 1, true, true),
	(3, 'ESCENARIO 2', 'ESCENARIO 2', 2, true, true),
	(4, 'ESCENARIO 3', 'ESCENARIO 3', 3, true, true);

-- 
-- Table: config.cctv_scenario_node_hierarchies
--
INSERT INTO conf.cctv_scenario_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(1, 2, true, true),
(1, 3, true, true),
(1, 4, true, true);	
	
