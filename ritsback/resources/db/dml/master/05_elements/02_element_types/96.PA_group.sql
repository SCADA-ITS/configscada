-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(96, NULL, 'PA_GROUP', 'PA_GROUP', 'LBL_ELEMENT_TYPE_PA_GROUP', 'LBL_ELEMENT_TYPE_PA_GROUP_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(96, 1, 1, NULL, 2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 1, 2, NULL, 2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 1, 5, null, 1, NULL, 'group_id', 'group_id', 'LBL_ELEMENT_TYPE_PARAM_GROUP_ID' , 'LBL_ELEMENT_TYPE_PARAM_GROUP_ID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 1, 6, null, 2, NULL, 'group_zones', 'group_zones', 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_GROUP_ZONES' , 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_GROUP_ZONES_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 1, 1003, null, 2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--MEASURE
(96, 2, 1, null, 5, NULL, 'zone_activated', 'zone_activated', 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_ZONE_ACTIVATED' , 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_ZONE_ACTIVATED_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 2, 1001, null, 2, NULL, 'message_id', 'message_id', 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_MESSAGE_ID' , 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_MESSAGE_ID_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 2, 1002, null, 2, NULL, 'message_desc', 'message_desc', 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_MESSAGE_DESC' , 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_MESSAGE_DESC_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 2, 1003, null, 2, NULL, 'message_text', 'message_text', 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_MESSAGE_TEXT' , 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_MESSAGE_TEXT_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 2, 1004, null, 2, NULL, 'tag_message', 'tag_message', 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_TAG_MESSAGE' , 'LBL_ELEMENT_TYPE_PARAM_PA_GROUP_TAG_MESSAGE_DESC', true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(96, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(96, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 96, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(010096, 2, 3, 96, NULL, NULL, true, true, 'ALARM PA ZONE NO CONNECTION', 'ALARM PA ZONE NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PA_GROUP_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PA_SYSTEM_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020096, 2, 3, 96, NULL, 3, true, true, 'ALARM ZONE', 'ALARM ZONE', 'LBL_ALARM_CONFIGS_ALARM_PA_GROUP', 'LBL_ALARM_CONFIGS_ALARM_PA_GROUP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(96, 2, 010096, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
