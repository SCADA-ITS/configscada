-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(1, 'ESCENARIO 1', 'ESCENARIO 1', 2, 2, true, true),
(2, 'ESCENARIO 2', 'ESCENARIO 2', 2, 2, true, true),
(3, 'ESCENARIO 3', 'ESCENARIO 3', 2, 2, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--ESCENARIO 1
(1, 2, 1, 1, true, true),
(1, 2, 2, 2, true, true),
(1, 2, 3, 3, true, true),
(1, 2, 4, 4, true, true),

--ESCENARIO 2
(2, 2, 5, 1, true, true),
(2, 2, 6, 2, true, true),
(2, 2, 7, 3, true, true),
(2, 2, 8, 4, true, true),

--ESCENARIO 3
(3, 2, 9, 1, true, true),
(3, 2, 10, 2, true, true),
(3, 2, 11, 3, true, true),
(3, 2, 12, 4, true, true);




	
