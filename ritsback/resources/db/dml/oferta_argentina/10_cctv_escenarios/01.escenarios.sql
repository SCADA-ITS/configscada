-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(1, 'Circunvalación', 'Circunvalación', 4, 3, true, true),
(2, 'Belén Escobar', 'Belén Escobar', 4, 2, true, true),
(3, 'Del Viso', 'Del Viso', 2, 3, true, true),
(4, 'Tigre', 'Tigre', 3, 2, true, true);

-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
-- Circunvalación
(1, 2, 1, 1, true, true),
(1, 2, 2, 2, true, true),
(1, 2, 3, 3, true, true),
(1, 2, 4, 4, true, true),
(1, 2, 5, 5, true, true),
(1, 2, 6, 6, true, true),
(1, 2, 7, 7, true, true),
(1, 2, 8, 8, true, true),
(1, 2, 9, 9, true, true),
(1, 2, 10, 10, true, true),
(1, 2, 11, 11, true, true),
(1, 2, 12, 12, true, true),

-- Belén Escobar
(2, 2, 45, 1, true, true),
(2, 2, 46, 2, true, true),
(2, 2, 47, 3, true, true),
(2, 2, 48, 4, true, true),
(2, 2, 49, 5, true, true),
(2, 2, 50, 6, true, true),
(2, 2, 51, 7, true, true),
(2, 2, 52, 8, true, true),

-- Del Viso
(3, 2, 18, 1, true, true),
(3, 2, 19, 2, true, true),
(3, 2, 20, 3, true, true),
(3, 2, 21, 4, true, true),
(3, 2, 22, 5, true, true),
(3, 2, 23, 6, true, true),

-- Tigre
(4, 2, 38, 1, true, true),
(4, 2, 39, 2, true, true),
(4, 2, 40, 3, true, true),
(4, 2, 41, 4, true, true),
(4, 2, 42, 5, true, true),
(4, 2, 43, 6, true, true);


