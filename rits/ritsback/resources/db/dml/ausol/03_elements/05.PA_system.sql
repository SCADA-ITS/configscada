INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(5, 1, 1, 1000, NULL, NULL, 'RV-DAC-1', 'RV-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1, 2, 1007, NULL, NULL, 'MM-DAC-1', 'MM-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1, 3, 1018, NULL, NULL, 'STMI-DAC-1', 'STMI-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1, 4, 1021, NULL, NULL, 'LQ-DAC-1', 'LQ-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1, 5, 1031, NULL, NULL, 'CH-DAC-1', 'CH-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1, 6, 1053, NULL, NULL, 'COR-DAC-1', 'COR-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1, 7, 1064, NULL, NULL, 'STM2-DAC-1', 'STM2-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1, 8, 1078, NULL, NULL, 'NG-DAC-1', 'NG-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1, 9, 1042, NULL, NULL, 'CAS-DAC-1', 'CAS-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1, 25, 1075, NULL, NULL, 'EST-DAC-1', 'EST-DAC-1', NULL, NULL, 'PAsystemParamsAdapter.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(5, 5, 1, 1, NULL, '192.168.11.13', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 1, NULL, 'RV-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(5, 5, 1, 2, NULL, '192.168.12.13', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 2, NULL, 'MM-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(5, 5, 1, 3, NULL, '192.168.13.15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 3, NULL, 'STMI-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(5, 5, 1, 4, NULL, '192.168.14.15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 4, NULL, 'LQ-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(5, 5, 1, 5, NULL, '192.168.9.15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 5, NULL, 'CH-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(5, 5, 1, 6, NULL, '192.168.15.15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 6, NULL, 'COR-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(5, 5, 1, 7, NULL, '192.168.10.15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 7, NULL, 'STM2-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(5, 5, 1, 8, NULL, '192.168.17.53', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 8, NULL, 'NG-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(5, 5, 1, 9, NULL, '192.168.5.135', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 9, NULL, 'CAS-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(5, 5, 1, 25, NULL, '192.168.16.15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 25, NULL, 'EST-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(5, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 25, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);