-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2010052, 2, 3, 52, NULL, 3, true, true, 'CITILOG_COMM_ERROR', 'CITILOG_COMM_ERROR', 'LBL_ALARM_CONFIGS_CITILOG_COMM_ERROR', '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
