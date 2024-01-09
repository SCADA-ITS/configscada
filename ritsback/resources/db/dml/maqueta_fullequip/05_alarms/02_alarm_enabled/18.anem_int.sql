UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 18 AND alarm_config_id = 010018; --LBL_ALARM_CONFIGS_ALARM_ANEM_INT_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 18 AND alarm_config_id = 020018; --LBL_ALARM_CONFIGS_ALARM_ANEM_SENSOR
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 18 AND alarm_config_id = 030018; --LBL_ALARM_CONFIGS_ALARM_ANEM_FA
