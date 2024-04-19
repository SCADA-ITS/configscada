INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(2, 4, 6, 0060002, NULL, NULL, 'RAD-001', 'RAD-001', 'null', NULL, 'ChangeMeasureCamera.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 4, 7, 0070002, NULL, NULL, 'RAD-002', 'RAD-002', 'null', NULL, 'ChangeMeasureCamera.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 4, 8, 0080002, NULL, NULL, 'RAD-003', 'RAD-003', 'null', NULL, 'ChangeMeasureCamera.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 4, 9, 0090002, NULL, NULL, 'RAD-004', 'RAD-004', 'null', NULL, 'ChangeMeasureCamera.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 4, 10, 0100002, NULL, NULL, 'RAD-005', 'RAD-005', 'null', NULL, 'ChangeMeasureCamera.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 4, 11, 0110002, NULL, NULL, 'RAD-006', 'RAD-006', 'null', NULL, 'ChangeMeasureCamera.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(2, 1, 1, 6, NULL, 'RAD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 6, NULL, 'Local', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 6, NULL, 'RAD-001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 7, NULL, 'RAD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 7, NULL, 'Local', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 7, NULL, 'RAD-002', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 8, NULL, 'RAD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 8, NULL, 'Local', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 8, NULL, 'RAD-003', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 9, NULL, 'RAD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 9, NULL, 'Local', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 9, NULL, 'RAD-004', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 10, NULL, 'RAD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 10, NULL, 'Local', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 10, NULL, 'RAD-005', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 11, NULL, 'RAD', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 11, NULL, 'Local', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 11, NULL, 'RAD-006', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(2, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);