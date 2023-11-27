-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(6, 'MOSAICO STA. MARIA I', 'MOSAICO STA. MARIA I', 2, 3, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES

--MOSAICO STA.MARIA I
(6, 2, 31, 1, true, true),
(6, 2, 32, 2, true, true),
(6, 2, 33, 3, true, true),
(6, 2, 36, 4, true, true),
(6, 2, 34, 5, true, true),
(6, 2, 35, 6, true, true);
	