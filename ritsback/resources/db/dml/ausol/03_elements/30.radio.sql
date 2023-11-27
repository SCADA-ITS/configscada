INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(30, NULL, 1, 0010030, NULL, NULL, 'RV-MOD-1', 'RV-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, NULL, 2, 0020030, NULL, NULL, 'MM-MOD-1', 'MM-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, NULL, 3, 0030030, NULL, NULL, 'CH-MOD-1', 'CH-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, NULL, 4, 0040030, NULL, NULL, 'COR-MOD-1', 'COR-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, NULL, 5, 0050030, NULL, NULL, 'EST-MOD-1', 'EST-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, NULL, 6, 0060030, NULL, NULL, 'LQ-MOD-1', 'LQ-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, NULL, 7, 0070030, NULL, NULL, 'NG-MOD-1', 'NG-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, NULL, 8, 0080030, NULL, NULL, 'STM2-MOD-1', 'STM2-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, NULL, 9, 0090030, NULL, NULL, 'STMI-MOD-1', 'STMI-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, NULL, 10, 0100030, NULL, NULL, 'CAS-MOD-1', 'CAS-MOD-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(30, 1003, 1, 1, NULL, 'RV-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(30, 1003, 1, 2, NULL, 'MM-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(30, 1003, 1, 3, NULL, 'CH-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(30, 1003, 1, 4, NULL, 'COR-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(30, 1003, 1, 5, NULL, 'EST-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(30, 1003, 1, 6, NULL, 'LQ-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(30, 1003, 1, 7, NULL, 'NG-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(30, 1003, 1, 8, NULL, 'STM2-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(30, 1003, 1, 9, NULL, 'STMI-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(30, 1003, 1, 10, NULL, 'CAS-MOD-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(30, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(30, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
