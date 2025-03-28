INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1, 'LBL_SELECTOR_TREE_EXTERNAL_INCIDENTS', 1951, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, position, enabled, visible) VALUES
(0101, 1, 'LBL_EXT_ENTITY_TYPE_C4', 'ext_entity_type/ext_entity_C4.png', 1950, 1,true, true),
(0201, 1, 'LBL_EXT_ENTITY_TYPE_112', 'ext_entity_type/ext_entity_112.png', 1951, 2, true, true),
(03, 1, 'LBL_EXT_ENTITY_WAZE', 'ext_entity_type/waze.png', null, 3, true, true),
	(0301, 1, 'LBL_EXT_ENTITY_WAZE_TRAFFIC_ALERT', 'ext_entity_type/waze_accident.png', 8000, 4, true, true),
	(0302, 1, 'LBL_EXT_ENTITY_WAZE_TRAFFIC_JAM', 'ext_entity_type/waze_traffic_hazard.png', 8001, 5, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(1, 03, 0301, true, true),
(1, 03, 0302, true, true);
