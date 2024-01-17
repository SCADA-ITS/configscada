INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(18, NULL, 1, 0010018, NULL, NULL, 'AN-2-01-DP', 'AN-2-01-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 2, 0020018, NULL, NULL, 'AN-2-02-DP', 'AN-2-02-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 3, 0030018, NULL, NULL, 'AN-2-03-DP', 'AN-2-03-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 4, 0040018, NULL, NULL, 'AN-2-04-DP', 'AN-2-04-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 5, 0050018, NULL, NULL, 'AN-1-01-DP', 'AN-1-01-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 6, 0060018, NULL, NULL, 'AN-1-02-DP', 'AN-1-02-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 7, 0070018, NULL, NULL, 'AN-1-03-DP', 'AN-1-03-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 8, 0080018, NULL, NULL, 'AN-1-04-DP', 'AN-1-04-DP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(18, 1003, 1, 1, NULL, 'AN-2-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 2, NULL, 'AN-2-02-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 3, NULL, 'AN-2-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 4, NULL, 'AN-2-04-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 5, NULL, 'AN-1-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 6, NULL, 'AN-1-02-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 7, NULL, 'AN-1-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 8, NULL, 'AN-1-04-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(18, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);