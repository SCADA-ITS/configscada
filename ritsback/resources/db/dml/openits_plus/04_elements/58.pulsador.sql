INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(58, NULL, 1, 0010058, NULL, NULL, 'TUN-CT-CGBT-PULS-1', 'TUN-CT-CGBT-PULS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(58, NULL, 2, 0020058, NULL, NULL, 'TUN-CT-ITS-PULS-1', 'TUN-CT-ITS-PULS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(58, NULL, 3, 0030058, NULL, NULL, 'TUN-CT-PCI-PULS-1', 'TUN-CT-PCI-PULS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(58, 1003, 1, 1, NULL, 'TUN-CT-CGBT-PULS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(58, 1003, 1, 2, NULL, 'TUN-CT-ITS-PULS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(58, 1003, 1, 3, NULL, 'TUN-CT-PCI-PULS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(58, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(58, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(58, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);