INSERT INTO ui.module_types(id, "name", description, enabled, visible) VALUES
(1, 'Custom Window', 'Custom Window', true, true),
(2, 'Generic Grid Window', 'Generic Grid Window', true, true),
(3, 'Generic Map Window', 'Generic Map Window', true, true),
(4, 'Generic Synoptic Window', 'Generic Synoptic Window', true, true),
(5, 'Generic External App Window', 'Generic External App Window', true, true),
(6, 'Service', 'Service', true, true);

--INSERT INTO ui.module_params(id, module_type_id, "name", description, enabled, visible) VALUES
--(1, 1, 'hasMinButton', 'hasMinButton', true, true),
--(2, 1, 'hasMaxButton', 'hasMaxButton', true, true),
--(3, 1, 'isMaximized', 'isMaximized', true, true),
--(4, 1, 'heigth', 'heigth', true, true),
--(5, 1, 'width', 'width', true, true),
--(6, 1, 'x', 'x', true, true),
--(7, 1, 'y', 'y', true, true),
--(8, 1, 'allowMultipleInstances', 'allowMultipleInstances', true, true),
--(9, 1, 'image', 'image', true, true),
--(10, 1, 'icon', 'icon', true, true),
--(11, 1, 'color', 'color', true, true),
--(12, 1, 'label', 'label', true, true);

INSERT INTO ui.modules(id, module_type_id, "name", description, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible) VALUES
(5, 1, 'Equipments', 'Equipments module', './js/modules/equipments.js', 'getViewEquipments', 'assingEquipmentCallbacks', NULL, NULL, true, true),
(6, 1, 'Module 2', 'Module 2', NULL, NULL, NULL, NULL, NULL, true, true),
(7, 1, 'Module 3', 'Module 3', NULL, NULL, NULL, NULL, NULL, true, true),
(8, 1, 'Module 4', 'Module 4', NULL, NULL, NULL, NULL, NULL, true, true),
(9, 1, 'Module 5', 'Module 5', NULL, NULL, NULL, NULL, NULL, true, true),
(10, 1, 'Module 6', 'Module 6', NULL, NULL, NULL, NULL, NULL, true, true);

--INSERT INTO ui.module_values(id, module_id, module_param_id, value) VALUES
--(1, 5, 1, 'true'),
--(2, 5, 2, 'true'),
--(3, 5, 3, 'false'),
--(4, 5, 4, '720'),
--(5, 5, 5, '1280'),
--(7, 5, 7, ''),
--(6, 5, 6, ''),
--(9, 5, 8, 'true'),
--(10, 5, 9, 'img/module/module_default.png'),
--(11, 5, 10, 'img/module/module_default.png'),
--(12, 5, 11, '#464646'),
--(13, 5, 12, 'LBL_MODULE_DEFAULT');

INSERT INTO ui.window_params(id, module_type_id, "name", description, enabled, visible) VALUES
(1, 1, 'hasMinButton', 'hasMinButton', true, true),
(2, 1, 'hasMaxButton', 'hasMaxButton', true, true),
(3, 1, 'isMaximized', 'isMaximized', true, true),
(4, 1, 'heigth', 'heigth', true, true),
(5, 1, 'width', 'width', true, true),
(6, 1, 'x', 'x', true, true),
(7, 1, 'y', 'y', true, true),
(8, 1, 'allowMultipleInstances', 'allowMultipleInstances', true, true),
(9, 1, 'image', 'image', true, true),
(10, 1, 'icon', 'icon', true, true),
(11, 1, 'color', 'color', true, true),
(12, 1, 'label', 'label', true, true);

INSERT INTO ui.window_values(id, module_id, window_param_id, value) VALUES
(1, 5, 1, 'true'),
(2, 5, 2, 'true'),
(3, 5, 3, 'false'),
(4, 5, 4, '720'),
(5, 5, 5, '1400'),
(7, 5, 7, ''),
(6, 5, 6, ''),
(9, 5, 8, 'true'),
(10, 5, 9, 'img/module/module_default.png'),
(11, 5, 10, 'img/module/module_default.png'),
(12, 5, 11, '#464646'),
(13, 5, 12, 'LBL_MODULE_DEFAULT');