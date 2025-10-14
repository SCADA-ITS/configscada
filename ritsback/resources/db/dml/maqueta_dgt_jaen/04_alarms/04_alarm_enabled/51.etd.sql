UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 51 AND alarm_config_id = 10051; --LBL_ALARM_CONFIGS_ALARM_ETD_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 51 AND alarm_config_id = 20051; --LBL_ALARM_CONFIGS_ALARM_ETD_BATTERY_LOW
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 51 AND alarm_config_id = 30051; --LBL_ALARM_CONFIGS_ALARM_ETD_POWER_FAIL
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 51 AND alarm_config_id = 40051; --LBL_ALARM_CONFIGS_ALARM_ETD_DOOR_OPEN
