INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(65, NULL, 1, 0010065, NULL, NULL, 'SAI-N-DP2', 'SAI-N-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 2, 0020065, NULL, NULL, 'SAI-S-DP2', 'SAI-S-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 3, 0030065, NULL, NULL, 'SAI-N-DP1', 'SAI-N-DP1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 4, 0040065, NULL, NULL, 'SAI-S-DP1', 'SAI-S-DP1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(65, 1003, 1, 1, NULL, 'SAI-N-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 2, NULL, 'SAI-S-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 3, NULL, 'SAI-N-DP1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 4, NULL, 'SAI-S-DP1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(65, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);