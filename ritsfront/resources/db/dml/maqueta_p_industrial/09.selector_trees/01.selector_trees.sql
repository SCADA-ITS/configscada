INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(02, 'LBL_SELECTOR_TREE_INCIDENTS_BACKOFFICE', 6000, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(0201, 2, 'LBL_INCIDENTS', null, 6000, true, true);