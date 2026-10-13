INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(87, NULL, 1, 0010087, NULL, NULL, 'TANK-ALJ-1', 'TANK-ALJ-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, NULL, 2, 0020087, NULL, NULL, 'TANK-ALJ-2', 'TANK-ALJ-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, NULL, 3, 0030087, NULL, NULL, 'TANK-ALJ-3', 'TANK-ALJ-3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, NULL, 4, 0040087, NULL, NULL, 'TANK-ALJ-4', 'TANK-ALJ-4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(87, 1003, 1, 1, NULL, 'TANK-ALJ-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(87, 1003, 1, 2, NULL, 'TANK-ALJ-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(87, 1003, 1, 3, NULL, 'TANK-ALJ-3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(87, 1003, 1, 4, NULL, 'TANK-ALJ-4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(87, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(87, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
