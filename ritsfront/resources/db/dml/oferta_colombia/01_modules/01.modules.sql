UPDATE ui.modules SET autoload=true WHERE id=1018;

INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES
(910, 1, 'EquipmentForm', 'Equipment form', 2, null, 'modules/equipmentFormModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

-- Maps
(970, 3, 'Map', 'Map', 1, null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(1920, 8, 'Selector Tree Module', 'Selector Tree Module', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6000, 9, 'NOGALES GIP - actions', 'Nogales GIP - actions', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6001, 9, 'NOGALES GIP - agents', 'Nogales GIP - agents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6002, 9, 'NOGALES GIP - assigned_pdas', 'Nogales GIP - assigned_pdas', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6003, 9, 'NOGALES GIP - assigned_resources', 'Nogales GIP - assigned_resources', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6004, 9, 'NOGALES GIP - assistance_types', 'Nogales GIP - assistance_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6005, 9, 'NOGALES GIP - atmospheric_states', 'Nogales GIP - atmospheric_states', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6006, 9, 'NOGALES GIP - attendeds', 'Nogales GIP - attendeds', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6007, 9, 'NOGALES GIP - causes', 'Nogales GIP - causes', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6008, 9, 'NOGALES GIP - conditions', 'Nogales GIP - conditions', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6009, 9, 'NOGALES GIP - elements', 'Nogales GIP - elements', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6010, 9, 'NOGALES GIP - genders', 'Nogales GIP - genders', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6011, 9, 'NOGALES GIP - incidents', 'Nogales GIP - incidents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6012, 9, 'NOGALES GIP - infrastructure_damages', 'Nogales GIP - infrastructure_damages', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6013, 9, 'NOGALES GIP - injury_levels', 'Nogales GIP - injury_levels', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6014, 9, 'NOGALES GIP - injury_types', 'Nogales GIP - injury_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6015, 9, 'NOGALES GIP - interrupt_agents', 'Nogales GIP - interrupt_agents', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6016, 9, 'NOGALES GIP - lanes', 'Nogales GIP - lanes', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6017, 9, 'NOGALES GIP - lanes_actions', 'Nogales GIP - lanes_actions', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6018, 9, 'NOGALES GIP - lanes_states', 'Nogales GIP - lanes_states', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6019, 9, 'NOGALES GIP - luminosities', 'Nogales GIP - luminosities', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6020, 9, 'NOGALES GIP - maneuvers', 'Nogales GIP - maneuvers', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6021, 9, 'NOGALES GIP - participant_assistance_types', 'Nogales GIP - participant_assistance_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6022, 9, 'NOGALES GIP - participant_transfer_places', 'Nogales GIP - participant_transfer_places', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6023, 9, 'NOGALES GIP - participant_types', 'Nogales GIP - participant_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6024, 9, 'NOGALES GIP - participants', 'Nogales GIP - participants', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6025, 9, 'NOGALES GIP - pdas', 'Nogales GIP - pdas', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6026, 9, 'NOGALES GIP - resources', 'Nogales GIP - resources', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6027, 9, 'NOGALES GIP - road_impacts', 'Nogales GIP - road_impacts', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6028, 9, 'NOGALES GIP - sources', 'Nogales GIP - sources', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6029, 9, 'NOGALES GIP - stages', 'Nogales GIP - stages', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6030, 9, 'NOGALES GIP - transfer_places', 'Nogales GIP - transfer_places', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6031, 9, 'NOGALES GIP - typologies', 'Nogales GIP - typologies', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6032, 9, 'NOGALES GIP - unavailable_lanes', 'Nogales GIP - unavailable_lanes', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6033, 9, 'NOGALES GIP - user_types', 'Nogales GIP - user_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6034, 9, 'NOGALES GIP - vehicle_brands', 'Nogales GIP - vehicle_brands', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6035, 9, 'NOGALES GIP - vehicle_damages', 'Nogales GIP - vehicle_damages', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6036, 9, 'NOGALES GIP - vehicle_models', 'Nogales GIP - vehicle_models', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6037, 9, 'NOGALES GIP - vehicle_types', 'Nogales GIP - vehicle_types', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(6038, 9, 'NOGALES GIP - vehicles', 'Nogales GIP - vehicles', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);


INSERT INTO ui.module_actions(id, module_id, name, description, system_permission_id) VALUES
(91001, 910, 'view equipment', 'view equipment', NULL);
