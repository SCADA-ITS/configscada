INSERT INTO static.sm_state_machines
(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2, 'VENTILATION_CONFORT', 'States machine to calulate service regime in ventilation', 'LBL_VENTILATION_CONFORT', 'LBL_VENTILATION_CONFORT_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_state
INSERT INTO static.sm_states
(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES
-- Estados para la única máquina estados que tiene este túnel
(2, 2, 'NORMAL', 'NORMAL', 'LBL_SM_STATE_NORMAL', NULL, true, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'ALTO', 'ALTO', 'LBL_SM_STATE_ALTO', NULL, false, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 'ALTO-ALTO', 'ALTO-ALTO', 'LBL_SM_STATE_ALTO-ALTO', NULL, false, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 'MAXIMO', 'MAXIMO', 'LBL_SM_STATE_MAXIMO', NULL, false, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES
-- Estados
(12, 'Threshold_CO_0', 'Threshold CO 0 (ppm)', 'LBL_THRESHOLD_CO_0', 'LBL_THRESHOLD_CO_0', 3, '15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 'Threshold_CO_1', 'Threshold CO 1 (ppm)', 'LBL_THRESHOLD_CO_1', 'LBL_THRESHOLD_CO_1', 3, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 'Threshold_CO_2', 'Threshold CO 2 (ppm)', 'LBL_THRESHOLD_CO_2', 'LBL_THRESHOLD_CO_2', 3, '100', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(42, 'Threshold_K_0', 'Threshold K 0 (k-1)', 'LBL_THRESHOLD_K_0', 'LBL_THRESHOLD_K_0', 3, '2.25', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(52, 'Threshold_K_1', 'Threshold K 1 (k-1)', 'LBL_THRESHOLD_K_1', 'LBL_THRESHOLD_K_1', 3, '4.5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(62, 'Threshold_K_2', 'Threshold K 2 (k-1)', 'LBL_THRESHOLD_K_2', 'LBL_THRESHOLD_K_2', 3, '7.5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
-- Despeñaperros - Ponemos todas las posibles transiciones entre estados de sentido Madrid
-- Desde el estado inicial podremos transitar al resto de estados. De cada estado podremos transitar a los otros
(2, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NORMAL -> ALTO
(2, 2, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NORMAL -> ALTO-ALTO
(2, 2, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NORMAL -> MAXIMO

(2, 3, 1, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO -> ALTO-ALTO
(2, 3, 2, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO -> MAXIMO
(2, 3, 3, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO -> NORMAL

(2, 4, 1, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO-ALTO -> MAXIMO
(2, 4, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO-ALTO -> ALTO
(2, 4, 3, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO-ALTO -> NORMAL

(2, 5, 1, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MAXIMO-> ALTO-ALTO
(2, 5, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MAXIMO-> ALTO
(2, 5, 3, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- MAXIMO-> NORMAL


INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
-- Operations: 1 = Entre; 4 = Mayor que; 6 = Menor que
(12, 6, 12, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --< 15 CO
(22, 1, 12, 22, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --> 15 CO  < 30 CO 
(32, 1, 22, 32, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 30 CO < 100 CO 
(42, 4, 32, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 100 CO

(52, 6, 42, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --< 2.25 opac
(62, 1, 42, 52, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 2.25 opac < 4.5 opac
(72, 1, 52, 62, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 4.5 opac < 7.5 opac
(82, 4, 62, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- > 7.5 opac


INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES
--NORMAL -> ALTO
(2, 2, 1, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 2, 1, 62, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--NORMAL -> ALTO-ALTO
(2, 2, 2, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 2, 2, 72, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--NORMAL -> MAXIMO
(2, 2, 3, 42, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 2, 3, 82, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO -> NORMAL
(2, 3, 3, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 3, 3, 52, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO -> ALTO-ALTO
(2, 3, 1, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 3, 1, 72, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO -> MAXIMO
(2, 3, 2, 42, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 3, 2, 82, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO-ALTO -> NORMAL
(2, 4, 3, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 4, 3, 52, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO-ALTO -> ALTO
(2, 4, 2, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 4, 2, 62, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO-ALTO -> MAXIMO
(2, 4, 1, 42, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 4, 1, 82, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--MAXIMO -> NORMAL
(2, 5, 3, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 5, 3, 52, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--MAXIMO -> ALTO
(2, 5, 2, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(2, 5, 2, 62, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--MAXIMO -> ALTO-ALTO
(2, 5, 1, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 1, 72, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
