INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES
(200, 3, 'MAP', 'MAP', 1,null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
(901, 4, 'SINOPTICOS_RUTA_MINA', 'SINOPTICOS_RUTA_MINA', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, true);


