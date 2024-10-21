-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(3000002, 3, 3, 2, NULL, 3, true, false, 'ALARM CAMERA ROAD', 'ALARM CAMERA ROAD', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_ROAD', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3010002, 3, 3, 2, NULL, 3, true, false, 'ALARM CAMERA LOW', 'ALARM CAMERA LOW', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_LOW', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3020002, 3, 3, 2, NULL, 3, true, false, 'ALARM CAMERA SPORT', 'ALARM CAMERA SPORT', 'LBL_ALARM_CONFIGS_ALARM_CAMERA_SPORT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
