UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 33 AND alarm_config_id = 10033; --LBL_ALARM_CONFIGS_ALARM_TRAFO_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 33 AND alarm_config_id = 40033; --LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_1
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 33 AND alarm_config_id = 50033; --LBL_ALARM_CONFIGS_ALARM_TEMPERATURE_2
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 33 AND alarm_config_id = 60033; --LBL_ALARM_CONFIGS_TEMP_CONTROL_PROTECTION_FAULT
