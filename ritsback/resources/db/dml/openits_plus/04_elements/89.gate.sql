INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(89, NULL, 1, 0010089, NULL, NULL, 'TUN-PTA1-GAL', 'TUN-PTA1-GAL', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 2, 0020089, NULL, NULL, 'TUN-PTA2-GAL', 'TUN-PTA2-GAL', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 3, 0030089, NULL, NULL, 'HOSP-PTA-N', 'HOSP-PTA-N', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 4, 0040089, NULL, NULL, 'HOSP-PTA-S', 'HOSP-PTA-S', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, NULL, 5, 0050089, NULL, NULL, 'HOSP-PTA-O', 'HOSP-PTA-O', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(89, 1003, 1, 1, NULL, 'TUN-PTA1-GAL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 2, NULL, 'TUN-PTA2-GAL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 3, NULL, 'HOSP-PTA-N', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 4, NULL, 'HOSP-PTA-S', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(89, 1003, 1, 5, NULL, 'HOSP-PTA-O', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(89, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(89, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);