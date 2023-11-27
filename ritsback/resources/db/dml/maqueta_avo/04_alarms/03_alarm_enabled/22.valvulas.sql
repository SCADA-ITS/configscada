UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 22 AND alarm_config_id = 57; --LBL_ALARM_CONFIGS_ALARM_VALVULA_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 22 AND alarm_config_id = 10010022; --LBL_ALARM_CONFIGS_ALARM_DISCHARGE_BUTTERFLY_VALVE
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 22 AND alarm_config_id = 10020022; --LBL_ALARM_CONFIGS_ALARM_BUTTERFLY_VALVE_TEST_LINE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 22 AND alarm_config_id = 10030022; --LBL_ALARM_CONFIGS_ALARM_OS&Y_PUMP_VALVE
UPDATE conf.alarm_configs SET enabled = False WHERE element_type_id = 22 AND alarm_config_id = 10040022; --LBL_ALARM_CONFIGS_ALARM_VALVULA_CERRADA
