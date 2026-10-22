-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(2003, NULL, 'Electrical panel', 'Electrical panel', 'LBL_ELEMENT_TYPE_ELECTRICAL_PANEL', 'LBL_ELEMENT_TYPE_ELECTRILCA_PANEL_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- Table: master.element_type_states.ventilation
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2003, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(2003, 1, 1, null , 1, NULL, 'start_ramp_seconds', 'start ramp in seconds of a fan', 'LBL_ELEMENT_TYPE_PARAM_VENTILATION_START_RAMP_S', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 1, 2, null , 1, NULL, 'start_ramp_n_fans', 'maximum number of fans that can be started at the same time', 'LBL_ELEMENT_TYPE_PARAM_VENTILATION_START_RAMP_N_FANS', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 1, 3, null , 2, NULL, 'start_ramp_configs', 'Start ramp configs', 'LBL_ELEMENT_TYPE_PARAM_STAR_RAMP_CONFIG', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 1, 4, null , 1, NULL, 'max_n_fans_start', 'max n fans start', 'LBL_ELEMENT_TYPE_PARAM_MAX_FANS_START', null, true, true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(2003, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(2003, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


