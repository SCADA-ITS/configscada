INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(8, NULL, 1, 0010008, NULL, NULL, 'BA-1-01-CA-D', 'BA-1-01-CA-D', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, NULL, 2, 0020008, NULL, NULL, 'BA-1-01-CA-I', 'BA-1-01-CA-I', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, NULL, 3, 0030008, NULL, NULL, 'BA-2-01-CZ-D', 'BA-2-01-CZ-D', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, NULL, 4, 0040008, NULL, NULL, 'BA-2-01-CZ-I', 'BA-2-01-CZ-I', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, NULL, 5, 0050008, NULL, NULL, 'BA-1-01-DP-D', 'BA-1-01-DP-D', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, NULL, 6, 0060008, NULL, NULL, 'BA-1-01-DP-I', 'BA-1-01-DP-I', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, NULL, 7, 0070008, NULL, NULL, 'BA-2-01-DP-D', 'BA-2-01-DP-D', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, NULL, 8, 0080008, NULL, NULL, 'BA-2-01-DP-I', 'BA-2-01-DP-I', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(8, 1003, 1, 1, NULL, 'BA-1-01-CA-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 2, NULL, 'BA-1-01-CA-I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 3, NULL, 'BA-2-01-CZ-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 4, NULL, 'BA-2-01-CZ-I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 5, NULL, 'BA-1-01-DP-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 6, NULL, 'BA-1-01-DP-I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 7, NULL, 'BA-2-01-DP-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(8, 1003, 1, 8, NULL, 'BA-2-01-DP-I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(8, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(8, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);