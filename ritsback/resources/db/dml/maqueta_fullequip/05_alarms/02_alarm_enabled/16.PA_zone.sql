UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 16 AND alarm_config_id = 50; --LBL_ALARM_CONFIGS_ALARM_ANALIZADOR_PA_ZONE_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 16 AND alarm_config_id = 51; --LBL_ALARM_CONFIGS_ALARM_PA_ZONE
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 16 AND alarm_config_id = 52; --LBL_ALARM_CONFIGS_ALARM_EMERGENCY
