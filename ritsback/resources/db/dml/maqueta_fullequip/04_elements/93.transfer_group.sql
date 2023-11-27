INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(93, NULL, 1, 0010093, NULL, NULL, 'RV-DEP-GE', 'RV-DEP-GE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, NULL, 2, 0020093, NULL, NULL, 'LQ-DEP-GE', 'LQ-DEP-GE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, NULL, 4, 0040093, NULL, NULL, 'MM-DEP-GE', 'MM-DEP-GE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, NULL, 5, 0050093, NULL, NULL, 'CAS-DEP-GE-BM', 'CAS-DEP-GE-BM', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, NULL, 6, 0060093, NULL, NULL, 'CAS-DEP-GE-BC', 'CAS-DEP-GE-BC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, NULL, 7, 0070093, NULL, NULL, 'CH-DEP-GE', 'CH-DEP-GE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, NULL, 9, 0090093, NULL, NULL, 'NG-DEP-GE', 'NG-DEP-GE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, NULL, 10, 0100093, NULL, NULL, 'STM2-DEP-GE', 'STM2-DEP-GE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, NULL, 11, 0110093, NULL, NULL, 'COR-DEP-GE-BM', 'COR-DEP-GE-BM', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, NULL, 12, 0120093, NULL, NULL, 'COR-DEP-GE-BC', 'COR-DEP-GE-BC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(93, 1003, 1, 1, NULL, 'RV-DEP-GE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(93, 1003, 1, 2, NULL, 'LQ-DEP-GE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(93, 1003, 1, 4, NULL, 'MM-DEP-GE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(93, 1003, 1, 5, NULL, 'CAS-DEP-GE-BM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(93, 1003, 1, 6, NULL, 'CAS-DEP-GE-BC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(93, 1003, 1, 7, NULL, 'CH-DEP-GE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(93, 1003, 1, 9, NULL, 'NG-DEP-GE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(93, 1003, 1, 10, NULL, 'STM2-DEP-GE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(93, 1003, 1, 11, NULL, 'COR-DEP-GE-BM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(93, 1003, 1, 12, NULL, 'COR-DEP-GE-BC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(93, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(93, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);