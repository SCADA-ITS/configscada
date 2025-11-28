INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(20, NULL, 1, 0010020, NULL, NULL, 'SNO-1-01-CA', 'SNO-1-01-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 2, 0020020, NULL, NULL, 'SNO-1-02-CA', 'SNO-1-02-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 3, 0030020, NULL, NULL, 'SNO-1-03-CA', 'SNO-1-03-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 4, 0040020, NULL, NULL, 'SNO-1-04-CA', 'SNO-1-04-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 5, 0050020, NULL, NULL, 'SNO-2-01-CA', 'SNO-2-01-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 6, 0060020, NULL, NULL, 'SNO-2-02-CA', 'SNO-2-02-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 7, 0070020, NULL, NULL, 'SNO-2-03-CA', 'SNO-2-03-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 8, 0080020, NULL, NULL, 'SNO-2-04-CA', 'SNO-2-04-CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(20, 1, 1, 1, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 1, NULL, 'SNO-1-01-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1, 1, 2, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 2, NULL, 'SNO-1-02-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1, 1, 3, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 3, NULL, 'SNO-1-03-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1, 1, 4, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 4, NULL, 'SNO-1-04-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1, 1, 5, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 5, NULL, 'SNO-2-01-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1, 1, 6, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 6, NULL, 'SNO-2-02-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1, 1, 7, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 7, NULL, 'SNO-2-03-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1, 1, 8, NULL, 'Ortrat', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 8, NULL, 'SNO-2-04-CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(20, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);