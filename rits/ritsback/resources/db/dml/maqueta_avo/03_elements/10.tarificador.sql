INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(10, NULL, 1, null, NULL, NULL, 'RATE_SERVER', 'RATE_SERVER', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(10, 1, 1, 1, NULL, 'KAPSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(10, 2, 1, 1, NULL, 'RATE SERVER', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(10, 3, 1, 1, NULL, 
'[{"gateCode":"101","element":"71:394"},{"gateCode":"102","element":"71:393"},{"gateCode":"104","element":"71:392"},{"gateCode":"110","element":"71:389"},{"gateCode":"201","element":"71:396"},{"gateCode":"202","element":"71:395"},{"gateCode":"205","element":"71:390"},{"gateCode":"206","element":"71:397"},{"gateCode":"209","element":"71:398"},{"gateCode":"1207","element":"71:391"}]',		
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 1003, 1, 1, NULL, 'RATE_SERVER', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(10, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
