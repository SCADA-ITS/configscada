INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(90, NULL, 1, 0010099, NULL, NULL, 'SANCIONADOR', 'SANCIONADOR', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(90, 1, 1, 1, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(90, 2, 1, 1, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(90, 3, 1, 1, NULL, '99999999', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(90, 4, 1, 1, NULL, '6240899900001327607855', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
