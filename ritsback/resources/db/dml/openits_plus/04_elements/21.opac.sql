INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(21, NULL, 9, 0090021, NULL, NULL, 'LQ-OPC-1CA', 'LQ-OPC-1CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 10, 0100021, NULL, NULL, 'LQ-OPC-2CA', 'LQ-OPC-2CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 11, 0110021, NULL, NULL, 'LQ-OPC-3CA', 'LQ-OPC-3CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 12, 0120021, NULL, NULL, 'LQ-OPC-4CA', 'LQ-OPC-4CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 13, 0130021, NULL, NULL, 'LQ-OPC-5CA', 'LQ-OPC-5CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 14, 0140021, NULL, NULL, 'LQ-OPC-1MA', 'LQ-OPC-1MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 15, 0150021, NULL, NULL, 'LQ-OPC-2MA', 'LQ-OPC-2MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 16, 0160021, NULL, NULL, 'LQ-OPC-3MA', 'LQ-OPC-3MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 17, 0170021, NULL, NULL, 'LQ-OPC-4MA', 'LQ-OPC-4MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(21, 1003, 1, 9, NULL, 'LQ-OPC-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 10, NULL, 'LQ-OPC-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 11, NULL, 'LQ-OPC-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 12, NULL, 'LQ-OPC-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 13, NULL, 'LQ-OPC-5CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 14, NULL, 'LQ-OPC-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 15, NULL, 'LQ-OPC-2MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 16, NULL, 'LQ-OPC-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 17, NULL, 'LQ-OPC-4MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(21, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 13, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 14, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 15, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 16, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 17, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);