INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(21, NULL, 1, 0010021, NULL, NULL, 'OP-1-01-DP', 'OP-1-01-DP', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 2, 0020021, NULL, NULL, 'OP-1-02-DP', 'OP-1-02-DP', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 3, 0030021, NULL, NULL, 'OP-1-03-DP', 'OP-1-03-DP', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 4, 0040021, NULL, NULL, 'OP-1-04-DP', 'OP-1-04-DP', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 5, 0050021, NULL, NULL, 'OP-2-01-DP', 'OP-2-01-DP', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 6, 0060021, NULL, NULL, 'OP-2-02-DP', 'OP-2-02-DP', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 7, 0070021, NULL, NULL, 'OP-2-03-DP', 'OP-2-03-DP', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 8, 0080021, NULL, NULL, 'OP-2-04-DP', 'OP-2-04-DP', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 9, 0090021, NULL, NULL, 'OP-1-01-CA', 'OP-1-01-CA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 10, 0100021, NULL, NULL, 'OP-2-01-CA', 'OP-2-01-CA', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 11, 0110021, NULL, NULL, 'OP-1-01-CZ', 'OP-1-01-CZ', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 12, 0120021, NULL, NULL, 'OP-2-01-CZ', 'OP-2-01-CZ', 'SendAlarmsToVentilation.groovy', 'SendFanStateToVentilation.groovy', 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(21, 1, 1, 1, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 1, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 1, NULL, 'OP-1-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 2, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 2, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 2, NULL, 'OP-1-02-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 3, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 3, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 3, NULL, 'OP-1-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 4, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 4, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 4, NULL, 'OP-1-04-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 5, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 5, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 5, NULL, 'OP-2-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 6, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 6, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 6, NULL, 'OP-2-02-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 7, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 7, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 7, NULL, 'OP-2-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 8, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 8, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 8, NULL, 'OP-2-04-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 9, NULL, 'SICK', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 9, NULL, 'VISIC620-1111', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 9, NULL, 'OP-1-01-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 10, NULL, 'SICK', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 10, NULL, 'VISIC620-1111', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 10, NULL, 'OP-2-01-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 11, NULL, 'SICK', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 11, NULL, 'VISIC620-1111', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 11, NULL, 'OP-1-01-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1, 1, 12, NULL, 'SICK', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 12, NULL, 'VISIC620-1111', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 1003, 1, 12, NULL, 'OP-2-01-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(21, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
