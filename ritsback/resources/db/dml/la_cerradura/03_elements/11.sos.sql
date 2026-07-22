INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(11, 4, 1, 0010011, NULL, NULL, 'PSI-2-01-CE', 'PSI-2-01-CE', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 2, 0020011, NULL, NULL, 'PSI-1-01-CE', 'PSI-1-01-CE', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 3, 0030011, NULL, NULL, 'PSE-2-01-CE', 'PSE-2-01-CE', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 4, 0040011, NULL, NULL, 'PSE-2-02-CE', 'PSE-2-02-CE', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 5, 0050011, NULL, NULL, 'PSE-1-01-CE', 'PSE-1-01-CE', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 6, 0060011, NULL, NULL, 'PSE-1-02-CE', 'PSE-1-02-CE', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(11, 1, 1, 1, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 1, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 1, NULL, 'PSI-2-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 2, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 2, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 2, NULL, 'PSI-1-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 3, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 3, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 3, NULL, 'PSE-2-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 4, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 4, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 4, NULL, 'PSE-2-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 5, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 5, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 5, NULL, 'PSE-1-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(11, 1, 1, 6, NULL, 'Equitel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 6, NULL, 'E301', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 1003, 1, 6, NULL, 'PSE-1-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(11, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(11, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
