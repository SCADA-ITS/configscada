INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(51, NULL, 1, 0010051, NULL, NULL, 'ETD-1-1-DP', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 2, 0020051, NULL, NULL, 'ETD-1-1-CZ', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 3, 0030051, NULL, NULL, 'ETD-2-1-CZ', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 4, 0040051, NULL, NULL, 'ETD-1-1-CA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, NULL, 5, 0050051, NULL, NULL, 'ETD-1-2-CA', 'Revenga ETD', NULL, NULL, 'EtdParamsAdapterTFA.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(51, 1, 1, 1, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 1, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 1, NULL, 'ETD-1-1-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 2, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 2, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 2, NULL, 'ETD-1-1-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 3, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 3, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 3, NULL, 'ETD-2-1-CZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 4, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 4, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 4, NULL, 'ETD-1-1-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(51, 1, 1, 5, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 5, NULL, 'Revenga ETD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 1003, 1, 5, NULL, 'ETD-1-2-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(51, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(51, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);