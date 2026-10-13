UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 87 AND alarm_config_id = 8701; --LBL_ALARM_CONFIGS_ALARM_TANK_DISCONNECTED
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 87 AND alarm_config_id = 10020087; --LBL_ALARM_CONFIGS_ALARM_CRITICAL_POND_LEVEL
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 87 AND alarm_config_id = 10030087; --LBL_ALARM_CONFIGS_ALARM_MEDIUM_POND_LEVEL
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 87 AND alarm_config_id = 10040087; --LBL_ALARM_CONFIGS_ALARM_LOW_POND_LEVEL
