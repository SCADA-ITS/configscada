INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(75, NULL, 1, 0010075, NULL, NULL, 'GE2-DP', 'GE2-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(75, NULL, 2, 0020075, NULL, NULL, 'GE3-DP', 'GE3-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(75, NULL, 3, 0030075, NULL, NULL, 'GE4-CA', 'GE4-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(75, NULL, 4, 0040075, NULL, NULL, 'GE1-CZ', 'GE1-CZ', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(75, 1003, 1, 1, NULL, 'GE2-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(75, 1003, 1, 2, NULL, 'GE3-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(75, 1003, 1, 3, NULL, 'GE4-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(75, 1003, 1, 4, NULL, 'GE1-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(75, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(75, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(75, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(75, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
