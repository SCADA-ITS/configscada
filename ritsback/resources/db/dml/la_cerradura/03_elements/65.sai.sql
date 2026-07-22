INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(65, NULL, 1, 0010065, NULL, NULL, 'SAI-N-DP2', 'SAI-N-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 2, 0020065, NULL, NULL, 'SAI-S-DP2', 'SAI-S-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 3, 0030065, NULL, NULL, 'SAI-N-DP1', 'SAI-N-DP1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 4, 0040065, NULL, NULL, 'SAI-S-DP1', 'SAI-S-DP1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 5, 0050065, NULL, NULL, 'SAI-N-CA', 'SAI-N-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 6, 0060065, NULL, NULL, 'SAI-S-CA', 'SAI-S-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 7, 0070065, NULL, NULL, 'SAI-N-CZ', 'SAI-N-CZ', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, NULL, 8, 0080065, NULL, NULL, 'SAI-S-CZ', 'SAI-S-CZ', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(65, 1, 1, 1, NULL, 'Multi Sentry', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 1, NULL, 'MST 50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 1003, 1, 1, NULL, 'SAI-N-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1, 1, 2, NULL, 'Multi Sentry', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 2, NULL, 'MST 50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 1003, 1, 2, NULL, 'SAI-S-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1, 1, 3, NULL, 'Multi Sentry', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 3, NULL, 'MST 50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 1003, 1, 3, NULL, 'SAI-N-DP1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1, 1, 4, NULL, 'Multi Sentry', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 4, NULL, 'MST 50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 1003, 1, 4, NULL, 'SAI-S-DP1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1, 1, 5, NULL, 'Multi Sentry', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 5, NULL, 'MST 50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 1003, 1, 5, NULL, 'SAI-N-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1, 1, 6, NULL, 'Multi Sentry', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 6, NULL, 'MST 50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 1003, 1, 6, NULL, 'SAI-S-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1, 1, 7, NULL, 'Multi Sentry', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 7, NULL, 'MST 50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 1003, 1, 7, NULL, 'SAI-N-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1, 1, 8, NULL, 'Multi Sentry', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 8, NULL, 'MST 50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 1003, 1, 8, NULL, 'SAI-S-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(65, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
