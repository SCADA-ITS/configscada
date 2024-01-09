UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 8 AND alarm_config_id = 010008; --LBL_ALARM_CONFIGS_ALARM_BARRIER_DISCONNECTED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 8 AND alarm_config_id = 070008; --LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN_VEHICLE_DET
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 8 AND alarm_config_id = 080008; --LBL_ALARM_CONFIGS_ALARM_BARRIER_NO_RETURN
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 8 AND alarm_config_id = 090008; --LBL_ALARM_CONFIGS_ALARM_BARRIER_DOWN_NO_RED_SEM
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 8 AND alarm_config_id = 100008; --LBL_ALARM_CONFIGS_ALARM_BARRIER_COMMAND_FAIL
