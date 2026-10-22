INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(40, NULL, 1, 0010040, NULL, NULL, 'Pohybove_cidlo_1', 'Pohybove_cidlo_1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, NULL, 2, 0020040, NULL, NULL, 'Pohybove_cidlo_2', 'Pohybove_cidlo_2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, NULL, 3, 0030040, NULL, NULL, 'Pohybove_cidlo_3', 'Pohybove_cidlo_3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, NULL, 4, 0040040, NULL, NULL, 'Pohybove_cidlo_4', 'Pohybove_cidlo_4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, NULL, 5, 0050040, NULL, NULL, 'Pohybove_cidlo_5', 'Pohybove_cidlo_5', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, NULL, 6, 0060040, NULL, NULL, 'Pohybove_cidlo_6', 'Pohybove_cidlo_6', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, NULL, 7, 0070040, NULL, NULL, 'Pohybove_cidlo_7', 'Pohybove_cidlo_7', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, NULL, 8, 0080040, NULL, NULL, 'Pohybove_cidlo_8', 'Pohybove_cidlo_8', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(40, 3, 1, 1, NULL, 'RAIDJSKD01', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 1003, 1, 1, NULL, 'Pohybove_cidlo_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(40, 3, 1, 2, NULL, 'RAIDJSKD09', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 1003, 1, 2, NULL, 'Pohybove_cidlo_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(40, 3, 1, 3, NULL, 'RAIDJSKD10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 1003, 1, 3, NULL, 'Pohybove_cidlo_3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(40, 3, 1, 4, NULL, 'RAIDJSKD13', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 1003, 1, 4, NULL, 'Pohybove_cidlo_4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(40, 3, 1, 5, NULL, 'RAIDJSKD14', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 1003, 1, 5, NULL, 'Pohybove_cidlo_5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(40, 3, 1, 6, NULL, 'RAIDJSKD17', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 1003, 1, 6, NULL, 'Pohybove_cidlo_6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(40, 3, 1, 7, NULL, 'RAIDJSKD18', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 1003, 1, 7, NULL, 'Pohybove_cidlo_7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(40, 3, 1, 8, NULL, 'RAIDJSKD30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 1003, 1, 8, NULL, 'Pohybove_cidlo_8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(40, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(40, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
