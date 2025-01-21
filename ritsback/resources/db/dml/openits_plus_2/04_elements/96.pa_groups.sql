-- INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
-- (96, NULL, 1, 0010096, NULL, NULL, 'TUN-MEG-GR-TUNEL', 'TUN-MEG-GR-TUNEL', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- (96, NULL, 2, 0020096, NULL, NULL, 'TUN-MEG-GR-TSC', 'TUN-MEG-GR-TSC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- (96, NULL, 3, 0030096, NULL, NULL, 'TUN-MEG-GR-TSM', 'TUN-MEG-GR-TSM', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
-- (96, 5, 1, 1, NULL, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- (96, 1003, 1, 1, NULL, 'TUN-MEG-GR-TUNEL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

-- (96, 5, 1, 2, NULL, '21', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- (96, 1003, 1, 2, NULL, 'TUN-MEG-GR-TSC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

-- (96, 5, 1, 3, NULL, '22', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- (96, 1003, 1, 3, NULL, 'TUN-MEG-GR-TSM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
-- (96, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- (96, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- (96, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);