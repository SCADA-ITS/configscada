-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(97, 'RTMS', 'Rtms Radar','LBL_ELEMENT_TYPE_RTMS', 'LBL_ELEMENT_TYPE_RTMS_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES

(97,1,1, NULL,5, null, 'RTMS ROUTE', 'RTMS ROUTE', 'LBL_ELEMENT_TYPE_PARAM_IP', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97,1,2, NULL,1, null, 'RTMS USER', 'RTMS USER', 'LBL_ELEMENT_TYPE_PARAM_USER', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97,1,3, NULL,1, null, 'RTMS PASSWORD', 'RTMS PASSWORD', 'LBL_ELEMENT_TYPE_PARAM_PASSWORD', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(97, 0, 'UNKNOWN', 'Unknown state','LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 1, 'UP', 'The state of the equipment is: comunicates','LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(97, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(3, 97, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

