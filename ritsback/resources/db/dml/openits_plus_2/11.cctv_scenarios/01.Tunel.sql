-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(1, 'MOSAICO', 'MOSAICO', 2, 5, true, true),
(2, 'Norte', 'Norte', 2, 3, true, true),
(3, 'Sur', 'Sur', 2, 3, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--MOSAICO
(1, 2, 3, 1, true, true),
(1, 2, 4, 2, true, true),
(1, 2, 5, 3, true, true),
(1, 2, 6, 4, true, true),
(1, 2, 7, 5, true, true),
(1, 2, 8, 6, true, true),
(1, 2, 9, 7, true, true),
(1, 2, 10, 8, true, true),
(1, 2, 11, 9, true, true),
(1, 2, 12, 10, true, true),

--NORTE
(2, 2, 2, 1, true, true),
(2, 2, 3, 2, true, true),
(2, 2, 4, 3, true, true),
(2, 2, 5, 4, true, true),
(2, 2, 6, 5, true, true),
(2, 2, 7, 6, true, true),

--SUR
(3, 2, 16, 1, true, true),
(3, 2, 17, 2, true, true),
(3, 2, 18, 3, true, true),
(3, 2, 19, 4, true, true),
(3, 2, 20, 5, true, true),
(3, 2, 21, 6, true, true);
	