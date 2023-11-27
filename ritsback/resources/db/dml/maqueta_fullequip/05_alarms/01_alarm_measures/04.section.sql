-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
--INCIDENT ALARMS
--(Vel =<50) 
(3100004, 2, 5, 4, NULL, NULL, true, false, 'ALARM CONDITIONS CRITICAL HEAVY TRAFFIC', 'ALARM CONDITIONS CRITICAL HEAVY TRAFFIC', 'LBL_ALARM_CONDITIONS_CRITICAL_HEAVY_TRAFFIC', '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_measures
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES

(3100004, 4, 2, 9, 1, '1', '51', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

