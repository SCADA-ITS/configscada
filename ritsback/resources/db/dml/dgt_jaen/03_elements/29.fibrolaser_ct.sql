INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(29, NULL, 1, 0010029, NULL, NULL, 'CEN-LIST-DP1', 'CEN-LIST-DP1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, NULL, 2, 0020029, NULL, NULL, 'CEN-LIST-DP2', 'CEN-LIST-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, NULL, 3, 0030029, NULL, NULL, 'CEN-LIST-CA', 'CEN-LIST-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, NULL, 4, 0040029, NULL, NULL, 'CEN-LIST-CZ', 'CEN-LIST-CZ', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(29, 1, 1, 1, NULL, 'LISTEC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 1, 1, NULL, 'LISTcontroller', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 1003, 1, 1, NULL, 'CEN-LIST-DP1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1, 1, 2, NULL, 'LISTEC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 1, 2, NULL, 'LISTcontroller', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 1003, 1, 2, NULL, 'CEN-LIST-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1, 1, 3, NULL, 'LISTEC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 1, 3, NULL, 'LISTcontroller', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 1003, 1, 3, NULL, 'CEN-LIST-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1, 1, 4, NULL, 'LISTEC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 1, 4, NULL, 'LISTcontroller', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 1003, 1, 4, NULL, 'CEN-LIST-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(29, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);