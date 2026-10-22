-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(27, 2, 'PLC', 'PLC', 'LBL_ELEMENT_TYPE_PLC', 'LBL_ELEMENT_TYPE_PLC_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(27,1,1, NULL,2, ' ', 'brand', 'brand', 'LBL_ELEMENT_TYPE_PARAM_BRAND' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,1,2, NULL,2, ' ', 'model', 'model', 'LBL_ELEMENT_TYPE_PARAM_MODEL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,1,3,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,1, NULL,5, NULL, 'master_cpu_state', 'master_cpu_state', 'LBL_ELEMENT_TYPE_PARAM_MASTER_CPU_STATE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,2, NULL,5, NULL, 'slave_cpu_state', 'slave_cpu_state', 'LBL_ELEMENT_TYPE_PARAM_SLAVE_CPU_STATE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,3, NULL,1, NULL, 'master_cpu_function', 'master_cpu_function', 'LBL_ELEMENT_TYPE_PARAM_MASTER_CPU_FUNCTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,4, NULL,1, NULL, 'slave_cpu_function', 'slave_cpu_function', 'LBL_ELEMENT_TYPE_PARAM_SLAVE_CPU_FUNCTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,5, NULL,1, NULL, 'device_error_t1', 'device_error_t1', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T1' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,6, NULL,1, NULL, 'device_error_t2', 'device_error_t2', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T2' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,7, NULL,1, NULL, 'device_error_t3', 'device_error_t3', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T3' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,8, NULL,1, NULL, 'device_error_t4', 'device_error_t4', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T4' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,9, NULL,1, NULL, 'device_error_t5', 'device_error_t5', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T5' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,10, NULL,1, NULL, 'device_error_t6', 'device_error_t6', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T6' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,11, NULL,1, NULL, 'device_error_t7', 'device_error_t7', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T7' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,12, NULL,1, NULL, 'device_error_t8', 'device_error_t8', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T8' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,13, NULL,1, NULL, 'device_error_t9', 'device_error_t9', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T9' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,14, NULL,1, NULL, 'device_error_t10', 'device_error_t10', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T10' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,15, NULL,1, NULL, 'device_error_t11', 'device_error_t11', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T11' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,16, NULL,1, NULL, 'device_error_t12', 'device_error_t12', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T12' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,17, NULL,1, NULL, 'device_error_t13', 'device_error_t13', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T13' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,18, NULL,1, NULL, 'device_error_t14', 'device_error_t14', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T14' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,19, NULL,1, NULL, 'device_error_t15', 'device_error_t15', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T15' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,20, NULL,1, NULL, 'device_error_t16', 'device_error_t16', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T16' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,21, NULL,1, NULL, 'device_error_t17', 'device_error_t17', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T17' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,22, NULL,1, NULL, 'device_error_t18', 'device_error_t18', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T18' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,23, NULL,1, NULL, 'device_error_t19', 'device_error_t19', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T19' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,24, NULL,1, NULL, 'device_error_t20', 'device_error_t20', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T20' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,25, NULL,1, NULL, 'device_error_t21', 'device_error_t21', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T21' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,26, NULL,1, NULL, 'device_error_t22', 'device_error_t22', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T22' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,27, NULL,1, NULL, 'device_error_t23', 'device_error_t23', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T23' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,28, NULL,1, NULL, 'device_error_t24', 'device_error_t24', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T24' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,29, NULL,1, NULL, 'device_error_t25', 'device_error_t25', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T25' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,30, NULL,1, NULL, 'device_error_t26', 'device_error_t26', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T26' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,31, NULL,1, NULL, 'device_error_t27', 'device_error_t27', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T27' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,32, NULL,1, NULL, 'device_error_t28', 'device_error_t28', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T28' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,33, NULL,1, NULL, 'device_error_t29', 'device_error_t29', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T29' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,34, NULL,1, NULL, 'device_error_t30', 'device_error_t30', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T30' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,35, NULL,1, NULL, 'device_error_t31', 'device_error_t31', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T31' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,36, NULL,1, NULL, 'device_error_t32', 'device_error_t32', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T32' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,37, NULL,1, NULL, 'device_error_t33', 'device_error_t33', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T33' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,38, NULL,1, NULL, 'device_error_t34', 'device_error_t34', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T34' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,39, NULL,1, NULL, 'device_error_t35', 'device_error_t35', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T35' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,40, NULL,1, NULL, 'device_error_t36', 'device_error_t36', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T36' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,41, NULL,1, NULL, 'device_error_t37', 'device_error_t37', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T37' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,42, NULL,1, NULL, 'device_error_t38', 'device_error_t38', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T38' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,43, NULL,1, NULL, 'device_error_t39', 'device_error_t39', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T39' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,44, NULL,1, NULL, 'device_error_t40', 'device_error_t40', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T40' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,45, NULL,1, NULL, 'device_error_t41', 'device_error_t41', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T41' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,46, NULL,1, NULL, 'device_error_t42', 'device_error_t42', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T42' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,47, NULL,1, NULL, 'device_error_t43', 'device_error_t43', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T43' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,48, NULL,1, NULL, 'device_error_t44', 'device_error_t44', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T44' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,49, NULL,1, NULL, 'device_error_t45', 'device_error_t45', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T45' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,50, NULL,1, NULL, 'device_error_t46', 'device_error_t46', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T46' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,51, NULL,1, NULL, 'device_error_t47', 'device_error_t47', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T47' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,52, NULL,1, NULL, 'device_error_t48', 'device_error_t48', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T48' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,53, NULL,1, NULL, 'device_error_t49', 'device_error_t49', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T49' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,54, NULL,1, NULL, 'device_error_t50', 'device_error_t50', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T50' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,55, NULL,1, NULL, 'device_error_t51', 'device_error_t51', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T51' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,56, NULL,1, NULL, 'device_error_t52', 'device_error_t52', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T52' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,2,57, NULL,1, NULL, 'device_error_t53', 'device_error_t53', 'LBL_ELEMENT_TYPE_PARAM_DEVICE_ERROR_T53' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27,1,1003, NULL,2, ' ', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO_SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(27, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions 
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(27, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 27, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(010027, 2, 3, 27, NULL, NULL, true, true, 'ALARM PLC NO CONNECTION', 'ALARM PLC NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PLC_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_PLC_NO_CONNECTION_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020027, 2, 3, 27, NULL, 3, true, true, 'ALARM RACK FRONT DOOR OPEN', 'ALARM RACK FRONT DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_RACK_FRONT_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_RACK_FRONT_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030027, 2, 3, 27, NULL, 3, true, true, 'ALARM RACK BACK DOOR OPEN', 'ALARM RACK BACK DOOR OPEN', 'LBL_ALARM_CONFIGS_ALARM_RACK_BACK_DOOR_OPEN', 'LBL_ALARM_CONFIGS_ALARM_RACK_BACK_DOOR_OPEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(27, 2, 010027, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
