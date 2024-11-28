-- 
-- Table: master.element_types
--
INSERT INTO master.element_types
(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, is_logic, enabled, visible, created_at, updated_at) VALUES
(2002, 'ILLUMINATION', 'Tunnel illumination system', 'LBL_ELEMENT_TYPE_ILUMINATION', 'LBL_ELEMENT_TYPE_ILLUMINATION_DESC', 0, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- Table: master.element_type_states.ventilation
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2002, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 1, 'STARTED', 'Illumination started', 'LBL_ELEMENT_TYPE_STATE_START', 'LBL_ELEMENT_TYPE_STATE_START_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 2, 'STOPPED', 'Illumination stopped', 'LBL_ELEMENT_TYPE_STATE_STOP', 'LBL_ELEMENT_TYPE_STATE_STOP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(2002, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
--
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(2002, 2000, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 96, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);