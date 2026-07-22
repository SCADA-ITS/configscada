INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(73, NULL, 1, 0010073, NULL, NULL, 'ILUM-EMER-GRU-DP2', 'ILUM-EMER-GRU-DP2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(73, NULL, 2, 0020073, NULL, NULL, 'ILUM-EMER-GRU-DP1', 'ILUM-EMER-GRU-DP1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(73, NULL, 3, 0030073, NULL, NULL, 'ILUM-EMER-GRU-CA2', 'ILUM-EMER-GRU-CA2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(73, NULL, 4, 0040073, NULL, NULL, 'ILUM-EMER-GRU-CA1', 'ILUM-EMER-GRU-CA1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(73, 1003, 1, 1, NULL, 'ILUM-EMER-GRU-DP2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(73, 1003, 1, 2, NULL, 'ILUM-EMER-GRU-DP1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(73, 1003, 1, 3, NULL, 'ILUM-EMER-GRU-CA2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(73, 1003, 1, 4, NULL, 'ILUM-EMER-GRU-CA1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(73, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(73, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(73, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(73, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
