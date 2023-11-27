INSERT INTO ui.view_types(id, name, description, enabled, visible) values
(1, 'tab', 'tab', true, true),
(2, 'window', 'window', true, true),
(3, 'custom', 'custom', true, true);

INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible) VALUES
(100, 3, 'Main Map', 'Main Map', 1, '../widgets/map.js', 'map', 'DEPRECATED', NULL, NULL, true, true),
(101, 4, 'Synoptic lineal', 'Synoptic lineal', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(102, 4, 'Synoptic global', 'Synoptic global', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(103, 4, 'Synoptic CTS', 'Synoptic CTS', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(104, 4, 'Synoptic CSA', 'Synoptic CSA', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(105, 4, 'Synoptic CSB', 'Synoptic CSB', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(106, 4, 'Synoptic CSV', 'Synoptic CSV', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),

(200, 1, 'Equipments', 'Equipments', 1, '../widgets/equipments.js', 'equipments()', 'DEPRECATED', NULL, NULL, true, true),
(201, 1, 'Users', 'User', 1, '../widgets/users.js', 'users()', 'DEPRECATED', NULL, NULL, true, true),
(202, 1, 'Roles', 'Roles', 1, '../widgets/roles.js', 'roles()', 'DEPRECATED', NULL, NULL, true, true),
(203, 1, 'Alarm Configs', 'Alarm Configs', 1, '../widgets/alarmconfigs.js', 'alarmconfigs()', 'DEPRECATED', NULL, NULL, true, true),

(300, 1, 'Plans', 'Plans', 1, '../widgets/plans.js', 'plans()', 'DEPRECATED', NULL, NULL, true, true),
(301, 1, 'Plan schedules', 'Plan schedules', 1, '../widgets/planschedule.js', 'planschedules()', 'DEPRECATED', NULL, NULL, true, true),

(400, 1, 'Alarms', 'Alarms', 1, '../widgets/alarms.js', 'alarms()', 'DEPRECATED', NULL, NULL, true, true),
(401, 1, 'CCTV', 'CCTV', 1, '../widgets/cctv.js', 'cctv()', 'DEPRECATED', NULL, NULL, true, true),
(402, 5, 'Control panel', 'Control panel', 1, '../modules/externalApp.js', 'getView', NULL, NULL, NULL, true, true),
(403, 5, 'Reports', 'Reports', 1, '../modules/externalApp.js', 'getView', NULL, NULL, NULL, true, true),
(404, 1, 'User manager', 'User manager', 1, '../widgets/usersmanager.js', 'usersmanager()', 'DEPRECATED', NULL, NULL, true, true),

(500, 1, 'Equipments', 'Equipments module', 2, '../modules/equipmentModule.js', 'getView', 'loadData', NULL, NULL, true, true),

(1000, 2, 'Users', 'Users', 1, '../modules/genericGridModule.js', 'getView', 'loadData', NULL, NULL, true, true),
(1001, 2, 'Roles', 'Roles', 1, '../modules/genericGridModule.js', 'getView', 'loadData', NULL, NULL, true, true),
(1002, 2, 'Alarm config', 'Alarm config', 1, '../modules/genericGridModule.js', 'getView', 'loadData', NULL, NULL, true, true);

INSERT INTO ui.module_actions(id, module_id, name, description) VALUES
(50001, 500, 'view equipment', 'view equipment'),
(100001, 1000, 'add', 'add new user'),
(100002, 1000, 'edit', 'edit user'),
(100003, 1000, 'remove', 'remove users'),
(100101, 1001, 'add', 'add new role'),
(100102, 1001, 'edit', 'edit role'),
(100103, 1001, 'remove', 'remove roles');

INSERT INTO ui.module_values(module_id, module_param_id, value) VALUES
(101, 4, 'synopticTunnel'),
(102, 4, 'synopticGlobal'),
(103, 4, 'synopticCTs'),
(402, 5, 'http://192.168.88.200:3030/d/qQFlxpaZz/detectors?orgId=1&refresh=5s'),
(403, 5, 'http://192.168.88.163:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=es_ES'),

(1000, 3, '1000'),
(1001, 3, '1001'),
(1002, 3, '1002');

INSERT INTO ui.window_values(module_id, window_param_id, value) VALUES
(100, 8, 'false'),
(100, 9, 'img/module/default.png'),
(100, 10, 'img/module/default.png'),
(100, 11, '#464646'),
(100, 12, 'LBL_MAIN_MAP'),
(100, 14, 'true'),

(101, 8, 'false'),
(101, 9, 'img/module/default.png'),
(101, 10, 'img/module/default.png'),
(101, 11, '#464646'),
(101, 12, 'LBL_SYNOPTIC_LINEAL'),
(101, 14, 'true'),

(102, 8, 'false'),
(102, 9, 'img/module/default.png'),
(102, 10, 'img/module/default.png'),
(102, 11, '#464646'),
(102, 12, 'LBL_SYNOPTIC_GLOBAL'),
(102, 14, 'true'),

(103, 8, 'false'),
(103, 9, 'img/module/default.png'),
(103, 10, 'img/module/default.png'),
(103, 11, '#464646'),
(103, 12, 'LBL_SYNOPTIC_CT_ITS'),
(103, 14, 'true'),

(104, 8, 'false'),
(104, 9, 'img/module/default.png'),
(104, 10, 'img/module/default.png'),
(104, 11, '#464646'),
(104, 12, 'LBL_SYNOPTIC_CSA'),
(104, 14, 'true'),

(105, 8, 'false'),
(105, 9, 'img/module/default.png'),
(105, 10, 'img/module/default.png'),
(105, 11, '#464646'),
(105, 12, 'LBL_SYNOPTIC_CSB'),
(105, 14, 'true'),

(106, 8, 'false'),
(106, 9, 'img/module/default.png'),
(106, 10, 'img/module/default.png'),
(106, 11, '#464646'),
(106, 12, 'LBL_SYNOPTIC_CSV'),
(106, 14, 'true'),

(200, 8, 'true'),
(200, 9, 'img/module/default.png'),
(200, 10, 'img/module/default.png'),
(200, 11, '#464646'),
(200, 12, 'LBL_EQUIPMENTS'),
(200, 14, 'true'),

(201, 8, 'true'),
(201, 9, 'img/module/default.png'),
(201, 10, 'img/module/default.png'),
(201, 11, '#464646'),
(201, 12, 'LBL_USERS'),
(201, 14, 'true'),

(202, 8, 'true'),
(202, 9, 'img/module/default.png'),
(202, 10, 'img/module/default.png'),
(202, 11, '#464646'),
(202, 12, 'LBL_ROLES'),
(202, 14, 'true'),

(203, 8, 'true'),
(203, 9, 'img/module/default.png'),
(203, 10, 'img/module/default.png'),
(203, 11, '#464646'),
(203, 12, 'LBL_ALARM_CONFIGS'),
(203, 14, 'true'),

(300, 8, 'false'),
(300, 9, 'img/module/default.png'),
(300, 10, 'img/module/default.png'),
(300, 11, '#464646'),
(300, 12, 'LBL_PLANS'),
(300, 14, 'true'),

(301, 8, 'false'),
(301, 9, 'img/module/default.png'),
(301, 10, 'img/module/default.png'),
(301, 11, '#464646'),
(301, 12, 'LBL_PLAN_SCHEDULES'),
(301, 14, 'true'),

(400, 8, 'false'),
(400, 9, 'img/module/default.png'),
(400, 10, 'img/module/default.png'),
(400, 11, '#464646'),
(400, 12, 'LBL_ALARMS'),
(400, 14, 'true'),

(401, 8, 'false'),
(401, 9, 'img/module/default.png'),
(401, 10, 'img/module/default.png'),
(401, 11, '#464646'),
(401, 12, 'LBL_CCTV'),
(401, 14, 'true'),

(402, 8, 'false'),
(402, 9, 'img/module/default.png'),
(402, 10, 'img/module/default.png'),
(402, 11, '#464646'),
(402, 12, 'LBL_CONTROL_PANEL'),
(402, 14, 'true'),

(403, 8, 'false'),
(403, 9, 'img/module/default.png'),
(403, 10, 'img/module/default.png'),
(403, 11, '#464646'),
(403, 12, 'LBL_REPORTS'),
(403, 14, 'true'),

(404, 8, 'false'),
(404, 9, 'img/module/default.png'),
(404, 10, 'img/module/default.png'),
(404, 11, '#464646'),
(404, 12, 'LBL_USER_MANAGER'),
(404, 14, 'true'),

(500, 8, 'true'),
(500, 9, 'img/module/default.png'),
(500, 10, 'img/module/default.png'),
(500, 11, '#464646'),
(500, 12, 'LBL_EQUIPMENTS'),
(500, 14, 'true'),

(1000, 8, 'true'),
(1000, 9, 'img/module/user.png'),
(1000, 10, 'img/module/user.png'),
(1000, 11, '#464646'),
(1000, 12, 'LBL_USERS'),
(1000, 14, 'true'),

(1001, 8, 'true'),
(1001, 9, 'img/module/role.png'),
(1001, 10, 'img/module/role.png'),
(1001, 11, '#464646'),
(1001, 12, 'LBL_ROLES'),
(1001, 14, 'true'),

(1002, 8, 'true'),
(1002, 9, 'img/module/alarmConfig.png'),
(1002, 10, 'img/module/alarmConfig.png'),
(1002, 11, '#464646'),
(1002, 12, 'LBL_ALARM_CONFIG'),
(1002, 14, 'true');

