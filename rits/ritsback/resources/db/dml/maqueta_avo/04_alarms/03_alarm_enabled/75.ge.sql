UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 75 AND alarm_config_id = 222; --LBL_ALARM_CONFIGS_ALARM_GE_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 75 AND alarm_config_id = 10075; --LBL_ALARM_CONFIGS_ALARM_CRITICAL_FUEL_LEVEL
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 75 AND alarm_config_id = 20075; --LBL_ALARM_CONFIGS_ALARM_RESTARTABLE_ENGINE
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 75 AND alarm_config_id = 30075; --LBL_ALARM_CONFIGS_ALARM_NO_RESTARTABLE_ENGINE
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 75 AND alarm_config_id = 40075; --LBL_ALARM_CONFIGS_ALARM_EXTERNAL_EMERGENCY_STOP
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 75 AND alarm_config_id = 30010075; --LBL_ALARM_CONFIGS_ALARM_GE_START
