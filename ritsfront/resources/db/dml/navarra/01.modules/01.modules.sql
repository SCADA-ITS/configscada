INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES
(200, 3, 'MAP', 'MAP', 1,null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, true),

(1950, 9, 'c4_ext_entities_with_values', 'c4_ext_entities_with_values', 1, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(2020, 8, 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(6000, 9, 'C4 - Obras', 'C4 - Obras', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6001, 9, 'C4 - Meteorológicas', 'C4 - Meteorológicas', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6002, 9, 'C4 - Red viaría', 'C4 - Red viaría', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);


