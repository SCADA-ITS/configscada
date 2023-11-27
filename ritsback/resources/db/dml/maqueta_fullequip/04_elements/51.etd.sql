INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(51, NULL, 1, 0010051, NULL, NULL, 'RV-ETD-1CA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 2, 0020051, NULL, NULL, 'RV-ETD-1MA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 3, 0030051, NULL, NULL, 'MM-ETD-1CA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 4, 0040051, NULL, NULL, 'MM-ETD-1MA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 5, 0050051, NULL, NULL, 'STMI-ETD-1CA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 6, 0060051, NULL, NULL, 'STMI-ETD-1MA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 7, 0070051, NULL, NULL, 'CH-ETD-1MA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 8, 0080051, NULL, NULL, 'CH-ETD-1CA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 9, 0090051, NULL, NULL, 'LQ-ETD-1CA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 10, 0100051, NULL, NULL, 'LQ-ETD-1MA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 11, 0110051, NULL, NULL, 'EST-ETD-1CA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 12, 0120051, NULL, NULL, 'EST-ETD-1MA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(51, 1, 1, 1, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 1, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 1, NULL, 'RV-ETD-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 2, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 2, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 2, NULL, 'RV-ETD-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 3, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 3, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 3, NULL, 'MM-ETD-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 4, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 4, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 4, NULL, 'MM-ETD-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 5, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 5, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 5, NULL, 'STMI-ETD-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 6, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 6, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 6, NULL, 'STMI-ETD-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 7, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 7, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 7, NULL, 'CH-ETD-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 8, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 8, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 8, NULL, 'CH-ETD-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 9, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 9, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 9, NULL, 'LQ-ETD-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 10, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 10, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 10, NULL, 'LQ-ETD-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 11, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 11, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 11, NULL, 'EST-ETD-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 12, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 12, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 12, NULL, 'EST-ETD-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(51, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);