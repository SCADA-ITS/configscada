INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(86, NULL, 1, 0010086, NULL, NULL, 'TUN-BEL-1', 'TUN-BEL-1', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 2, 0020086, NULL, NULL, 'TUN-BDI-1', 'TUN-BDI-1', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 3, 0030086, NULL, NULL, 'PN-BB-MAR', 'PN-BB-MAR', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 4, 0040086, NULL, NULL, 'PN-BB-CON', 'PN-BB-CON', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 5, 0050086, NULL, NULL, 'PN-BB-RET', 'PN-BB-RET', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 6, 0060086, NULL, NULL, 'PN-BB-REF', 'PN-BB-REF', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 7, 0070086, NULL, NULL, 'PN-BB-VAP', 'PN-BB-VAP', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(86, 1003, 1, 1, NULL, 'TUN-BEL-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 2, NULL, 'TUN-BDI-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 3, NULL, 'PN-BB-MAR', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 4, NULL, 'PN-BB-CON', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 5, NULL, 'PN-BB-RET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 6, NULL, 'PN-BB-REF', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 7, NULL, 'PN-BB-VAP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(86, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);