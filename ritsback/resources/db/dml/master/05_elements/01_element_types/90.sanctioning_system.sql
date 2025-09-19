-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(90, 'SANCTIONING SYSTEM', 'Traffic fines sanctioning system','LBL_ELEMENT_TYPE_SANCTIONING_SYSTEM', 'LBL_ELEMENT_TYPE_SANCTIONING_SYSTEM_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(90,1,1, NULL,1, null, 'notificationInHand', 'notificationInHand', 'LBL_ELEMENT_TYPE_PARAM_NOTIFICATION_IN_HAND', 'LBL_ELEMENT_TYPE_PARAM_NOTIFICATION_IN_HAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90,1,2, NULL,1, null, 'notificationNotInHandCause', 'notificationNotInHandCause', 'LBL_ELEMENT_TYPE_PARAM_NOTIFICATION_NOT_IN_HAND', 'LBL_ELEMENT_TYPE_PARAM_NOTIFICATION_NOT_IN_HAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90,1,3, NULL,1, null, 'agent1Code', 'agent1Code', 'LBL_ELEMENT_TYPE_PARAM_AGENT1CODE', 'LBL_ELEMENT_TYPE_PARAM_AGENT1CODE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90,1,4, NULL,1, null, 'infractionTypedboidid', 'infractionTypedboidid', 'LBL_ELEMENT_TYPE_PARAM_INFRACTION_TYPE_DBOID_ID', 'LBL_ELEMENT_TYPE_PARAM_INFRACTION_TYPE_DBOID_ID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(90, 0, 'UNKNOWN', 'Unknown state','LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90, 1, 'UP', 'The state of the equipment is: comunicates','LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90, 3, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(90, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

