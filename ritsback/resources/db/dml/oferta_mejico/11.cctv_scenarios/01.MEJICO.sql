-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(1, 'MOSAICO 1', 'MOSAICO 1', 1, 2, true, true),
(2, 'MOSAICO 2', 'MOSAICO 2', 1, 2, true, true),
(3, 'MOSAICO 3', 'MOSAICO 3', 1, 2, true, true),
(4, 'MOSAICO 4', 'MOSAICO 4', 1, 2, true, true);


-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--MOSAICO 1

(1, 2, 1, 1, true, true),
(1, 2, 2, 2, true, true),


--MOSAICO 2

(2, 2, 3, 1, true, true),
(2, 2, 4, 2, true, true),


--MOSAICO 3
(3, 2, 5, 1, true, true),
(3, 2, 6, 2, true, true),


--MOSAICO 4
(4, 2, 7, 1, true, true),
(4, 2, 8, 2, true, true);