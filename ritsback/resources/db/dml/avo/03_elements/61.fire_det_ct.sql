INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(61, 2, 1, 0010061, NULL, NULL, 'CT01-CCI-001', 'CT01-CCI-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 2, 2, 0020061, NULL, NULL, 'CT02-CCI-001', 'CT02-CCI-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 2, 3, 0030061, NULL, NULL, 'CT03-CCI-001', 'CT03-CCI-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 2, 4, 0040061, NULL, NULL, 'CT04-CCI-001', 'CT04-CCI-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 2, 5, 0050061, NULL, NULL, 'CT05-CCI-001', 'CT05-CCI-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 2, 6, 0060061, NULL, NULL, 'CT06-CCI-001', 'CT06-CCI-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 2, 7, 0070061, NULL, NULL, 'CT07-CCI-001', 'CT07-CCI-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 2, 8, 0080061, NULL, NULL, 'CT08-CCI-001', 'CT08-CCI-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(61, 1003, 1, 1, NULL, 'CT01-CCI-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 2, NULL, 'CT02-CCI-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 3, NULL, 'CT03-CCI-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 4, NULL, 'CT04-CCI-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 5, NULL, 'CT05-CCI-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 6, NULL, 'CT06-CCI-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 7, NULL, 'CT07-CCI-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(61, 1003, 1, 8, NULL, 'CT08-CCI-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(61, 1, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 2, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 3, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 4, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 5, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 6, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 7, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(61, 8, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);