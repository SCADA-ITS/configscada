INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(53, 2, 1, 0010053, NULL, NULL, 'SMF-1-03-CA-D', 'SMF-1-03-CA-D', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 2, 0020053, NULL, NULL, 'SMF-1-03-CA-I', 'SMF-1-03-CA-I', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 3, 0030053, NULL, NULL, 'SMF-2-01-CA-D', 'SMF-2-01-CA-D', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 4, 0040053, NULL, NULL, 'SMF-2-01-CA-I', 'SMF-2-01-CA-I', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 5, 0050053, NULL, NULL, 'SMF-2-03-CZ-D', 'SMF-2-03-CZ-D', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 6, 0060053, NULL, NULL, 'SMF-2-03-CZ-I', 'SMF-2-03-CZ-I', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(53, 1003, 1, 1, NULL, 'SMF-1-03-CA-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 2, NULL, 'SMF-1-03-CA-I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 3, NULL, 'SMF-2-01-CA-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 4, NULL, 'SMF-2-01-CA-I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 5, NULL, 'SMF-2-03-CZ-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 6, NULL, 'SMF-2-03-CZ-I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(53, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
