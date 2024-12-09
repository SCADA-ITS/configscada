UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = true, treatment = True WHERE element_type_id = 19 AND alarm_config_id = 10019; --LBL_ALARM_CONFIGS_ALARM_CO_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = false, treatment = True WHERE element_type_id = 19 AND alarm_config_id = 20019; --LBL_ALARM_CONFIGS_ALARM_CO_SENSOR
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = false, treatment = True WHERE element_type_id = 19 AND alarm_config_id = 30019; --LBL_ALARM_CONFIGS_ALARM_CO_FA
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = true, treatment = True WHERE element_type_id = 19 AND alarm_config_id = 3010019; --LBL_ALARM_CONFIGS_ALARM_CO_CONCENTRATION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = true, treatment = True WHERE element_type_id = 19 AND alarm_config_id = 3020019; --LBL_ALARM_CONFIGS_ALARM_CO_HIGH_CONCENTRATION
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = true, treatment = True WHERE element_type_id = 19 AND alarm_config_id = 3030019; --LBL_ALARM_CONFIGS_ALARM_CO_EXCESIVE_CONCENTRATION
