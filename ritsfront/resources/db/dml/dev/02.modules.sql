INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES
(402, 5, 'Control panel', 'Control panel', 1, null, 'modules/externalApp.js', 'getView', NULL, NULL, NULL, true, true, NULL),

-- Synoptics
(920, 4, 'Synoptic Test', 'Synoptic Test', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

-- Maps
(970, 3, 'Map Test', 'Map Test', 1, null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

-- BackOffice custom type modules
(1900, 9, 'Orders backoffice', 'Orders backoffice', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1901, 9, 'Customers backoffice', 'Customers backoffice', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1902, 9, 'Products backoffice', 'Products backoffice', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1903, 9, 'Order 1', 'Order 1', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1904, 9, 'Categories', 'Categories', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

-- GIP
(1920, 9, 'Locations', 'Locations', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1921, 9, 'Incidents', 'Incidents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1922, 9, 'Resources', 'Resources', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1923, 9, 'Agents', 'Agents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1924, 9, 'Vehicles', 'Vehicles', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1925, 9, 'Lanes', 'Lanes', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(1926, 9, 'Participants', 'Participants', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);

--(2001, 1, 'Test module 1', 'Test module 1', 1, null, 'modules/test1Module.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
--(2002, 3, 'TestMap', 'TestMap', 1, null, 'modules/testMap.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);

INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload, management_areas) VALUES
(2001, 1, 'Test module 1', 'Test module 1', 1, null, 'modules/test1Module.js', 'getView', 'loadData', NULL, NULL, true, true, NULL, '["ManagementArea:1"]'),
(2002, 3, 'TestMap', 'TestMap', 1, null, 'modules/testMap.js', 'getView', 'loadData', NULL, NULL, true, true, true, '["ManagementArea:1"]');

INSERT INTO ui.module_values(module_id, module_param_id, value) VALUES
(402, 5, 'http://192.168.88.200:3030/d/qQFlxpaZz/detectors?orgId=1&refresh=5s'),
(402, 12, 'true'),
(920, 4, '1'),
(970, 6, '1'),
(1040, 8, '{"treeCustom": [1,4,5]}'),

(1900, 13, '1'),
(1900, 14, 'orders'),
(1900, 18, 'true'),

(1901, 13, '1'),
(1901, 14, 'customers'),

(1902, 13, '1'),
(1902, 14, 'products'),

(1903, 13, '1'),
(1903, 14, 'orders'),
(1903, 15, 'customer_id'),
(1903, 16, '3'),

(1904, 13, '1'),
(1904, 14, 'vw_subcategories'),

(1920, 13, '2'),
(1920, 14, 'locations'),

(1921, 13, '2'),
(1921, 14, 'incidents'),

(1922, 13, '2'),
(1922, 14, 'resources'),

(1923, 13, '2'),
(1923, 14, 'agents'),

(1924, 13, '2'),
(1924, 14, 'vehicles'),

(1925, 13, '2'),
(1925, 14, 'lanes'),

(1926, 13, '2'),
(1926, 14, 'participants');


INSERT INTO ui.window_values(module_id, window_param_id, value) VALUES
(402, 8, 'true'),
(402, 10, 'img/module/control-panel-color.png'),
(402, 11, '#464646'),
(402, 12, 'LBL_CONTROL_PANEL'),
(402, 14, 'true'),

(920, 4, '825'),
(920, 5, '1600'),
(920, 8, 'true'),
(920, 10, 'img/module/synoptic-color.png'),
(920, 11, '#464646'),
(920, 12, 'LBL_SYNOPTIC_TEST'),
(920, 14, 'true'),
(920, 17, 'true'),

(970, 4, '825'),
(970, 5, '1600'),
(970, 8, 'true'),
(970, 10, 'img/module/map-color.png'),
(970, 11, '#464646'),
(970, 12, 'LBL_MAP_TEST'),
(970, 14, 'true'),
(970, 17, 'true'),

(1900, 4, '825'),
(1900, 5, '1400'),
(1900, 8, 'true'),
(1900, 10, 'img/module/default.png'),
(1900, 11, '#464646'),
(1900, 12, 'LBL_ORDERS'),
(1900, 14, 'true'),

(1901, 4, '825'),
(1901, 5, '1400'),
(1901, 8, 'true'),
(1901, 10, 'img/module/default.png'),
(1901, 11, '#464646'),
(1901, 12, 'LBL_CUSTOMERS'),
(1901, 14, 'true'),

(1902, 4, '825'),
(1902, 5, '1400'),
(1902, 8, 'true'),
(1902, 10, 'img/module/default.png'),
(1902, 11, '#464646'),
(1902, 12, 'LBL_PRODUCTS'),
(1902, 14, 'true'),

(1903, 4, '700'),
(1903, 5, '900'),
(1903, 8, 'true'),
(1903, 10, 'img/module/default.png'),
(1903, 11, '#464646'),
(1903, 12, 'LBL_ORDER'),
(1903, 14, 'true'),

(1920, 4, '825'),
(1920, 5, '1400'),
(1920, 8, 'true'),
(1920, 10, 'img/module/default.png'),
(1920, 11, '#464646'),
(1920, 12, 'LBL_LOCATIONS'),
(1920, 14, 'true'),

(1921, 4, '825'),
(1921, 5, '1400'),
(1921, 8, 'true'),
(1921, 10, 'img/module/default.png'),
(1921, 11, '#464646'),
(1921, 12, 'LBL_INCIDENTS'),
(1921, 14, 'true'),

(1922, 4, '825'),
(1922, 5, '1400'),
(1922, 8, 'true'),
(1922, 10, 'img/module/default.png'),
(1922, 11, '#464646'),
(1922, 12, 'LBL_RESOURCES'),
(1922, 14, 'true'),

(1923, 4, '825'),
(1923, 5, '1400'),
(1923, 8, 'true'),
(1923, 10, 'img/module/default.png'),
(1923, 11, '#464646'),
(1923, 12, 'LBL_AGENTS'),
(1923, 14, 'true'),

(1924, 4, '825'),
(1924, 5, '1400'),
(1924, 8, 'true'),
(1924, 10, 'img/module/default.png'),
(1924, 11, '#464646'),
(1924, 12, 'LBL_VEHICLES'),
(1924, 14, 'true'),

(1925, 4, '825'),
(1925, 5, '1400'),
(1925, 8, 'true'),
(1925, 10, 'img/module/default.png'),
(1925, 11, '#464646'),
(1925, 12, 'LBL_LANES'),
(1925, 14, 'true'),

(1926, 4, '825'),
(1926, 5, '1400'),
(1926, 8, 'true'),
(1926, 10, 'img/module/default.png'),
(1926, 11, '#464646'),
(1926, 12, 'LBL_PARTICIPANTS'),
(1926, 14, 'true');