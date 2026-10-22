INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(48, NULL, 1, 0010048, NULL, NULL, 'CAS-COMP-TOX-BC', 'CAS-COMP-TOX-BC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, NULL, 2, 0020048, NULL, NULL, 'MM-COMP-TOX-BC', 'MM-COMP-TOX-BC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, NULL, 3, 0030048, NULL, NULL, 'CAS-COMP-TOX-BM', 'CAS-COMP-TOX-BM', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, NULL, 4, 0040048, NULL, NULL, 'COR-COMP-TOX-BM', 'COR-COMP-TOX-BM', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, NULL, 5, 0050048, NULL, NULL, 'STM2-COMP-TOX-BC', 'STM2-COMP-TOX-BC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, NULL, 6, 0060048, NULL, NULL, 'STM2-COMP-TOX-BM', 'STM2-COMP-TOX-BM', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, NULL, 7, 0070048, NULL, NULL, 'CH-COMP-TOX-BC', 'CH-COMP-TOX-BC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, NULL, 8, 0080048, NULL, NULL, 'CH-COMP-TOX-BM', 'CH-COMP-TOX-BM', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, NULL, 9, 0090048, NULL, NULL, 'LQ-COMP-TOX-MA', 'LQ-COMP-TOX-MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(48, 1003, 1, 1, NULL, 'CAS-COMP-TOX-BC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(48, 1003, 1, 2, NULL, 'MM-COMP-TOX-BC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(48, 1003, 1, 3, NULL, 'CAS-COMP-TOX-BM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(48, 1003, 1, 4, NULL, 'COR-COMP-TOX-BM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(48, 1003, 1, 5, NULL, 'STM2-COMP-TOX-BC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(48, 1003, 1, 6, NULL, 'STM2-COMP-TOX-BM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(48, 1003, 1, 7, NULL, 'CH-COMP-TOX-BC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(48, 1003, 1, 8, NULL, 'CH-COMP-TOX-BM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(48, 1003, 1, 9, NULL, 'LQ-COMP-TOX-MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(48, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(48, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);