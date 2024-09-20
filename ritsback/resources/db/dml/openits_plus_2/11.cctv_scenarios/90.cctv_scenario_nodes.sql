
-- 
-- Table: config.cctv_scenario_nodes
--
INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, management_area_id, enabled, visible) VALUES
(2, 'TUNEL', 'TUNEL', NULL, 1, true, true),
		(3, 'MOSAICO', 'MOSAICO', 1, 1, true, true),
		(4, 'Tubo sentido Norte', 'Tubo sentido Norte', 2, 1, true, true),
		(5, 'Tubo sentido Sur', 'Tubo sentido Sur', 3, 1, true, true);

INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, enabled, visible) VALUES
(1, 'TUNELES', 'TUNELES', NULL, true, true);


-- 
-- Table: config.cctv_scenario_node_hierarchies
--
INSERT INTO conf.cctv_scenario_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(1, 2, true, true),
	(2, 3, true, true),
	(2, 4, true, true),
	(2, 5, true, true);
	
	
