INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(36, NULL, 1, 0010036, NULL, NULL, 'BAL_A', 'Baliza de iluminación amarilla', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(36, NULL, 2, 0020036, NULL, NULL, 'BAL_V', 'Baliza de iluminación verde', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(36, 1003, 1, 1, NULL, 'BAL_A', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(36, 1003, 1, 2, NULL, 'BAL_V', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(36, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(36, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);