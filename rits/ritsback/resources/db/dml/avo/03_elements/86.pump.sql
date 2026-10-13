INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(86, NULL, 1, 0010086, NULL, NULL, '1ES2-BCI-001-BBA1', '1ES2-BCI-001-BBA1',NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 2, 0020086, NULL, NULL, '1ES2-BCI-001-BBA2', '1ES2-BCI-001-BBA2',NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 3, 0030086, NULL, NULL, 'CT04-BCI-001-BBA1', 'CT04-BCI-001-BBA1',NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 4, 0040086, NULL, NULL, 'CT04-BCI-001-BBA2', 'CT04-BCI-001-BBA2',NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 5, 0050086, NULL, NULL, 'CT06-BCI-001-BBA1', 'CT06-BCI-001-BBA1',NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 6, 0060086, NULL, NULL, 'CT06-BCI-001-BBA2', 'CT06-BCI-001-BBA2',NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 7, 0070086, NULL, NULL, 'CT07-BCI-001-BBA1', 'CT07-BCI-001-BBA1',NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, NULL, 8, 0080086, NULL, NULL, 'CT07-BCI-001-BBA2', 'CT07-BCI-001-BBA2',NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(86, 1003, 1, 1, NULL, '1ES2-BCI-001-BBA1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 2, NULL, '1ES2-BCI-001-BBA2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 3, NULL, 'CT04-BCI-001-BBA1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 4, NULL, 'CT04-BCI-001-BBA2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 5, NULL, 'CT06-BCI-001-BBA1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 6, NULL, 'CT06-BCI-001-BBA2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 7, NULL, 'CT07-BCI-001-BBA1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(86, 1003, 1, 8, NULL, 'CT07-BCI-001-BBA2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(86, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(86, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE conf.alarm_configs SET enabled = false WHERE alarm_config_id IN (8602,8603,8604);
