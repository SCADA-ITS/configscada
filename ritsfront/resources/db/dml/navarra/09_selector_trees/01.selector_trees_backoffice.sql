INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1, 'LBL_SELECTOR_TREE_INCIDENTS_C4', 6000, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(1, 1, 'LBL_SELECTOR_TREE_INCIDENTS_C4', null, 1950, true, true),
(2, 1, 'LBL_EXT_ENTITY_TYPE_C4_WORKS', null, 6000, true, true),
(3, 1, 'LBL_EXT_ENTITY_TYPE_C4_WEATHER', null, 6001, true, true),
(4, 1, 'LBL_EXT_ENTITY_TYPE_C4_ROAD', null, 6002, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(1, 1, 2, true, true),
(1, 1, 3, true, true),
(1, 1, 4, true, true);