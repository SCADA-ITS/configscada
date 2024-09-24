INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(51, NULL, 1, 0010051, NULL, NULL, 'TUN-ETD-1CA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 2, 0020051, NULL, NULL, 'TUN-ETD-1MA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 3, 0030051, NULL, NULL, 'MAP-ETD-1', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 4, 0040051, NULL, NULL, 'MAP-ETD-2', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 5, 0050051, NULL, NULL, 'MAP-ETD-3', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 6, 0060051, NULL, NULL, 'MAP-ETD-4', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 7, 0070051, NULL, NULL, 'MAP-ETD-5', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(51, 1, 1, 1, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 1, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 1, NULL, 'TUN-ETD-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 2, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 2, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 2, NULL, 'TUN-ETD-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 3, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 3, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 3, NULL, 'MAP-ETD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 4, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 4, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 4, NULL, 'MAP-ETD-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 5, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 5, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 5, NULL, 'MAP-ETD-3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 6, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 6, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 6, NULL, 'MAP-ETD-4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 7, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 7, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 7, NULL, 'MAP-ETD-5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(51, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
