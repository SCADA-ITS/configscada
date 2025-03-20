
-- 
-- Table: config.cctv_scenario_nodes
--
INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, enabled, visible) VALUES
(100, 'ITS', 'ITS', null, true, true),
(1, 'Circunvalación', 'Circunvalación', 1, true, true),
(2, 'Belén Escobar', 'Belén Escobar', 2, true, true),
(3, 'Del Viso', 'Del Viso', 3, true, true),
(4, 'Tigre', 'Tigre', 4, true, true);

INSERT INTO conf.cctv_scenario_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(100, 1, true, true),
(100, 2, true, true),
(100, 3, true, true),
(100, 4, true, true);
