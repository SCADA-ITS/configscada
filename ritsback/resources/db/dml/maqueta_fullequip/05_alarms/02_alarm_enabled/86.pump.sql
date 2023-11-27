UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 86 AND alarm_config_id = 8601; --LBL_ALARM_CONFIGS_ALARM_PUMP_DISCONNECTED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 86 AND alarm_config_id = 8602; --LBL_ALARM_CONFIGS_ALARM_PUMP_NO_OPERATION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 86 AND alarm_config_id = 8603; --LBL_ALARM_CONFIGS_ALARM_PUMP_NO_AUTO_STATE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 86 AND alarm_config_id = 8604; --LBL_ALARM_CONFIGS_ALARM_PUMP_ON
