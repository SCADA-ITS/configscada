-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description,  initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(24, 'BIE', 'BIE', 'LBL_ELEMENT_TYPE_BIE', 'LBL_ELEMENT_TYPE_BIE', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(24,1,1, NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24,1,3,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24,1,1003, NULL,2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(1, 24, 'bie', 'bie', 'LBL_ELEMENT_SUBTYPE_BIE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 24, 'bie intermedia', 'bie intermedia', 'LBL_ELEMENT_SUBTYPE_BIE_INTERMEDIA', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(24, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(24, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(24, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2401, 2, 5, 24, NULL, 3, true, true, 'ALARM EXTINGUISHER EXTRACTION', 'ALARM EXTINGUISHER EXTRACTION', 'LBL_ALARM_CONFIGS_ALARM_EXTINGUISHER_EXTRACTION', 'LBL_ALARM_CONFIGS_ALARM_EXTINGUISHER_EXTRACTION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2402, 2, 5, 24, NULL, 3, true, true, 'ALARM BIE DOOR OPEN', 'ALARM BIE DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_BIE_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_BIE_DOOR_OPEN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2403, 2, 5, 24, NULL, 3, true, true, 'ALARM EXTINGUISHER DOOR OPEN', 'ALARM EXTINGUISHER DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_EXTINGUISHER_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_EXTINGUISHER_DOOR_OPEN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2404, 2, 4, 24, NULL, NULL, true, true, 'ALARM BIE NO CONNECTION', 'ALARM BIE NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_BIE_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_BIE_NO_CONNECTION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(24, 2, 2404, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);