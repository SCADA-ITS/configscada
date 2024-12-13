INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(1, 5, 1, 0010001, NULL, NULL, 'EM-1-01-CZ', 'EM-1-01-CZ', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 5, 2, 0020001, NULL, NULL, 'EM-2-01-CA', 'EM-2-01-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 4001, 1, 1, NULL, '192.168.88.196', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4002, 1, 1, NULL, '5000', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4003, 1, 1, NULL, '0x05 0x20', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4004, 1, 1, NULL, '0x06', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4005, 1, 1, NULL, '0x20', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(1, 4001, 1, 2, NULL, '192.168.88.196', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4002, 1, 2, NULL, '5000', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4003, 1, 2, NULL, '0x05 0x20', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4004, 1, 2, NULL, '0x06', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1, 4005, 1, 2, NULL, '0x21', true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 4, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 4, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
