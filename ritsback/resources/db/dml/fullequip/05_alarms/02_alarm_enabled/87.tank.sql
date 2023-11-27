UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 87 AND alarm_config_id = 8701; --LBL_ALARM_CONFIGS_ALARM_TANK_DISCONNECTED
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 87 AND alarm_config_id = 10020087; --LBL_ALARM_CONFIGS_ALARM_CRITICAL_POND_LEVEL
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = False WHERE element_type_id = 87 AND alarm_config_id = 3000087; --LBL_WATER_LEVEL_LESS_90
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 87 AND alarm_config_id = 3010087; --LBL_DIESEL_LEVEL_LESS_60
