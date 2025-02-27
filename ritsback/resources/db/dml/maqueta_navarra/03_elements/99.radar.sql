INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(99, 1, 1, 0010099, NULL, NULL, 'RADF-127+670D', 'RADF-127+670D', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 2, 2, 0020099, NULL, NULL, 'RADF-91+358D', 'RADF-91+358D', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 3, 3, 0030099, NULL, NULL, 'RADF-32+560C', 'RADF-32+560C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 4, 4, 0040099, NULL, NULL, 'RADF-401+561C', 'RADF-401+561C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, NULL, 5, 0050099, NULL, NULL, 'RADF-25+900D', 'RADF-25+900D', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, NULL, 6, 0060099, NULL, NULL, 'RADF-60+230C', 'RADF-60+230C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(99, 1, 1, 1, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 2, 1, 1, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 1003, 1, 1, NULL, 'RADF-127+670D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(99, 5001, 1, 1, NULL, '192.168.88.163', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5002, 1, 1, NULL, '22', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5003, 1, 1, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5004, 1, 1, NULL, 'xwwWugWPXoZtxgitWOGq5w==', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5005, 1, 1, NULL, '/radar/subtipo1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(99, 1, 1, 2, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 2, 1, 2, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 1003, 1, 2, NULL, 'RADF-91+358D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(99, 5001, 1, 2, NULL, '192.168.88.163', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5002, 1, 2, NULL, '22', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5003, 1, 2, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5004, 1, 2, NULL, 'xwwWugWPXoZtxgitWOGq5w==', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5005, 1, 2, NULL, '/radar/subtipo2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(99, 1, 1, 3, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 2, 1, 3, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 1003, 1, 3, NULL, 'RADF-32+560C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(99, 5001, 1, 3, NULL, '192.168.88.163', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5002, 1, 3, NULL, '22', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5003, 1, 3, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5004, 1, 3, NULL, 'xwwWugWPXoZtxgitWOGq5w==', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5005, 1, 3, NULL, '/radar/subtipo3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(99, 1, 1, 4, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 2, 1, 4, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 1003, 1, 4, NULL, 'RADF-401+561C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(99, 5001, 1, 4, NULL, '192.168.88.163', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5002, 1, 4, NULL, '22', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5003, 1, 4, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5004, 1, 4, NULL, 'xwwWugWPXoZtxgitWOGq5w==', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5005, 1, 4, NULL, '/radar/subtipo4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(99, 1, 1, 5, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 2, 1, 5, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 1003, 1, 5, NULL, 'RADF-25+900D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(99, 1, 1, 6, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 2, 1, 6, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 1003, 1, 6, NULL, 'RADF-60+230C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(99, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(99, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);