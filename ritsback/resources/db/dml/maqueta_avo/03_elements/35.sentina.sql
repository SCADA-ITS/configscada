INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(35, NULL, 1, 0010035, NULL, NULL, 'SENTINA_1', 'SENTINA_1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(35, NULL, 2, 0020035, NULL, NULL, 'SENTINA_2', 'SENTINA_2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(35, NULL, 3, 0030035, NULL, NULL, 'SENTINA_3', 'SENTINA_3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(35, NULL, 4, 0040035, NULL, NULL, 'SENTINA_4', 'SENTINA_4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(35, 1003, 1, 1, NULL, 'SENTINA_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(35, 1003, 1, 2, NULL, 'SENTINA_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(35, 1003, 1, 3, NULL, 'SENTINA_3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(35, 1003, 1, 4, NULL, 'SENTINA_4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(35, 1, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(35, 2, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(35, 3, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(35, 4, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
