INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(02, 'LBL_SELECTOR_TREE_INCIDENTS_BACKOFFICE', 6011, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(0201, 2, 'LBL_MASTER', null, null, true, true),
	(020101, 2, 'LBL_MUNICIPALITIES', null, 6001, true, true),
	(020102, 2, 'LBL_REQUESTING_ENTITIES', null, 6002, true, true),
	(020103, 2, 'LBL_VEHICLES', null, 6012, true, true),
		(02010301, 2, 'LBL_VEHICLE_TYPES', null, 6015, true, true),
		(02010302, 2, 'LBL_VEHICLE_DAMAGES', null, 6016, true, true),
		(02010303, 2, 'LBL_VEHICLE_MODELS', null, 6014, true, true),
		(02010304, 2, 'LBL_VEHICLE_BRANDS', null, 6013, true, true),
	(020104, 2, 'LBL_AFFECTION_DETAILS', null, 6017, true, true),
(0202, 2, 'LBL_INCIDENTS', null, 6011, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(2, 0201, 020101, true, true),
(2, 0201, 020102, true, true),
(2, 0201, 020103, true, true),
	(2, 020103, 02010301, true, true),
	(2, 020103, 02010302, true, true),
	(2, 020103, 02010303, true, true),
	(2, 020103, 02010304, true, true),
(2, 0201, 020104, true, true);