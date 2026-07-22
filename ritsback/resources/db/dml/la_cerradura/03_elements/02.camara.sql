INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(2, 1, 1, 0010002, NULL, NULL, 'TVE-2-01-CE', 'TVE-2-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 2, 0020002, NULL, NULL, 'TVE-1-01-CE', 'TVE-1-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 3, 0030002, NULL, NULL, 'TVI-2-01-CE', 'TVI-2-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 4, 0040002, NULL, NULL, 'TVI-2-02-CE', 'TVI-2-02-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 5, 0050002, NULL, NULL, 'TVI-1-01-CE', 'TVI-1-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 6, 0060002, NULL, NULL, 'TVI-1-02-CE', 'TVI-1-02-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 7, 0070002, NULL, NULL, 'TVI-3-01-CE', 'TVI-3-01-CE', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(2, 1, 1, 1, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 1, NULL, 'VG4-AutoDome', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 1, NULL, 'http://192.168.88.201/TVE-1-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 7, 1, 1, NULL, 'PTZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 9, 1, 1, NULL, '172.21.3.119', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 1, NULL, 'TVE-2-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 2, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 2, NULL, 'VG4-AutoDome', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 2, NULL, 'http://192.168.88.201/TVE-2-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 7, 1, 2, NULL, 'PTZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 9, 1, 2, NULL, '172.21.3.134', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 2, NULL, 'TVE-1-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 3, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 3, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 3, NULL, 'http://192.168.88.201/TVI-1-01-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 9, 1, 3, NULL, '172.21.3.122', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 3, NULL, 'TVI-2-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 4, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 4, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 4, NULL, 'http://192.168.88.201/TVI-1-02-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 9, 1, 4, NULL, '172.21.3.122', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 4, NULL, 'TVI-2-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 5, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 5, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 5, NULL, 'http://192.168.88.201/TVI-1-03-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 9, 1, 5, NULL, '172.21.3.123', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 5, NULL, 'TVI-1-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 6, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 6, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 6, NULL, 'http://192.168.88.201/TVI-1-04-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 9, 1, 6, NULL, '172.21.3.123', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 6, NULL, 'TVI-1-02-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 7, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 7, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 7, NULL, 'http://192.168.88.201/TVI-1-05-DP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 9, 1, 7, NULL, '172.21.3.124', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 7, NULL, 'TVI-3-01-CE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(2, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);