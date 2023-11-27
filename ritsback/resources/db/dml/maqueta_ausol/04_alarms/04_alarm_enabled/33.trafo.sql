UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 33 AND alarm_config_id = 10033; --LBL_ALARM_CONFIGS_ALARM_TRAFO_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 33 AND alarm_config_id = 20033; --LBL_ALARM_CONFIGS_ALARM_FAIL_TRAFO_MT_T154
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 33 AND alarm_config_id = 30033; --LBL_ALARM_CONFIGS_ALARM_TRAFO_EXCESS_WINDING_TEMPERATURE
