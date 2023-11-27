-- 
-- Table: conf.elements
--
INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(53, 1, 1, 1001, NULL, NULL, 'RV-SMF-AA-1CA', 'RV-SMF-AA-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 1, 2, 1002, NULL, NULL, 'RV-SMF-AA-1MA', 'RV-SMF-AA-1MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 2, 3, 1001, NULL, NULL, 'RV-SMF-RR-1CA', 'RV-SMF-RR-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(53, 2, 4, 1002, NULL, NULL, 'RV-SMF-RR-1MA', 'RV-SMF-RR-1MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(53, 1003, 1, 1, NULL, 'RV-SMF-AA-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 2, NULL, 'RV-SMF-AA-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 3, NULL, 'RV-SMF-RR-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(53, 1003, 1, 4, NULL, 'RV-SMF-RR-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);