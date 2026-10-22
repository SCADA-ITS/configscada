-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(6, 'SEGMENT', 'Traffic segment', 'LBL_ELEMENT_TYPE_SEGMENT', 'LBL_ELEMENT_TYPE_SEGMENT_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params.SEGMENT
--
INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES

(6,2,1,4, null, 'date', 'date', 'LBL_ELEMENT_TYPE_PARAM_DATE', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,2,2,1, null, 'los', 'level_of_service', 'LBL_ELEMENT_TYPE_PARAM_LEVEL_OF_SERVICE', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,2,3,1, null, 'los_percent', 'level_of_service_percent', 'LBL_ELEMENT_TYPE_PARAM_LEVEL_OF_SERVICE_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,2,4,1, null, 'density', 'density', 'LBL_ELEMENT_TYPE_PARAM_DENSITY', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,2,5,1, null, 'speed', 'avg_speed', 'LBL_ELEMENT_TYPE_PARAM_SPEED', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,2,6,1, null, 'flow', 'Traffic flow', 'LBL_ELEMENT_TYPE_PARAM_FLOW', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(6,1,1,1, null, 'direction', 'direction', 'LBL_ELEMENT_TYPE_PARAM_DIRECTION', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,1,2,2, null, 'pk_ini', 'pk_ini', 'LBL_ELEMENT_TYPE_PARAM_PK_INI', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,1,3,2, null, 'pk_end', 'pk_end', 'LBL_ELEMENT_TYPE_PARAM_PK_END', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,1,4,1, null, 'distance', 'distance', 'LBL_ELEMENT_TYPE_PARAM_DISTANCE', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(6, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 'VALID', 'Unknown valid', 'LBL_ELEMENT_TYPE_STATE_VALID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 'INVALID', 'Unknown invalid', 'LBL_ELEMENT_TYPE_STATE_INVALID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 3, 'ALARMS', 'The state of the equipment is: with alarms','LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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