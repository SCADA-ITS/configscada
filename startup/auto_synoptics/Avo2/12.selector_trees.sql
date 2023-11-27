INSERT INTO ui.selector_trees 
(id, default_module_id, label) VALUES 
(1, 920, 'LBL_SINOPTICOS');

INSERT INTO ui.selector_tree_nodes 
(id, selector_tree_id, label, module_id) VALUES 
(1, 1, 'LBL_TREE_MENU_CT', NULL),
(2, 1, 'LBL_TREE_CT_1', 901),
(3, 1, 'LBL_TREE_CT_2', 902),
(4, 1, 'LBL_TREE_CT_3', 903),
(5, 1, 'LBL_TREE_CT_4', 904),
(6, 1, 'LBL_TREE_CT_5', 905),
(7, 1, 'LBL_TREE_CT_6', 906),
(8, 1, 'LBL_TREE_CT_7', 907),
(9, 1, 'LBL_TREE_CT_8', 908),
(10, 1, 'LBL_TREE_MENU_PANELES_TARIFARIOS', NULL),
(11, 1, 'LBL_TREE_PANELES_TARIFARIOS', 909),
(12, 1, 'LBL_LBL_TREE_MENU_SUBSISTEMA_PCI', NULL),
(13, 1, 'LBL_LBL_TREE_ALJ_1', 910),
(14, 1, 'LBL_LBL_TREE_ALJ_2', 911),
(15, 1, 'LBL_LBL_TREE_ALJ_3', 912),
(16, 1, 'LBL_LBL_TREE_ALJ_4', 913),
(17, 1, 'LBL_LBL_TREE_S1', NULL),
(18, 1, 'LBL_LBL_TREE_S1_T1', 914);

INSERT INTO ui.selector_tree_hierarchies 
(selector_tree_id, node_parent_id, node_child_id) VALUES 
(1, 1, 2),
(1, 1, 3),
(1, 1, 4),
(1, 1, 5),
(1, 1, 6),
(1, 1, 7),
(1, 1, 8),
(1, 1, 9),
(1, 10, 11),
(1, 12, 13),
(1, 12, 14),
(1, 12, 15),
(1, 12, 16),
(1, 17, 18);

