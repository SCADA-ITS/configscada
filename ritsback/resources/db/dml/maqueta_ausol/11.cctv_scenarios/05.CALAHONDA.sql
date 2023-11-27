-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(9, 'CH-CA', 'CH-CA', 3, 4, true, true),
(10, 'CH-MA', 'CH-MA', 3, 4, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--CH-CA
(9, 2, 61, 1, true, true),
(9, 2, 62, 2, true, true),
(9, 2, 63, 3, true, true),
(9, 2, 64, 4, true, true),
(9, 2, 65, 5, true, true),
(9, 2, 70, 6, true, true),
(9, 2, 71, 7, true, true),
(9, 2, 66, 8, true, true),
(9, 2, 67, 9, true, true),
(9, 2, 68, 10, true, true),
(9, 2, 69, 11, true, true),
(9, 2, 80, 12, true, true),

--CH-MA
(10, 2, 80, 1, true, true),
(10, 2, 72, 2, true, true),
(10, 2, 73, 3, true, true),
(10, 2, 74, 4, true, true),
(10, 2, 75, 5, true, true),
(10, 2, 70, 6, true, true),
(10, 2, 71, 7, true, true),
(10, 2, 76, 8, true, true),
(10, 2, 77, 9, true, true),
(10, 2, 78, 10, true, true),
(10, 2, 79, 11, true, true),
(10, 2, 61, 12, true, true);
	