-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(5, NULL, 'PA_SYSTEM', 'PA_SYSTEM', 'LBL_ELEMENT_TYPE_PA_SYSTEM', 'LBL_ELEMENT_TYPE_PA_SYSTEM_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(5,1,1,NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,1,2,NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,1,5,NULL,2, ' ', 'sender_ip', 'sender_ip', 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_SENDER_IP' , 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_SENDER_IP_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,1,1003,NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--MEASURE
(5,2,1,null,2, NULL, 'messages_on_air', 'messages_on_air', 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_MESSAGES_ON_AIR' , 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_MESSAGES_ON_AIR_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,2,2,null,2, NULL, 'zones_list', 'zones_list', 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_ZONES_LIST' , 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_ZONES_LIST_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,2,1001,null,1, NULL, 'message_id', 'message_id', 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_MESSAGE_ID' , 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_MESSAGE_ID_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,2,1002,null,1, NULL, 'message_text', 'message_text', 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_MESSAGE_TEXT' , 'LBL_ELEMENT_TYPE_PARAM_PA_SYSTEM_MESSAGE_TEXT_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(5, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(5, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(5, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 96, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 5, 'SWITCH OFF ZONES', 'SWITCH OFF ZONES', 'LBL_COMMAND_ELEMENT_TYPE_SWITCH_OFF_ZONES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 'SEND MESSAGE ZONES', 'SEND MESSAGE ZONES', 'LBL_COMMAND_ELEMENT_TYPE_SEND_MESSAGE_ZONES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 5, 'SWITCH OFF GROUPS', 'SWITCH OFF GROUPS', 'LBL_COMMAND_ELEMENT_TYPE_SWITCH_OFF_GROUPS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 5, 'SEND MESSAGE GROUPS', 'SEND MESSAGE GROUPS', 'LBL_COMMAND_ELEMENT_TYPE_SEND_MESSAGE_GROUPS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_type_params
--
INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, alias, description, Label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 5, 1, 2, 'ZONES', 'ZONES', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SWITCH_OFF_ZONES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 1, 2, 'ZONES', 'ZONES', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SEND_MESSAGE_ZONES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 2, 2, 'MESSAGE_ID', 'MESSAGE_ID', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SEND_MESSAGE_ZONES_MESSAGE_ID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 5, 1, 2, 'GROUPS', 'GROUPS', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SWITCH_OFF_GROUPS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 5, 1, 2, 'GROUPS', 'GROUPS', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SEND_MESSAGE_GROUPS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 5, 2, 2, 'MESSAGE_ID', 'MESSAGE_ID', 'LBL_COMMAND_ELEMENT_TYPE_PARAM_SEND_MESSAGE_GROUPS_MESSAGE_ID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(010005, 2, 3, 5, NULL, NULL, true, true, 'ALARM PA SYSTEM NO CONNECTION', 'ALARM PA SYSTEM NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PA_SYSTEM_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PA_SYSTEM_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020005, 2, 3, 5, NULL, 3, true, true, 'ALARM GENERAL', 'ALARM GENERAL', 'LBL_ALARM_CONFIGS_ALARM_PA_SYSTEM_GENERAL', 'LBL_ALARM_CONFIGS_ALARM_GENERAL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(5, 2, 010005, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
