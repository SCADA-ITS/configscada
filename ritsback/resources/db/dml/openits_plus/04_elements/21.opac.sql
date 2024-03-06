INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(21, NULL, 1, 0010021, NULL, NULL, 'LQ-OPC-1CA', 'LQ-OPC-1CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 2, 0020021, NULL, NULL, 'LQ-OPC-2CA', 'LQ-OPC-2CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 3, 0030021, NULL, NULL, 'LQ-OPC-3CA', 'LQ-OPC-3CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 4, 0040021, NULL, NULL, 'LQ-OPC-4CA', 'LQ-OPC-4CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 5, 0050021, NULL, NULL, 'LQ-OPC-5CA', 'LQ-OPC-5CA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 6, 0060021, NULL, NULL, 'LQ-OPC-1MA', 'LQ-OPC-1MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 7, 0070021, NULL, NULL, 'LQ-OPC-2MA', 'LQ-OPC-2MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 8, 0080021, NULL, NULL, 'LQ-OPC-3MA', 'LQ-OPC-3MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, NULL, 9, 0090021, NULL, NULL, 'LQ-OPC-4MA', 'LQ-OPC-4MA', NULL, NULL, 'SendValuesToVentilation.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(21, 1003, 1, 1, NULL, 'LQ-OPC-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 2, NULL, 'LQ-OPC-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 3, NULL, 'LQ-OPC-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 4, NULL, 'LQ-OPC-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 5, NULL, 'LQ-OPC-5CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 6, NULL, 'LQ-OPC-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 7, NULL, 'LQ-OPC-2MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 8, NULL, 'LQ-OPC-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(21, 1003, 1, 9, NULL, 'LQ-OPC-4MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(21, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(21, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);