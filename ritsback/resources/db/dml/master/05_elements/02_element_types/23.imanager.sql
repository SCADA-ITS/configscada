-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description,  initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(23, 'INCIDENT MANAGER', 'INCIDENT MANAGER', 'LBL_ELEMENT_TYPE_IMANAGER', 'LBL_ELEMENT_TYPE_IMANAGER_DESC', 0, 1, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(23, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(23, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2301, 2, 5, 23, NULL, 3, true, true, 'ALARM ENABLE INCIDENTS', 'ALARM ENABLE INCIDENTS', 'LBL_ALARM_CONFIGS_ALARM_ENABLE_INCIDENTS', 'LBL_ALARM_CONFIGS_ALARM_ENABLE_INCIDENTS', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.command_element_types
--
INSERT INTO master.command_element_types
(command_element_type_id, element_type_id, alias, description, label_alias, label_description, on_change, enabled, visible, created_at, updated_at) VALUES
(1, 23, 'ENABLE INCIDENTS', 'ENABLE INCIDENTS', 'LBL_COMMAND_ELEMENT_TYPE_ENABLE_INCIDENTS', null, 'Incidents/EnableIncidents', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 23, 'DISABLE INCIDENTS', 'DISABLE INCIDENTS', 'LBL_COMMAND_ELEMENT_TYPE_DISABLE_INCIDENTS', null, 'Incidents/DisableIncidents', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
