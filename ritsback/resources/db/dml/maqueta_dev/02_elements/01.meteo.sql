----
---- Table: master.element_type_params
----
--INSERT INTO master.element_type_params
--(element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
----UNE PARAMS 
--(1, 1, 4001, NULL, 2, NULL, 'socket une host', 'socket une host', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_HOST' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_HOST_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1, 1, 4002, NULL, 2, NULL, 'socket une port', 'socket une port', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_PORT' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_PORT_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1, 1, 4003, NULL, 6, NULL, 'socket une enq frame', 'socket une enq frame', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ENQ_FRAME' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ENQ_FRAME_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1, 1, 4004, NULL, 6, NULL, 'socket une ack frame', 'socket une ack frame', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ACK_FRAME' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ACK_FRAME_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1, 1, 4005, NULL, 6, NULL, 'socket une ack address', 'address', 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ADDRESS' , 'LBL_ELEMENT_TYPE_PARAM_SOCKET_UNE_ADDRESS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
--
---- 
---- Table: master.element_subtypes
----
--INSERT INTO master.element_subtypes
--(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
--(5, 1, 'Meteo socket une', 'Meteo socket une', 'LBL_ELEMENT_SUBTYPE_METEO_SOCKET_UNE', 'LBL_ELEMENT_SUBTYPE_METEO_OCKET_UNE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
---- 
---- Table: master.element_subtype_active_params 
----
--
--INSERT INTO master.element_subtype_active_params
--(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
--
--(5, 1, 1, 4001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(5, 1, 1, 4002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(5, 1, 1, 4003, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(5, 1, 1, 4004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(5, 1, 1, 4005, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.elements.WS
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(1, NULL, 1, 1, NULL,  NULL, 'MDKD01L', 'MDKD01L', NULL, NULL, 'VmsTemp.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 5, 2, 1, NULL,  NULL, 'Meteo_socket_une_1', 'Meteo_socket_une_1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 3, 1, NULL,  NULL, 'Meteo_socket_une_2', 'Meteo_socket_une_2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 4, 1, NULL,  NULL, 'Meteo_socket_une_3', 'Meteo_socket_une_3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 5, 1, NULL,  NULL, 'Meteo_socket_une_4', 'Meteo_socket_une_4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.element_values.METEO
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(1,1,1,1,'Dilus',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1,2,1,1,'P2',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1,1003,1,1,'MDKD01L',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(1, 4001, 1, 2, '192.168.88.196', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4002, 1, 2, '5000', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4003, 1, 2, '0x05 0x20', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4004, 1, 2, '0x06', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4005, 1, 2, '0x20', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(1, 4001, 1, 3, '192.168.88.197', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4002, 1, 3, '5000', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4003, 1, 3, '0x05 0x21', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4004, 1, 3, '0x06', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4005, 1, 3, '0x21', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(1, 4001, 1, 4, '192.168.88.187', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4002, 1, 4, '5000', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4003, 1, 4, '0x05 0x22', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4004, 1, 4, '0x06', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4005, 1, 4, '0x22', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(1, 4001, 1, 5, '192.168.88.188', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4002, 1, 5, '5000', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4003, 1, 5, '0x05 0x23', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4004, 1, 5, '0x06', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4005, 1, 5, '0x23', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


