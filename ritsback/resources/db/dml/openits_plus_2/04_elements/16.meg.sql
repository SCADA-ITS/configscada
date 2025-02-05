INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(16, NULL, 1, 0010016, NULL, NULL, 'TUN-MEG-1MA', 'TUN-MEG-1MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, NULL, 2, 0020016, NULL, NULL, 'TUN-MEG-2MA', 'TUN-MEG-2MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, NULL, 3, 0030016, NULL, NULL, 'TUN-MEG-3MA', 'TUN-MEG-3MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, NULL, 4, 0040016, NULL, NULL, 'TUN-MEG-4MA', 'TUN-MEG-4MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, NULL, 5, 0050016, NULL, NULL, 'TUN-MEG-1CA', 'TUN-MEG-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, NULL, 6, 0060016, NULL, NULL, 'TUN-MEG-2CA', 'TUN-MEG-2CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, NULL, 7, 0070016, NULL, NULL, 'TUN-MEG-3CA', 'TUN-MEG-3CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, NULL, 8, 0080016, NULL, NULL, 'TUN-MEG-4CA', 'TUN-MEG-4CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, NULL, 9, 0090016, NULL, NULL, 'TUN-RADIO', 'TUN-RADIO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(16, 5, 1, 1, NULL, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 1, NULL, 'TUN-MEG-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(16, 5, 1, 2, NULL, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 2, NULL, 'TUN-MEG-2MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(16, 5, 1, 3, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 3, NULL, 'TUN-MEG-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(16, 5, 1, 4, NULL, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 4, NULL, 'TUN-MEG-4MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(16, 5, 1, 5, NULL, '5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 5, NULL, 'TUN-MEG-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(16, 5, 1, 6, NULL, '6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 6, NULL, 'TUN-MEG-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(16, 5, 1, 7, NULL, '7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 7, NULL, 'TUN-MEG-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(16, 5, 1, 8, NULL, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 8, NULL, 'TUN-MEG-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(16, 5, 1, 9, NULL, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 9, NULL, 'TUN-RADIO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(16, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);