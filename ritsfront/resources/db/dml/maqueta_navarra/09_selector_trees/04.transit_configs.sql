INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(04, 'Editor de tránsitos', 9000, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, position, enabled, visible) VALUES
(0401, 04, 'Lista blanca', null, 9000, 1,true, true),
(0402, 04, 'Lista negra', null, 9001, 2, true, true),
(0403, 04, 'Motivos de descarte', null, 9002, 2, true, true);
