INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(71, 18, 1, 0010071, NULL, NULL, 'PMV-1', 'PMV-1', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 2, 0020071, NULL, NULL, 'PMV-2', 'PMV-2', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 22, 3, 0030071, NULL, NULL, 'PMV-3', 'PMV-3', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 22, 4, 0040071, NULL, NULL, 'PMV-4', 'PMV-4', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(71, 1, 1, 1, NULL, 'Alba Electrónica', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 1, NULL, 'Modbus', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 1, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 1, NULL, 'PMV-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 2, NULL, 'Alba Electrónica', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 2, NULL, 'Modbus', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 2, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 2, NULL, 'PMV-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 3, NULL, 'Alba Electrónica', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 3, NULL, 'Modbus', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 3, NULL, '[{"zone":1,"type":"graphic","vms_group_id":22},{"zone":2,"type":"text","maxCharsPerLine":12},{"zone":3,"type":"graphic","vms_group_id":22}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 3, NULL, 'PMV-3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 4, NULL, 'Alba Electrónica', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 4, NULL, 'Modbus', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 4, NULL, '[{"zone":1,"type":"graphic","vms_group_id":22},{"zone":2,"type":"text","maxCharsPerLine":12},{"zone":3,"type":"graphic","vms_group_id":22}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 4, NULL, 'PMV-4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(71, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);