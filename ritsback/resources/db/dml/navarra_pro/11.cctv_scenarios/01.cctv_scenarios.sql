-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(1, 'IMAGENES', 'IMAGENES', 4, 4, true, true),
(2, 'FLUJO VIDEO', 'FLUJO VIDEO', 3, 3, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--IMAGENES
(1, 2, 1, 1, true, true),
(1, 2, 2, 2, true, true),
(1, 2, 3, 3, true, true),
(1, 2, 4, 4, true, true),
(1, 2, 5, 5, true, true),
(1, 2, 6, 6, true, true),
(1, 2, 7, 7, true, true),
(1, 2, 8, 8, true, true),
(1, 2, 11, 9, true, true),
(1, 2, 12, 10, true, true),
(1, 2, 13, 11, true, true),
(1, 2, 14, 12, true, true),
(1, 2, 15, 13, true, true),
(1, 2, 17, 14, true, true),
(1, 2, 18, 15, true, true),
(1, 2, 19, 16, true, true),

--FLUJO VIDEO
(2, 2, 9, 1, true, true),
(2, 2, 10, 2, true, true),
(2, 2, 16, 3, true, true),
(2, 2, 20, 4, true, true),
(2, 2, 21, 5, true, true),
(2, 2, 22, 6, true, true),
(2, 2, 23, 7, true, true),
(2, 2, 24, 8, true, true),
(2, 2, 25, 9, true, true),
(2, 2, 26, 10, true, true),
--(2, 2, 27, 11, true, true),
(2, 2, 28, 12, true, true);




	
