INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(4, 2, 1, 0010004, NULL, NULL, 'ETD1-CRE', 'ETD1-CRE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 2, 0020004, NULL, NULL, 'ETD1-DEC', 'ETD1-DEC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 3, 0030004, NULL, NULL, 'ETD2-CRE', 'ETD2-CRE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 4, 0040004, NULL, NULL, 'ETD2-DEC', 'ETD2-DEC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 5, 0050004, NULL, NULL, 'ETD3-CRE', 'ETD3-CRE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 6, 0060004, NULL, NULL, 'ETD3-DEC', 'ETD3-DEC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 7, 0070004, NULL, NULL, 'ETD4-CRE', 'ETD4-CRE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 8, 0080004, NULL, NULL, 'ETD4-DEC', 'ETD4-DEC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 9, 0090004, NULL, NULL, 'ETD5-CRE', 'ETD5-CRE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 10, 0100004, NULL, NULL, 'ETD5-DEC', 'ETD5-DEC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(4, 1, 1, 1, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 1, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 1, NULL, '25.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 2, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 2, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 2, NULL, '25.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 3, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 3, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 3, NULL, '30.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 4, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 4, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 4, NULL, '30.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 5, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 5, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 5, NULL, '40.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 6, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 6, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 6, NULL, '40.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 7, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 7, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 7, NULL, '50.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 8, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 8, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 8, NULL, '50.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 9, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 9, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 9, NULL, '61.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 10, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 10, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 10, NULL, '61.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(4, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);