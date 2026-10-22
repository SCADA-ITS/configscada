INSERT INTO ui.modules 
(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES 
(1920, 4, 'SELECTOR_TREE_NODES_SINOPTICOS_PLANO', 'SELECTOR_TREE_NODES_SINOPTICOS_PLANO', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, true),
(1921, 8, 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6000, 9, 'INCIDENTS GIP - incidents', 'Incidents GIP - incidents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);

INSERT INTO ui.module_values 
(module_id, module_param_id, value) VALUES 
(1920, 4, '1920'),
(1921, 9, '2'),
(6000, 13, '1'), (6000, 14, 'incidents');

INSERT INTO ui.window_values 
(module_id, window_param_id, value) VALUES 
(1920, 4, '825'),
(1920, 8, 'true'),
(1920, 9, 'img/module/default.png'),
(1920, 10, 'img/module/default.png'),
(1920, 11, '#464646'),
(1920, 12, 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_PLANO'),
(1920, 14, 'true'),

(6000, 4, '825'),
(6000, 5, '1600'),
(6000, 8, 'true'),
(6000, 10, 'img/module/default.png'),
(6000, 11, '#464646'),
(6000, 12, 'LBL_INCIDENTS'),
(6000, 14, 'true');
