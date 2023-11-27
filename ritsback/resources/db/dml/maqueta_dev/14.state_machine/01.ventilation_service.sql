
-- 
-- Table: static.sm_thresholds
--
INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES

(1, 'Threshold_CO_0', 'Threshold CO 0 (ppm)', 'LBL_THRESHOLD_CO_0', 'LBL_THRESHOLD_CO_0', 1, '40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Threshold_CO_1', 'Threshold CO 1 (ppm)', 'LBL_THRESHOLD_CO_1', 'LBL_THRESHOLD_CO_1', 1, '50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Threshold_CO_2', 'Threshold CO 2 (ppm)', 'LBL_THRESHOLD_CO_2', 'LBL_THRESHOLD_CO_2', 1, '75', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Threshold_CO_3', 'Threshold CO 3 (ppm)', 'LBL_THRESHOLD_CO_3', 'LBL_THRESHOLD_CO_3', 1, '120', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Threshold_CO_4', 'Threshold CO 4 (ppm)', 'LBL_THRESHOLD_CO_4', 'LBL_THRESHOLD_CO_4', 1, '200', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(6, 'Threshold_K_0', 'Threshold K 0 (m-1)', 'LBL_THRESHOLD_K_0', 'LBL_THRESHOLD_K_0', 3, '0.0040', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Threshold_K_1', 'Threshold K 1 (m-1)', 'LBL_THRESHOLD_K_1', 'LBL_THRESHOLD_K_1', 3, '0.0050', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Threshold_K_2', 'Threshold K 2 (m-1)', 'LBL_THRESHOLD_K_2', 'LBL_THRESHOLD_K_2', 3, '0.0070', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'Threshold_K_3', 'Threshold K 3 (m-1)', 'LBL_THRESHOLD_K_3', 'LBL_THRESHOLD_K_3', 3, '0.0090', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Threshold_K_4', 'Threshold K 4 (m-1)', 'LBL_THRESHOLD_K_4', 'LBL_THRESHOLD_K_4', 3, '0.0120', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(11, 'Threshold_NO2_0', 'Threshold NO2 0 (ppm)', 'LBL_THRESHOLD_NO2_0', 'LBL_THRESHOLD_NO2_0', 3, '0.35', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'Threshold_NO2_1', 'Threshold NO2 1 (ppm)', 'LBL_THRESHOLD_NO2_1', 'LBL_THRESHOLD_NO2_1', 3, '0.40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'Threshold_NO2_2', 'Threshold NO2 2 (ppm)', 'LBL_THRESHOLD_NO2_2', 'LBL_THRESHOLD_NO2_2', 3, '1.00', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'Threshold_NO2_3', 'Threshold NO2 3 (ppm)', 'LBL_THRESHOLD_NO2_3', 'LBL_THRESHOLD_NO2_3', 3, '3.50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'Threshold_NO2_4', 'Threshold NO2 4 (ppm)', 'LBL_THRESHOLD_NO2_4', 'LBL_THRESHOLD_NO2_4', 3, '5.00', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(16, 'Threshold_Timer_CO_1', 'Threshold Timer CO 1 (min)', 'LBL_THRESHOLD_TIMER_CO_1', 'LBL_THRESHOLD_TIMER_CO_1', 1, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 'Threshold_Timer_CO_2', 'Threshold Timer CO 2 (min)', 'LBL_THRESHOLD_TIMER_CO_2', 'LBL_THRESHOLD_TIMER_CO_2', 1, '20', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 'Threshold_Timer_CO_3', 'Threshold Timer CO 3 (min)', 'LBL_THRESHOLD_TIMER_CO_3', 'LBL_THRESHOLD_TIMER_CO_3', 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(19, 'Threshold_Timer_K_1', 'Timer K 1 (min)', 'LBL_THRESHOLD_TIMER_K_1', 'LBL_THRESHOLD_TIMER_K_1', 1, '45', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'Threshold_Timer_K_2', 'Timer K 2 (min)', 'LBL_THRESHOLD_TIMER_K_2', 'LBL_THRESHOLD_TIMER_K_2', 1, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 'Threshold_Timer_K_3', 'Timer K 3 (min)', 'LBL_THRESHOLD_TIMER_K_3', 'LBL_THRESHOLD_TIMER_K_3', 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(22, 'Threshold_Timer_NO2_1', 'Threshold Timer NO2 1(min)', 'LBL_THRESHOLD_TIMER_NO2_1', 'LBL_THRESHOLD_TIMER_NO2_1', 1, '45', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'Threshold_Timer_NO2_2', 'Threshold Timer NO2 2(min)', 'LBL_THRESHOLD_TIMER_NO2_2', 'LBL_THRESHOLD_TIMER_NO2_2', 1, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 'Threshold_Timer_NO2_3', 'Threshold Timer NO2 3(min)', 'LBL_THRESHOLD_TIMER_NO2_3', 'LBL_THRESHOLD_TIMER_NO2_3', 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_timers
--
INSERT INTO static.sm_timers
(timer_id, alias, description, label_alias, label_description, threshold_id, element_type_id, element_type_param_id, param_type_id, enabled, visible, created_at, updated_at)VALUES
(1, 'Timer_CO_1', 'Timer CO 1 (min)', 'LBL_TIMER_CO_1', 'LBL_TIMER_CO_1', 2, 19, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Timer_CO_2', 'Timer CO 2 (min)', 'LBL_TIMER_CO_2', 'LBL_TIMER_CO_2', 3, 19, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Timer_CO_3', 'Timer CO 3 (min)', 'LBL_TIMER_CO_3', 'LBL_TIMER_CO_3', 4, 19, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4, 'Timer_K_1', 'Timer K 1 (min)', 'LBL_TIMER_K_1', 'LBL_TIMER_K_1', 7, 21, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Timer_K_2', 'Timer K 2 (min)', 'LBL_TIMER_K_2', 'LBL_TIMER_K_2', 8, 21, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Timer_K_3', 'Timer K 3 (min)', 'LBL_TIMER_K_3', 'LBL_TIMER_K_3', 9, 21, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(7, 'Timer_NO2_1', 'Timer NO2 1 (min)', 'LBL_TIMER_NO2_1', 'LBL_TIMER_NO2_1', 12, 20, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Timer_NO2_2', 'Timer NO2 2 (min)', 'LBL_TIMER_NO2_2', 'LBL_TIMER_NO2_2', 13, 20, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'Timer_NO2_3', 'Timer NO2 3 (min)', 'LBL_TIMER_NO2_3', 'LBL_TIMER_NO2_3', 14, 20, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: static.sm_conditions
--
INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
(1, 4, 2, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_1
(2, 4, 3, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_2
(3, 4, 4, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_3
(4, 4, 5, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO > Threshold_CO_4

(5, 6, 1, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO < Threshold_CO_0
(6, 6, 2, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO < Threshold_CO_1
(7, 6, 3, NULL, 19, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- CO < Threshold_CO_2

(8, 4, 16, NULL, NULL, NULL, NULL, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_CO_1 > Threshold_Timer_CO_1
(9, 4, 17, NULL, NULL, NULL, NULL, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_CO_2 > Threshold_Timer_CO_2
(10, 4, 18, NULL, NULL, NULL, NULL, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_CO_3 > Threshold_Timer_CO_3

(11, 4, 7, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_1
(12, 4, 8, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_2
(13, 4, 9, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_3
(14, 4, 10, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K > Threshold_K_4

(15, 6, 6, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K < Threshold_K_0
(16, 6, 7, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K < Threshold_K_1
(17, 6, 8, NULL, 21, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- K < Threshold_K_2

(18, 4, 19, NULL, NULL, NULL, NULL, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_K_1 > Threshold_Timer_K_1
(19, 4, 20, NULL, NULL, NULL, NULL, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_K_2 > Threshold_Timer_K_2
(20, 4, 21, NULL, NULL, NULL, NULL, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_K_3 > Threshold_Timer_K_3

(21, 4, 12, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_1
(22, 4, 13, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_2
(23, 4, 14, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_3
(24, 4, 15, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 > Threshold_NO2_4

(25, 6, 11, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 < Threshold_NO2_0
(26, 6, 12, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 < Threshold_NO2_1
(27, 6, 13, NULL, 20, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- NO2 < Threshold_NO2_2

(28, 4, 22, NULL, NULL, NULL, NULL, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_NO2_1 > Threshold_Timer_NO2_1
(29, 4, 23, NULL, NULL, NULL, NULL, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --- Timer_NO2_2 > Threshold_Timer_NO2_2
(30, 4, 24, NULL, NULL, NULL, NULL, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); --- Timer_NO2_3 > Threshold_Timer_NO2_3


-- 
-- Table: static.sm_state_machines
--
INSERT INTO static.sm_state_machines
(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'VENTILATION_SERVICE', 'States machine to calulate service regime in ventilation', 'LBL_VENTILATION', 'LBL_VENTIALTION_DESCRIPTION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'EXAMPLE', 'States machine example', 'LBL_SM_EXAMPLE', 'LBL_SM_EXAMPLE_DESCRIPTION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.sm_state_machines
--
INSERT INTO static.sm_states
(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'INIT', 'Init', 'LBL_SM_STATE_INIT', NULL, true, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'INIT', 'Init', 'LBL_SM_STATE_INIT', NULL, true, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'SERVICE', 'Service', 'LBL_SM_STATE_SERVICE', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'SERVICE', 'Service', 'LBL_SM_STATE_SERVICE', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 'ALERT_BY_THRESHOLD', 'Alert by Threshold', 'LBL_SM_STATE_ALERT_BY_THRESHOLD', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'ALERT_BY_THRESHOLD', 'Alert by Threshold', 'LBL_SM_STATE_ALERT_BY_THRESHOLD', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 'ALERT_BY_TIMER', 'Alert by Timer', 'LBL_SM_STATE_ALERT_BY_TIMER', NULL, false, 5,  true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 'ALERT_BY_TIMER', 'Alert by Timer', 'LBL_SM_STATE_ALERT_BY_TIMER', NULL, false, 5,  true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 'ALARM_BY_THRESHOLD', 'Alarm by Threshold', 'LBL_SM_STATE_ALARM_BY_THRESHOLD', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 'ALARM_BY_THRESHOLD', 'Alarm by Threshold', 'LBL_SM_STATE_ALARM_BY_THRESHOLD', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 6, 'ALARM_BY_TIMER', 'Alarm by Timer', 'LBL_SM_STATE_ALARM_BY_TIMER', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 6, 'ALARM_BY_TIMER', 'Alarm by Timer', 'LBL_SM_STATE_ALARM_BY_TIMER', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 7, 'EMERGENCY', 'Emergency', 'LBL_SM_STATE_EMERGENCY', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 7, 'EMERGENCY', 'Emergency', 'LBL_SM_STATE_EMERGENCY', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: static.sm_state_machines
--
INSERT INTO static.sm_state_timers
(state_machine_id, state_id, timer_id, enabled, visible, created_at, updated_at) VALUES

(1, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_CO_1
(1, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_K_1
(1, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_NO2_1

(1, 3, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_CO_2
(1, 3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_K_2
(1, 3, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_NO2_2
(1, 4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_CO_2
(1, 4, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_K_2
(1, 4, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_NO2_2

(1, 5, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_CO_3
(1, 5, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_K_3
(1, 5, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_NO2_3
(1, 6, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_CO_3
(1, 6, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Timer_K_3
(1, 6, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Timer_NO2_3

INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> SERVICE
(2, 1, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> SERVICE

(1, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SERVICE --> ALERT_BY_THRESHOLD
(2, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SERVICE --> ALERT_BY_THRESHOLD
(1, 2, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SERVICE --> ALERT_BY_TIMER
(2, 2, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SERVICE --> ALERT_BY_TIMER
(1, 2, 3, true, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SERVICE --> INIT
(2, 2, 3, true, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SERVICE --> INIT

(1, 3, 1, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_THRESHOLD --> ALARM_BY_THRESHOLD
(2, 3, 1, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_THRESHOLD --> ALARM_BY_THRESHOLD
(1, 3, 2, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_THRESHOLD --> ALARM_BY_TIMER
(2, 3, 2, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_THRESHOLD --> ALARM_BY_TIMER
(1, 3, 3, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_THRESHOLD --> SERVICE
(2, 3, 3, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_THRESHOLD --> SERVICE

(1, 4, 1, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_TIMER --> ALARM_BY_THRESHOLD
(2, 4, 1, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_TIMER --> ALARM_BY_THRESHOLD
(1, 4, 2, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_TIMER --> ALARM_BY_TIMER
(2, 4, 2, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_TIMER --> ALARM_BY_TIMER
(1, 4, 3, true,  2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_THRESHOLD --> SERVICE
(2, 4, 3, true,  2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALERT_BY_THRESHOLD --> SERVICE

(1, 5, 1, false, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALARM_BY_THRESHOLD --> EMERGENCY
(2, 5, 1, false, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALARM_BY_THRESHOLD --> EMERGENCY
(1, 5, 2, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALARM_BY_THRESHOLD --> ALERT_BY_THRESHOLD
(2, 5, 2, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALARM_BY_THRESHOLD --> ALERT_BY_THRESHOLD

(1, 6, 1, false, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALARM_BY_TIMER --> EMERGENCY
(2, 6, 1, false, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALARM_BY_TIMER --> EMERGENCY
(1, 6, 2, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- ALARM_BY_TIMER --> ALERT_BY_THRESHOLD
(2, 6, 2, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- ALARM_BY_TIMER --> ALERT_BY_THRESHOLD


INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES

-- INIT --> SERVICE
-- CO > Threshold_CO_1 || K > Threshold_K_1  || NO2 > Threshold_NO2_1
(1, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 1, 1, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 1, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 1, 1, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 1, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 


-- SERVICE --> ALERT_BY_THRESHOLD
-- CO > Threshold_CO_2 || K > Threshold_K_2  || NO2 > Threshold_NO2_2
(1, 2, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 1, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 1, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

-- SERVICE --> ALERT_BY_TIMER
-- Timer_CO_1 > Threshold_Timer_CO_1 || Timer_K_1 > Threshold_Timer_K_1 ||  Timer_NO2_1 > Threshold_Timer_NO2_1
(1, 2, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 2, 28, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

-- SERVICE --> INIT
-- CO < Threshold_CO_0 && K < Threshold_K_0  && NO2 > Threshold_NO2_0
(1, 2, 3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 2, 3, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 3, 25, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- ALERT_BY_THRESHOLD --> ALARM_BY_THRESHOLD
-- CO > Threshold_CO_3 || K > Threshold_K_3 || NO2 > Threshold_NO2_3
(1, 3, 1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 1, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 1, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALERT_BY_THRESHOLD --> ALARM_BY_TIMER
-- Timer_CO_2 > Threshold_Timer_CO_2 || Timer_K_2 > Threshold_Timer_K_2 ||  Timer_NO2_1 > Threshold_Timer_NO2_2
(1, 3, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 29, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALERT_BY_THRESHOLD --> SERVICE
-- CO < Threshold_CO_1 && K < Threshold_K_1  && NO2 > Threshold_NO2_1
(1, 3, 3, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 3, 3, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 3, 3, 26, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- ALERT_BY_TIMER --> ALARM_BY_THRESHOLD
-- CO > Threshold_CO_3 || K > Threshold_K_3 || NO2 > Threshold_NO2_3
(1, 4, 1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 1, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 1, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALERT_BY_TIMER --> ALARM_BY_TIMER
-- Timer_CO_2 > Threshold_Timer_CO_2 || Timer_K_2 > Threshold_Timer_K_2 ||  Timer_NO2_1 > Threshold_Timer_NO2_2
(1, 4, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 29, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ALERT_BY_TIMER --> SERVICE
-- CO < Threshold_CO_0 && K < Threshold_K_0  && NO2 > Threshold_NO2_0
(1, 4, 3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 4, 3, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 4, 3, 25, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- ALARM_BY_THRESHOLD --> EMERGENCY
-- CO > Threshold_CO_4 || K > Threshold_K_4 || NO2 > Threshold_NO2_4
-- Timer_CO_3 > Threshold_Timer_CO_3 || Timer_K_3 > Threshold_Timer_K_3 ||  Timer_NO2_1 > Threshold_Timer_NO2_3
(1, 5, 1, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 5, 1, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 5, 1, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(1, 5, 1, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 5, 1, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 5, 1, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

-- ALARM_BY_THRESHOLD --> ALERT_BY_THRESHOLD 
-- CO < Threshold_CO_2 && K < Threshold_K_2  && NO2 > Threshold_NO2_2
(1, 5, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 5, 2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 5, 2, 27, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  


-- ALARM_BY_TIMER --> EMERGENCY
-- CO > Threshold_CO_4 || K > Threshold_K_4 || NO2 > Threshold_NO2_4
-- Timer_CO_3 > Threshold_Timer_CO_3 || Timer_K_3 > Threshold_Timer_K_3 ||  Timer_NO2_1 > Threshold_Timer_NO2_3
(1, 6, 1, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 6, 1, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 6, 1, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(1, 6, 1, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 6, 1, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 6, 1, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

-- ALARM_BY_TIMER --> ALERT_BY_THRESHOLD 
-- CO < Threshold_CO_1 && K < Threshold_K_1  && NO2 > Threshold_NO2_1
(1, 6, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 6, 2, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(1, 6, 2, 26, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);















