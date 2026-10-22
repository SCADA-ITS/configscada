-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(11, 'CAS-CA', 'CAS-CA', 3, 5, true, true),
(12, 'CAS-MA', 'CAS-MA', 3, 5, true, true),
(13, 'CAS-GAL', 'CAS-GAL', 2, 2, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--CAS-CA
(11, 2, 96, 1, true, true),
(11, 2, 82, 2, true, true),
(11, 2, 83, 3, true, true),
(11, 2, 84, 4, true, true),
(11, 2, 85, 5, true, true),
(11, 2, 86, 6, true, true),
(11, 2, 87, 7, true, true),
(11, 2, 88, 8, true, true),
(11, 2, 89, 9, true, true),
(11, 2, 90, 10, true, true),
(11, 2, 91, 11, true, true),
(11, 2, 92, 12, true, true),
(11, 2, 93, 13, true, true),
(11, 2, 94, 14, true, true),
(11, 2, 95, 15, true, true),

--CAS-MA
(12, 2, 81, 1, true, true),
(12, 2, 97, 2, true, true),
(12, 2, 98, 3, true, true),
(12, 2, 99, 4, true, true),
(12, 2, 100, 5, true, true),
(12, 2, 101, 6, true, true),
(12, 2, 102, 7, true, true),
(12, 2, 103, 8, true, true),
(12, 2, 104, 9, true, true),
(12, 2, 105, 10, true, true),
(12, 2, 106, 11, true, true),
(12, 2, 107, 12, true, true),
(12, 2, 108, 13, true, true),
(12, 2, 109, 14, true, true),
(12, 2, 110, 15, true, true),

--CAS-GA
(13, 2, 111, 1, true, true),
(13, 2, 112, 2, true, true),
(13, 2, 113, 3, true, true),
(13, 2, 114, 4, true, true);



	