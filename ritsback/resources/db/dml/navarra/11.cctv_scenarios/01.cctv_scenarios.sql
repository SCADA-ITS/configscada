-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(1, 'MOSAICO', 'MOSAICO', 4, 5, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--MOSAICO
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
(1, 2, 11, 11, true, true),
(1, 2, 12, 12, true, true),
(1, 2, 13, 13, true, true),
(1, 2, 14, 14, true, true),
(1, 2, 15, 15, true, true),
(1, 2, 16, 16, true, true),
(1, 2, 17, 17, true, true),
(1, 2, 18, 18, true, true),
(1, 2, 19, 19, true, true);
	