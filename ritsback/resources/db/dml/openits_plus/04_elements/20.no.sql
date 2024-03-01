INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(20, NULL, 9, 0090020, NULL, NULL, 'LQ-NO-1CA', 'LQ-NO-1CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 10, 0100020, NULL, NULL, 'LQ-NO-2CA', 'LQ-NO-2CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 11, 0110020, NULL, NULL, 'LQ-NO-3CA', 'LQ-NO-3CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 12, 0120020, NULL, NULL, 'LQ-NO-4CA', 'LQ-NO-4CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 13, 0130020, NULL, NULL, 'LQ-NO-5CA', 'LQ-NO-5CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 14, 0140020, NULL, NULL, 'LQ-NO-1MA', 'LQ-NO-1MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 15, 0150020, NULL, NULL, 'LQ-NO-2MA', 'LQ-NO-2MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 16, 0160020, NULL, NULL, 'LQ-NO-3MA', 'LQ-NO-3MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, NULL, 17, 0170020, NULL, NULL, 'LQ-NO-4MA', 'LQ-NO-4MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(20, 2, 1, 9, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 9, NULL, 'LQ-NO-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 10, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 10, NULL, 'LQ-NO-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 11, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 11, NULL, 'LQ-NO-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 12, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 12, NULL, 'LQ-NO-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 13, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 13, NULL, 'LQ-NO-5CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 14, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 14, NULL, 'LQ-NO-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 15, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 15, NULL, 'LQ-NO-2MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 16, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 16, NULL, 'LQ-NO-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(20, 2, 1, 17, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 1003, 1, 17, NULL, 'LQ-NO-4MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(20, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 13, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 14, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 15, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 16, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(20, 17, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);