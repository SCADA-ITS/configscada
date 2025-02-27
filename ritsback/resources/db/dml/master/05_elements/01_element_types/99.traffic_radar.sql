-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(99, 'RADAR', 'Traffic Radar','LBL_ELEMENT_TYPE_TRAFFIC_RADAR', 'LBL_ELEMENT_TYPE_TRAFFIC_RADAR_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(99,1,1, NULL,1, null, 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND', 'LBL_ELEMENT_TYPE_PARAM_BRAND_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99,1,2, NULL,1, null, 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL', 'LBL_ELEMENT_TYPE_PARAM_MODEL_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99,1,1003, null, 2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--UNE PARAMS 
(99, 1, 4001, NULL, 2, NULL, 'socket une host', 'socket une host', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_HOST' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_HOST_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 4002, NULL, 1, NULL, 'socket une port', 'socket une port', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_PORT' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_PORT_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 4003, NULL, 6, NULL, 'socket une enq frame', 'socket une enq frame', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ENQ_FRAME' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ENQ_FRAME_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 4004, NULL, 6, NULL, 'socket une ack frame', 'socket une ack frame', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ACK_FRAME' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ACK_FRAME_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 4005, NULL, 6, NULL, 'socket une ack address', 'address', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ADDRESS' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ADDRESS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SFTP PARAMS 
(99, 1, 5001, NULL, 2, NULL, 'sftp host', 'sftp host', 'LBL_ELEMENT_TYPE_PARAM_SFTP_HOST' , 'LBL_ELEMENT_TYPE_PARAM_SFTP_HOST_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 5002, NULL, 1, NULL, 'sftp port', 'sftp port', 'LBL_ELEMENT_TYPE_PARAM_SFTP_PORT' , 'LBL_ELEMENT_TYPE_PARAM_SFTP_PORT_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 5003, NULL, 2, NULL, 'sftp user', 'sftp user', 'LBL_ELEMENT_TYPE_PARAM_SFTP_USER' , 'LBL_ELEMENT_TYPE_PARAM_SFTP_USER_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 5004, NULL, 2, NULL, 'sftp password', 'sftp password', 'LBL_ELEMENT_TYPE_PARAM_SOFTP_PASSWORD' , 'LBL_ELEMENT_TYPE_PARAM_SFTP_PASSWORD_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 5005, NULL, 2, NULL, 'sftp remote directory', 'sftp remote directory', 'LBL_ELEMENT_TYPE_PARAM_SFTP_REMOTE_DIRECTORY' , 'LBL_ELEMENT_TYPE_PARAM_SFTP_REMOTE_DIRECTORY_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(99, 0, 'UNKNOWN', 'Unknown state','LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 'UP', 'The state of the equipment is: comunicates','LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 3, 'MAINTENANCE', 'The state of the equipment is: on Maintenance', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE', 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(99, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 99, 'SAIMA', 'SAIMA', 'LBL_ELEMENT_SUBTYPE_SAIMA', 'LBL_ELEMENT_SUBTYPE_SAIMA_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 99, 'TRADESEGUR_6F', 'TRADESEGUR_6F', 'LBL_ELEMENT_SUBTYPE_TRADESEGUR_6F', 'LBL_ELEMENT_SUBTYPE_TRADESEGUR_6F_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 99, 'TRADESEGUR_MRC', 'TRADESEGUR_MRC', 'LBL_ELEMENT_SUBTYPE_TRADESEGUR_MRC', 'LBL_ELEMENT_SUBTYPE_TRADESEGUR_MRC_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 99, 'TRADESEGUR_TC2', 'TRADESEGUR_TC2', 'LBL_ELEMENT_SUBTYPE_TRADESEGUR_TC2', 'LBL_ELEMENT_SUBTYPE_TRADESEGUR_TC2_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
--Común a todos los subtipos
(010099, 4, 6, 99, NULL, NULL, true, true, 'ALARM RADAR NO CONNECTION', 'ALARM RADAR NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_RADAR_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_RADAR_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(99, 2, 010099, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(3, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

