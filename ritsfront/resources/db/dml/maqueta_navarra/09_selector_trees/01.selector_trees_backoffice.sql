INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1, 'LBL_SELECTOR_TREE_EXTERNAL_INCIDENTS', 1950, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(2, 1, 'LBL_EXT_ENTITY_C4', null, 1950, true, true),
(3, 1, 'LBL_EXT_ENTITY_C4', null, 1951, true, true);


