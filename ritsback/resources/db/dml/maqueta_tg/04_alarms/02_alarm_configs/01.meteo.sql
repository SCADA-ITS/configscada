-- 
-- Table: conf.alarm_configs.WS
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, enabled, visible, created_at, updated_at) VALUES
(18, 2, 3, 1, NULL, NULL, true, true, 'ALARM WS NO CONNECTION', 'ALARM WS NO CONNECTION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
--(1000001, 1, 2, 43, 5, '65', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1010001, 1, 2, 41, 5, '100', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1020001, 1, 2, 43, 5, '90', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1030001, 1, 2, 40, 5, '110', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1040001, 1, 2, 43, 5, '60', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1050001, 1, 2, 28, 5, '6', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1060001, 1, 2, 30, 5, '1', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1070001, 1, 2, 34, 7, '2', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1080001, 1, 2, 26, 7, '4', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1090001, 1, 2, 38, 7, '1000', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1100001, 1, 2, 38, 7, '120', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);