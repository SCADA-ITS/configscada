INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(53, 1, 1, 0010053, NULL, NULL, 'LQ-SMF-AA-1MA', 'LQ-SMF-AA-1MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 1, 2, 0020053, NULL, NULL, 'LQ-SMF-AA-1CA', 'LQ-SMF-AA-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 3, 0030053, NULL, NULL, 'LQ-SMF-RR-1MA', 'LQ-SMF-RR-1MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 4, 0040053, NULL, NULL, 'LQ-SMF-RR-1CA', 'LQ-SMF-RR-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(53, 1003, 1, 1, NULL, 'LQ-SMF-AA-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 2, NULL, 'LQ-SMF-AA-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 3, NULL, 'LQ-SMF-RR-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 4, NULL, 'LQ-SMF-RR-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(53, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);