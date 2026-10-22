
-- 
-- Table: config.cctv_scenario_nodes
--
INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, enabled, visible) VALUES
(1, 'TUNELES', 'TUNELES', NULL, true, true),
	(2, 'RIO VERDE', 'RIO VERDE', NULL, true, true),
		(3, 'MOSAICO', 'MOSAICO', 1, true, true),
		(4, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 2, true, true),
		(5, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 3, true, true),
	(6, 'MONTEMAYOR', 'MONTEMAYOR', NULL, true, true),
		(7, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 4, true, true),
		(8, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 5, true, true),
	(9, 'STA. MARIA I', 'STA. MARIA I', NULL, true, true),
		(10, 'MOSAICO', 'MOSAICO', 6, true, true),
	(11, 'LA QUINTA', 'LA QUINTA', NULL, true, true),
		(12, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 7, true, true),
		(13, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 8, true, true),
	(14, 'CALAHONDA', 'CALAHONDA', NULL, true, true),
		(15, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 9, true, true),
		(16, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 10, true, true),
	(17, 'CASARES', 'CASARES', NULL, true, true),
		(18, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 11, true, true),
		(19, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 12, true, true),
		(20, 'Galeria', 'Galeria', 13, true, true),
	(21, 'COROMINAS', 'COROMINAS', NULL, true, true),
		(22, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 14, true, true),
		(23, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 15, true, true),
	(25, 'ESTEPONA', 'ESTEPONA', NULL, true, true),
		(26, 'MOSAICO', 'MOSAICO', 17, true, true),
	(27, 'NAGUELES', 'NAGUELES', NULL, true, true),
		(28, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 18, true, true),
		(29, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 19, true, true),
	(30, 'STA. MARIA II', 'STA. MARIA II', NULL, true, true),
		(31, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 20, true, true),
		(32, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 21, true, true);

-- 
-- Table: config.cctv_scenario_node_hierarchies
--
INSERT INTO conf.cctv_scenario_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(1, 2, true, true),
	(2, 3, true, true),
	(2, 4, true, true),
	(2, 5, true, true),
(1, 6, true, true),
	(6, 7, true, true),
	(6, 8, true, true),
(1, 9, true, true),
	(9, 10, true, true),
(1, 11, true, true),
	(11, 12, true, true),
	(11, 13, true, true),
(1, 14, true, true),
	(14, 15, true, true),
	(14, 16, true, true),
(1, 17, true, true),
	(17, 18, true, true),
	(17, 19, true, true),
	(17, 20, true, true),
(1, 21, true, true),
	(21, 22, true, true),
	(21, 23, true, true),
(1, 25, true, true),
	(25, 26, true, true),
(1, 27, true, true),
	(27, 28, true, true),
	(27, 29, true, true),
(1, 30, true, true),
	(30, 31, true, true),
	(30, 32, true, true);
	
	