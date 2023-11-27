
-- 
-- Table: config.cctv_scenario_nodes
--
INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, management_area_id, enabled, visible) VALUES
-- Ausol I —> Túneles de Calahonda, La Quinta, Montemayor, Nagueles, Rio Verde y Sta. Maria I
(14, 'CALAHONDA', 'CALAHONDA', NULL, 1, true, true),
		(15, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 9, 1, true, true),
		(16, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 10, 1, true, true),
(11, 'LA QUINTA', 'LA QUINTA', NULL, 1, true, true),
		(12, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 7, 1, true, true),
		(13, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 8, 1, true, true),
(6, 'MONTEMAYOR', 'MONTEMAYOR', NULL, 1, true, true),
		(7, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 4, 1, true, true),
		(8, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 5, 1, true, true),
(27, 'NAGUELES', 'NAGUELES', NULL, 1, true, true),
		(28, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 18, 1, true, true),
		(29, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 19, 1, true, true),
(2, 'RIO VERDE', 'RIO VERDE', NULL, 1, true, true),
		(3, 'MOSAICO', 'MOSAICO', 1, 1, true, true),
		(4, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 2, 1, true, true),
		(5, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 3, 1, true, true),
(9, 'STA. MARIA I', 'STA. MARIA I', NULL, 1, true, true),
			(10, 'MOSAICO', 'MOSAICO', 6, 1, true, true),

-- Ausol II —> Tuneles de Casares, Corominas, Estepona y Sta.Maria II
(17, 'CASARES', 'CASARES', NULL, 2, true, true),
		(18, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 11, 2, true, true),
		(19, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 12, 2, true, true),
		(20, 'Galeria', 'Galeria', 13, 2, true, true),
(21, 'COROMINAS', 'COROMINAS', NULL, 2, true, true),
		(22, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 14, 2, true, true),
		(23, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 15, 2, true, true),
(25, 'ESTEPONA', 'ESTEPONA', NULL, 2, true, true),
		(26, 'MOSAICO', 'MOSAICO', 17, 2, true, true),
(30, 'STA. MARIA II', 'STA. MARIA II', NULL, 2, true, true),
		(31, 'Tubo sentido Cádiz', 'Tubo sentido Cádiz', 20, 2, true, true),
		(32, 'Tubo sentido Málaga', 'Tubo sentido Málaga', 21, 2, true, true);

INSERT INTO conf.cctv_scenario_nodes (node_id, alias, description, scenario_id, enabled, visible) VALUES
(1, 'TUNELES', 'TUNELES', NULL, true, true);


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
	
	
