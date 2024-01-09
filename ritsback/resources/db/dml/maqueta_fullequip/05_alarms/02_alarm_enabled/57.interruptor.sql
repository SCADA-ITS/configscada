UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 57 AND alarm_config_id = 010057; --LBL_ALARM_CONFIGS_ALARM_MAGNETOTERMICO_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 57 AND alarm_config_id = 020057; --LBL_ALARM_CONFIGS_ALARM_DIMMER_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 57 AND alarm_config_id = 030057; --LBL_ALARM_CONFIGS_ALARM_DIMMER_START_GE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 57 AND alarm_config_id = 040057; --LBL_ALARM_CONFIGS_ALARM_ELECTRICAL_PROTECTION_TRIGGERED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 57 AND alarm_config_id = 080057; --LBL_ALARM_CONFIGS_ALARM_WITHOUT_MAINS_VOLTAGE
