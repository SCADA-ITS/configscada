UPDATE master.element_type_params SET enabled = false where element_type_id = 1 and param_type_id = 2 and element_type_param_id not in (1,4,10,11,15,16,19,23);

INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(1, NULL, 1, 0010001, NULL, NULL, 'Est_Meteo_1', 'Est_Meteo_1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, NULL, 2, 0020001, NULL, NULL, 'Est_Meteo_2', 'Est_Meteo_2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, NULL, 3, 0030001, NULL, NULL, 'Est_Meteo_3', 'Est_Meteo_3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, NULL, 4, 0040001, NULL, NULL, 'Est_Meteo_4', 'Est_Meteo_4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, NULL, 5, 0050001, NULL, NULL, 'Est_Meteo_7', 'Est_Meteo_7', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 1, 1, 1, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 1, 1, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 1003, 1, 1, NULL, 'Est_Meteo_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(1, 1, 1, 2, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 1, 2, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 1003, 1, 2, NULL, 'Est_Meteo_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(1, 1, 1, 3, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 1, 3, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 1003, 1, 3, NULL, 'Est_Meteo_3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(1, 1, 1, 4, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 1, 4, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 1003, 1, 4, NULL, 'Est_Meteo_4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(1, 1, 1, 5, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 1, 5, NULL, 'Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 1003, 1, 5, NULL, 'Est_Meteo_7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(1, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);