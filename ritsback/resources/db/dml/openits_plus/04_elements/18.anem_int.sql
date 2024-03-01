INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(18, NULL, 7, 0070018, NULL, NULL, 'LQ-ANI-1CA', 'LQ-ANI-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 8, 0080018, NULL, NULL, 'LQ-ANI-2CA', 'LQ-ANI-2CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 9, 0090018, NULL, NULL, 'LQ-ANI-3CA', 'LQ-ANI-3CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 11, 0110018, NULL, NULL, 'LQ-ANI-1MA', 'LQ-ANI-1MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 12, 0120018, NULL, NULL, 'LQ-ANI-2MA', 'LQ-ANI-2MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, NULL, 13, 0130018, NULL, NULL, 'LQ-ANI-3MA', 'LQ-ANI-3MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(18, 1003, 1, 7, NULL, 'LQ-ANI-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 8, NULL, 'LQ-ANI-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 9, NULL, 'LQ-ANI-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 11, NULL, 'LQ-ANI-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 12, NULL, 'LQ-ANI-2MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(18, 1003, 1, 13, NULL, 'LQ-ANI-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(18, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(18, 13, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);