UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 60 AND alarm_config_id = 185; --LBL_ALARM_CONFIGS_ALARM_SIRENA_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 6, enabled = True, treatment = True WHERE element_type_id = 60 AND alarm_config_id = 186; --LBL_ALARM_CONFIGS_ALARM_SIRENA_ACTIVATED
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 60 AND alarm_config_id = 304; --LBL_ALARM_CONFIGS_ALARM_SIRENA_FAIL
