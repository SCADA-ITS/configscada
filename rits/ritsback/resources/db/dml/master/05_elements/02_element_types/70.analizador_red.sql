-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, element_group_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at) VALUES
(70, NULL, 'ANALIZADOR_RED', 'ANALIZADOR_RED', 'LBL_ELEMENT_TYPE_ANALIZADOR_RED', 'LBL_ELEMENT_TYPE_ANALIZADOR_RED_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params (element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES
(70,1,1, NULL,2, ' ', 'PK', 'PK', 'LBL_ELEMENT_TYPE_PARAM_PK' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,1,2,1,2, ' ', 'entrance_tunnel_distance', 'entrance_tunnel_distance', 'LBL_ELEMENT_TYPE_PARAM_ENTRANCE_TUNNEL_DISTANCE' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,1,3, NULL,2, ' ', 'road', 'road', 'LBL_ELEMENT_TYPE_PARAM_ROAD' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,1,4, NULL,2, ' ', 'alias_section', 'alias_section', 'LBL_ELEMENT_TYPE_PARAM_ALIAS_SECTION' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,1,1003, NULL,2, '', 'MANGO SESSION_UID', 'MANGO SESSION_UID', 'LBL_ELEMENT_TYPE_PARAM_MANGO SESSION_UID' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,1,13,1, '', 'corriente_fase_a', 'corriente_fase_a', 'LBL_ELEMENT_TYPE_PARAM_CORRIENTE_FASE_A' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,2,13,1, '', 'corriente_fase_b', 'corriente_fase_b', 'LBL_ELEMENT_TYPE_PARAM_CORRIENTE_FASE_B' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,3,13,1, '', 'corriente_fase_c', 'corriente_fase_c', 'LBL_ELEMENT_TYPE_PARAM_CORRIENTE_FASE_C' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,4,13,1, '', 'corriente_fase_neutro', 'corriente_fase_neutro', 'LBL_ELEMENT_TYPE_PARAM_CORRIENTE_FASE_NEUTRO' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,5,13,1, '', 'corriente_fase_trifasica', 'corriente_fase_trifasica', 'LBL_ELEMENT_TYPE_PARAM_CORRIENTE_FASE_TRIFASICA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,6,14,1, '', 'tension_fase_a', 'tension_fase_a', 'LBL_ELEMENT_TYPE_PARAM_TENSION_FASE_A' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,7,14,1, '', 'tension_fase_c', 'tension_fase_c', 'LBL_ELEMENT_TYPE_PARAM_TENSION_FASE_C' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,8,14,1, '', 'tension_fase_b', 'tension_fase_b', 'LBL_ELEMENT_TYPE_PARAM_TENSION_FASE_B' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,9,14,1, '', 'tension_fase_trifasica', 'tension_fase_trifasica', 'LBL_ELEMENT_TYPE_PARAM_TENSION_FASE_TRIFASICA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,10,14,1, '', 'tension_fase_a_b', 'tension_fase_a_b', 'LBL_ELEMENT_TYPE_PARAM_TENSION_FASE_A_B' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,11,14,1, '', 'tension_fase_b_c', 'tension_fase_b_c', 'LBL_ELEMENT_TYPE_PARAM_TENSION_FASE_B_C' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,12,14,1, '', 'tension_fase_c_a', 'tension_fase_c_a', 'LBL_ELEMENT_TYPE_PARAM_TENSION_FASE_C_A' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,13,14,1, '', 'tension_promedio_fase_neutro', 'tension_promedio_fase_neutro', 'LBL_ELEMENT_TYPE_PARAM_TENSION_PROMEDIO_FASE_NEUTRO' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,14,15,1, '', 'potencia_activa_fase_a', 'potencia_activa_fase_a', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_ACTIVA_FASE_A' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,15,15,1, '', 'potencia_activa_fase_b', 'potencia_activa_fase_b', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_ACTIVA_FASE_B' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,16,15,1, '', 'potencia_activa_fase_c', 'potencia_activa_fase_c', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_ACTIVA_FASE_C' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,17,15,1, '', 'potencia_activa_total', 'potencia_activa_total', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_ACTIVA_TOTAL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,18,16,1, '', 'potencia_reactiva_fase_a', 'potencia_reactiva_fase_a', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_REACTIVA_FASE_A' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,19,16,1, '', 'potencia_reactiva_fase_b', 'potencia_reactiva_fase_b', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_REACTIVA_FASE_B' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,20,16,1, '', 'potencia_reactiva_fase_c', 'potencia_reactiva_fase_c', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_REACTIVA_FASE_C' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,21,16,1, '', 'potencia_reactiva_total', 'potencia_reactiva_total', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_REACTIVA_TOTAL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,22,17,1, '', 'potencia_aparente_fase_a', 'corriente_aparente_fase_a', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_APARENTE_FASE_A' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,23,17,1, '', 'potencia_aparente_fase_b', 'corriente_aparente_fase_b', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_APARENTE_FASE_B' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,24,17,1, '', 'potencia_aparente_fase_c', 'corriente_aparente_fase_c', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_APARENTE_FASE_C' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,25,17,1, '', 'potencia_aparente_total', 'corriente_aparente_total', 'LBL_ELEMENT_TYPE_PARAM_POTENCIA_APARENTE_TOTAL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,26, NULL,1, '', 'factor_de_potencia_fase_a', 'factor_de_potencia_fase_a', 'LBL_ELEMENT_TYPE_PARAM_FACTOR_DE_POTENCIA_FASE_A' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,27, NULL,1, '', 'factor_de_potencia_fase_b', 'factor_de_potencia_fase_b', 'LBL_ELEMENT_TYPE_PARAM_FACTOR_DE_POTENCIA_FASE_B' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,28, NULL,1, '', 'factor_de_potencia_fase_c', 'factor_de_potencia_fase_c', 'LBL_ELEMENT_TYPE_PARAM_FACTOR_DE_POTENCIA_FASE_C' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,29, NULL,1, '', 'factor_de_potencia_trifasico', 'factor_de_potencia_trifasico', 'LBL_ELEMENT_TYPE_PARAM_FACTOR_DE_POTENCIA_TRIFASICO' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,30,18,1, '', 'frecuencia', 'frecuencia', 'LBL_ELEMENT_TYPE_PARAM_FRECUENCIA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,31, NULL,1, '', 'reset_energia_acumulada_date_time', 'reset_energia_acumulada_date_time', 'LBL_ELEMENT_TYPE_PARAM_RESET_ENERGIA_ACUMULADA_DATE_TIME' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,32,19,1, '', 'energia_activa_entregada', 'energia_activa_entregada', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_ACTIVA_ENTREGADA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,33,19,1, '', 'energia_activa_recibida', 'energia_activa_recibida', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_ACTIVA_RECIBIDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,34,19,1, '', 'energia_activa_entregada+recibida', 'energia_activa_entregada+recibida', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_ACTIVA_ENTREGADA+RECIBIDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,35,19,1, '', 'energia_activa_entregada-recibida', 'energia_activa_entregada-recibida', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_ACTIVA_ENTREGADA-RECIBIDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,36,20,1, '', 'energia_reactiva_entregada', 'energia_reactiva_entregada', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_REACTIVA_ENTREGADA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,37,20,1, '', 'energia_reactiva_recibida', 'energia_reactiva_recibida', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_REACTIVA_RECIBIDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,38,20,1, '', 'energia_reactiva_entregada+recibida', 'energia_reactiva_entregada+recibida', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_REACTIVA_ENTREGADA+RECIBIDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,39,20,1, '', 'energia_reactiva_entregada-recibida', 'energia_reactiva_entregada-recibida', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_REACTIVA_ENTREGADA-RECIBIDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,40,21,1, '', 'energia_aparente_entregada', 'energia_aparente_entregada', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_APARENTE_ENTREGADA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,41,21,1, '', 'energia_aparente_recibida', 'energia_aparente_recibida', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_APARENTE_RECIBIDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,42,21,1, '', 'energia_aparente_entregada+recibida', 'energia_aparente_entregada+recibida', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_APARENTE_ENTREGADA+RECIBIDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,43,21,1, '', 'energia_aparente_entregada-recibida', 'energia_aparente_entregada-recibida', 'LBL_ELEMENT_TYPE_PARAM_ENERGIA_APARENTE_ENTREGADA-RECIBIDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,44,15,1, '', 'ultima_demanda', 'ultima_demanda', 'LBL_ELEMENT_TYPE_PARAM_ULTIMA_DEMANDA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,45,15,1, '', 'demanda_actual', 'demanda_Actual', 'LBL_ELEMENT_TYPE_PARAM_DEMANDA_ACTUAL' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,46,15,1, '', 'demanda_prevista', 'demanda_prevista', 'LBL_ELEMENT_TYPE_PARAM_DEMANDA_PREVISTA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70,2,47,15,1, '', 'demanda_maxima', 'demanda_maxima', 'LBL_ELEMENT_TYPE_PARAM_DEMANDA_MAXIMA' , null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(70, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 1, 'UP', 'The state of the equipment is: comunicates', 'LBL_ELEMENT_TYPE_STATE_UP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 2, 'DOWN', 'The state of the equipment is: out of communication', 'LBL_ELEMENT_TYPE_STATE_DOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 3, 'ALARMS', 'The state of the equipment is: with alarms', 'LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions (element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(70, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 70, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs 
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(217, 2, 3, 70, NULL, NULL, true, true, 'ALARM ANALIZADOR_RED NO CONNECTION', 'ALARM ANALIZADOR_RED NO CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_ANALIZADOR_RED_NO_CONNECTION', 'LBL_ALARM_CONFIGS_ALARM_ANALIZADOR_RED_NO_CONNECTION_DESC', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_state_alarms
--
INSERT INTO conf.element_state_alarms
(element_type_id, element_type_state_id, alarm_config_id, created_at, updated_at) VALUES
(70, 2, 217, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);