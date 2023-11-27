-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(18, 'NAG-CA', 'NAG-CA', 3, 4, true, true),
(19, 'NAG-MA', 'NAG-MA', 3, 4, true, true);
-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--NAG-CA
(18, 2, 191, 1, true, true),
(18, 2, 153, 2, true, true),
(18, 2, 154, 3, true, true),
(18, 2, 155, 4, true, true),
(18, 2, 156, 5, true, true),
(18, 2, 170, 6, true, true),
(18, 2, 171, 7, true, true),
(18, 2, 157, 8, true, true),
(18, 2, 158, 9, true, true),
(18, 2, 159, 10, true, true),
(18, 2, 160, 11, true, true),
(18, 2, 161, 12, true, true),

--NAG-MA
(19, 2, 190, 1, true, true),
(19, 2, 162, 2, true, true),
(19, 2, 163, 3, true, true),
(19, 2, 164, 4, true, true),
(19, 2, 165, 5, true, true),
(19, 2, 171, 6, true, true),
(19, 2, 170, 7, true, true),
(19, 2, 166, 8, true, true),
(19, 2, 167, 9, true, true),
(19, 2, 168, 10, true, true),
(19, 2, 169, 11, true, true);



	