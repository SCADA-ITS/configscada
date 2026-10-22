-- 
-- Table: config.cctv_scenarios
--

INSERT INTO conf.cctv_scenarios (scenario_id, alias, description, n_rows, n_columns, enabled, visible) VALUES
(14, 'COR-CA', 'COR-CA', 3, 5, true, true),
(15, 'COR-MA', 'COR-MA', 3, 5, true, true);
-- 
-- Table: config.cctv_scenario_elements
--
INSERT INTO conf.cctv_scenario_elements (scenario_id, element_type_id, element_id, n_order, enabled, visible) VALUES
--COR-CA
(14, 2, 140, 1, true, true),
(14, 2, 115, 2, true, true),
(14, 2, 116, 3, true, true),
(14, 2, 117, 4, true, true),
(14, 2, 118, 5, true, true),
(14, 2, 119, 6, true, true),
(14, 2, 120, 7, true, true),
(14, 2, 121, 8, true, true),
(14, 2, 122, 9, true, true),
(14, 2, 123, 10, true, true),
(14, 2, 124, 11, true, true),
(14, 2, 125, 12, true, true),
(14, 2, 126, 13, true, true),
(14, 2, 142, 14, true, true),
(14, 2, 144, 15, true, true),
(14, 2, 146, 16, true, true),

--COR-MA
(15, 2, 139, 1, true, true),
(15, 2, 127, 2, true, true),
(15, 2, 128, 3, true, true),
(15, 2, 129, 4, true, true),
(15, 2, 130, 5, true, true),
(15, 2, 131, 6, true, true),
(15, 2, 132, 7, true, true),
(15, 2, 133, 8, true, true),
(15, 2, 134, 9, true, true),
(15, 2, 135, 10, true, true),
(15, 2, 136, 11, true, true),
(15, 2, 137, 12, true, true),
(15, 2, 138, 13, true, true),
(15, 2, 141, 14, true, true),
(15, 2, 143, 15, true, true),
(15, 2, 145, 16, true, true);

	