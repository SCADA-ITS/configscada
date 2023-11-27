-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(1, 'CCTV SCENE 3x3', 'CCTV SCENE 3x3', 3, 3, true, true),
(2, 'CCTV SCENE 4x2', 'CCTV SCENE 4x2', 4, 2, true, true),
(3, 'CCTV SCENE 4x3', 'CCTV SCENE 4x3', 3, 4, true, true),
(4, 'CCTV SCENE 2x5', 'CCTV SCENE 2x5', 2, 5, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
(1, 2, 1, 1, true, true),
(1, 2, 2, 2, true, true),
(1, 2, 3, 3, true, true),
(1, 2, 4, 4, true, true),
(1, 2, 5, 5, true, true),
(1, 2, 6, 6, true, true),
(1, 2, 7, 7, true, true),
(1, 2, 8, 8, true, true),
(1, 2, 9, 9, true, true),
(1, 2, 10, 10, true, true),

(2, 2, 1, 1, true, true),
(2, 2, 2, 2, true, true),
(2, 2, 3, 3, true, true),
(2, 2, 4, 4, true, true),
(2, 2, 5, 5, true, true),
(2, 2, 6, 6, true, true),
(2, 2, 7, 7, true, true),
(2, 2, 8, 8, true, true),
(2, 2, 9, 9, true, true),
(2, 2, 10, 10, true, true),

(3, 2, 1, 1, true, true),
(3, 2, 2, 2, true, true),
(3, 2, 3, 3, true, true),
(3, 2, 4, 4, true, true),
(3, 2, 5, 5, true, true),
(3, 2, 6, 6, true, true),
(3, 2, 7, 7, true, true),
(3, 2, 8, 8, true, true),
(3, 2, 9, 9, true, true),
(3, 2, 10, 10, true, true),

(4, 2, 1, 1, true, true),
(4, 2, 2, 2, true, true),
(4, 2, 3, 3, true, true),
(4, 2, 4, 4, true, true),
(4, 2, 5, 5, true, true),
(4, 2, 6, 6, true, true),
(4, 2, 7, 7, true, true),
(4, 2, 8, 8, true, true),
(4, 2, 9, 9, true, true),
(4, 2, 10, 10, true, true);

-- 
-- Table: config.cctv_scenario_nodes
--
INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, enabled, visible) VALUES
(1, 'NODE.1', 'NODE.1', NULL, true, true),
	(2, 'NODE.1.1', 'NODE.1.1', NULL, true, true),
		(3, 'CCTV SCENE 1', 'CCTV SCENE 1', 1, true, true),
		(4, 'CCTV SCENE 2', 'CCTV SCENE 2', 2, true, true),
	(5, 'NODE.1.2', 'NODE.1.2', NULL, true, true),
		(6, 'CCTV SCENE 3', 'CCTV SCENE 3', 3, true, true),
(7, 'NODE.2', 'NODE.2', NULL, true, true),
	(8, 'CCTV SCENE 4', 'CCTV SCENE 4', 4, true, true);
-- 
-- Table: config.cctv_scenario_node_hierarchies
--
INSERT INTO conf.cctv_scenario_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(1, 2, true, true),
	(2, 3, true, true),
	(2, 4, true, true),
(1, 5, true, true),
	(5, 6, true, true),
(7, 3, true, true),
(7, 4, true, true),
(7, 6, true, true),
	(7, 8, true, true);
