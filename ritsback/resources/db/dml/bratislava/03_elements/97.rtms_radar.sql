INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(97, NULL, 1, 0010097, NULL, NULL, 'ADPJ12P+5,148', 'ADPJ12P+5,148', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, NULL, 2, 0020097, NULL, NULL, 'ADPJ13L+5,148', 'ADPJ13L+5,148', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, NULL, 3, 0030097, NULL, NULL, 'ADPJ14P+6,438', 'ADPJ14P+6,438', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, NULL, 4, 0040097, NULL, NULL, 'ADPJ15L+6,438', 'ADPJ15L+6,438', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, NULL, 5, 0050097, NULL, NULL, 'ADPJ16P+7,558', 'ADPJ16P+7,558', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(97, 1, 1, 1, NULL, 'http://10.191.9.6/api/v1/', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 1, 1, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 1, 1, NULL, 'R4D7Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 4, 1, 1, NULL, 'RNRJS10P', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 1003, 1, 1, NULL, 'ADPJ12P+5,148', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(97, 1, 1, 2, NULL, 'http://10.191.9.7/api/v1/', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 1, 2, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 1, 2, NULL, 'R4D7Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 4, 1, 2, NULL, 'RNRJS10P', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 1003, 1, 2, NULL, 'ADPJ13L+5,148', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(97, 1, 1, 3, NULL, 'http://10.191.10.3/api/v1/', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 1, 3, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 1, 3, NULL, 'R4D7Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 4, 1, 3, NULL, 'RNRJS11P', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 1003, 1, 3, NULL, 'ADPJ14P+6,438', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(97, 1, 1, 4, NULL, 'http://10.191.10.4/api/v1/', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 1, 4, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 1, 4, NULL, 'R4D7Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 4, 1, 4, NULL, 'RNRJS11P', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 1003, 1, 4, NULL, 'ADPJ15L+6,438', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(97, 1, 1, 5, NULL, 'http://10.191.10.12/api/v1/', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 1, 5, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 1, 5, NULL, 'R4D7Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 4, 1, 5, NULL, 'RNRJS13P', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 1003, 1, 5, NULL, 'ADPJ16P+7,558', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(97, 1, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 4, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 5, 3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);