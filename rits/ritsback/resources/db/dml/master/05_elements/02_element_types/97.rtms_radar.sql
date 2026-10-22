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
--CONFIG
(97,1,1, NULL,1, null, 'RTMS ROUTE', 'RTMS ROUTE', 'LBL_ELEMENT_TYPE_PARAM_RTMS_IP', 'LBL_ELEMENT_TYPE_PARAM_RTMS_IP_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97,1,2, NULL,1, null, 'RTMS USER', 'RTMS USER', 'LBL_ELEMENT_TYPE_PARAM_RTMS_USER', 'LBL_ELEMENT_TYPE_PARAM_RTMS_USER_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97,1,3, NULL,1, null, 'RTMS PASSWORD', 'RTMS PASSWORD', 'LBL_ELEMENT_TYPE_PARAM_RTMS_PASSWORD', 'LBL_ELEMENT_TYPE_PARAM_RTMS_PASSWORD_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97,1,4, NULL,2, ' ', 'associated_cabinet', 'associated_cabinet', 'LBL_ELEMENT_TYPE_PARAM_RTMS_ASSOCIATED_CABINET' , 'LBL_ELEMENT_TYPE_PARAM_RTMS_ASSOCIATED_CABINET_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97,1,1003, null, 2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(97, 0, 'UNKNOWN', 'Unknown state','LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 1, 'UP', 'The state of the equipment is: comunicates','LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 3, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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

