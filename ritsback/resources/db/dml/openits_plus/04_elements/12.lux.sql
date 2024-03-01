INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(12, NULL, 1, 0010012, NULL, NULL, 'LQ-LUX-1CA', 'LQ-LUX-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, NULL, 2, 0020012, NULL, NULL, 'LQ-LUX-2CA', 'LQ-LUX-2CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, NULL, 3, 0030012, NULL, NULL, 'LQ-LUX-3CA', 'LQ-LUX-3CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, NULL, 4, 0040012, NULL, NULL, 'LQ-LUX-4CA', 'LQ-LUX-4CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, NULL, 5, 0050012, NULL, NULL, 'LQ-LUX-1MA', 'LQ-LUX-1MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, NULL, 6, 0060012, NULL, NULL, 'LQ-LUX-2MA', 'LQ-LUX-2MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, NULL, 7, 0070012, NULL, NULL, 'LQ-LUX-3MA', 'LQ-LUX-3MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, NULL, 8, 0080012, NULL, NULL, 'LQ-LUX-4MA', 'LQ-LUX-4MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(12, 1003, 1, 1, NULL, 'LQ-LUX-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(12, 1003, 1, 2, NULL, 'LQ-LUX-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(12, 1003, 1, 3, NULL, 'LQ-LUX-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(12, 1003, 1, 4, NULL, 'LQ-LUX-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(12, 1003, 1, 5, NULL, 'LQ-LUX-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(12, 1003, 1, 6, NULL, 'LQ-LUX-2MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(12, 1003, 1, 7, NULL, 'LQ-LUX-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(12, 1003, 1, 8, NULL, 'LQ-LUX-4MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(12, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(12, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);