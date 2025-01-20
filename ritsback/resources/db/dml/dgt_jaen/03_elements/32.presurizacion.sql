INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(32, 3, 1, 0010032, NULL, NULL, 'PRES-GA1-S', 'PRES-GA1-S', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 3, 2, 0020032, NULL, NULL, 'PRES-GA1-N', 'PRES-GA1-N', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 3, 3, 0030032, NULL, NULL, 'PRES-GA2-S', 'PRES-GA2-S', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 3, 4, 0040032, NULL, NULL, 'PRES-GA2-N', 'PRES-GA2-N', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 3, 5, 0050032, NULL, NULL, 'PRES-GA3-S', 'PRES-GA3-S', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 3, 6, 0060032, NULL, NULL, 'PRES-GA3-N', 'PRES-GA3-N', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 3, 7, 0070032, NULL, NULL, 'PRES-GA4-S', 'PRES-GA4-S', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 3, 8, 0080032, NULL, NULL, 'PRES-GA4-N', 'PRES-GA4-N', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(32, 1003, 1, 1, NULL, 'PRES-GA1-S', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(32, 1003, 1, 2, NULL, 'PRES-GA1-N', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(32, 1003, 1, 3, NULL, 'PRES-GA2-S', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(32, 1003, 1, 4, NULL, 'PRES-GA2-N', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(32, 1003, 1, 5, NULL, 'PRES-GA3-S', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(32, 1003, 1, 6, NULL, 'PRES-GA3-N', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(32, 1003, 1, 7, NULL, 'PRES-GA4-S', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(32, 1003, 1, 8, NULL, 'PRES-GA4-N', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(32, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(32, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
