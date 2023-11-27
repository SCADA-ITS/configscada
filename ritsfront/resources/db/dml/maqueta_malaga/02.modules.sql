INSERT INTO ui.modules 
(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES 
(901, 4, 'TREE_SINOPTICO', 'TREE_SINOPTICO', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, true);

UPDATE ui.modules SET visible=false, autoload=false WHERE id=1;


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
(901, 12, 'LBL_TREE_SINOPTICO'),
(901, 14, 'true');

