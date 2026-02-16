INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(11, 4, 1, 0010011, NULL, NULL, 'SOS-1', 'SOS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 2, 0020011, NULL, NULL, 'SOS-2', 'SOS-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 3, 0030011, NULL, NULL, 'SOS-3', 'SOS-3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 4, 0040011, NULL, NULL, 'SOS-4', 'SOS-4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 5, 0050011, NULL, NULL, 'SOS-5', 'SOS-5', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 6, 0060011, NULL, NULL, 'SOS-6', 'SOS-6', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 7, 0070011, NULL, NULL, 'SOS-7', 'SOS-7', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 8, 0080011, NULL, NULL, 'SOS-8', 'SOS-8', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 9, 0090011, NULL, NULL, 'SOS-9', 'SOS-9', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 10, 0100011, NULL, NULL, 'SOS-10', 'SOS-10', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(11, 1, 1, 1, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 1, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 1, NULL, 'SOS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 2, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 2, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 2, NULL, 'SOS-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 3, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 3, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 3, NULL, 'SOS-3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 4, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 4, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 4, NULL, 'SOS-4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 5, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 5, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 5, NULL, 'SOS-5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 6, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 6, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 6, NULL, 'SOS-6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 7, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 7, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 7, NULL, 'SOS-7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 8, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 8, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 8, NULL, 'SOS-8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 9, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 9, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 9, NULL, 'SOS-9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 10, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 10, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 10, NULL, 'SOS-10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(11, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);