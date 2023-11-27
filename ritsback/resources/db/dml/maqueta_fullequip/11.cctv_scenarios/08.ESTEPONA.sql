-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(17, 'MOSAICO ESTEPONA', 'MOSAICO ESTEPONA', 2, 3, true, true);
-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--MOSAICO ESTEPONA
(17, 2, 147, 1, true, true),
(17, 2, 149, 2, true, true),
(17, 2, 150, 3, true, true),
(17, 2, 148, 4, true, true),
(17, 2, 151, 5, true, true),
(17, 2, 152, 6, true, true);


	