UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 59 AND alarm_config_id = 183; --LBL_ALARM_CONFIGS_ALARM_SMOKE_DETECTOR_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 6, enabled = True, treatment = True WHERE element_type_id = 59 AND alarm_config_id = 184; --LBL_ALARM_CONFIGS_ALARM_SMOKE_DETECTOR_SMOKE_ALARM
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 59 AND alarm_config_id = 303; --LBL_ALARM_CONFIGS_ALARM_SMOKE_DETECTOR_FAIL
