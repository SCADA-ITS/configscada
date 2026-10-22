-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(16, NULL, 'PA_ZONE', 'PA_ZONE', 'LBL_ELEMENT_TYPE_PA_ZONE', 'LBL_ELEMENT_TYPE_PA_ZONE_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(16, 1, 5, null, 1, NULL, 'zone_id', 'zone_id', 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_ZONE_ID' , 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_ZONE_ID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 1, 6, null, 2, NULL, 'group_zones', 'group_zones', 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_GROUP_ZONES' , 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_GROUP_ZONES_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 1, 1003, null, 2, NULL, 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(16, 2, 1, null, 5, NULL, 'zone_activated', 'zone_activated', 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_ZONE_ACTIVATED' , 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_ZONE_ACTIVATED_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 2, 1001, null, 2, NULL, 'message_id', 'message_id', 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_MESSAGE_ID' , 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_MESSAGE_ID_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 2, 1002, null, 2, NULL, 'message_desc', 'message_desc', 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_MESSAGE_DESC' , 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_MESSAGE_DESC_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 2, 1003, null, 2, NULL, 'message_text', 'message_text', 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_MESSAGE_TEXT' , 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_MESSAGE_TEXT_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 2, 1004, null, 2, NULL, 'tag_message', 'tag_message', 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_TAG_MESSAGE' , 'LBL_ELEMENT_TYPE_PARAM_PA_ZONE_TAG_MESSAGE_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(16, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(16, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(010016, 2, 3, 16, NULL, NULL, true, true, 'ALARM PA ZONE NO CONNECTION', 'ALARM PA ZONE NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_ANALIZADOR_PA_ZONE_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PA_SYSTEM_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020016, 2, 3, 16, NULL, 3, true, true, 'ALARM ZONE', 'ALARM ZONE', 'LBL_ALARM_CONFIGS_ALARM_PA_ZONE', 'LBL_ALARM_CONFIGS_ALARM_PA_ZONE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030016, 2, 3, 16, NULL, 3, true, true, 'ALARM EMERGENCY', 'ALARM EMERGENCY', 'LBL_ALARM_CONFIGS_ALARM_EMERGENCY', 'LBL_ALARM_CONFIGS_ALARM_EMERGENCY_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(16, 2, 010016, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
