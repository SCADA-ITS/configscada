-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(1, 'MOSAICO RIO VERDE', 'MOSAICO RIO VERDE', 2, 5, true, true),
(2, 'RV-CA', 'RV-CA', 2, 3, true, true),
(3, 'RV-MA', 'RV-MA', 2, 3, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--MOSAICO RIO VERDE
(1, 2, 10, 1, true, true),
(1, 2, 1, 2, true, true),
(1, 2, 2, 3, true, true),
(1, 2, 3, 4, true, true),
(1, 2, 4, 5, true, true),
(1, 2, 5, 6, true, true),
(1, 2, 6, 7, true, true),
(1, 2, 7, 8, true, true),
(1, 2, 8, 9, true, true),
(1, 2, 9, 10, true, true),

--RV-CA
(2, 2, 10, 1, true, true),
(2, 2, 1, 2, true, true),
(2, 2, 2, 3, true, true),
(2, 2, 3, 4, true, true),
(2, 2, 4, 5, true, true),
(2, 2, 5, 6, true, true),

--RV-MA
(3, 2, 5, 1, true, true),
(3, 2, 6, 2, true, true),
(3, 2, 7, 3, true, true),
(3, 2, 8, 4, true, true),
(3, 2, 9, 5, true, true),
(3, 2, 10, 6, true, true);
	