INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(55, 1, 1, 0010055, NULL, NULL, 'Reset_tunel', 'Reset_tunel', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 1, 2, 0020055, NULL, NULL, 'CT-1', 'CT-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 1, 3, 0030055, NULL, NULL, 'CT-2', 'CT-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 1, 4, 0040055, NULL, NULL, 'CT-3', 'CT-3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 1, 5, 0050055, NULL, NULL, 'CT-4', 'CT-4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 1, 6, 0060055, NULL, NULL, 'CT-5', 'CT-5', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 1, 7, 0070055, NULL, NULL, 'CT-6', 'CT-6', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 1, 8, 0080055, NULL, NULL, 'CT-7', 'CT-7', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 1, 9, 0090055, NULL, NULL, 'CT-8', 'CT-8', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(55, 1003, 1, 1, NULL, 'Reset_tunel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 2, NULL, 'CT-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 3, NULL, 'CT-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 4, NULL, 'CT-3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 5, NULL, 'CT-4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 6, NULL, 'CT-5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 7, NULL, 'CT-6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 8, NULL, 'CT-7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 9, NULL, 'CT-8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(55, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
