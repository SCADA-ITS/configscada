INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(18, NULL, 1, 0010018, NULL, NULL, 'AN-1-01-CE', 'AN-1-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 2, 0020018, NULL, NULL, 'AN-2-01-CE', 'AN-2-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(18, 3, 1, 1, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 1003, 1, 1, NULL, 'AN-1-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 3, 1, 2, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 1003, 1, 2, NULL, 'AN-2-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(18, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);