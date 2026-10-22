-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(4, 'MM-CA', 'MM-CA', 3, 4, true, true),
(5, 'MM-MA', 'MM-MA', 3, 4, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--MM-CA
(4, 2, 11, 1, true, true),
(4, 2, 12, 2, true, true),
(4, 2, 13, 3, true, true),
(4, 2, 14, 4, true, true),
(4, 2, 15, 5, true, true),
(4, 2, 20, 6, true, true),
(4, 2, 21, 7, true, true),
(4, 2, 16, 8, true, true),
(4, 2, 17, 9, true, true),
(4, 2, 18, 10, true, true),
(4, 2, 19, 11, true, true),
(4, 2, 30, 12, true, true),

--MM-MA
(5, 2, 30, 1, true, true),
(5, 2, 22, 2, true, true),
(5, 2, 23, 3, true, true),
(5, 2, 24, 4, true, true),
(5, 2, 25, 5, true, true),
(5, 2, 20, 6, true, true),
(5, 2, 21, 7, true, true),
(5, 2, 26, 8, true, true),
(5, 2, 27, 9, true, true),
(5, 2, 28, 10, true, true),
(5, 2, 29, 11, true, true),
(5, 2, 11, 12, true, true);
	