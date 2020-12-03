INSERT INTO ui.modules(id, module_type_id, "name", description, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible) VALUES
(1, 3, 'Main Map', 'Main Map', '../widgets/map.js', 'map', 'DEPRECATED', NULL, NULL, true, true),
(2, 1, 'Equipments', 'Equipments', '../widgets/equipments.js', 'equipments', 'DEPRECATED', NULL, NULL, true, true),
(3, 4, 'Synoptic lineal', 'Synoptic lineal', '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(4, 4, 'Synoptic global', 'Synoptic global', '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(5, 4, 'Synoptic CTS', 'Synoptic CTS', '../classes/Synoptic.js', '', 'DEPRECATED', NULL, NULL, true, true),
(6, 1, 'Plans', 'Plans', '../widgets/plans.js', 'plans', 'DEPRECATED', NULL, NULL, true, true),
(7, 1, 'Plan schedules', 'Plan schedules', '../widgets/planschedule.js', 'planschedules', 'DEPRECATED', NULL, NULL, true, true),
(8, 1, 'Equipments', 'Equipments module', '../modules/equipmentModule.js', 'getView', 'loadData', NULL, NULL, true, true);

INSERT INTO ui.module_values(module_id, module_param_id, value) VALUES
(3, 4, 'synopticTunnel'),
(4, 4, 'synopticGlobal'),
(5, 4, 'synopticCTs');

INSERT INTO ui.window_values(module_id, window_param_id, value) VALUES
(1, 8, 'false'),
(1, 9, 'img/module/equipments_default.png'),
(1, 10, 'img/module/equipments_default.png'),
(1, 11, '#464646'),
(1, 12, 'LBL_MAIN_MAP'),
(1, 14, 'true'),

(2, 8, 'true'),
(2, 9, 'img/module/equipments_default.png'),
(2, 10, 'img/module/equipments_default.png'),
(2, 11, '#464646'),
(2, 12, 'LBL_EQUIPMENTS'),
(2, 14, 'true'),

(3, 8, 'false'),
(3, 9, 'img/module/equipments_default.png'),
(3, 10, 'img/module/equipments_default.png'),
(3, 11, '#464646'),
(3, 12, 'LBL_SYNOPTIC_LINEAL'),
(3, 14, 'true'),

(4, 8, 'false'),
(4, 9, 'img/module/equipments_default.png'),
(4, 10, 'img/module/equipments_default.png'),
(4, 11, '#464646'),
(4, 12, 'LBL_SYNOPTIC_GLOBAL'),
(4, 14, 'true'),

(5, 8, 'false'),
(5, 9, 'img/module/equipments_default.png'),
(5, 10, 'img/module/equipments_default.png'),
(5, 11, '#464646'),
(5, 12, 'LBL_SYNOPTIC_GLOBAL'),
(5, 14, 'true'),

(6, 8, 'false'),
(6, 9, 'img/module/equipments_default.png'),
(6, 10, 'img/module/equipments_default.png'),
(6, 11, '#464646'),
(6, 12, 'LBL_PLANS'),
(6, 14, 'true'),

(7, 8, 'false'),
(7, 9, 'img/module/equipments_default.png'),
(7, 10, 'img/module/equipments_default.png'),
(7, 11, '#464646'),
(7, 12, 'LBL_PLAN_SCHEDULES'),
(7, 14, 'true'),

(8, 8, 'true'),
(8, 9, 'img/module/equipments_default.png'),
(8, 10, 'img/module/equipments_default.png'),
(8, 11, '#464646'),
(8, 12, 'LBL_EQUIPMENTS'),
(8, 14, 'true');
