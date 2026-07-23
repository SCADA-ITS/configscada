INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(20, NULL, 1, 0010020, NULL, NULL, 'SNO-1-01-CE', 'SNO-1-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 2, 0020020, NULL, NULL, 'SNO-1-02-CE', 'SNO-1-02-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 3, 0030020, NULL, NULL, 'SNO-2-01-CE', 'SNO-2-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 4, 0040020, NULL, NULL, 'SNO-2-02-CE', 'SNO-2-02-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(20, 1003, 1, 1, NULL, 'SNO-1-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1003, 1, 2, NULL, 'SNO-1-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1003, 1, 3, NULL, 'SNO-2-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 1003, 1, 4, NULL, 'SNO-2-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(20, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);