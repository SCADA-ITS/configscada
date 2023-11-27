INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(28, NULL, 1, 0010028, NULL, NULL, 'RV-NAS-1', 'RV-NAS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, NULL, 2, 0020028, NULL, NULL, 'MM-NAS-1', 'MM-NAS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, NULL, 3, 0030028, NULL, NULL, 'STMI-NAS-1', 'STMI-NAS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, NULL, 4, 0040028, NULL, NULL, 'COR-NAS1-BC', 'COR-NAS1-BC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, NULL, 5, 0050028, NULL, NULL, 'LQ-NAS-1', 'LQ-NAS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, NULL, 6, 0060028, NULL, NULL, 'CH-NAS-1', 'CH-NAS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, NULL, 7, 0070028, NULL, NULL, 'STM2-NAS-1', 'STM2-NAS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, NULL, 8, 0080028, NULL, NULL, 'CAS-NAS1-BC', 'CAS-NAS1-BC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, NULL, 9, 0090028, NULL, NULL, 'EST-NAS-1', 'EST-NAS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, NULL, 10, 0100028, NULL, NULL, 'NG-NAS-1', 'NG-NAS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(28, 1003, 1, 1, NULL, 'RV-NAS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(28, 1003, 1, 2, NULL, 'MM-NAS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(28, 1003, 1, 3, NULL, 'STMI-NAS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(28, 1003, 1, 4, NULL, 'COR-NAS1-BC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(28, 1003, 1, 5, NULL, 'LQ-NAS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(28, 1003, 1, 6, NULL, 'CH-NAS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(28, 1003, 1, 7, NULL, 'STM2-NAS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(28, 1003, 1, 8, NULL, 'CAS-NAS1-BC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(28, 1003, 1, 9, NULL, 'EST-NAS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(28, 1003, 1, 10, NULL, 'NG-NAS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(28, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(28, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);