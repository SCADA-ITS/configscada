-- 
-- Table: static.sm_state_machines
--
INSERT INTO static.sm_state_machines
(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES(1, 'VENTILATION_SERVICE', 'States machine to calulate service regime in ventilation', 'LBL_VENTILATION_SERVICE', 'LBL_VENTILATION_SERVICE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_state_machines
--
INSERT INTO static.sm_states
(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'PARADO', 'PARADO', 'LBL_SM_STATE_INIT', NULL, true, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'BAJO', 'BAJO', 'LBL_SM_STATE_SERVICE', NULL, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 'MEDIO', 'MEDIO', 'LBL_SM_STATE_ALERT_BY_THRESHOLD', NULL, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 'ALTO', 'ALTO', 'LBL_SM_STATE_ALARM_BY_THRESHOLD', NULL, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_thresholds
--
INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES

(1, 'Threshold_CO_0', 'Threshold CO 0 (ppm)', 'LBL_THRESHOLD_CO_0', 'LBL_THRESHOLD_CO_0', 1, '40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Threshold_CO_2', 'Threshold CO 2 (ppm)', 'LBL_THRESHOLD_CO_2', 'LBL_THRESHOLD_CO_2', 1, '75', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Threshold_CO_4', 'Threshold CO 4 (ppm)', 'LBL_THRESHOLD_CO_4', 'LBL_THRESHOLD_CO_4', 1, '200', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4, 'Threshold_K_0', 'Threshold K 0 (m-1)', 'LBL_THRESHOLD_K_0', 'LBL_THRESHOLD_K_0', 3, '0.0040', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Threshold_K_2', 'Threshold K 2 (m-1)', 'LBL_THRESHOLD_K_2', 'LBL_THRESHOLD_K_2', 3, '0.0070', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Threshold_K_4', 'Threshold K 4 (m-1)', 'LBL_THRESHOLD_K_4', 'LBL_THRESHOLD_K_4', 3, '0.0120', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(7, 'Threshold_NO2_0', 'Threshold NO2 0 (ppm)', 'LBL_THRESHOLD_NO2_0', 'LBL_THRESHOLD_NO2_0', 3, '0.35', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Threshold_NO2_2', 'Threshold NO2 2 (ppm)', 'LBL_THRESHOLD_NO2_2', 'LBL_THRESHOLD_NO2_2', 3, '1.00', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'Threshold_NO2_4', 'Threshold NO2 4 (ppm)', 'LBL_THRESHOLD_NO2_4', 'LBL_THRESHOLD_NO2_4', 3, '5.00', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- PARADO --> BAJO
(1, 1, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- PARADO --> MEDIO
(1, 1, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- PARADO --> ALTO

(1, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- BAJO --> MEDIO
(1, 2, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- BAJO --> ALTO
(1, 2, 3, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- BAJO --> PARADO

(1, 3, 1, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MEDIO --> ALTO
(1, 3, 2, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MEDIO --> BAJO
(1, 3, 3, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MEDIO --> PARADO

(1, 4, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO --> MEDIO
(1, 4, 2, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO --> BAJO
(1, 4, 3, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- ALTO --> PARADO



-- 
-- Table: static.sm_conditions
--
INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
(1, 6, 1, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO < Threshold_CO_0
(2, 1, 1, 2, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_0 CO < Threshold_CO_2
(3, 1, 2, 3, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_2 CO < Threshold_CO_4
(4, 4, 3, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_2

(5, 6, 4, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K < Threshold_K_0
(6, 1, 4, 5, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_0 K < Threshold_K_2
(7, 1, 5, 6, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_2 K < Threshold_K_4
(8, 4, 6, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_4

(10, 6, 7, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 < Threshold_NO2_0
(11, 1, 7, 8, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_0 NO2 < Threshold_NO2_2
(12, 1, 8, 9, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_2 NO2 < Threshold_NO2_4
(13, 4, 9, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); --- NO2 > Threshold_NO2_4









INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES

-- PARADO --> BAJO
(1, 1, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 1, 1, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 1, 1, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

-- PARADO --> MEDIO
(1, 1, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 1, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 1, 2, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

-- PARADO --> ALTO
(1, 1, 3, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 1, 3, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 1, 3, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  


-- BAJO --> MEDIO
(1, 2, 1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- BAJO --> ALTO
(1, 2, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- BAJO --> PARADO
(1, 2, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 3, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MEDIO --> ALTO
(1, 3, 1, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 1, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 3, 1, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MEDIO --> BAJO
(1, 3, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 3, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MEDIO --> PARADO
(1, 3, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 3, 3, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- ALTO --> MEDIO
(1, 4, 1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 1, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 4, 1, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALTO --> BAJO
(1, 4, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 4, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALTO --> PARADO
(1, 4, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 4, 3, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);














