INSERT INTO ui.modules 
(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES 
(970, 3, 'Map', 'Map', 1, null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, true);





INSERT INTO ui.module_values 
(module_id, module_param_id, value) VALUES 
(970, 6, '1');


INSERT INTO ui.window_values 
(module_id, window_param_id, value) VALUES 
(970, 8, 'true'),
(970, 12, 'LBL_MENU_ITEM_MAP');


