INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES
(200, 3, 'ALL INFORMATION MAP', 'ALL INFORMATION MAP', 1,null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(201, 3, 'EQUIPMENTS', 'EQUIPMENTS', 1,null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(202, 3, 'EXTERNAL INCIDENTS MAP', 'EXTERNAL INCIDENTS MAP', 1,null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(203, 3, 'INTERNAL INCIDENTS MAP', 'INTERNAL INCIDENTS MAP', 1,null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(401, 5, 'ExternalMap', 'ExternalMap', 1, null, 'modules/externalApp.js', 'getView', NULL, NULL, NULL, true, true, NULL),
(402, 5, 'ExternalMap', 'ExternalMap', 1, null, 'modules/externalApp.js', 'getView', NULL, NULL, NULL, true, true, NULL),

(1920, 8, 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6001, 9, 'INCIDENTS GIP - municipalities', 'Incidents GIP - municipalities', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6002, 9, 'INCIDENTS GIP - entities', 'Incidents GIP - entities', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6011, 9, 'INCIDENTS GIP - incidents', 'Incidents GIP - incidents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6012, 9, 'INCIDENTS GIP - vehicles', 'Incidents GIP - vehicles', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6013, 9, 'INCIDENTS GIP - vehicle_brands', 'Incidents GIP - vehicle_brands', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6014, 9, 'INCIDENTS GIP - vehicle_models', 'Incidents GIP - vehicle_models', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6015, 9, 'INCIDENTS GIP - vehicle_types', 'Incidents GIP - vehicle_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6016, 9, 'INCIDENTS GIP - vehicle_damages', 'Incidents GIP - vehicle_damages', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6017, 9, 'INCIDENTS GIP - affection_details', 'Incidents GIP - affection_details', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(1950, 9, 'c4_ext_entities_with_values', 'c4_ext_entities_with_values', 1, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1951, 9, 'e112_ext_entities_with_values', 'e112_ext_entities_with_values', 1, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(7001, 9, '112 - Resources', '112 - Resources', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(7002, 9, '112 - Vehicles', '112 - Vehicles', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(8000, 9, 'waze_traffic_alert_ext_entities_with_values', 'waze_traffic_alert_ext_entities_with_values', 1, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(8001, 9, 'waze_traffic_jam_ext_entities_with_values', 'waze_traffic_jam_ext_entities_with_values', 1, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(9000, 9, 'Transits - White list', 'Transits - White list', 1, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(9001, 9, 'Transits - Black list', 'Transits - Black list', 1, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(9002, 9, 'Transits - Discarded reasons', 'Transits - Discarded reasons', 1, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(2020, 8, 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(2021, 8, 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, true),
(2022, 8, 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);


