-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(98, 'PROCESS INFO', 'Process Information','LBL_ELEMENT_TYPE_PROCESS_INFO', 'LBL_ELEMENT_TYPE_PROCESS_INFO_DESC', 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
--CONFIG
(98,2,1, NULL,1, null, 'PROCESS PID', 'PROCESS PID', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_PID', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_PID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,2, NULL,2, null, 'PROCESS MAIN', 'PROCESS MAIN', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_MAIN', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_MAIN_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,3, NULL,2, null, 'PROCESS COMMAND LINE', 'PROCESS COMMAND LINE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_COMMAND_LINE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_COMMAND_LINE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,4, NULL,3, null, 'PROCESS CPU LOAD', 'PROCESS CPU LOAD', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_CPU_LOAD', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_CPU_LOAD_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,5, NULL,1, null, 'PROCESS CPU TIME', 'PROCESS CPU TIME', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_CPU_TIME', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_CPU_TIME_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,6, NULL,3, null, 'PROCESS CPU AVERAGE', 'PROCESS CPU AVERAGE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_CPU_AVERAGE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_CPU_AVERAGE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,7, NULL,1, null, 'PROCESS HEAP SIZE', 'PROCESS HEAP SIZE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_HEAP_SIZE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_HEAP_SIZE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,8, NULL,1, null, 'PROCESS HEAP USED', 'PROCESS HEAP USED', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_HEAP_USED', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_HEAP_USED_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,9, NULL,1, null, 'PROCESS HEAP MAX', 'PROCESS HEAP MAX', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_HEAP_MAX', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_HEAP_MAX_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,10, NULL,1, null, 'PROCESS THREADS LIVE', 'PROCESS THREADS LIVE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_THREADS_LIVE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_THREADS_LIVE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,11, NULL,1, null, 'PROCESS NUM RESTARTS', 'PROCESS NUM RESTARTS', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_NUM_RESTARTS', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_NUM_RESTARTS_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,12, NULL,5, null, 'PROCESS READY', 'PROCESS READY', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_READY', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_READY_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,13, NULL,2, null, 'PROCESS GIT COMMIT', 'PROCESS GIT COMMIT', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_GIT_COMMIT', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_GIT_COMMIT_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,14, NULL,2, null, 'PROCESS GIT AUTHOR', 'PROCESS GIT AUTHOR', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_GIT_AUTHOR', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_GIT_AUTHOR_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,15, NULL,2, null, 'PROCESS GIT DATE', 'PROCESS GIT DATE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_GIT_DATE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_GIT_DATE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98,2,16, NULL,2, null, 'PROCESS GIT MESSAGE', 'PROCESS GIT MESSAGE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_GIT_MESSAGE', 'LBL_ELEMENT_TYPE_PARAM_PROCESS_GIT_MESSAGE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(98, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', 'LBL_ELEMENT_TYPE_STATE_UP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', 'LBL_ELEMENT_TYPE_STATE_DOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', 'LBL_ELEMENT_TYPE_STATE_ALARMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(98, NULL, 1, NULL, NULL,  1, 'com.revenga.rits.back.data.loader.app.DataLoader', 'Data Loader', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 2, NULL, NULL,  1, 'com.revenga.rits.back.ep.pub.stomp.app.PubStompApplication -Dfile.encoding=UTF-8 --xml.adapter=pub_stomp_adapter_v1.xml', 'Ep Pub Stomp', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 3, NULL, NULL,  1, 'com.revenga.rits.back.equipment.manager.app.EquipmentManager -Dfile.encoding=UTF-8 --xml.adapter=equipment_manager_adapter_v1.xml', 'Equipment Manager', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 4, NULL, NULL,  1, 'com.revenga.rits.back.historical.manager.app.HistoricalManagerApplication -Dfile.encoding=UTF-8 --xml.adapter=historical_manager_adapter_v1.xml', 'Historical Manager', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 5, NULL, NULL,  1, 'com.revenga.rits.back.incident.manager.app.IncidentManager -Dfile.encoding=UTF-8 --xml.adapter=incident_manager_adapter_v1.xml', 'Incident Manager', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 6, NULL, NULL,  1, 'com.revenga.rits.back.io.controller.app.IOController -Dfile.encoding=UTF-8 --xml.adapter=controller_adapter_v1.xml', 'IO Controller', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 7, NULL, NULL,  1, 'com.revenga.rits.back.io.external.manager.app.IOExternalManager -Dfile.encoding=UTF-8 --xml.adapter=io_external_manager_adapter_v1.xml', 'IO External Controller', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 8, NULL, NULL,  1, 'com.revenga.rits.back.plan.manager.app.PlanManager -Dfile.encoding=UTF-8 --xml.adapter=plan_manager_adapter_v1.xml', 'Plan Manager', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 9, NULL, NULL,  1, 'com.revenga.rits.back.service.graphql.ServiceGraphqlApplication -Dfile.encoding=UTF-8 --xml.adapter=service_graphql_adapter_v1.xml', 'Service GraphQL', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 10, NULL, NULL,  1, 'com.revenga.rits.back.traffic.manager.app.TrafficManager --xml.adapter=traffic_manager_adapter_v1.xml', 'Traffic Manager', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, NULL, 11, NULL, NULL,  1, 'com.revenga.rits.back.virtual.equipment.manager.app.VitualEquipmentManager -Dfile.encoding=UTF-8 --xml.adapter=virtual_equipment_manager_adapter_v1.xml', 'Virtual Equipment Manager', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);