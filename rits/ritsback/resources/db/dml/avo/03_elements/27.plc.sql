INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(27, 1, 1, 0010027, NULL, NULL, 'PLC_CT01', 'PLC_CT01', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 1, 2, 0020027, NULL, NULL, 'PLC_CT02', 'PLC_CT02', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 1, 3, 0030027, NULL, NULL, 'PLC_CT03', 'PLC_CT03', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 1, 4, 0040027, NULL, NULL, 'PLC_CT04', 'PLC_CT04', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 1, 5, 0050027, NULL, NULL, 'PLC_CT05', 'PLC_CT05', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 1, 6, 0060027, NULL, NULL, 'PLC_CT06', 'PLC_CT06', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 1, 7, 0070027, NULL, NULL, 'PLC_CT07', 'PLC_CT07', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 1, 8, 0080027, NULL, NULL, 'PLC_CT08', 'PLC_CT08', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(27, 1003, 1, 1, NULL, 'PLC_CT01', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(27, 1003, 1, 2, NULL, 'PLC_CT02', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(27, 1003, 1, 3, NULL, 'PLC_CT03', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(27, 1003, 1, 4, NULL, 'PLC_CT04', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(27, 1003, 1, 5, NULL, 'PLC_CT05', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(27, 1003, 1, 6, NULL, 'PLC_CT06', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(27, 1003, 1, 7, NULL, 'PLC_CT07', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(27, 1003, 1, 8, NULL, 'PLC_CT08', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(27, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(27, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
