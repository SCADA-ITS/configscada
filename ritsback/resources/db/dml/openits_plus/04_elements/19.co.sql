INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(19, NULL, 1, 0010019, NULL, NULL, 'LQ-CO-1CA', 'LQ-CO-1CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 2, 0020019, NULL, NULL, 'LQ-CO-2CA', 'LQ-CO-2CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 3, 0030019, NULL, NULL, 'LQ-CO-3CA', 'LQ-CO-3CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 4, 0040019, NULL, NULL, 'LQ-CO-4CA', 'LQ-CO-4CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 5, 0050019, NULL, NULL, 'LQ-CO-5CA', 'LQ-CO-5CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 6, 0060019, NULL, NULL, 'LQ-CO-1MA', 'LQ-CO-1MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 7, 0070019, NULL, NULL, 'LQ-CO-2MA', 'LQ-CO-2MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 8, 0080019, NULL, NULL, 'LQ-CO-3MA', 'LQ-CO-3MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, NULL, 9, 0090019, NULL, NULL, 'LQ-CO-4MA', 'LQ-CO-4MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(19, 2, 1, 1, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 1, NULL, 'LQ-CO-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 2, 1, 2, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 2, NULL, 'LQ-CO-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 2, 1, 3, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 3, NULL, 'LQ-CO-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 2, 1, 4, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 4, NULL, 'LQ-CO-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 2, 1, 5, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 5, NULL, 'LQ-CO-5CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 2, 1, 6, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 6, NULL, 'LQ-CO-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 2, 1, 7, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 7, NULL, 'LQ-CO-2MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 2, 1, 8, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 8, NULL, 'LQ-CO-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(19, 2, 1, 9, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 1003, 1, 9, NULL, 'LQ-CO-4MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(19, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(19, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);