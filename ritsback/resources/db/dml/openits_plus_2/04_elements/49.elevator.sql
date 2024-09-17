INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(49, NULL, 1, 0010049, NULL, NULL, 'HOSP-ELEV-NO', 'HOSP-ELEV-NO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, NULL, 2, 0020049, NULL, NULL, 'HOSP-ELEV-CO', 'HOSP-ELEV-CO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, NULL, 3, 0030049, NULL, NULL, 'HOSP-ELEV-SO', 'HOSP-ELEV-SO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, NULL, 4, 0040049, NULL, NULL, 'HOSP-ELEV-NC', 'HOSP-ELEV-NC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, NULL, 5, 0050049, NULL, NULL, 'HOSP-ELEV-SC', 'HOSP-ELEV-SC', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, NULL, 6, 0060049, NULL, NULL, 'HOSP-ELEV-NE', 'HOSP-ELEV-NE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, NULL, 7, 0070049, NULL, NULL, 'HOSP-ELEV-CE', 'HOSP-ELEV-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(49, 1003, 1, 1, NULL, 'HOSP-ELEV-NO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(49, 1003, 1, 2, NULL, 'HOSP-ELEV-CO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(49, 1003, 1, 3, NULL, 'HOSP-ELEV-SO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(49, 1003, 1, 4, NULL, 'HOSP-ELEV-NC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(49, 1003, 1, 5, NULL, 'HOSP-ELEV-SC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(49, 1003, 1, 6, NULL, 'HOSP-ELEV-NE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(49, 1003, 1, 7, NULL, 'HOSP-ELEV-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(49, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(49, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);