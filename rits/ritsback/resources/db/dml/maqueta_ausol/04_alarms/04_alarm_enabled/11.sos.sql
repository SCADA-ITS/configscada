UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 11 AND alarm_config_id = 41; --LBL_ALARM_CONFIGS_ALARM_SOS_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 42; --LBL_ALARM_CONFIGS_ALARM_SOS_DOOR_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 43; --LBL_ALARM_CONFIGS_ALARM_SOS_FA_BOARD
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 44; --LBL_ALARM_CONFIGS_ALARM_SOS_LEFT_EXTINGUISHER_DOOR_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 45; --LBL_ALARM_CONFIGS_ALARM_SOS_RIGHT_EXTINGUISHER_DOOR_OPEN
