/*INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(3, NULL, 1, 0010003, NULL, NULL, 'SEC1_DET1', 'SEC1_DET1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, NULL, 2, 0020003, NULL, NULL, 'SEC1_DET2', 'SEC1_DET2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, NULL, 3, 0030003, NULL, NULL, 'SEC1_DET3', 'SEC1_DET3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, NULL, 4, 0040003, NULL, NULL, 'SEC1_DET4', 'SEC1_DET4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, NULL, 5, 0050003, NULL, NULL, 'SEC2_DET1', 'SEC2_DET1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, NULL, 6, 0060003, NULL, NULL, 'SEC2_DET2', 'SEC2_DET2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, NULL, 7, 0070003, NULL, NULL, 'SEC2_DET3', 'SEC2_DET3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, NULL, 8, 0080003, NULL, NULL, 'SEC2_DET4', 'SEC2_DET4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(3, 1, 1, 1, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 1, NULL, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 1, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 1, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 1, NULL, 'SEC1_DET1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 2, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 2, NULL, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 2, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 2, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 2, NULL, 'SEC1_DET2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 3, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 3, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 3, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 3, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 3, NULL, 'SEC1_DET3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 4, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 4, NULL, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 4, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 4, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 4, NULL, 'SEC1_DET4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 5, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 5, NULL, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 5, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 5, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 5, NULL, 'SEC2_DET1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 6, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 6, NULL, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 6, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 6, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 6, NULL, 'SEC2_DET2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 7, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 7, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 7, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 7, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 7, NULL, 'SEC2_DET3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 8, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 8, NULL, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 8, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 8, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 8, NULL, 'SEC2_DET4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(3, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);*/