INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(89, NULL, 1, 0010089, NULL, NULL, 'Dvere_1', 'Dvere_1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 2, 0020089, NULL, NULL, 'Dvere_2', 'Dvere_2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 3, 0030089, NULL, NULL, 'Dvere_3', 'Dvere_3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 4, 0040089, NULL, NULL, 'Dvere_4', 'Dvere_4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 5, 0050089, NULL, NULL, 'Dvere_5', 'Dvere_5', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 6, 0060089, NULL, NULL, 'Dvere_6', 'Dvere_6', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 7, 0070089, NULL, NULL, 'Dvere_7', 'Dvere_7', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 8, 0080089, NULL, NULL, 'Dvere_8', 'Dvere_8', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 9, 0090089, NULL, NULL, 'Dvere_9', 'Dvere_9', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 10, 0100089, NULL, NULL, 'Dvere_10', 'Dvere_10', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(89, 1003, 1, 1, NULL, 'Dvere_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 2, NULL, 'Dvere_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 3, NULL, 'Dvere_3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 4, NULL, 'Dvere_4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 5, NULL, 'Dvere_5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 6, NULL, 'Dvere_6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 7, NULL, 'Dvere_7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 8, NULL, 'Dvere_8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 9, NULL, 'Dvere_9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 10, NULL, 'Dvere_10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(89, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
