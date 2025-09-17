INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(67, NULL, 1, 0010067, NULL, NULL, 'MASTER DP', 'MASTER DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(67, NULL, 2, 0020067, NULL, NULL, 'MASTER CA', 'MASTER CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(67, NULL, 3, 0030067, NULL, NULL, 'SLAVE DP', 'SLAVE DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(67, NULL, 4, 0040067, NULL, NULL, 'SLAVE CA', 'SLAVE CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(67, 1003, 1, 1, NULL, 'MASTER DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(67, 1003, 1, 2, NULL, 'MASTER CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(67, 1003, 1, 3, NULL, 'SLAVE DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(67, 1003, 1, 4, NULL, 'SLAVE CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(67, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(67, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(67, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(67, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
