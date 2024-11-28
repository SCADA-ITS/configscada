-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, is_logic, enabled, visible, created_at, updated_at) VALUES
(2001, 'VENTILATION', 'Tunnel ventilation system', 'LBL_ELEMENT_TYPE_VENTILATION', 'LBL_ELEMENT_TYPE_VENTILATION_DESC', 0, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- Table: master.element_type_states.ventilation
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2001, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 1, 'STARTED', 'ventilation started', 'LBL_ELEMENT_TYPE_STATE_START', 'LBL_ELEMENT_TYPE_STATE_START_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 2, 'STOPPED', 'ventilation stopped', 'LBL_ELEMENT_TYPE_STATE_STOP', 'LBL_ELEMENT_TYPE_STATE_STOP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 3, 'EVACUATION', 'Fire in evacuation phase', 'LBL_ELEMENT_TYPE_STATE_EVACUATION', 'LBL_ELEMENT_TYPE_STATE_EVACUATION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 4, 'EXTRACTION', 'Fire in extraction phase', 'LBL_ELEMENT_TYPE_STATE_EXTRACTION', 'LBL_ELEMENT_TYPE_STATE_EXTRACTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(2001, 1, 1, null , 2, NULL, 'emergency_algorithm_configs', 'Emergency algorithm configs', 'LBL_ELEMENT_TYPE_PARAM_EMERGENCY_ALGORITHM_CONFIG', 'LBL_ELEMENT_TYPE_PARAM_EMERGENCY_ALGORITHM_CONFIG_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(2001, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 3, 4, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 4, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 4, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(2001, 2000, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, on_change, enabled, visible, created_at, updated_at) VALUES
(1, 2001, 'SEND_VENTILATION_STOP', 'VENTILATION_STOP', 'LBL_COMMAND_ELEMENT_TYPE_SEND_VENTILATION_STOP', null, 'TunnelVentilationSystem/SetVentilationStopCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2001, 'SEND_VENTILATION_START', 'VENTILATION_START', 'LBL_COMMAND_ELEMENT_TYPE_SEND_VENTILATION_START', null, 'TunnelVentilationSystem/SetVentilationStartCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2001, 'SEND_VENTILATION_FIRE', 'VENTILATION_FIRE', 'LBL_COMMAND_ELEMENT_TYPE_SEND_VENTILATION_FIRE', null, 'TunnelVentilationSystem/SetFireAlarmCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2001, 'SEND_VENTILATION_EXTRACTION', 'VENTILATION_EXTRACTION', 'LBL_COMMAND_ELEMENT_TYPE_SEND_VENTILATION_EXTRACTION', null, 'TunnelVentilationSystem/SetVentilationExtrationCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, alias, description, Label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(3, 2001, 1, 2, 'STRETCH_IDS', 'stretch id', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_STRETCH_ID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
