INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(66, 1, 1, 0010066, NULL, NULL, 'RV-VTA-ANE-1CA', 'RV-VTA-ANE-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1, 2, 0020066, NULL, NULL, 'MM-VTA-ANE-1CA', 'MM-VTA-ANE-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1, 3, 0030066, NULL, NULL, 'CH-VTA-ANE-1CA', 'CH-VTA-ANE-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1, 4, 0040066, NULL, NULL, 'LQ-VTA-ANE-1MA', 'LQ-VTA-ANE-1MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1, 5, 0050066, NULL, NULL, 'COR-VTA-ANE-1CA', 'COR-VTA-ANE-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1, 6, 0060066, NULL, NULL, 'NG-VTA-ANE-1CA', 'NG-VTA-ANE-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1, 7, 0070066, NULL, NULL, 'STM2-VTA-ANE-1CA', 'STM2-VTA-ANE-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1, 8, 0080066, NULL, NULL, 'CAS-VTA-ANE-1CA', 'CAS-VTA-ANE-1CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(66, 2, 1, 1, NULL, '330', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1003, 1, 1, NULL, 'RV-VTA-ANE-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(66, 2, 1, 2, NULL, '-30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1003, 1, 2, NULL, 'MM-VTA-ANE-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(66, 2, 1, 3, NULL, '-30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1003, 1, 3, NULL, 'CH-VTA-ANE-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(66, 2, 1, 4, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1003, 1, 4, NULL, 'LQ-VTA-ANE-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(66, 2, 1, 5, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1003, 1, 5, NULL, 'COR-VTA-ANE-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(66, 2, 1, 6, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1003, 1, 6, NULL, 'NG-VTA-ANE-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(66, 2, 1, 7, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1003, 1, 7, NULL, 'STM2-VTA-ANE-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(66, 2, 1, 8, NULL, '-', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 1003, 1, 8, NULL, 'CAS-VTA-ANE-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(66, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(66, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);