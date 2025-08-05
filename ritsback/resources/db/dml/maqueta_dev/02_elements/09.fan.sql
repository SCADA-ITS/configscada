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
(9, 2, 100, 22, NULL, NULL, 'S1TS-VCH-100A', 'S1TS-VCH-100A', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- modbus fan
(9, NULL, 11, 22, NULL, NULL, 'RV-VENT-MODBUS_1', 'RV-VENT-MODBUS_1', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, NULL, 12, 22, NULL, NULL, 'RV-VENT-MODBUS_2', 'RV-VENT-MODBUS_2', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, NULL, 13, 22, NULL, NULL, 'RV-VENT-MODBUS_3', 'RV-VENT-MODBUS_3', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- modbus addresses for state
INSERT INTO conf.modbus_element_element_type_states (element_type_id, element_id, xaddress, enabled, visible, created_at, updated_at) VALUES
(9, 11, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 12, 110, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 13, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- modbus addresses for measures
INSERT INTO conf.modbus_element_element_type_params (element_type_id, element_type_param_id, param_type_id, element_id, xaddress, enabled, visible, created_at, updated_at) VALUES
(9, 1, 2, 11, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2, 2, 11, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 3, 2, 11, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 4, 2, 11, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 2, 12, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2, 2, 12, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 3, 2, 12, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 4, 2, 12, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 2, 13, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2, 2, 13, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 3, 2, 13, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 4, 2, 13, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- modbus addresses for alarms
INSERT INTO conf.modbus_element_alarm_configs (element_type_id, element_id, alarm_config_id, xaddress, enabled, visible, created_at, updated_at) VALUES
(9, 11, 10009, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 11, 20009, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 11, 60009, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 11, 70009, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 12, 10009, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 12, 20009, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 12, 60009, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 12, 70009, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 13, 10009, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 13, 20009, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 13, 60009, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 13, 70009, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- modbus addresses for commands	
INSERT INTO conf.modbus_element_command_element_types  (command_element_type_id, element_type_id, element_id, xaddress, enabled, visible, created_at, updated_at) VALUES
(1, 9, 11, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 11, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 11, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 9, 11, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 9, 11, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 9, 11, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 9, 11, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 9, 11, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 9, 11, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 9, 11, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 9, 11, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 9, 12, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 12, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 12, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 9, 12, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 9, 12, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 9, 12, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 9, 12, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 9, 12, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 9, 12, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 9, 12, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 9, 12, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 9, 13, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 13, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 13, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 9, 13, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 9, 13, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 9, 13, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 9, 13, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 9, 13, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 9, 13, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 9, 13, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 9, 13, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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