INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(7, 'LBL_SELECTOR_TREE_DGT_BACKOFFICE', 6050, true, true);


INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(700001, 7, 'LBL_DGT_DATOS', null, null, true, true),
	(70000101, 7, 'LBL_DGT_DATOS', null, 6050, true, true);



INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(7,  700001, 70000101, true, true);
