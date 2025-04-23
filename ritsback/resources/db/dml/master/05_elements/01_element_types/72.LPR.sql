-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(72, 'CAMERA', 'Camera','LBL_ELEMENT_TYPE_LPR', 'LBL_ELEMENT_TYPE_LPR_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(72,1,1, NULL,2, NULL, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72,1,2, NULL,2, NULL, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--UNE PARAMS 
(72, 1, 4001, NULL, 2, NULL, 'socket une host', 'socket une host', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_HOST' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_HOST_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 1, 4002, NULL, 1, NULL, 'socket une port', 'socket une port', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_PORT' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_PORT_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 1, 4003, NULL, 6, NULL, 'socket une enq frame', 'socket une enq frame', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ENQ_FRAME' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ENQ_FRAME_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 1, 4004, NULL, 6, NULL, 'socket une ack frame', 'socket une ack frame', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ACK_FRAME' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ACK_FRAME_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 1, 4005, NULL, 6, NULL, 'socket une ack address', 'address', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ADDRESS' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ADDRESS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 1, 4006, NULL, 6, NULL, 'socket une ssl trust store', 'ssl trust store', 'LBL_ELEMENT_TYPE_PARAM_SSL_TRUST_STORE' , 'LBL_ELEMENT_TYPE_PARAM_SSL_TRUST_STORE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 1, 4007, NULL, 6, NULL, 'socket une ssl trust store password', 'ssl trust store password', 'LBL_ELEMENT_TYPE_PARAM_SSL_TRUST_STORE_PASSWORD' , 'LBL_ELEMENT_TYPE_PARAM_SSL_TRUST_STORE_PASSWORD_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(72, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 4, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(72, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(4, 72, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
--Común a todos los subtipos
(010072, 4, 6, 72, NULL, NULL, true, true, 'ALARM LPR NO CONNECTION', 'ALARM LPR NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_LPR_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_LPR_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(72, 2, 010072, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
