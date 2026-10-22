INSERT INTO ui.selector_trees 
(id, default_module_id, label) VALUES 
(1, 920, 'LBL_SINOPTICOS');

INSERT INTO ui.selector_tree_nodes 
(id, selector_tree_id, label, module_id) VALUES 
(1, 1, 'LBL_SELECTOR_TREE_NODES_SINOPTICOS', NULL),
(2, 1, 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_PLANO', 1920);

INSERT INTO ui.selector_tree_hierarchies 
(selector_tree_id, node_parent_id, node_child_id) VALUES 
(1, 1, 2);

