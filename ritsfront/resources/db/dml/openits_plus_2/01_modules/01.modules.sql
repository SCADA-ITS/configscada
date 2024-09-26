UPDATE ui.modules SET enabled=false WHERE id=1018;

INSERT INTO ui.modules 
(id, module_type_id, management_areas, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES 
--Management Area 1
(900, 8, '["ManagementArea:1"]', 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, true),
(901, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_CT', 'SELECTOR_TREE_NODES_SINOPTICOS_CT', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(902, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(903, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(904, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(905, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(906, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(907, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(908, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(909, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(910, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(911, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(912, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(913, 4, '["ManagementArea:1"]', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 'SELECTOR_TREE_NODES_SINOPTICOS_UF', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

--Management Area 2
(970, 3, '["ManagementArea:2"]', 'MAP', 'MAP', 1, null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, true),

--Management Area 3
(800, 8, '["ManagementArea:3"]', 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, true),
(801, 4, '["ManagementArea:3"]', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L1', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L1', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(802, 4, '["ManagementArea:3"]', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L2', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L2', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(803, 4, '["ManagementArea:3"]', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L3', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L3', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

--Management Area 4
(700, 8, '["ManagementArea:4"]', 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, true),
(701, 4, '["ManagementArea:4"]', 'SELECTOR_TREE_NODES_SINOPTICOS_CENTRAL_NUCLEAR', 'SELECTOR_TREE_NODES_SINOPTICOS_CENTRAL_NUCLEAR', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);

UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1020;
UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1027;
UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1028;
UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1030;
UPDATE ui.modules SET management_areas='["ManagementArea:2"]' WHERE id=1041;
UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1051;
