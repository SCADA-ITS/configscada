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
(700, 8, '["ManagementArea:4"]', 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(701, 4, '["ManagementArea:4"]', 'SELECTOR_TREE_NODES_SINOPTICOS_CENTRAL_NUCLEAR', 'SELECTOR_TREE_NODES_SINOPTICOS_CENTRAL_NUCLEAR', 1, NULL, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, true),

(1920, 8, NULL, 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6000, 9, NULL, 'GIP - actions', 'GIP - actions', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6001, 9, NULL, 'GIP - agents', 'GIP - agents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6002, 9, NULL, 'GIP - assigned_pdas', 'GIP - assigned_pdas', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6003, 9, NULL, 'GIP - assigned_resources', 'GIP - assigned_resources', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6004, 9, NULL, 'GIP - assistance_types', 'GIP - assistance_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6005, 9, NULL, 'GIP - atmospheric_states', 'GIP - atmospheric_states', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6006, 9, NULL, 'GIP - attendeds', 'GIP - attendeds', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6007, 9, NULL, 'GIP - causes', 'GIP - causes', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6008, 9, NULL, 'GIP - conditions', 'GIP - conditions', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6009, 9, NULL, 'GIP - elements', 'GIP - elements', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6010, 9, NULL, 'GIP - genders', 'GIP - genders', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6011, 9, NULL, 'GIP - incidents', 'GIP - incidents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6012, 9, NULL, 'GIP - infrastructure_damages', 'GIP - infrastructure_damages', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6013, 9, NULL, 'GIP - injury_levels', 'GIP - injury_levels', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6014, 9, NULL, 'GIP - injury_types', 'GIP - injury_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6015, 9, NULL, 'GIP - interrupt_agents', 'GIP - interrupt_agents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6016, 9, NULL, 'GIP - lanes', 'GIP - lanes', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6017, 9, NULL, 'GIP - lanes_actions', 'GIP - lanes_actions', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6018, 9, NULL, 'GIP - lanes_states', 'GIP - lanes_states', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6019, 9, NULL, 'GIP - luminosities', 'GIP - luminosities', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6020, 9, NULL, 'GIP - maneuvers', 'GIP - maneuvers', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6021, 9, NULL, 'GIP - participant_assistance_types', 'GIP - participant_assistance_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6022, 9, NULL, 'GIP - participant_transfer_places', 'GIP - participant_transfer_places', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6023, 9, NULL, 'GIP - participant_types', 'GIP - participant_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6024, 9, NULL, 'GIP - participants', 'GIP - participants', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6025, 9, NULL, 'GIP - pdas', 'GIP - pdas', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6026, 9, NULL, 'GIP - resources', 'GIP - resources', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6027, 9, NULL, 'GIP - road_impacts', 'GIP - road_impacts', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6028, 9, NULL, 'GIP - sources', 'GIP - sources', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6029, 9, NULL, 'GIP - stages', 'GIP - stages', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6030, 9, NULL, 'GIP - transfer_places', 'GIP - transfer_places', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6031, 9, NULL, 'GIP - typologies', 'GIP - typologies', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6032, 9, NULL, 'GIP - unavailable_lanes', 'GIP - unavailable_lanes', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6033, 9, NULL, 'GIP - user_types', 'GIP - user_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6034, 9, NULL, 'GIP - vehicle_brands', 'GIP - vehicle_brands', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6035, 9, NULL, 'GIP - vehicle_damages', 'GIP - vehicle_damages', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6036, 9, NULL, 'GIP - vehicle_models', 'GIP - vehicle_models', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6037, 9, NULL, 'GIP - vehicle_types', 'GIP - vehicle_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6038, 9, NULL, 'GIP - vehicles', 'GIP - vehicles', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);


UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1020;
UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1027;
UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1028;
UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1030;
UPDATE ui.modules SET management_areas='["ManagementArea:2"]' WHERE id=1041;
UPDATE ui.modules SET management_areas='["ManagementArea:1"]' WHERE id=1051;
