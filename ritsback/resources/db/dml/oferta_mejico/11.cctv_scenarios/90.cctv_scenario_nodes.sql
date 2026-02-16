
-- 
-- Table: config.cctv_scenario_nodes
--
INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, enabled, visible) VALUES
(1, 'CAMARAS', 'CAMARAS', NULL, true, true),
	(2, 'MOSAICO 1', 'MOSAICO 1', 1, true, true),
	(3, 'MOSAICO 2', 'MOSAICO 2', 2, true, true),
	(4, 'MOSAICO 3', 'MOSAICO 3', 3, true, true),
	(5, 'MOSAICO 4', 'MOSAICO 4', 4, true, true);


-- 
-- Table: config.cctv_scenario_node_hierarchies
--
INSERT INTO conf.cctv_scenario_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(1, 2, true, true),
(1, 3, true, true),
(1, 4, true, true),
(1, 5, true, true);

	
