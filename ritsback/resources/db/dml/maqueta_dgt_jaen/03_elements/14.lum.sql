INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(14, NULL, 1, 0010014, NULL, NULL, 'LUM-0-03-DP', 'LUM-0-03-DP', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 2, 0020014, NULL, NULL, 'LUM-0-01-CA', 'LUM-0-01-CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 3, 0030014, NULL, NULL, 'LUM-0-02-CA', 'LUM-0-02-CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 4, 0040014, NULL, NULL, 'LUM-0-04-CZ', 'LUM-0-04-CZ', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(14, 1003, 1, 1, NULL, 'LUM-0-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 2, NULL, 'LUM-0-01-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 3, NULL, 'LUM-0-02-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 4, NULL, 'LUM-0-04-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(14, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
