INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(17, NULL, 1, 0010017, NULL, NULL, 'ICEO-AIM-001', 'ICEO-AIM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, NULL, 2, 0020017, NULL, NULL, 'ICEP-AIM-001', 'ICEP-AIM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, NULL, 3, 0030017, NULL, NULL, 'IVEN-AIM-001', 'IVEN-AIM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, NULL, 4, 0040017, NULL, NULL, 'INKP-AIM-001', 'INKP-AIM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, NULL, 5, 0050017, NULL, NULL, 'IVIP-AIM-001', 'IVIP-AIM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, NULL, 6, 0060017, NULL, NULL, 'IPRO-AIM-001', 'IPRO-AIM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, NULL, 7, 0070017, NULL, NULL, 'ICCO-AIM-001', 'ICCO-AIM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, NULL, 8, 0080017, NULL, NULL, 'ICCP-AIM-001', 'ICCP-AIM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, NULL, 9, 0090017, NULL, NULL, 'IBTO-AIM-001', 'IBTO-AIM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(17, 1003, 1, 1, NULL, 'ICEO-AIM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(17, 1003, 1, 2, NULL, 'ICEP-AIM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(17, 1003, 1, 3, NULL, 'IVEN-AIM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(17, 1003, 1, 4, NULL, 'INKP-AIM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(17, 1003, 1, 5, NULL, 'IVIP-AIM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(17, 1003, 1, 6, NULL, 'IPRO-AIM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(17, 1003, 1, 7, NULL, 'ICCO-AIM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(17, 1003, 1, 8, NULL, 'ICCP-AIM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(17, 1003, 1, 9, NULL, 'IBTO-AIM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(17, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(17, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
