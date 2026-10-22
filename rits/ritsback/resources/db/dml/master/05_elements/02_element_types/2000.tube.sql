-- 
-- TUBE
--

INSERT INTO master.element_types
(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(2000, NULL, 'TUBE', 'Tunnel tube', 'LBL_ELEMENT_TYPE_TUBE', 'LBL_ELEMENT_TYPE_TUBE_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, durable, created_at, updated_at) VALUES
(2000, 1, 1, null , 2, NULL, 'Tube_ventilation_regimes_config', 'Tube ventilation regimes config', 'LBL_ELEMENT_TYPE_PARAM_VENTILATION_REGIMES_CONFIG', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, null , 1, 1, 'Ventilation_mode', 'ventilation mode: 0 manual, 1 auto, 2 auto maintenance', 'LBL_ELEMENT_TYPE_PARAM_VENTILATION_MODE', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 2, null , 1, NULL, 'Ventilation_regime', 'current ventilation regime', 'LBL_ELEMENT_TYPE_PARAM_VENTILATION_REGIME', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2000, 1, 2, null , 2, NULL, 'Tube_illumination_regimes_configs', 'Tube illumination regimes configs', 'LBL_ELEMENT_TYPE_PARAM_ILLUMINATION_REGIMES_CONFIG', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 3, null , 1, 1, 'Illumination_mode', 'illumination mode: 0 manual, 1 auto', 'LBL_ELEMENT_TYPE_PARAM_ILUMINATION_MODE', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 4, null , 1, NULL, 'Illumination_regime', 'current illumination regime', 'LBL_ELEMENT_TYPE_PARAM_ILUMINATION_REGIME', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2000, 2, 5, null , 1, NULL, 'Ventilation_regime_propoused', 'ventilation regime propoused', 'LBL_ELEMENT_TYPE_PARAM_VENTILATION_REGIME_PROPOUSED', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 6, null , 1, NULL, 'Illumination_regime_propoused', 'Illumination regime propoused', 'LBL_ELEMENT_TYPE_PARAM_ILUMINATION_REGIME_PROPOUSED', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2000, 2, 7, null , 2, 'ElementTypeState:2001:1', 'VentilationElementTypeStateId', 'current ventilation state', 'LBL_ELEMENT_TYPE_PARAM_VENTILATION_STATE', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 8, null , 2, 'ElementTypeState:2002:1', 'IlluminationElementTypeStateId', 'current illumination state', 'LBL_ELEMENT_TYPE_PARAM_ILLUMINATION_STATE', null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2000, 1, 1003, NULL, 2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , null, true, true, false, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2000, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(2000, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(2000, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 31, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 85, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, on_change, enabled, visible, created_at, updated_at) VALUES
(1, 2000, 'SEND_FANS_STOP', 'FANS STOP', 'LBL_COMMAND_ELEMENT_TYPE_SEND_FANS_STOP', null, 'TunnelVentilationSystem/SetFansStopCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2000, 'SEND_FANS_START', 'FANS START', 'LBL_COMMAND_ELEMENT_TYPE_SEND_FANS_START', null, 'TunnelVentilationSystem/SetFansStartCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2000, 'SEND_FANS_START REVERSE', 'FANS START REVERSE', 'LBL_COMMAND_ELEMENT_TYPE_SEND_FANS_START_REVERSE', null, 'TunnelVentilationSystem/SetFansStartReverseCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2000, 'SEND_VENTILATION_MODE', 'CHANGE VENTILATION MODE', 'LBL_COMMAND_ELEMENT_TYPE_SEND VENTILATION MODE', null, 'TunnelVentilationSystem/SetVentilationModeCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2000, 'SEND_VENTILATION_REGIME', 'CHANGE VENTILATION REGIME', 'LBL_COMMAND_ELEMENT_TYPE_SEND_VENTILATION_REGIME', null, 'TunnelVentilationSystem/SetVentilationRegimeCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2000, 'SEND_ILLUMINATION_MODE', 'CHANGE ILLUMINATION MODE', 'LBL_COMMAND_ELEMENT_TYPE_SEND_ILLUMINATION_MODE', null, 'TunnelIlluminationSystem/SetIlluminationModeCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 2000, 'SEND_ILLUMINATION_REGIME', 'CHANGE ILLUMINATION REGIME', 'LBL_COMMAND_ELEMENT_TYPE_SEND_ILLUMINATION_REGIME', null, 'TunnelIlluminationSystem/SetIlluminationRegimeCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2000, 'SEND_CIRCUITS_OFF', 'CIRCUITS OFF', 'LBL_COMMAND_ELEMENT_TYPE_SEND_CIRCUITS_OFF', null, 'TunnelIlluminationSystem/SetCircuitsOffCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2000, 'SEND_CIRCUITS_ON', 'CIRCUITS ON', 'LBL_COMMAND_ELEMENT_TYPE_SEND_CIRCUITS_ON', null, 'TunnelIlluminationSystem/SetCircuitsOnCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2000, 'SEND_FANS_RESET_PARTIAL_HOURS', 'FANS RESET PARTIAL HOURS', 'LBL_COMMAND_ELEMENT_TYPE_SEND_FANS_RESET_PARTIAL_HOURS', null, 'TunnelVentilationSystem/SetFansResetPartialHoursCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 2000, 'SEND_FANS_RESET_TOTAL_HOURS', 'FANS RESET TOTAL HOURS', 'LBL_COMMAND_ELEMENT_TYPE_SEND_FANS_RESET_TOTAL_HOURS', null, 'TunnelVentilationSystem/SetFansResetTotalHoursCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 2000, 'SEND_FANS_RESET_ALARMS', 'FANS RESET ALARMS', 'LBL_COMMAND_ELEMENT_TYPE_SEND_FANS_RESET_ALARMS', null, 'TunnelVentilationSystem/SetFansResetAlarmCommand', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, alias, description, Label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 2000, 1, 2, 'FAN_IDS', 'List of fan ids', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_FAN_IDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2000, 1, 2, 'FAN_IDS', 'List of fan ids', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_FAN_IDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2000, 1, 2, 'FAN_IDS', 'List of fan ids', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_FAN_IDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2000, 1, 1, 'VENTILATION_MODE', 'New ventilation mode: 0 manual, 1 auto, 2 auto maintenance', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_VENTILATION_MODE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2000, 1, 1, 'VENTILATION_REGIME', 'New ventilation regime', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_VENTILATION_REGIME', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2000, 1, 1, 'ILLUMINATION_MODE', 'New illumination mode: 0 manual, 1 auto', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_ILLUMINATION_MODE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 2000, 1, 1, 'ILLUMINATION_REGIME', 'New illumination regime', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_ILLUMINATION_MODE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2000, 1, 2, 'CIRCUIT_IDS', 'List of circuit ids', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_CIRCUITS_IDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2000, 1, 2, 'CIRCUIT_IDS', 'List of circuit ids', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_CIRCUITS_IDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2000, 1, 2, 'FAN_IDS', 'List of fan ids', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_FAN_IDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 2000, 1, 2, 'FAN_IDS', 'List of fan ids', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_FAN_IDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 2000, 1, 2, 'FAN_IDS', 'List of fan ids', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_FAN_IDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 2000, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(012000, 2, 3, 2000, NULL, 3, true, true, 'ALARM FIRE MODE', 'ALARM FIRE MODE', 'LBL_ALARM_CONFIGS_ALARM_FIRE_MODE', '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

