UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 41; --LBL_ALARM_CONFIGS_ALARM_SOS_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 42; --LBL_ALARM_CONFIGS_ALARM_SOS_DOOR_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 306; --LBL_ALARM_CONFIGS_ALARM_SOS_EXTINGUISHER_DOOR_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 307; --LBL_ALARM_CONFIGS_ALARM_SOS_EXTINGUISHER_EXTRACTION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 11001; --LBL_ALARM_CONFIGS_ALARM_SOS_SIP
