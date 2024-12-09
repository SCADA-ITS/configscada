INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(61, NULL, 1, 0010061, NULL, NULL, 'CEN-INC-DP1', 'CEN-INC-DP1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, NULL, 2, 0020061, NULL, NULL, 'CEN-INC-DP2', 'CEN-INC-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, NULL, 3, 0030061, NULL, NULL, 'CEN-INC-CA', 'CEN-INC-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, NULL, 4, 0040061, NULL, NULL, 'CEN-INC-CZ', 'CEN-INC-CZ', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(61, 1003, 1, 1, NULL, 'CEN-INC-DP1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 2, NULL, 'CEN-INC-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 3, NULL, 'CEN-INC-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 4, NULL, 'CEN-INC-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(61, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);