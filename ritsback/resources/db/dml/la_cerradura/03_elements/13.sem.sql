INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(13, 1, 1, 0010013, NULL, NULL, 'SMF-2-01-CE', 'SMF-2-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 1, 2, 0020013, NULL, NULL, 'SMF-2-02-CE', 'SMF-2-02-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 1, 3, 0030013, NULL, NULL, 'SMF-2-03-CE', 'SMF-2-03-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 1, 4, 0040013, NULL, NULL, 'SMF-2-04-CE', 'SMF-2-04-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 1, 5, 0050013, NULL, NULL, 'SMF-1-01-CE', 'SMF-1-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 1, 6, 0060013, NULL, NULL, 'SMF-1-02-CE', 'SMF-1-02-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 1, 7, 0070013, NULL, NULL, 'SMF-1-03-CE', 'SMF-1-03-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 1, 8, 0080013, NULL, NULL, 'SMF-1-04-CE', 'SMF-1-04-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(13, 1003, 1, 1, NULL, 'SMF-2-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(13, 1003, 1, 2, NULL, 'SMF-2-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(13, 1003, 1, 3, NULL, 'SMF-2-03-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(13, 1003, 1, 4, NULL, 'SMF-2-04-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(13, 1003, 1, 5, NULL, 'SMF-1-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(13, 1003, 1, 6, NULL, 'SMF-1-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(13, 1003, 1, 7, NULL, 'SMF-1-03-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(13, 1003, 1, 8, NULL, 'SMF-1-04-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(13, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(13, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);