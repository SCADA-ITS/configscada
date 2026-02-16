UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 11 AND alarm_config_id = 010011; --LBL_ALARM_CONFIGS_ALARM_SOS_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 020011; --LBL_ALARM_CONFIGS_ALARM_SOS_DOOR_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 030011; --LBL_ALARM_CONFIGS_ALARM_SOS_FA_BOARD
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 040011; --LBL_ALARM_CONFIGS_ALARM_SOS_LEFT_EXTINGUISHER_DOOR_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 11 AND alarm_config_id = 050011; --LBL_ALARM_CONFIGS_ALARM_SOS_RIGHT_EXTINGUISHER_DOOR_OPEN
