INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(39, NULL, 1, 0010039, NULL, NULL, 'Poklop_1', 'Poklop_1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 2, 0020039, NULL, NULL, 'Poklop_2', 'Poklop_2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 3, 0030039, NULL, NULL, 'Poklop_3', 'Poklop_3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 4, 0040039, NULL, NULL, 'Poklop_4', 'Poklop_4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 5, 0050039, NULL, NULL, 'Poklop_5', 'Poklop_5', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 6, 0060039, NULL, NULL, 'Poklop_6', 'Poklop_6', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 7, 0070039, NULL, NULL, 'Poklop_7', 'Poklop_7', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 8, 0080039, NULL, NULL, 'Poklop_8', 'Poklop_8', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 9, 0090039, NULL, NULL, 'Poklop_9', 'Poklop_9', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 10, 0100039, NULL, NULL, 'Poklop_10', 'Poklop_10', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, NULL, 11, 0110039, NULL, NULL, 'Poklop_11', 'Poklop_11', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(39, 3, 1, 1, NULL, 'RAIDJSKD01', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 1, NULL, 'Poklop_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 2, NULL, 'RAIDJSKD09', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 2, NULL, 'Poklop_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 3, NULL, 'RAIDJSKD10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 3, NULL, 'Poklop_3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 4, NULL, 'RAIDJSKD11', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 4, NULL, 'Poklop_4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 5, NULL, 'RAIDJSKD12', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 5, NULL, 'Poklop_5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 6, NULL, 'RAIDJSKD13', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 6, NULL, 'Poklop_6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 7, NULL, 'RAIDJSKD15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 7, NULL, 'Poklop_7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 8, NULL, 'RAIDJSKD17', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 8, NULL, 'Poklop_8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 9, NULL, 'RAIDJSKD18', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 9, NULL, 'Poklop_9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 10, NULL, 'RAIDJSKD23', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 10, NULL, 'Poklop_10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(39, 3, 1, 11, NULL, 'RAIDJSKD30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 1003, 1, 11, NULL, 'Poklop_11', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(39, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(39, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
