-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(5, NULL, 'PA_SYSTEM', 'PA_SYSTEM', 'LBL_ELEMENT_TYPE_PA_SYSTEM', 'LBL_ELEMENT_TYPE_PA_SYSTEM_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(5,1,1,null,2, ' ', 'PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,1,2,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,1,3,null,2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,1,4,null,2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,1,5,null,2, ' ', 'sender_ip', 'sender_ip', 'LBL_ELEMENT_TYPE_PARAM_SENDER_IP' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,1,1003,null,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,2,1,null,2, NULL, 'messages_on_air', 'messages_on_air', 'LBL_ELEMENT_TYPE_PARAM_MESSAGES_ON_AIR' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,2,1001,null,1, NULL, 'message_id', 'message_id', 'LBL_ELEMENT_TYPE_PARAM_MESSAGE_ID' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,2,1002,null,1, NULL, 'message_text', 'message_text', 'LBL_ELEMENT_TYPE_PARAM_MESSAGE_TEXT' , null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(5, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
(5, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 5, 'SWITCH OFF', 'SWITCH OFF', 'LBL_COMMAND_ELEMENT_TYPE_SWITCH_OFF', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 'SEND MESSAGE', 'SEND MESSAGE', 'LBL_COMMAND_ELEMENT_TYPE_SEND_MESSAGE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_type_params
--
INSERT INTO master.command_element_type_params
(command_element_type_id, element_type_id, command_element_type_param_id, data_type_id, alias, description, Label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 5, 1, 2, 'ZONES', 'ZONES', 'LBL_COMMAND_ELEMENT_TYPE_ZONES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 1, 2, 'ZONES', 'ZONES', 'LBL_COMMAND_ELEMENT_TYPE_ZONES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 2, 2, 'MESSAGE_ID', 'MESSAGE_ID', 'LBL_COMMAND_ELEMENT_TYPE_MESSAGE_ID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
(37, 2, 3, 5, NULL, NULL, true, true, 'ALARM PA SYSTEM NO CONNECTION', 'ALARM PA SYSTEM NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PA_SYSTEM_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PA_SYSTEM_NO_CONNECTION_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 2, 3, 5, NULL, 3, true, true, 'ALARM GENERAL', 'ALARM GENERAL', 'LBL_ALARM_CONFIGS_ALARM_GENERAL', 'LBL_ALARM_CONFIGS_ALARM_GENERAL_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(5, 2, 37, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
