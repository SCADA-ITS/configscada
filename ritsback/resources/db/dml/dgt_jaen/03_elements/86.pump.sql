INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(86, NULL, 1, 0010086, NULL, NULL, 'BOMB-PRIN-1-DP2', 'BOMB-PRIN-1-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 2, 0020086, NULL, NULL, 'BOMB-PRIN-2-DP2', 'BOMB-PRIN-2-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 3, 0030086, NULL, NULL, 'BOMB-LLEN-1-DP2', 'BOMB-LLEN-1-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 4, 0040086, NULL, NULL, 'BOMB-JOCK-1-DP2', 'BOMB-JOCK-1-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(86, 1003, 1, 1, NULL, 'BOMB-PRIN-1-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 2, NULL, 'BOMB-PRIN-2-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 3, NULL, 'BOMB-LLEN-1-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 4, NULL, 'BOMB-JOCK-1-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(86, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);