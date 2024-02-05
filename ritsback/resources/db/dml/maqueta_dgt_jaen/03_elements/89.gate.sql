INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(89, NULL, 1, 0010089, NULL, NULL, 'PUE-GA1-S', 'PUE-GA1-S', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 2, 0020089, NULL, NULL, 'PUE-GA1-N', 'PUE-GA1-N', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 3, 0030089, NULL, NULL, 'PUE-GA2-S', 'PUE-GA2-S', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 4, 0040089, NULL, NULL, 'PUE-GA2-N', 'PUE-GA2-N', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 5, 0050089, NULL, NULL, 'PUE-GA3-S', 'PUE-GA3-S', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 6, 0060089, NULL, NULL, 'PUE-GA3-N', 'PUE-GA3-N', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 7, 0070089, NULL, NULL, 'PUE-GA4-S', 'PUE-GA4-S', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 8, 0080089, NULL, NULL, 'PUE-GA4-N', 'PUE-GA4-N', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(89, 1003, 1, 1, NULL, 'PUE-GA1-S', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 2, NULL, 'PUE-GA1-N', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 3, NULL, 'PUE-GA2-S', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 4, NULL, 'PUE-GA2-N', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 5, NULL, 'PUE-GA3-S', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 6, NULL, 'PUE-GA3-N', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 7, NULL, 'PUE-GA4-S', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 8, NULL, 'PUE-GA4-N', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(89, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
