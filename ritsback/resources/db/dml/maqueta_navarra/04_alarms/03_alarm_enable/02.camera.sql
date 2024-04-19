UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 2 AND alarm_config_id = 29; --LBL_ALARM_CONFIGS_ALARM_CAMERA_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 2 AND alarm_config_id = 2010002; --LBL_ALARM_CONFIGS_ALARM_OVERSPEED
UPDATE conf.alarm_configs SET element_subtype_id = 2 WHERE alarm_config_id = 010402;
