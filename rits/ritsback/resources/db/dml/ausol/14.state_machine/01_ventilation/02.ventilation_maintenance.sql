
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
(1000, 1, 'INIT', 'Init', 'LBL_SM_STATE_INIT', NULL, true, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 2, 'SERVICE', 'Service', 'LBL_SM_STATE_SERVICE', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 3, 'ALERT', 'Alert', 'LBL_SM_STATE_ALERT', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 4, 'ALARM', 'Alarm', 'LBL_SM_STATE_ALARM', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_thresholds
--
-- Para el id seguiremos el siguiente criterio:
-- 1.- Id maquina de estados (4 cifras)
-- 2.- Orden del umbral (2 cifras)
INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES

(100001, 'Threshold_CO_0', 'Threshold CO 0 (ppm)', 'LBL_THRESHOLD_CO_0', 'LBL_THRESHOLD_CO_0', 1, '15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100002, 'Threshold_CO_1', 'Threshold CO 1 (ppm)', 'LBL_THRESHOLD_CO_1', 'LBL_THRESHOLD_CO_1', 1, '20', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100003, 'Threshold_CO_2', 'Threshold CO 2 (ppm)', 'LBL_THRESHOLD_CO_2', 'LBL_THRESHOLD_CO_2', 1, '25', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100004, 'Threshold_CO_3', 'Threshold CO 3 (ppm)', 'LBL_THRESHOLD_CO_3', 'LBL_THRESHOLD_CO_3', 1, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(100005, 'Threshold_K_0', 'Threshold K 0 (m-1)', 'LBL_THRESHOLD_K_0', 'LBL_THRESHOLD_K_0', 3, '0.0020', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100006, 'Threshold_K_1', 'Threshold K 1 (m-1)', 'LBL_THRESHOLD_K_1', 'LBL_THRESHOLD_K_1', 3, '0.0030', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100007, 'Threshold_K_2', 'Threshold K 2 (m-1)', 'LBL_THRESHOLD_K_2', 'LBL_THRESHOLD_K_2', 3, '0.0040', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100008, 'Threshold_K_3', 'Threshold K 3 (m-1)', 'LBL_THRESHOLD_K_3', 'LBL_THRESHOLD_K_3', 3, '0.0050', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(100009, 'Threshold_NO2_0', 'Threshold NO2 0 (ppm)', 'LBL_THRESHOLD_NO2_0', 'LBL_THRESHOLD_NO2_0', 3, '0.05', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100010, 'Threshold_NO2_1', 'Threshold NO2 1 (ppm)', 'LBL_THRESHOLD_NO2_1', 'LBL_THRESHOLD_NO2_1', 3, '0.20', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100011, 'Threshold_NO2_2', 'Threshold NO2 2 (ppm)', 'LBL_THRESHOLD_NO2_2', 'LBL_THRESHOLD_NO2_2', 3, '0.25', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100012, 'Threshold_NO2_3', 'Threshold NO2 3 (ppm)', 'LBL_THRESHOLD_NO2_3', 'LBL_THRESHOLD_NO2_3', 3, '0.40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(100013, 'Threshold_Timer_CO_1', 'Threshold Timer CO 1 (min)', 'LBL_THRESHOLD_TIMER_CO_1', 'LBL_THRESHOLD_TIMER_CO_1', 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100014, 'Threshold_Timer_CO_2', 'Threshold Timer CO 2 (min)', 'LBL_THRESHOLD_TIMER_CO_2', 'LBL_THRESHOLD_TIMER_CO_2', 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(100015, 'Threshold_Timer_K_1', 'Timer K 1 (min)', 'LBL_THRESHOLD_TIMER_K_1', 'LBL_THRESHOLD_TIMER_K_1', 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100016, 'Threshold_Timer_K_2', 'Timer K 2 (min)', 'LBL_THRESHOLD_TIMER_K_2', 'LBL_THRESHOLD_TIMER_K_2', 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(100017, 'Threshold_Timer_NO2_1', 'Threshold Timer NO2 1(min)', 'LBL_THRESHOLD_TIMER_NO2_1', 'LBL_THRESHOLD_TIMER_NO2_1', 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100018, 'Threshold_Timer_NO2_2', 'Threshold Timer NO2 2(min)', 'LBL_THRESHOLD_TIMER_NO2_2', 'LBL_THRESHOLD_TIMER_NO2_2', 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_timers
--
-- Para el id seguiremos el siguiente criterio:
-- 1.- Id maquina de estados (4 cifras)
-- 2.- Orden del timer (2 cifras)
INSERT INTO static.sm_timers
(timer_id, alias, description, label_alias, label_description, threshold_id, element_type_id, element_type_param_id, param_type_id, enabled, visible, created_at, updated_at)VALUES
(100001, 'Timer_CO_1', 'Timer CO 1 (min)', 'LBL_TIMER_CO_1', 'LBL_TIMER_CO_1', 100002, 19, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100002, 'Timer_CO_2', 'Timer CO 2 (min)', 'LBL_TIMER_CO_2', 'LBL_TIMER_CO_2', 100003, 19, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(100003, 'Timer_K_1', 'Timer K 1 (min)', 'LBL_TIMER_K_1', 'LBL_TIMER_K_1', 100006, 21, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100004, 'Timer_K_2', 'Timer K 2 (min)', 'LBL_TIMER_K_2', 'LBL_TIMER_K_2', 100007, 21, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(100005, 'Timer_NO2_1', 'Timer NO2 1 (min)', 'LBL_TIMER_NO2_1', 'LBL_TIMER_NO2_1', 100010, 20, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100006, 'Timer_NO2_2', 'Timer NO2 2 (min)', 'LBL_TIMER_NO2_2', 'LBL_TIMER_NO2_2', 100011, 20, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_conditions
--
-- Operations: 1 = Entre; 4 = Mayor que; 6 = Menor que
INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Id maquina de estados (4 cifras)
-- 2.- Orden de la condición (2 cifras)

-- CO
(100001, 4, 100002, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_1
(100002, 4, 100003, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_2
(100003, 4, 100004, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_3

(100004, 6, 100001, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO < Threshold_CO_0

(100006, 4, 100013, NULL, NULL, NULL, NULL, 100001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_CO_1 > Threshold_Timer_CO_1
(100007, 4, 100014, NULL, NULL, NULL, NULL, 100002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_CO_2 > Threshold_Timer_CO_2

-- K 
(100008, 4, 100006, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_1
(100009, 4, 100007, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_2
(100010, 4, 100008, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_3

(100011, 6, 100005, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K < Threshold_K_0

(100012, 4, 100015, NULL, NULL, NULL, NULL, 100003, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_K_1 > Threshold_Timer_K_1
(100013, 4, 100016, NULL, NULL, NULL, NULL, 100004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_K_2 > Threshold_Timer_K_2

-- NO
(100014, 4, 100010, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_1
(100015, 4, 100011, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_2
(100016, 4, 100012, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_3

(100017, 6, 100009, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 < Threshold_NO2_0

(100018, 4, 100017, NULL, NULL, NULL, NULL, 100005, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_NO2_1 > Threshold_Timer_NO2_1
(100019, 4, 100018, NULL, NULL, NULL, NULL, 100006, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); --- Timer_NO2_2 > Threshold_Timer_NO2_2


-- 
-- Table: static.sm_state_machines
--
INSERT INTO static.sm_state_timers
(state_machine_id, state_id, timer_id, enabled, visible, created_at, updated_at) VALUES
--SERVICE
(1000, 2, 100001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_CO_1
(1000, 2, 100003, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_K_1
(1000, 2, 100005, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_NO2_1

--ALERT
(1000, 3, 100002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_CO_2
(1000, 3, 100004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_K_2
(1000, 3, 100006, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Timer_NO2_2


-- 
-- Table: static.sm_transitions
--
INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
(1000, 1, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> SERVICE

(1000, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SERVICE --> ALERT
(1000, 2, 2, true, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SERVICE --> INIT

(1000, 3, 1, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT --> ALARM
(1000, 3, 2, true, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT --> INIT

(1000, 4, 1, true, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- ALARM --> INIT

-- 
-- Table: static.sm_transition_conditions
--
INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES

-- INIT --> SERVICE
-- CO > Threshold_CO_1 || K > Threshold_K_1  || NO2 > Threshold_NO2_1
(1000, 1, 1, 100001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 1, 1, 100008, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1000, 1, 1, 100014, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

-- SERVICE --> ALERT
-- CO > Threshold_CO_2 || K > Threshold_K_2  || NO2 > Threshold_NO2_2
(1000, 2, 1, 100002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 2, 1, 100009, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 2, 1, 100015, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
-- Timer_CO_1 > Threshold_Timer_CO_1 || Timer_K_1 > Threshold_Timer_K_1 ||  Timer_NO2_1 > Threshold_Timer_NO2_1
(1000, 2, 1, 100006, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 2, 1, 100012, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 2, 1, 100018, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

-- SERVICE --> INIT
-- CO < Threshold_CO_0 && K < Threshold_K_0  && NO2 > Threshold_NO2_0
(1000, 2, 2, 100004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 2, 2, 100011, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1000, 2, 2, 100017, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALERT_-> ALARM
-- CO > Threshold_CO_3 || K > Threshold_K_3 || NO2 > Threshold_NO2_3
(1000, 3, 1, 100003, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 3, 1, 100010, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 3, 1, 100016, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Timer_CO_2 > Threshold_Timer_CO_2 || Timer_K_2 > Threshold_Timer_K_2 ||  Timer_NO2_1 > Threshold_Timer_NO2_2
(1000, 3, 1, 100007, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 3, 1, 100013, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1000, 3, 1, 100019, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALERT --> INIT
-- CO < Threshold_CO_1 && K < Threshold_K_1  && NO2 > Threshold_NO2_1
(1000, 3, 2, 100004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 3, 2, 100011, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1000, 3, 2, 100017, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALARM --> INIT
-- CO < Threshold_CO_1 && K < Threshold_K_1  && NO2 > Threshold_NO2_1
(1000, 4, 1, 100004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1000, 4, 1, 100011, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1000, 4, 1, 100017, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
