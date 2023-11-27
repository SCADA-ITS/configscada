INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(8, 1, 1, 0010008, NULL, NULL, 'ICEO-BAR-001', 'ICEO-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 2, 0020008, NULL, NULL, 'ICEP-BAR-001', 'ICEP-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 3, 0030008, NULL, NULL, 'IVEN-BAR-001', 'IVEN-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 4, 0040008, NULL, NULL, 'S1TS-BAR-001', 'S1TS-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 5, 0050008, NULL, NULL, 'S1TS-BAR-002', 'S1TS-BAR-002', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 6, 0060008, NULL, NULL, 'INKP-BAR-001', 'INKP-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 7, 0070008, NULL, NULL, 'IVIP-BAR-001', 'IVIP-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 8, 0080008, NULL, NULL, 'IPRO-BAR-001', 'IPRO-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 9, 0090008, NULL, NULL, 'ICCO-BAR-001', 'ICCO-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 10, 0100008, NULL, NULL, 'ICCP-BAR-001', 'ICCP-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 1, 11, 0110008, NULL, NULL, 'IBTO-BAR-001', 'IBTO-BAR-001', 'Reset.groovy', NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(8, 1003, 1, 1, NULL, 'ICEO-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 2, NULL, 'ICEP-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 3, NULL, 'IVEN-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 4, NULL, 'S1TS-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 5, NULL, 'S1TS-BAR-002', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 6, NULL, 'INKP-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 7, NULL, 'IVIP-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 8, NULL, 'IPRO-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 9, NULL, 'ICCO-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 10, NULL, 'ICCP-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 11, NULL, 'IBTO-BAR-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(8, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
