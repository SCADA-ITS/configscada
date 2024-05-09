INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, enabled, visible) VALUES
(1, 'CARRETERAS', 'CARRETERAS', NULL, true, true),
	(2, 'CAMARA', 'CAMARA', 1, true, true);


-- 
-- Table: config.cctv_scenario_node_hierarchies
--
INSERT INTO conf.cctv_scenario_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(1, 2, true, true);
	
	
