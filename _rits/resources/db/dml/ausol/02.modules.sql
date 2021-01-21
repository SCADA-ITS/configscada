INSERT INTO ui.view_types(id, name, description, enabled, visible) values
(1, 'tab', 'tab', true, true),
(2, 'window', 'window', true, true),
(3, 'custom', 'custom', true, true);

INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible) VALUES
(1, 4, 'Welcome screen', 'Welcome screen', 1, '../modules/welcomeScreenModule.js', 'getView', '', NULL, NULL, true, true),

(10, 6, 'User service', 'User service', null, '../services/userService.js', 'getView', 'loadData', NULL, NULL, true, true),
(11, 6, 'Alarm service', 'Alarm service', null, '../services/alarmService.js', 'getView', 'loadData', NULL, NULL, true, true),
(12, 6, 'Notification service', 'Notification service', null, '../services/notificationService.js', 'getView', 'loadData', NULL, NULL, true, true),
(13, 6, 'Time service', 'Time service', null, '../services/timeService.js', 'getView', 'loadData', NULL, NULL, true, true),

(100, 4, 'Lineal', 'Lineal', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(101, 4, 'Global', 'Global', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(102, 3, 'Mapa', 'Mapa', 1, '../classes/Map.js', 'map', 'DEPRECATED', NULL, NULL, true, true),
(103, 4, 'UNIFILAR CSA', 'UNIFILAR CSA', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(104, 4, 'UNIFILAR CSB', 'UNIFILAR CSB', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(105, 4, 'UNIFILAR CSV', 'UNIFILAR CSV', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(106, 4, 'UNIFILAR CGBT', 'UNIFILAR CGBT', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(900, 4, 'RIO VERDE', 'RIO VERDE', 1, '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),

(300, 1, 'Plans', 'Plans', 1, '../widgets/plans.js', 'plans', 'DEPRECATED', NULL, NULL, true, true),
(301, 1, 'Plan schedules', 'Plan schedules', 1, '../widgets/planschedule.js', 'planschedules', 'DEPRECATED', NULL, NULL, true, true),

(400, 1, 'Alarms', 'Alarms', 1, '../widgets/alarms.js', 'alarms', 'DEPRECATED', NULL, NULL, true, true),
(401, 1, 'CCTV', 'CCTV', 1, '../widgets/cctv.js', 'cctv', 'DEPRECATED', NULL, NULL, true, true),
(402, 5, 'IMD', 'IMD', 1, '../modules/externalApp.js', 'getView', NULL, NULL, NULL, true, true),
(405, 5, 'Vel. Media', 'Vel. Media', 1, '../modules/externalApp.js', 'getView', NULL, NULL, NULL, true, true),

(403, 5, 'Reports', 'Reports', 1, '../modules/externalApp.js', 'getView', NULL, NULL, NULL, true, true),
(404, 1, 'User manager', 'User manager', 1, '../widgets/usersmanager.js', 'usersmanager', 'DEPRECATED', NULL, NULL, true, true),

(500, 1, 'Equipments', 'Equipments module', 1, '../modules/equipmentModule.js', 'getView', 'loadData', NULL, NULL, true, true),

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

(10, 1, '1'),
(10, 2, null),
(11, 1, '2'),
(11, 2, '{"stats":{"rest":{"active_alarms":"generic_datas/GenericData:1","no_recognized_alarms":"generic_datas/GenericData:2","muted_alarms":"generic_datas/GenericData:3"},"ws":{"alarms": "/topic/alarm.>","active_alarms":"/topic/generic_data.GenericData:1","no_recognized_alarms":"/topic/generic_data.GenericData:2","muted_alarms":"/topic/generic_data.GenericData:3"}},"levels_notif":[3,4,5,6],"level_acustic":[6],"default_sound":"alarm.wav"}'),
(12, 1, '3'),
(12, 2, null),
(13, 1, '4'),
(13, 2, null),

(100, 4, 'synopticMetro'),
(101, 4, 'synopticGlobal'),
(102, 6, 'mapAusol'),
(103, 4, 'synopticUnifilarCsa'),
(104, 4, 'synopticUnifilarCsb'),
(105, 4, 'synopticUnifilarCsv'),
(106, 4, 'synopticUnifilarCGBT'),
(900, 4, 'synopticTunnel'),

(402, 5, 'http://192.168.88.200:3030/d/TYXiORfMk/imd-veh-h?orgId=1&refresh=30s'),
(405, 5, 'http://192.168.88.200:3030/d/FfFhOgfGk/velocidad-media-km-h?orgId=1&refresh=30s'),

(403, 5, 'http://192.168.88.163:8081/jasperserver?j_username=user&j_password=bitnami&userLocale=es_ES'),

(1000, 3, '1000'),
(1001, 3, '1001'),
(1002, 3, '1002');

INSERT INTO ui.window_values(module_id, window_param_id, value) VALUES
(1, 8, 'true'),
(1, 9, 'img/module/default.png'),
(1, 10, 'img/module/default.png'),
(1, 11, '#464646'),
(1, 12, 'LBL_WELCOME_SCREEN'),
(1, 14, 'true'),

(100, 8, 'true'),
(100, 9, 'img/module/default.png'),
(100, 10, 'img/module/default.png'),
(100, 11, '#464646'),
(100, 12, 'LBL_LINEAL'),
(100, 14, 'true'),

(101, 8, 'true'),
(101, 9, 'img/module/default.png'),
(101, 10, 'img/module/default.png'),
(101, 11, '#464646'),
(101, 12, 'LBL_GLOBAL'),
(101, 14, 'true'),

(102, 8, 'true'),
(102, 9, 'img/module/default.png'),
(102, 10, 'img/module/default.png'),
(102, 11, '#464646'),
(102, 12, 'LBL_MAPA'),
(102, 14, 'true'),

(103, 8, 'true'),
(103, 9, 'img/module/default.png'),
(103, 10, 'img/module/default.png'),
(103, 11, '#464646'),
(103, 12, 'LBL_UNIFILAR_CSA'),
(103, 14, 'true'),

(104, 8, 'true'),
(104, 9, 'img/module/default.png'),
(104, 10, 'img/module/default.png'),
(104, 11, '#464646'),
(104, 12, 'LBL_UNIFILAR_CSB'),
(104, 14, 'true'),

(105, 8, 'true'),
(105, 9, 'img/module/default.png'),
(105, 10, 'img/module/default.png'),
(105, 11, '#464646'),
(105, 12, 'LBL_UNIFILAR_CSV'),
(105, 14, 'true'),

(106, 8, 'true'),
(106, 9, 'img/module/default.png'),
(106, 10, 'img/module/default.png'),
(106, 11, '#464646'),
(106, 12, 'LBL_UNIFILAR_CGBT'),
(106, 14, 'true'),

(900, 8, 'true'),
(900, 9, 'img/module/default.png'),
(900, 10, 'img/module/default.png'),
(900, 11, '#464646'),
(900, 12, 'LBL_RIO_VERDE'),
(900, 14, 'true'),

(300, 8, 'false'),
(300, 9, 'img/module/default.png'),
(300, 10, 'img/module/default.png'),
(300, 11, '#464646'),
(300, 12, 'LBL_PLANS'),
(300, 14, 'true'),

(301, 8, 'true'),
(301, 9, 'img/module/default.png'),
(301, 10, 'img/module/default.png'),
(301, 11, '#464646'),
(301, 12, 'LBL_PLAN_SCHEDULES'),
(301, 14, 'true'),

(400, 8, 'true'),
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

(402, 8, 'true'),
(402, 9, 'img/module/default.png'),
(402, 10, 'img/module/default.png'),
(402, 11, '#464646'),
(402, 12, 'LBL_IMD_VEH_H'),
(402, 14, 'true'),

(405, 8, 'true'),
(405, 9, 'img/module/default.png'),
(405, 10, 'img/module/default.png'),
(405, 11, '#464646'),
(405, 12, 'LBL_VEL_MED_KM_H'),
(405, 14, 'true'),

(403, 8, 'true'),
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

