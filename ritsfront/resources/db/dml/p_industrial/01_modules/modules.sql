INSERT INTO ui.modules 
(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES 
(1920, 4, 'SELECTOR_TREE_NODES_SINOPTICOS_PLANO', 'SELECTOR_TREE_NODES_SINOPTICOS_PLANO', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, true),
(1921, 8, 'Selector Tree Incidencias', 'Selector Tree Incidencias', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1922, 8, 'Selector Tree Reportes', 'Selector Tree Reportes', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(6000, 9, 'INCIDENTS GIP - incidents', 'Incidents GIP - incidents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6001, 9, 'INCIDENTS GIP - vehicles', 'Incidents GIP - vehicles', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6002, 9, 'INCIDENTS GIP - participants', 'Incidents GIP - participants', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6003, 9, 'INCIDENTS GIP - closures', 'Incidents GIP - closures', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6004, 9, 'INCIDENTS GIP - event', 'Incidents GIP - event', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6005, 9, 'INCIDENTS GIP - side', 'Incidents GIP - side', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6006, 9, 'INCIDENTS GIP - stretch', 'Incidents GIP - stretch', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6007, 9, 'INCIDENTS GIP - event_type', 'Incidents GIP - event_type', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6008, 9, 'INCIDENTS GIP - collision', 'Incidents GIP - collision', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6009, 9, 'INCIDENTS GIP - object_crash', 'Incidents GIP - object_crash', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6010, 9, 'INCIDENTS GIP - vehicle_crash', 'Incidents GIP - vehicle_crash', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6011, 9, 'INCIDENTS GIP - relative_location', 'Incidents GIP - relative_location', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6012, 9, 'INCIDENTS GIP - vehicle_types', 'Incidents GIP - vehicle_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6013, 9, 'INCIDENTS GIP - vehicle_services', 'Incidents GIP - vehicle_services', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6014, 9, 'INCIDENTS GIP - vehicle_consecuences', 'Incidents GIP - vehicle_consecuences', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6015, 9, 'INCIDENTS GIP - vehicle_manoeuvres', 'Incidents GIP - vehicle_manoeuvres', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6016, 9, 'INCIDENTS GIP - vehicle_brands', 'Incidents GIP - vehicle_brands', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6017, 9, 'INCIDENTS GIP - participant_types', 'Incidents GIP - participant_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6018, 9, 'INCIDENTS GIP - participant_injuries', 'Incidents GIP - participant_injuries', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6019, 9, 'INCIDENTS GIP - participant_nacionalities', 'Incidents GIP - participant_nacionalities', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6020, 9, 'INCIDENTS GIP - damages', 'Incidents GIP - damages', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(6100, 9, 'REPORTS - alarms', 'REPORTS GIP - alarms', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6101, 9, 'REPORTS - states', 'REPORTS GIP - states', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);


INSERT INTO ui.module_values 
(module_id, module_param_id, value) VALUES 
(1920, 4, '1920'),
(1921, 9, '1'),
(1922, 9, '2'),
(6000, 13, '1'), (6000, 14, 'incidents'),
(6001, 13, '1'), (6001, 14, 'vehicles'),
(6002, 13, '1'), (6002, 14, 'participants'),
(6003, 13, '1'), (6003, 14, 'closures'),
(6004, 13, '1'), (6004, 14, 'event'),
(6005, 13, '1'), (6005, 14, 'side'),
(6006, 13, '1'), (6006, 14, 'stretch'),
(6007, 13, '1'), (6007, 14, 'event_type'),
(6008, 13, '1'), (6008, 14, 'collision'),
(6009, 13, '1'), (6009, 14, 'object_crash'),
(6010, 13, '1'), (6010, 14, 'vehicle_crash'),
(6011, 13, '1'), (6011, 14, 'relative_location'),
(6012, 13, '1'), (6012, 14, 'vehicle_types'),
(6013, 13, '1'), (6013, 14, 'vehicle_services'),
(6014, 13, '1'), (6014, 14, 'vehicle_consecuences'),
(6015, 13, '1'), (6015, 14, 'vehicle_manoeuvres'),
(6016, 13, '1'), (6016, 14, 'vehicle_brands'),
(6017, 13, '1'), (6017, 14, 'participant_types'),
(6018, 13, '1'), (6018, 14, 'participant_injuries'),
(6019, 13, '1'), (6019, 14, 'participant_nacionalities'),
(6020, 13, '1'), (6020, 14, 'damages_responsabilities'),
(6100, 13, '2'), (6100, 14, 'alarmas_hist'),
(6101, 13, '2'), (6101, 14, 'estados_hist');

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
