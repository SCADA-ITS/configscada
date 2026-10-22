INSERT INTO ui.trees(id, adapter_id, "label", enabled, visible) VALUES
(1, 8, 'LBL_EQUIPMENTS', true, true),
(2, 10, 'LBL_EQUIPMENT_HIERARCHIES', true, true);

INSERT INTO ui.tree_option_values(tree_id, tree_option_id, value) VALUES
(1, 1, 'img/tree/equipment.png'),
(1, 2, 'false'),
(1, 3, 'true'),
(1, 4, 'true'),
(1, 5, 'true'),
(1, 6, 'false'),
(2, 1, 'img/tree/equipment.png'),
(2, 2, 'false'),
(2, 3, 'true'),
(2, 4, 'true'),
(2, 5, 'true'),
(2, 6, 'false');

INSERT INTO ui.tree_params (id, tree_id, "name") VALUES
(1, 1, 'state_image');

INSERT INTO ui.tree_styles (id, name, description, enabled, visible) VALUES
(1, 'FONT COLOR', 'FONT COLOR', true, true),
(2, 'BACKGROUND COLOR', 'BACKGROUND COLOR', true, true),
(3, 'ICON', 'ICON', true, true);

INSERT INTO ui.tree_param_styles (id, tree_style_id, tree_param_id, "operator", value1, value2, func, resource) VALUES
(1, 3, 1, NULL, NULL, NULL, NULL, NULL);
