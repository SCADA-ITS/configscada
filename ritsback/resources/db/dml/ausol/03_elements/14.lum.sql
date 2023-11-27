-- 
-- Table: conf.elements.LUM
--
INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, measure_change_all, created_at, updated_at) VALUES
(14, NULL, 1, 0010014, NULL, NULL, 'RV-LUM-1CA', 'RV-LUM-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 2, 0020014, NULL, NULL, 'RV-LUM-1MA', 'RV-LUM-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 3, 0030014, NULL, NULL, 'MM-LUM-1CA', 'MM-LUM-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 4, 0040014, NULL, NULL, 'MM-LUM-1MA', 'MM-LUM-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 5, 0050014, NULL, NULL, 'LQ-LUM-1CA', 'LQ-LUM-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 6, 0060014, NULL, NULL, 'LQ-LUM-1MA', 'LQ-LUM-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 7, 0070014, NULL, NULL, 'CH-LUM-1MA', 'CH-LUM-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 8, 0080014, NULL, NULL, 'CH-LUM-1CA', 'CH-LUM-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 9, 0090014, NULL, NULL, 'CAS-LUM-1MA', 'CAS-LUM-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 10, 0100014, NULL, NULL, 'CAS-LUM-1CA', 'CAS-LUM-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 11, 0110014, NULL, NULL, 'COR-LUM-1MA', 'COR-LUM-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 12, 0120014, NULL, NULL, 'COR-LUM-1CA', 'COR-LUM-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 13, 0130014, NULL, NULL, 'NG-LUM-1MA', 'NG-LUM-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 14, 0140014, NULL, NULL, 'NG-LUM-1CA', 'NG-LUM-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 15, 0150014, NULL, NULL, 'STM2-LUM-1MA', 'STM2-LUM-1MA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, NULL, 16, 0160014, NULL, NULL, 'STM2-LUM-1CA', 'STM2-LUM-1CA', NULL, NULL, 'SendValuesToIllumination.groovy', NULL, true, true, false, 0, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(14, 1003, 1, 1, NULL, 'RV-LUM-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 2, NULL, 'RV-LUM-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 3, NULL, 'MM-LUM-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 4, NULL, 'MM-LUM-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 5, NULL, 'LQ-LUM-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 6, NULL, 'LQ-LUM-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 7, NULL, 'CH-LUM-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 8, NULL, 'CH-LUM-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 9, NULL, 'CAS-LUM-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 10, NULL, 'CAS-LUM-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 11, NULL, 'COR-LUM-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 12, NULL, 'COR-LUM-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 13, NULL, 'NG-LUM-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 14, NULL, 'NG-LUM-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 15, NULL, 'STM2-LUM-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(14, 1003, 1, 16, NULL, 'STM2-LUM-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(14, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 13, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 14, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 15, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(14, 16, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
