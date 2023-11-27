-- 
-- Table: conf.elements.FAN
--
INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(9, NULL, 1, 18, NULL, NULL, 'RV-VENT-VF1-CA', 'RV-VENT-VF1-CA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(9, NULL, 2, 18, NULL, NULL, 'RV-VENT-VF2-CA', 'RV-VENT-VF2-CA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(9, NULL, 3, 18, NULL, NULL, 'RV-VENT-VF3-CA', 'RV-VENT-VF3-CA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(9, NULL, 4, 19, NULL, NULL, 'RV-VENT-VF4-CA', 'RV-VENT-VF4-CA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(9, NULL, 5, 20, NULL, NULL, 'RV-VENT-VF1-MA', 'RV-VENT-VF1-MA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(9, NULL, 6, 20, NULL, NULL, 'RV-VENT-VF2-MA', 'RV-VENT-VF2-MA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(9, NULL, 7, 21, NULL, NULL, 'RV-VENT-VF3-MA', 'RV-VENT-VF3-MA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(9, NULL, 8, 22, NULL, NULL, 'RV-VENT-VF4-MA', 'RV-VENT-VF4-MA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(9, NULL, 9, 22, NULL, NULL, 'RV-VENT-VF5-MA', 'RV-VENT-VF5-MA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(9, NULL, 10, 22, NULL, NULL, 'RV-VENT-VF6-MA', 'RV-VENT-VF6-MA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2, 100, 22, NULL, NULL, 'S1TS-VCH-100A', 'S1TS-VCH-100A', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.FAN
--
INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(9, 6, 1, 2, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1003, 1, 1, NULL, 'RV-VENT-VF1-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(9, 1003, 1, 2, NULL, 'RV-VENT-VF2-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(9, 1003, 1, 3, NULL, 'RV-VENT-VF3-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(9, 1003, 1, 4, NULL, 'RV-VENT-VF4-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(9, 1003, 1, 5, NULL, 'RV-VENT-VF1-MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(9, 1003, 1, 6, NULL, 'RV-VENT-VF2-MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(9, 1003, 1, 7, NULL, 'RV-VENT-VF3-MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(9, 1003, 1, 8, NULL, 'RV-VENT-VF4-MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(9, 1003, 1, 9, NULL, 'RV-VENT-VF5-MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(9, 1003, 1, 10, NULL, 'RV-VENT-VF6-MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 6, 1, 100, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1003, 1, 100, NULL, 'S1TS-VCH-100A', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(090900, 2, 3, 9, NULL, 3, true, true, 'ALARM_NOT_BETWEEN_50_AND_200', 'ALARM_NOT_BETWEEN_50_AND_200', 'LBL_ALARM_NOT_BETWEEN_50_AND_200', 'LBL_ALARM_NOT_BETWEEN_50_AND_200_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090901, 2, 3, 9, NULL, 3, true, true, 'ALARM_BETWEEN_50_AND_200', 'ALARM_BETWEEN_50_AND_200', 'LBL_ALARM_BETWEEN_50_AND_200', 'LBL_ALARM_BETWEEN_50_AND_200_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090902, 2, 3, 9, NULL, 3, true, true, 'ALARM_GREATER_OR_EQUAL_200', 'ALARM_GREATER_OR_EQUAL_200', 'LBL_ALARM_ALARM_GREATER_OR_EQUAL_200', 'LBL_ALARM_GREATER_OR_EQUAL_200_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(090900, 9, 2, 2, 10, '50', '200', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090901, 9, 2, 3, 1, '50', '200', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090902, 9, 2, 3, 5, '200', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


UPDATE master.element_type_params
  SET threshold_1 = '50',
      threshold_2 = '200'
 WHERE element_type_id = 9
   AND param_type_id = 2
   AND element_type_param_id = 2