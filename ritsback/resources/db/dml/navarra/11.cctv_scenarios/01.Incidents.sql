-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(1, 'INCIDENT', 'INCIDENT', 2, 5, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
(1, 2, 1, 1, true, true),
(1, 2, 2, 2, true, true),
(1, 2, 3, 3, true, true),
(1, 2, 4, 4, true, true),
(1, 2, 5, 5, true, true),
(1, 2, 12, 6, true, true),
(1, 2, 13, 7, true, true),
(1, 2, 14, 8, true, true),
(1, 2, 15, 9, true, true),
(1, 2, 16, 10, true, true);
	