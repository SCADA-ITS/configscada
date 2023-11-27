INSERT INTO conf.elements (element_type_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(65, 1, 0010065, NULL, NULL, 'CT01-UPS-001', 'CT01-UPS-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 0020065, NULL, NULL, 'CT02-UPS-001', 'CT02-UPS-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 3, 0030065, NULL, NULL, 'CT03-UPS-001', 'CT03-UPS-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 4, 0040065, NULL, NULL, 'CT04-UPS-001', 'CT04-UPS-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 5, 0050065, NULL, NULL, 'CT05-UPS-001', 'CT05-UPS-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 6, 0060065, NULL, NULL, 'CT06-UPS-001', 'CT06-UPS-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 7, 0070065, NULL, NULL, 'CT07-UPS-001', 'CT07-UPS-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 8, 0080065, NULL, NULL, 'CT08-UPS-001', 'CT08-UPS-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(65, 1003, 1, 1, NULL, 'CT01-UPS-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 2, NULL, 'CT02-UPS-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 3, NULL, 'CT03-UPS-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 4, NULL, 'CT04-UPS-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 5, NULL, 'CT05-UPS-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 6, NULL, 'CT06-UPS-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 7, NULL, 'CT07-UPS-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(65, 1003, 1, 8, NULL, 'CT08-UPS-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(65, 1, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 2, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 3, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 4, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 5, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 6, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 7, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(65, 8, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);