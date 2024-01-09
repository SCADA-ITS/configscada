UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 22 AND alarm_config_id = 010022; --LBL_ALARM_CONFIGS_ALARM_VALVULA_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 22 AND alarm_config_id = 030022; --LBL_ALARM_CONFIGS_ALARM_VALVULA_CERRADA
