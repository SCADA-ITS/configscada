INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(71, 12, 1, 0010071, NULL, NULL, 'AF-1-01-CE', 'AF-1-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 2, 0020071, NULL, NULL, 'AF-1-02-CE', 'AF-1-02-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 3, 0030071, NULL, NULL, 'AF-2-01-CE', 'AF-2-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 4, 0040071, NULL, NULL, 'AF-2-02-CE', 'AF-2-02-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(71, 1003, 1, 1, NULL, 'AF-1-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1003, 1, 2, NULL, 'AF-1-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1003, 1, 3, NULL, 'AF-2-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1003, 1, 4, NULL, 'AF-2-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(71, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);