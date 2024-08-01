UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 10011; --LBL_ALARM_CONFIGS_ALARM_SOS_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 20011; --LBL_ALARM_CONFIGS_ALARM_SOS_DOOR_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 80011; --LBL_ALARM_CONFIGS_ALARM_SOS_SIP
