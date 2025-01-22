INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(99, NULL, 1, NULL, NULL, NULL, 'RADF-127+670D', 'RADF-127+670D', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(99, 1, 1, 1, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 2, 1, 1, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(99, 5001, 1, 1, NULL, 'localhost', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5002, 1, 1, NULL, '22', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5003, 1, 1, NULL, 'sftpuser', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5004, 1, 1, NULL, '_Lit.03.2024_', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5005, 1, 1, NULL, '/data/APAVG01PK0D0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);