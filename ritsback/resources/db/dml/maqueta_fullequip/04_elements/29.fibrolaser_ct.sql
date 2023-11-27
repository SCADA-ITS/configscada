INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(29, 2, 1, 0010029, NULL, NULL, 'RV-OTS-1', 'RV-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 2, 0020029, NULL, NULL, 'MM-OTS-1', 'MM-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 3, 0030029, NULL, NULL, 'STMI-OTS-1', 'STMI-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 4, 0040029, NULL, NULL, 'CAS-OTS-1', 'CAS-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 5, 0050029, NULL, NULL, 'CH-OTS-1', 'CH-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 6, 0060029, NULL, NULL, 'EST-OTS-1', 'EST-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 7, 0070029, NULL, NULL, 'STM2-OTS-1', 'STM2-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 8, 0080029, NULL, NULL, 'LQ-OTS-1', 'LQ-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 9, 0090029, NULL, NULL, 'NG-OTS-1', 'NG-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 10, 0100029, NULL, NULL, 'COR-OTS-1', 'COR-OTS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(29, 1003, 1, 1, NULL, 'RV-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1003, 1, 2, NULL, 'MM-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1003, 1, 3, NULL, 'STMI-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1003, 1, 4, NULL, 'CAS-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1003, 1, 5, NULL, 'CH-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1003, 1, 6, NULL, 'EST-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1003, 1, 7, NULL, 'STM2-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1003, 1, 8, NULL, 'LQ-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1003, 1, 9, NULL, 'NG-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(29, 1003, 1, 10, NULL, 'COR-OTS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(29, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(29, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);