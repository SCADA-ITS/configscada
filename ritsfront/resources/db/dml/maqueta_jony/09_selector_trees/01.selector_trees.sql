INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1, 'LBL_SELECTOR_TREE_SINOPTICOS', 600, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
-- METRO
(01, 1, 'LBL_SYNOPTIC_METRO', null, 600, true, true),

--CALAHONDA
(02, 1, 'LBL_TREE_CALAHONDA', null, null, true, true),
	(0201, 1, 'LBL_TREE_CT', null, null, true, true),
		(020101, 1, 'LBL_SYNOPTIC_CH_CT_PLANO', null, 601, true, true),
	(0202, 1, 'LBL_TREE_TUNNEL', null, null, true, true),
		(020201, 1, 'LBL_SYNOPTIC_CH_TUN_COMMS', null, 602, true, true),
		(020202, 1, 'LBL_SYNOPTIC_CH_TUN_TRAFFIC_CONTROL', null, 603, true, true),
		(020203, 1, 'LBL_SYNOPTIC_CH_TUN_ENERGY', null, 604, true, true),
		(020204, 1, 'LBL_SYNOPTIC_CH_TUN_GENERAL', null, 605, true, true),
		(020205, 1, 'LBL_SYNOPTIC_CH_TUN_ILLUMINATION', null, 606, true, true),
		(020206, 1, 'LBL_SYNOPTIC_CH_TUN_PCI', null, 607, true, true),
		(020207, 1, 'LBL_SYNOPTIC_CH_TUN_VENTILATION', null, 608, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
--CALAHONDA
(1, 02, 0201, true, true),
	(1, 0201, 020101, true, true),
(1, 02, 0202, true, true),
	(1, 0202, 020201, true, true),
	(1, 0202, 020202, true, true),
	(1, 0202, 020203, true, true),
	(1, 0202, 020204, true, true),
	(1, 0202, 020205, true, true),
	(1, 0202, 020206, true, true),
	(1, 0202, 020207, true, true);

