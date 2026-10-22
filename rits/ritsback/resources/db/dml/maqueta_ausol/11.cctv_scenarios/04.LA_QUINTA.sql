-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(7, 'LQ-CA', 'LQ-CA', 3, 5, true, true),
(8, 'LQ-MA', 'LQ-MA', 3, 5, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--LQ-CA
(7, 2, 37, 1, true, true),
(7, 2, 39, 2, true, true),
(7, 2, 40, 3, true, true),
(7, 2, 41, 4, true, true),
(7, 2, 42, 5, true, true),
(7, 2, 50, 6, true, true),
(7, 2, 51, 7, true, true),
(7, 2, 43, 8, true, true),
(7, 2, 44, 9, true, true),
(7, 2, 45, 10, true, true),
(7, 2, 46, 11, true, true),
(7, 2, 47, 12, true, true),
(7, 2, 48, 13, true, true),
(7, 2, 49, 14, true, true),
(7, 2, 38, 16, true, true),

--LQ-MA
(8, 2, 38, 1, true, true),
(8, 2, 52, 2, true, true),
(8, 2, 53, 3, true, true),
(8, 2, 54, 4, true, true),
(8, 2, 55, 5, true, true),
(8, 2, 50, 6, true, true),
(8, 2, 51, 7, true, true),
(8, 2, 56, 8, true, true),
(8, 2, 57, 9, true, true),
(8, 2, 58, 10, true, true),
(8, 2, 59, 11, true, true),
(8, 2, 60, 12, true, true),
(8, 2, 37, 13, true, true);
	