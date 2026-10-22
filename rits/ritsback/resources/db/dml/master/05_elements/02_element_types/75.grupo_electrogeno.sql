-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(75, NULL, 'GE', 'Grupo electrogeno', 'LBL_ELEMENT_TYPE_GE', 'LBL_ELEMENT_TYPE_GE_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(75,1,1, NULL,2, ' ', 'PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , 'LBL_ELEMENT_TYPE_PARAM_PK_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,1,2,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,1,3, NULL,2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , 'LBL_ELEMENT_TYPE_PARAM_ROAD_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,1,4, NULL,2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,1, 13,1, '', 'grp_corriente_fase_1', 'grp_corriente_fase_1', 'LBL_ELEMENT_TYPE_PARAM_GRP_CORRIENTE_FASE_1' , 'LBL_ELEMENT_TYPE_PARAM_GRP_CORRIENTE_FASE_1_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,2, 13,1, '', 'grp_corriente_fase_2', 'grp_corriente_fase_2', 'LBL_ELEMENT_TYPE_PARAM_GRP_CORRIENTE_FASE_2' , 'LBL_ELEMENT_TYPE_PARAM_GRP_CORRIENTE_FASE_2_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,3, 13,1, '', 'grp_corriente_fase_3', 'grp_corriente_fase_3', 'LBL_ELEMENT_TYPE_PARAM_GRP_CORRIENTE_FASE_3' , 'LBL_ELEMENT_TYPE_PARAM_GRP_CORRIENTE_FASE_3_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,4, 18,1, '', 'grp_frecuencia_salida', 'grp_frecuencia_salida', 'LBL_ELEMENT_TYPE_PARAM_GRP_FRECUENCIA_SALIDA' , 'LBL_ELEMENT_TYPE_PARAM_GRP_FRECUENCIA_SALIDA_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,5, 31,1, '', 'grp_horas_tot_marcha', 'grp_horas_tot_marcha', 'LBL_ELEMENT_TYPE_PARAM_GRP_HORAS_TOT_MARCHA' , 'LBL_ELEMENT_TYPE_PARAM_GRP_HORAS_TOT_MARCHA_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,6, 32,1, '', 'grp_rpm_generador', 'grp_rpm_generador', 'LBL_ELEMENT_TYPE_PARAM_GRP_RPM_GENERADOR' , 'LBL_ELEMENT_TYPE_PARAM_GRP_RPM_GENERADOR_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,7, 14,1, '', 'grp_tension_bateria', 'grp_tension_bateria', 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_BATERIA' , 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_BATERIA_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,8, 14,1, '', 'grp_tension_salida_gen', 'grp_tension_salida_gen', 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_SALIDA_GEN' , 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_SALIDA_GEN_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,9, NULL,5, '', 'grp_modo_operacion_off', 'grp_modo_operacion_off', 'LBL_ELEMENT_TYPE_PARAM_GRP_MODO_OPERACION_OFF' , 'LBL_ELEMENT_TYPE_PARAM_GRP_MODO_OPERACION_OFF_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,10, NULL,5, '', 'grp_modo_operacion_man', 'grp_modo_operacion_man', 'LBL_ELEMENT_TYPE_PARAM_GRP_MODO_OPERACION_MAN' , 'LBL_ELEMENT_TYPE_PARAM_GRP_MODO_OPERACION_MAN_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,11, NULL,5, '', 'grp_modo_operacion_aut', 'grp_modo_operacion_aut', 'LBL_ELEMENT_TYPE_PARAM_GRP_MODO_OPERACION_AUT' , 'LBL_ELEMENT_TYPE_PARAM_GRP_MODO_OPERACION_AUT_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,12, 14,1, '', 'grp_vol_gen_L1_N', 'grp_vol_gen_L1_N', 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_L1_N' , 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_L1_N', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,13, 14,1, '', 'grp_vol_gen_L2_N', 'grp_vol_gen_L2_N', 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_L2_N' , 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_L2_N', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,14, 14,1, '', 'grp_vol_gen_L3_N', 'grp_vol_gen_L3_N', 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_L3_N' , 'LBL_ELEMENT_TYPE_PARAM_GRP_TENSION_L3_N', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,15, 3,1, '', 'grp_gas_level', 'grp_gas_level', 'LBL_ELEMENT_TYPE_PARAM_GRP_GASOIL_LEVEL' , 'LBL_ELEMENT_TYPE_PARAM_GRP_GASOIL_LEVEL', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,16, 8,1, '', 'grp_temp_rad', 'grp_temp_rad', 'LBL_ELEMENT_TYPE_PARAM_GRP_TEMP_RAD' , 'LBL_ELEMENT_TYPE_PARAM_GRP_TEMP_RAD', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75,2,17, NULL,1, '', 'grp_kwh', 'grp_kwh', 'LBL_ELEMENT_TYPE_PARAM_GRP_KWH' , 'LBL_ELEMENT_TYPE_PARAM_GRP_KWH', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(75, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(75, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 75, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(222, 2, 3, 75, NULL, NULL, true, true, 'ALARM GE NO CONNECTION', 'ALARM GE NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_GE_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_GE_NO_CONNECTION_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(223, 2, 3, 75, NULL, 3, true, true, 'ALARM GE TEMPERATURE', 'ALARM GE TEMPERATURE', 'LBL_ALARM_CONFIGS_ALARM_GE_TEMPERATURE', 'LBL_ALARM_CONFIGS_ALARM_GE_TEMPERATURE_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(224, 2, 3, 75, NULL, 3, true, true, 'ALARM GE GENERAL', 'ALARM GE GENERAL', 'LBL_ALARM_CONFIGS_ALARM_GE_GENERAL', 'LBL_ALARM_CONFIGS_ALARM_GE_GENERAL_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(225, 2, 3, 75, NULL, 3, true, true, 'ALARM GE ENGINE_ON', 'ALARM GE ENGINE_ON', 'LBL_ALARM_CONFIGS_ALARM_GE_ENGINE_ON', 'LBL_ALARM_CONFIGS_ALARM_GE_ENGINE_ON_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(226, 2, 3, 75, NULL, 3, true, true, 'ALARM GE RESERVED_FUEL_LEVEL', 'ALARM GE RESERVED_FUEL_LEVEL', 'LBL_ALARM_CONFIGS_ALARM_GE_RESERVED_FUEL_LEVEL', 'LBL_ALARM_CONFIGS_ALARM_GE_RESERVED_FUEL_LEVEL_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(227, 2, 3, 75, NULL, 3, true, true, 'ALARM DEPOSITO_DIESEL NIVEL_BAJO', 'ALARM DEPOSITO_DIESEL NIVEL_BAJO', 'LBL_ALARM_CONFIGS_ALARM_DEPOSITO_DIESEL_NIVEL_BAJO', 'LBL_ALARM_CONFIGS_ALARM_DEPOSITO_DIESEL_NIVEL_BAJO_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(228, 2, 3, 75, NULL, 3, true, true, 'ALARM DEPOSITO_DIESEL NIVEL_BAJO_CRITICO', 'ALARM DEPOSITO_DIESEL NIVEL_BAJO_CRITICO', 'LBL_ALARM_CONFIGS_ALARM_DEPOSITO_DIESEL_NIVEL_BAJO_CRITICO', 'LBL_ALARM_CONFIGS_ALARM_DEPOSITO_DIESEL_NIVEL_BAJO_CRITICO_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010075, 2, 3, 75, NULL, 3, true, true, 'ALARM MEASURE LOW BATTERY', 'ALARM MEASURE LOW BATTERY', 'LBL_ALARM_CONFIGS_ALARM_MEASURE_LOW_BATTERY', 'LBL_ALARM_CONFIGS_ALARM_MEASURE_LOW_BATTERY', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(75, 2, 222, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
