INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(52, NULL, 1, 0010052, NULL, NULL, 'RV-DAI', 'RV-DAI', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, NULL, 2, 0020052, NULL, NULL, 'MM-DAI', 'MM-DAI', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, NULL, 3, 0030052, NULL, NULL, 'STMI-DAI-1CA', 'STMI-DAI-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, NULL, 4, 0040052, NULL, NULL, 'CAS-DAI-1CA', 'CAS-DAI-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, NULL, 5, 0050052, NULL, NULL, 'CH-DAI', 'CH-DAI', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, NULL, 6, 0060052, NULL, NULL, 'COR-DAI', 'COR-DAI', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, NULL, 7, 0070052, NULL, NULL, 'EST-DAI', 'EST-DAI', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, NULL, 8, 0080052, NULL, NULL, 'LQ-DAI', 'LQ-DAI', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, NULL, 9, 0090052, NULL, NULL, 'NG-DAI-1', 'NG-DAI-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, NULL, 10, 0100052, NULL, NULL, 'STM2-DAI-1', 'STM2-DAI-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(52, 1003, 1, 1, NULL, 'RV-DAI', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(52, 1003, 1, 2, NULL, 'MM-DAI', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(52, 1003, 1, 3, NULL, 'STMI-DAI-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(52, 1003, 1, 4, NULL, 'CAS-DAI-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(52, 1003, 1, 5, NULL, 'CH-DAI', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(52, 1003, 1, 6, NULL, 'COR-DAI', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(52, 1003, 1, 7, NULL, 'EST-DAI', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(52, 1003, 1, 8, NULL, 'LQ-DAI', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(52, 1003, 1, 9, NULL, 'NG-DAI-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(52, 1003, 1, 10, NULL, 'STM2-DAI-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(52, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(52, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
