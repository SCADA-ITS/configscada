-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(6, 'TRAVEL_TIME', 'Travel time', 'LBL_ELEMENT_TYPE_TRAVEL_TIME', 'LBL_ELEMENT_TYPE_TRAVEL_TIME_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params.SEGMENT
--
INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, data_type_id, element_type_param_unit_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(6,1,1,5, null, null, 'direction', 'direction', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_DIRECTION', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_DIRECTION_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,1,2,3, null, null, 'pk_ini', 'pk_ini', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_PK_INI', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_PK_INI_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,1,3,3, null, null, 'pk_end', 'pk_end', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_PK_END', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_PK_END_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,1,4,2, null, null, 'name', 'name', 'LBL_ELEMENT_TYPE_PARAM_NAME', 'LBL_ELEMENT_TYPE_PARAM_NAME_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,1,5,2, null, null, 'panel', 'panel', 'LBL_ELEMENT_TYPE_PARAM_PANEL', 'LBL_ELEMENT_TYPE_PARAM_PANEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--MEASURE
(6,2,1,4, null, null, 'date', 'date', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_DATE', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_DATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,2,2,1,9, null, 'time_min', 'time_min', 'LBL_ELEMENT_TYPE_PARAM_TIME_IN_MINUTES', 'LBL_ELEMENT_TYPE_PARAM_TIME_IN_MINUTES_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(6, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 'VALID', 'Unknown valid', 'LBL_ELEMENT_TYPE_STATE_VALID', 'LBL_ELEMENT_TYPE_STATE_VALID_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 'INVALID', 'Unknown invalid', 'LBL_ELEMENT_TYPE_STATE_INVALID', 'LBL_ELEMENT_TYPE_STATE_INVALID_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 3, 'ALARMS', 'The state of the equipment is: with alarms','LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_state_transitions.Segment
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(6, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Table: master.element_hierarchies_allowed
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(6, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
