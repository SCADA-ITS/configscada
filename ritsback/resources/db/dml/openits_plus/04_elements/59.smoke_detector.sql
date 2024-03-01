INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(59, NULL, 42, 0420059, NULL, NULL, 'LQ-CT-CGBT-HUM-1', 'LQ-CT-CGBT-HUM-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 43, 0430059, NULL, NULL, 'LQ-CT-CGBT-HUM-2', 'LQ-CT-CGBT-HUM-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 44, 0440059, NULL, NULL, 'LQ-CT-ITS-HUM-1', 'LQ-CT-ITS-HUM-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 45, 0450059, NULL, NULL, 'LQ-CT-ITS-HUM-2', 'LQ-CT-ITS-HUM-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 46, 0460059, NULL, NULL, 'LQ-CT-PCI-HUM-1', 'LQ-CT-PCI-HUM-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 47, 0470059, NULL, NULL, 'LQ-CT-PCI-HUM-2', 'LQ-CT-PCI-HUM-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(59, 1003, 1, 42, NULL, 'LQ-CT-CGBT-HUM-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 43, NULL, 'LQ-CT-CGBT-HUM-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 44, NULL, 'LQ-CT-ITS-HUM-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 45, NULL, 'LQ-CT-ITS-HUM-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 46, NULL, 'LQ-CT-PCI-HUM-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 47, NULL, 'LQ-CT-PCI-HUM-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(59, 42, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 43, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 44, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 45, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 46, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 47, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);