INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(55, 2, 1, 0010055, NULL, NULL, 'LQ-CGBT-1', 'LQ-CGBT-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 2, 2, 0020055, NULL, NULL, 'LQ-CITS-1', 'LQ-CITS-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 2, 3, 0030055, NULL, NULL, 'LQ-PCI-1', 'LQ-PCI-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 2, 4, 0040055, NULL, NULL, 'LQ-CBT', 'LQ-CBT', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(55, 1003, 1, 1, NULL, 'LQ-CGBT-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 2, NULL, 'LQ-CITS-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 3, NULL, 'LQ-PCI-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(55, 1003, 1, 4, NULL, 'LQ-CBT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(55, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(55, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);