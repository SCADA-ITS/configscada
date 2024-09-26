INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(6, NULL, 1, 0010006, NULL, NULL, 'TT_01', 'Fuengirola - Marbella', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, NULL, 2, 0020006, NULL, NULL, 'TT_02', 'Fuengirola - San Pedro Alcántara', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, NULL, 3, 0030006, NULL, NULL, 'TT_03', 'Fuengirola - Estepona', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, NULL, 4, 0040006, NULL, NULL, 'TT_04', 'Elviria - Marbella', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, NULL, 5, 0050006, NULL, NULL, 'TT_05', 'Elviria - Estepona', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, NULL, 6, 0060006, NULL, NULL, 'TT_06', 'Marbella - Estepona', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(6, 1, 1, 1, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 1, NULL, '118.400', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 1, NULL, '119.100', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 1, NULL, 'Marbella', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 1, NULL, 'MAP-NTCIP-10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(6, 1, 1, 2, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 2, NULL, '118.400', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 2, NULL, '120.500', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 2, NULL, 'S Pedro A', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 2, NULL, 'MAP-NTCIP-10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(6, 1, 1, 3, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 3, NULL, '118.400', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 3, NULL, '121.300', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 3, NULL, 'Estepona', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 3, NULL, 'MAP-NTCIP-10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 


(6, 1, 1, 4, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 4, NULL, '118.800', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 4, NULL, '119.100', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 4, NULL, 'Marbella', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 4, NULL, 'MAP-NTCIP-9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 


(6, 1, 1, 5, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 5, NULL, '118.800', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 5, NULL, '121.300', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 5, NULL, 'Estepona', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 5, NULL, 'MAP-NTCIP-9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 


(6, 1, 1, 6, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 6, NULL, '119.300', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 6, NULL, '121.300', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 6, NULL, 'Estepona', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 6, NULL, 'MAP-NTCIP-7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 


INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(6, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(6, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
