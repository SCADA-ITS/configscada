INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(14, NULL, 1, 0010014, NULL, NULL, 'S1TS-LUM-001', 'S1TS-LUM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 2, 0020014, NULL, NULL, 'INKP-LUM-001', 'INKP-LUM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 3, 0030014, NULL, NULL, 'IVIP-LUM-001', 'IVIP-LUM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 4, 0040014, NULL, NULL, 'IPRO-LUM-001', 'IPRO-LUM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 5, 0050014, NULL, NULL, 'ICCO-LUM-001', 'ICCO-LUM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 6, 0060014, NULL, NULL, 'ICCP-LUM-001', 'ICCP-LUM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 7, 0070014, NULL, NULL, 'IBTO-LUM-001', 'IBTO-LUM-001', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(14, 1003, 1, 1, NULL, 'S1TS-LUM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 2, NULL, 'INKP-LUM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 3, NULL, 'IVIP-LUM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 4, NULL, 'IPRO-LUM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 5, NULL, 'ICCO-LUM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 6, NULL, 'ICCP-LUM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 7, NULL, 'IBTO-LUM-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(14, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
