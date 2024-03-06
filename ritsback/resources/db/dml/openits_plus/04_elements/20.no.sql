INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(20, NULL, 1, 0010020, NULL, NULL, 'LQ-NO-1CA', 'LQ-NO-1CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 2, 0020020, NULL, NULL, 'LQ-NO-2CA', 'LQ-NO-2CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 3, 0030020, NULL, NULL, 'LQ-NO-3CA', 'LQ-NO-3CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 4, 0040020, NULL, NULL, 'LQ-NO-4CA', 'LQ-NO-4CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 5, 0050020, NULL, NULL, 'LQ-NO-5CA', 'LQ-NO-5CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 6, 0060020, NULL, NULL, 'LQ-NO-1MA', 'LQ-NO-1MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 7, 0070020, NULL, NULL, 'LQ-NO-2MA', 'LQ-NO-2MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 8, 0080020, NULL, NULL, 'LQ-NO-3MA', 'LQ-NO-3MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 9, 0090020, NULL, NULL, 'LQ-NO-4MA', 'LQ-NO-4MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(20, 2, 1, 1, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 1, NULL, 'LQ-NO-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 2, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 2, NULL, 'LQ-NO-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 3, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 3, NULL, 'LQ-NO-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 4, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 4, NULL, 'LQ-NO-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 5, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 5, NULL, 'LQ-NO-5CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 6, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 6, NULL, 'LQ-NO-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 7, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 7, NULL, 'LQ-NO-2MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 8, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 8, NULL, 'LQ-NO-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 9, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 9, NULL, 'LQ-NO-4MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(20, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);