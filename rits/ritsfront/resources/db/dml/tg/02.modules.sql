INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES
(301, 1, 'Plan schedules', 'Plan schedules', 1, 'SystemPermission:4', 'widgets/planschedule.js', 'planschedules', 'DEPRECATED', NULL, NULL, true, true, NULL),
(401, 1, 'CCTV', 'CCTV', 1, null, 'widgets/cctv.js', 'cctv', 'DEPRECATED', NULL, NULL, true, true, NULL),
(970, 3, 'Map', 'Map', 1, null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(910, 1, 'EquipmentForm', 'Equipment form', 2, null, 'modules/equipmentFormModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);

INSERT INTO ui.module_values(module_id, module_param_id, value) VALUES
(403, 5, 'http://192.168.88.170:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=en_NZ'),
(970, 6, '1');

INSERT INTO ui.module_actions(id, module_id, name, description, system_permission_id) VALUES
(91001, 910, 'view equipment', 'view equipment', NULL);

INSERT INTO ui.window_values(module_id, window_param_id, value) VALUES
(301, 8, 'true'),
(301, 9, 'img/module/default.png'),
(301, 10, 'img/module/default.png'),
(301, 11, '#464646'),
(301, 12, 'LBL_PLAN_SCHEDULES'),
(301, 14, 'true'),

(401, 8, 'false'),
(401, 9, 'img/module/default.png'),
(401, 10, 'img/module/default.png'),
(401, 11, '#464646'),
(401, 12, 'LBL_CCTV'),
(401, 14, 'true'),

(970, 4, '825'),
(970, 5, '1600'),
(970, 8, 'true'),
(970, 9, 'img/module/default.png'),
(970, 10, 'img/module/default.png'),
(970, 11, '#464646'),
(970, 12, 'LBL_MAP'),
(970, 14, 'true');
