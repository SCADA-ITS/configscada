
-- 
-- Table: static.sm_state_machines
--
INSERT INTO static.sm_state_machines
(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES(1000, 'VENTILATION_MAINTENANCE', 'States machine to calulate service regime in ventilation maintenance', 'LBL_VENTILATION_MAINTENANCE', 'LBL_VENTILATION_MAINTENANCE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_state_machines
--
INSERT INTO static.sm_states
(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES
(1000, 1, 'PARADO', 'PARADO', 'LBL_SM_STATE_INIT', NULL, true, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 2, 'BAJO', 'BAJO', 'LBL_SM_STATE_SERVICE', NULL, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 3, 'MEDIO', 'MEDIO', 'LBL_SM_STATE_ALERT_BY_THRESHOLD', NULL, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 4, 'ALTO', 'ALTO', 'LBL_SM_STATE_ALARM_BY_THRESHOLD', NULL, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_thresholds
--
INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES

(10001, 'Threshold_CO_0', 'Threshold CO 0 (ppm)', 'LBL_THRESHOLD_CO_0', 'LBL_THRESHOLD_CO_0', 1, '15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10002, 'Threshold_CO_2', 'Threshold CO 2 (ppm)', 'LBL_THRESHOLD_CO_2', 'LBL_THRESHOLD_CO_2', 1, '25', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10003, 'Threshold_CO_4', 'Threshold CO 4 (ppm)', 'LBL_THRESHOLD_CO_4', 'LBL_THRESHOLD_CO_4', 1, '40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10004, 'Threshold_K_0', 'Threshold K 0 (m-1)', 'LBL_THRESHOLD_K_0', 'LBL_THRESHOLD_K_0', 3, '0.0020', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10005, 'Threshold_K_2', 'Threshold K 2 (m-1)', 'LBL_THRESHOLD_K_2', 'LBL_THRESHOLD_K_2', 3, '0.0040', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10006, 'Threshold_K_4', 'Threshold K 4 (m-1)', 'LBL_THRESHOLD_K_4', 'LBL_THRESHOLD_K_4', 3, '0.0060', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10007, 'Threshold_NO2_0', 'Threshold NO2 0 (ppm)', 'LBL_THRESHOLD_NO2_0', 'LBL_THRESHOLD_NO2_0', 3, '0.05', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10008, 'Threshold_NO2_2', 'Threshold NO2 2 (ppm)', 'LBL_THRESHOLD_NO2_2', 'LBL_THRESHOLD_NO2_2', 3, '0.25', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10009, 'Threshold_NO2_4', 'Threshold NO2 4 (ppm)', 'LBL_THRESHOLD_NO2_4', 'LBL_THRESHOLD_NO2_4', 3, '0.40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
(1000, 1, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- PARADO --> BAJO
(1000, 1, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- PARADO --> MEDIO
(1000, 1, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- PARADO --> ALTO

(1000, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- BAJO --> MEDIO
(1000, 2, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- BAJO --> ALTO
(1000, 2, 3, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- BAJO --> PARADO

(1000, 3, 1, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MEDIO --> ALTO
(1000, 3, 2, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MEDIO --> BAJO
(1000, 3, 3, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- MEDIO --> PARADO

(1000, 4, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO --> MEDIO
(1000, 4, 2, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALTO --> BAJO
(1000, 4, 3, false, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- ALTO --> PARADO



-- 
-- Table: static.sm_conditions
--
INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
(10001, 6, 10001, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO < Threshold_CO_0
(10002, 1, 10001, 10002, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_0 CO < Threshold_CO_2
(10003, 1, 10002, 10003, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_2 CO < Threshold_CO_4
(10004, 4, 10003, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_2

(10005, 6, 10004, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K < Threshold_K_0
(10006, 1, 10004, 10005, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_0 K < Threshold_K_2
(10007, 1, 10005, 10006, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_2 K < Threshold_K_4
(10008, 4, 10006, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_4

(100010, 6, 10007, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 < Threshold_NO2_0
(100011, 1, 10007, 10008, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_0 NO2 < Threshold_NO2_2
(100012, 1, 10008, 10009, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_2 NO2 < Threshold_NO2_4
(100013, 4, 10009, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); --- NO2 > Threshold_NO2_4


INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES

-- PARADO --> BAJO
(1000, 1, 1, 10002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 1, 1, 10006, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 1, 1, 100011, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

-- PARADO --> MEDIO
(1000, 1, 2, 10003, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 1, 2, 10007, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 1, 2, 100012, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

-- PARADO --> ALTO
(1, 1, 3, 10004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 1, 3, 10008, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 1, 3, 100013, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  


-- BAJO --> MEDIO
(1, 2, 1, 10003, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1, 10007, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1, 100012, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- BAJO --> ALTO
(1, 2, 2, 10004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 2, 10008, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 2, 100013, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- BAJO --> PARADO
(1, 2, 3, 10001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 3, 10005, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 3, 100010, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MEDIO --> ALTO
(1, 3, 1, 10004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 1, 10008, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 3, 1, 100013, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MEDIO --> BAJO
(1, 3, 2, 10002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 2, 10006, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 3, 2, 100011, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MEDIO --> PARADO
(1, 3, 3, 10001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 3, 10005, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 3, 3, 100010, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- ALTO --> MEDIO
(1, 4, 1, 10003, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 1, 10007, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 4, 1, 100012, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALTO --> BAJO
(1, 4, 2, 10002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 2, 10006, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 4, 2, 100011, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALTO --> PARADO
(1, 4, 3, 10001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 3, 10005, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 4, 3, 100010, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);










