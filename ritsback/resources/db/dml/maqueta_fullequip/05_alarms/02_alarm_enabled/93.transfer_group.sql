UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 93 AND alarm_config_id = 010093; --LBL_ALARM_CONFIGS_ALARM_TRANSFER_GROUP_DISCONNECTED
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 93 AND alarm_config_id = 020093; --LBL_ALARM_CONFIGS_ALARM_TRANSFER_GROUP_GENERAL_ALARM
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 93 AND alarm_config_id = 030093; --LBL_ALARM_CONFIGS_ALARM_TRANSFER_GROUP_ON
