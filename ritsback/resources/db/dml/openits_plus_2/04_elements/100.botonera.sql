INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(100, NULL, 1, 0010100, NULL, NULL, 'PN-BOTONERA', 'PN-BOTONERA', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(100, 1, 1, 1, NULL, 'PN-BOTONERA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(100, 2, 1, 1, NULL, 'PN-BOTONERA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(100, 1003, 1, 1, NULL, 'PN-BOTONERA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(100, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);