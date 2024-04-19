INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(6, NULL, 1, 0010006, NULL, NULL, 'TT_01', 'TT_01', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, NULL, 2, 0020006, NULL, NULL, 'TT_02', 'TT_02', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, NULL, 3, 0030006, NULL, NULL, 'TT_03', 'TT_03', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(6, 1, 1, 1, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 1, NULL, '25.9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 1, NULL, '39.9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 1, NULL, 'Ventas de Arnaiz', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 1, NULL, 'PMV-01', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 1003, 1, 1, NULL, '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(6, 1, 1, 2, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 2, NULL, '25.9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 2, NULL, '49.9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 2, NULL, 'Belate', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 2, NULL, 'PMV-01,PMV-02', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 1003, 1, 2, NULL, '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(6, 1, 1, 3, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 3, NULL, '25.9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 3, NULL, '60.9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 3, NULL, 'Etxalar', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 3, NULL, 'PMV-01,PMV-02,PMV-03', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 1003, 1, 3, NULL, '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(6, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);