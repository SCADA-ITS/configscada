INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1, 'LBL_SINOPTICOS', 920, true, true);


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