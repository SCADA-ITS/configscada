-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(30010035, 2, 3, 35, NULL, 3, true, true, 'ALARM HIGH LEVEL', 'ALARM HIGH LEVEL', 'LBL_ALARM_CONFIGS_ALARM_HIGH_LEVEL', 'LBL_ALARM_CONFIGS_ALARM_HIGH_LEVEL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30020035, 2, 3, 35, NULL, 3, true, true, 'ALARM LOW LEVEL', 'ALARM LOW LEVEL', 'LBL_ALARM_CONFIGS_ALARM_LOW_LEVEL', 'LBL_ALARM_CONFIGS_ALARM_LOW_LEVEL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.alarm_measures.tube
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(30010035, 35, 2, 1, 2, '4', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30020035, 35, 2, 1, 2, '3', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);