INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1, 'LBL_SELECTOR_TREE_EXTERNAL_INCIDENTS', 1951, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, position, enabled, visible) VALUES
(01, 1, 'LBL_EXT_ENTITY_C4', 'ext_entity_type/ext_entity_C4.png', null, 1,true, true),
	(0101, 1, 'LBL_EXT_ENTITY_C4_INCIDENTS', null, 1950, 2,true, true),
(02, 1, 'LBL_EXT_ENTITY_112', 'ext_entity_type/ext_entity_112.png', null, 3, true, true),
	(0201, 1, 'LBL_EXT_ENTITY_112_INCIDENTS', null, 1951, 4, true, true),
(03, 1, 'LBL_EXT_ENTITY_WAZE', 'ext_entity_type/waze.png', null, 7, true, true),
	(0301, 1, 'LBL_EXT_ENTITY_WAZE_TRAFFIC_ALERT', null, 8000, 8, true, true),
	(0302, 1, 'LBL_EXT_ENTITY_WAZE_TRAFFIC_JAM', null, 8001, 9, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(1, 01, 0101, true, true),
(1, 02, 0201, true, true),
(1, 03, 0301, true, true),
(1, 03, 0302, true, true);
