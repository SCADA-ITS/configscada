INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(74, NULL, 9, 0090074, NULL, NULL, 'LQ-ERU-1-MASTER', 'LQ-ERU-1-MASTER', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(74, NULL, 10, 0100074, NULL, NULL, 'LQ-ERU-1-SLAVE', 'LQ-ERU-1-SLAVE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(74, 5, 1, 9, NULL, 'Redundante', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(74, 6, 1, 9, NULL, 'Maestra', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(74, 7, 1, 9, NULL, 'LQ-ERU-1-SLAVE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(74, 1003, 1, 9, NULL, 'LQ-ERU-1-MASTER', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(74, 5, 1, 10, NULL, 'Redundante', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(74, 6, 1, 10, NULL, 'Esclava', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(74, 7, 1, 10, NULL, 'LQ-ERU-1-MASTER', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(74, 1003, 1, 10, NULL, 'LQ-ERU-1-SLAVE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(74, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(74, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);