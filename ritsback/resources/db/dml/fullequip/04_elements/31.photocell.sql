INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(31, NULL, 1, 0010031, NULL, NULL, 'STMI-FOT-1CA', 'STMI-FOT-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 2, 0020031, NULL, NULL, 'LQ-FOT-1MA', 'LQ-FOT-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 3, 0030031, NULL, NULL, 'RV-FOT-1CA', 'RV-FOT-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 4, 0040031, NULL, NULL, 'MM-FOT-1MA', 'MM-FOT-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 5, 0050031, NULL, NULL, 'CH-FOT-1CA', 'CH-FOT-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 6, 0060031, NULL, NULL, 'CAS-FOT-1MA', 'CAS-FOT-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 7, 0070031, NULL, NULL, 'CAS-FOT-1CA', 'CAS-FOT-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 8, 0080031, NULL, NULL, 'COR-FOT-1MA', 'COR-FOT-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 9, 0090031, NULL, NULL, 'COR-FOT-1CA', 'COR-FOT-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 10, 0100031, NULL, NULL, 'STM2-FOT-1MA', 'STM2-FOT-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 11, 0110031, NULL, NULL, 'EST-FOT-1CA', 'EST-FOT-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, NULL, 12, 0120031, NULL, NULL, 'NG-FOT-1CA', 'NG-FOT-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(31, 1003, 1, 1, NULL, 'STMI-FOT-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 2, NULL, 'LQ-FOT-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 3, NULL, 'RV-FOT-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 4, NULL, 'MM-FOT-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 5, NULL, 'CH-FOT-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 6, NULL, 'CAS-FOT-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 7, NULL, 'CAS-FOT-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 8, NULL, 'COR-FOT-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 9, NULL, 'COR-FOT-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 10, NULL, 'STM2-FOT-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 11, NULL, 'EST-FOT-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(31, 1003, 1, 12, NULL, 'NG-FOT-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(31, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(31, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);