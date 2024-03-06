INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(56, NULL, 1, 0010056, NULL, NULL, 'TUN-CS-ITS1', 'Sinoptico Mando y Proteccion C.S. ITS1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, NULL, 2, 0020056, NULL, NULL, 'TUN-CSA-SAI', 'Sinoptico Mando y Proteccion C.S.A SAI', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, NULL, 3, 0030056, NULL, NULL, 'TUN-CSA-RED', 'Sinoptico Mando y Proteccion C.S.A RED', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, NULL, 4, 0040056, NULL, NULL, 'TUN-CSV-TSM', 'Sinoptico Mando y Proteccion C.S.V TSM', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, NULL, 5, 0050056, NULL, NULL, 'TUN-CSV-TSC', 'Sinoptico Mando y Proteccion C.S.V TSC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, NULL, 6, 0060056, NULL, NULL, 'TUN-CSA', 'Sinoptico Mando y Proteccion C.S.A', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(56, 1003, 1, 1, NULL, 'TUN-CS-ITS1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(56, 1003, 1, 2, NULL, 'TUN-CSA-SAI', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(56, 1003, 1, 3, NULL, 'TUN-CSA-RED', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(56, 1003, 1, 4, NULL, 'TUN-CSV-TSM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(56, 1003, 1, 5, NULL, 'TUN-CSV-TSC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(56, 1003, 1, 6, NULL, 'TUN-CSA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(56, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(56, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);