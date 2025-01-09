INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1, 'LBL_SELECTOR_TREE_SINOPTICOS', 920, true, true);


INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, position, enabled, visible) VALUES
(1, 1, 'LBL_GRUPO', null, null, null, true, true),
(2, 1, 'LBL_GRUPO_1', null, null, null, true, true),
(3, 1, 'LBL_GRUPO_2', null, null, null, true, true),
(4, 1, 'LBL_GRUPO_1_1', null, null, null, true, true),
(5, 1, 'LBL_SYNOPTIC_NEW', null, 920, null, true, true),
(6, 1, 'LBL_AUDIT_LOG', null, 1006, null, true, true),
(7, 1, 'LBL_MENU_ITEM_EQUIPMENTS', null, 500, null, true, true),
(8, 1, 'LBL_MENU_ITEM_USERS', 'menu/user.png', 1000, null, true, true),
(9, 1, 'LBL_MENU_ITEM_ROLES', 'menu/role.png', 1001, null, true, true);


INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(1, 1, 2, true, true),
(1, 1, 3, true, true),
(1, 2, 4, true, true),
(1, 2, 5, true, true),
(1, 3, 6, true, true),
(1, 4, 7, true, true),
(1, 4, 8, true, true),
(1, 4, 9, true, true);



INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(2, 'LBL_SELECTOR_TREE_INCIDENTS_C4', 1927, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, position, enabled, visible) VALUES
(21, 2, 'LBL_SELECTOR_TREE_INCIDENTS_C4', null, null, null, true, true),
(22, 2, 'LBL_EXT_ENTITY_TYPE_C4', null, 1927, 1, true, true),
(23, 2, 'LBL_EXT_ENTITY_TYPE_C4_WORKS', null, 6000, 2, true, true),
(24, 2, 'LBL_EXT_ENTITY_TYPE_C4_WEATHER', null, 6001, 3, true, true),
(25, 2, 'LBL_EXT_ENTITY_TYPE_C4_ROAD', null, 6002, 4, true, true),
(26, 2, 'LBL_EXT_ENTITY_TYPE_112', null, 7000, 5, true, true),
(27, 2, 'LBL_EXT_ENTITY_TYPE_112_RESOURCES', null, 7001, 6, true, true),
(28, 2, 'LBL_EXT_ENTITY_TYPE_112_VEHICLES', null, 7002, 7, true, true),
(29, 2, 'LBL_EXT_ENTITY_WAZE_TRAFFIC_ALERT', null, 8000, 8, true, true),
(30, 2, 'LBL_EXT_ENTITY_WAZE_TRAFFIC_JAM', null, 8001, 9, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(2, 21, 22, true, true),
(2, 21, 23, true, true),
(2, 21, 24, true, true),
(2, 21, 25, true, true),
(2, 21, 26, true, true),
(2, 21, 27, true, true),
(2, 21, 28, true, true),
(2, 21, 29, true, true),
(2, 21, 30, true, true);

