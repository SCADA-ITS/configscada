INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(19, NULL, 1, 0010019, NULL, NULL, 'SCO-2-01-DP', 'SCO-2-01-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 2, 0020019, NULL, NULL, 'SCO-2-02-DP', 'SCO-2-02-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 3, 0030019, NULL, NULL, 'SCO-2-03-DP', 'SCO-2-03-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 4, 0040019, NULL, NULL, 'SCO-2-04-DP', 'SCO-2-04-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 5, 0050019, NULL, NULL, 'SCO-1-01-DP', 'SCO-1-01-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 6, 0060019, NULL, NULL, 'SCO-1-02-DP', 'SCO-1-02-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 7, 0070019, NULL, NULL, 'SCO-1-03-DP', 'SCO-1-03-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 8, 0080019, NULL, NULL, 'SCO-1-04-DP', 'SCO-1-04-DP', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(19, 1003, 1, 1, NULL, 'SCO-2-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1003, 1, 2, NULL, 'SCO-2-02-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1003, 1, 3, NULL, 'SCO-2-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1003, 1, 4, NULL, 'SCO-2-04-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1003, 1, 5, NULL, 'SCO-1-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1003, 1, 6, NULL, 'SCO-1-02-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1003, 1, 7, NULL, 'SCO-1-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 1003, 1, 8, NULL, 'SCO-1-04-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(19, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);