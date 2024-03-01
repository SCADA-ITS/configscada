INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(14, NULL, 5, 0050014, NULL, NULL, 'LQ-LUM-1CA', 'LQ-LUM-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 6, 0060014, NULL, NULL, 'LQ-LUM-1MA', 'LQ-LUM-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(14, 2, 1, 5, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 1003, 1, 5, NULL, 'LQ-LUM-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 2, 1, 6, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 1003, 1, 6, NULL, 'LQ-LUM-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(14, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);