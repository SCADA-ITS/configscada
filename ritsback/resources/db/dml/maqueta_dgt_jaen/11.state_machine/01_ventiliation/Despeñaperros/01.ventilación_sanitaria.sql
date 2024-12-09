INSERT INTO static.sm_state_machines
(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'VENTILATION_SERVICE', 'States machine to calulate service regime in ventilation', 'LBL_VENTILATION_SERVICE', 'LBL_VENTILATION_SERVICE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_state
INSERT INTO static.sm_states
(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES
-- Estados para la única máquina estados que tiene este túnel
(1, 2, 'NORMAL', 'NORMAL', 'LBL_SM_STATE_NORMAL', NULL, true, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 'ALTO', 'ALTO', 'LBL_SM_STATE_ALTO', NULL, false, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 'ALTO-ALTO', 'ALTO-ALTO', 'LBL_SM_STATE_ALTO-ALTO', NULL, false, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 'MAXIMO', 'MAXIMO', 'LBL_SM_STATE_MAXIMO', NULL, false, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES
-- Estados para la única máquina estados que tiene este túnel
(1, 'Threshold_CO_0', 'Threshold CO 0 (ppm)', 'LBL_THRESHOLD_CO_0', 'LBL_THRESHOLD_CO_0', 3, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Threshold_CO_1', 'Threshold CO 1 (ppm)', 'LBL_THRESHOLD_CO_1', 'LBL_THRESHOLD_CO_1', 3, '60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Threshold_CO_2', 'Threshold CO 2 (ppm)', 'LBL_THRESHOLD_CO_2', 'LBL_THRESHOLD_CO_2', 3, '200', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4, 'Threshold_K_0', 'Threshold K 0 (k-1)', 'LBL_THRESHOLD_K_0', 'LBL_THRESHOLD_K_0', 3, '4.5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Threshold_K_1', 'Threshold K 1 (k-1)', 'LBL_THRESHOLD_K_1', 'LBL_THRESHOLD_K_1', 3, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Threshold_K_2', 'Threshold K 2 (k-1)', 'LBL_THRESHOLD_K_2', 'LBL_THRESHOLD_K_2', 3, '15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
-- Despeñaperros - Ponemos todas las posibles transiciones entre estados de sentido Madrid
-- Desde el estado inicial podremos transitar al resto de estados. De cada estado podremos transitar a los otros
(1, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NORMAL -> ALTO
(1, 2, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NORMAL -> ALTO-ALTO
(1, 2, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NORMAL -> MAXIMO

(1, 3, 1, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO -> ALTO-ALTO
(1, 3, 2, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO -> MAXIMO
(1, 3, 3, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO -> NORMAL

(1, 4, 1, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO-ALTO -> MAXIMO
(1, 4, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO-ALTO -> ALTO
(1, 4, 3, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO-ALTO -> NORMAL

(1, 5, 1, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MAXIMO-> ALTO-ALTO
(1, 5, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MAXIMO-> ALTO
(1, 5, 3, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- MAXIMO-> NORMAL


INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
-- Operations: 1 = Entre; 4 = Mayor que; 6 = Menor que
(1, 6, 1, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --< 30 CO
(2, 1, 1, 2, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --> 30 CO  < 60 CO 
(3, 1, 2, 3, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 60 CO < 200 CO 
(4, 4, 3, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 200 CO

(5, 6, 4, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --< 4.5 opac
(6, 1, 4, 5, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 4.5 opac < 9 opac
(7, 1, 5, 6, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 9 opac < 15 opac
(8, 4, 6, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- > 15 opac


INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES
--NORMAL -> ALTO
(1, 2, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 1, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--NORMAL -> ALTO-ALTO
(1, 2, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--NORMAL -> MAXIMO
(1, 2, 3, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 3, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO -> NORMAL
(1, 3, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO -> ALTO-ALTO
(1, 3, 1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 1, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO -> MAXIMO
(1, 3, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO-ALTO -> NORMAL
(1, 4, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO-ALTO -> ALTO
(1, 4, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ALTO-ALTO -> MAXIMO
(1, 4, 1, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 1, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--MAXIMO -> NORMAL
(1, 5, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 5, 3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--MAXIMO -> ALTO
(1, 5, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 5, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--MAXIMO -> ALTO-ALTO
(1, 5, 1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 1, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
