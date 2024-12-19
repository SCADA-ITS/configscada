INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1, 'LBL_SELECTOR_TREE_EXTERNAL_INCIDENTS', 1950, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(2, 1, 'LBL_EXT_ENTITY_C4', 'ext_entity_type/ext_entity_C4.png', 1950, true, true),
(3, 1, 'LBL_EXT_ENTITY_112', 'ext_entity_type/ext_entity_112.png', 1951, true, true);

