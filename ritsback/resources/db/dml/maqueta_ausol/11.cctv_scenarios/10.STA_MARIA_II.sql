-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(20, 'STM2-CA', 'STM2-CA', 3, 4, true, true),
(21, 'STM2-MA', 'STM2-MA', 3, 4, true, true);
-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--STM2-CA
(20, 2, 173, 1, true, true),
(20, 2, 174, 2, true, true),
(20, 2, 175, 3, true, true),
(20, 2, 176, 4, true, true),
(20, 2, 177, 5, true, true),
(20, 2, 188, 6, true, true),
(20, 2, 189, 7, true, true),
(20, 2, 178, 8, true, true),
(20, 2, 179, 9, true, true),
(20, 2, 180, 10, true, true),

--STM2-MA
(21, 2, 172, 1, true, true),
(21, 2, 181, 2, true, true),
(21, 2, 182, 3, true, true),
(21, 2, 183, 4, true, true),
(21, 2, 189, 5, true, true),
(21, 2, 188, 6, true, true),
(21, 2, 184, 7, true, true),
(21, 2, 185, 8, true, true),
(21, 2, 186, 9, true, true),
(21, 2, 187, 10, true, true);



	