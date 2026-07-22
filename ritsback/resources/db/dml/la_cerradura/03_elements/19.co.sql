INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(19, NULL, 1, 0010019, NULL, NULL, 'SCO-1-01-DP', 'SCO-1-01-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 2, 0020019, NULL, NULL, 'SCO-1-02-DP', 'SCO-1-02-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 3, 0030019, NULL, NULL, 'SCO-1-03-DP', 'SCO-1-03-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 4, 0040019, NULL, NULL, 'SCO-1-04-DP', 'SCO-1-04-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 5, 0050019, NULL, NULL, 'SCO-2-01-DP', 'SCO-2-01-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 6, 0060019, NULL, NULL, 'SCO-2-02-DP', 'SCO-2-02-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 7, 0070019, NULL, NULL, 'SCO-2-03-DP', 'SCO-2-03-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 8, 0080019, NULL, NULL, 'SCO-2-04-DP', 'SCO-2-04-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 9, 0090019, NULL, NULL, 'SCO-1-01-CA', 'SCO-1-01-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 10, 0100019, NULL, NULL, 'SCO-2-01-CA', 'SCO-2-01-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 11, 0110019, NULL, NULL, 'SCO-1-01-CZ', 'SCO-1-01-CZ', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 12, 0120019, NULL, NULL, 'SCO-2-01-CZ', 'SCO-2-01-CZ', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 13, 0130019, NULL, NULL, 'SCO-1-03-CA', 'SCO-1-03-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 14, 0140019, NULL, NULL, 'SCO-1-04-CA', 'SCO-1-04-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 15, 0150019, NULL, NULL, 'SCO-1-02-CA', 'SCO-1-02-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 16, 0160019, NULL, NULL, 'SCO-2-02-CA', 'SCO-2-02-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 17, 0170019, NULL, NULL, 'SCO-2-03-CA', 'SCO-2-03-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 18, 0180019, NULL, NULL, 'SCO-2-04-CA', 'SCO-2-04-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 19, 0190019, NULL, NULL, 'SCO-1-05-CA', 'SCO-1-05-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 20, 0200019, NULL, NULL, 'SCO-2-05-CA', 'SCO-2-05-CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(19, 1, 1, 1, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 1, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 1, NULL, 'SCO-1-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 2, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 2, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 2, NULL, 'SCO-1-02-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 3, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 3, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 3, NULL, 'SCO-1-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 4, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 4, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 4, NULL, 'SCO-1-04-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 5, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 5, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 5, NULL, 'SCO-2-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 6, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 6, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 6, NULL, 'SCO-2-02-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 7, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 7, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 7, NULL, 'SCO-2-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 8, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 8, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 8, NULL, 'SCO-2-04-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 9, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 9, NULL, 'SCO-1-01-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 10, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 10, NULL, 'SCO-2-01-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 11, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 11, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 11, NULL, 'SCO-1-01-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 12, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 12, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 12, NULL, 'SCO-2-01-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 13, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 13, NULL, 'SCO-1-03-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 14, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 14, NULL, 'SCO-1-04-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 15, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 15, NULL, 'SCO-1-02-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 16, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 16, NULL, 'SCO-2-02-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 17, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 17, NULL, 'SCO-2-03-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 18, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 18, NULL, 'SCO-2-04-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 19, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 19, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 19, NULL, 'SCO-1-05-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1, 1, 20, NULL, 'Duran', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 20, NULL, 'Sondeltox', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 20, NULL, 'SCO-2-05-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(19, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 13, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 14, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 15, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 16, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 17, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 18, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 19, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 20, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
