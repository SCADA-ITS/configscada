INSERT INTO ui.modules 
(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES 
(901, 4, 'SELECTOR_TREE_NODES_SINOPTICO_LA_CERRADURA_TUNNEL', 'SELECTOR_TREE_NODES_SINOPTICO_LA_CERRADURA_TUNNEL', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);

INSERT INTO ui.module_values 
(module_id, module_param_id, value) VALUES 
(901, 4, '901');

INSERT INTO ui.window_values 
(module_id, window_param_id, value) VALUES 
(901, 4, '825'),
(901, 8, 'true'),
(901, 9, 'img/module/default.png'),
(901, 10, 'img/module/default.png'),
(901, 11, '#464646'),
(901, 12, 'LBL_SELECTOR_TREE_NODES_SINOPTICO_LA_CERRADURA_TUNNEL'),
(901, 14, 'true');

