INSERT INTO ui.selector_trees 
(id, default_module_id, label, enabled, visible) VALUES 
(1, 901, 'LBL_SELECTOR_TREES_SINOPTICOS', true, true);

INSERT INTO ui.selector_tree_nodes 
(id, selector_tree_id, label, module_id, image, enabled, visible) VALUES 
(1, 1, 'LBL_SELECTOR_TREE_NODES_SINOPTICO_LA_CERRADURA', NULL, NULL, true, true),
(2, 1, 'LBL_SELECTOR_TREE_NODES_SINOPTICO_LA_CERRADURA_TUNEL', 901, 'menu/synoptic-menu.png', true, true);

INSERT INTO ui.selector_tree_hierarchies 
(selector_tree_id, node_parent_id, node_child_id, enabled, visible) VALUES 
(1, 1, 2, true, true);

