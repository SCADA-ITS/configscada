INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(59, NULL, 1, 0010059, NULL, NULL, 'LQ-CT-CGBT-HUM-1', 'LQ-CT-CGBT-HUM-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 2, 0020059, NULL, NULL, 'LQ-CT-CGBT-HUM-2', 'LQ-CT-CGBT-HUM-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 3, 0030059, NULL, NULL, 'LQ-CT-ITS-HUM-1', 'LQ-CT-ITS-HUM-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 4, 0040059, NULL, NULL, 'LQ-CT-ITS-HUM-2', 'LQ-CT-ITS-HUM-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 5, 0050059, NULL, NULL, 'LQ-CT-PCI-HUM-1', 'LQ-CT-PCI-HUM-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, NULL, 6, 0060059, NULL, NULL, 'LQ-CT-PCI-HUM-2', 'LQ-CT-PCI-HUM-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(59, 1003, 1, 1, NULL, 'LQ-CT-CGBT-HUM-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 2, NULL, 'LQ-CT-CGBT-HUM-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 3, NULL, 'LQ-CT-ITS-HUM-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 4, NULL, 'LQ-CT-ITS-HUM-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 5, NULL, 'LQ-CT-PCI-HUM-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(59, 1003, 1, 6, NULL, 'LQ-CT-PCI-HUM-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(59, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(59, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);