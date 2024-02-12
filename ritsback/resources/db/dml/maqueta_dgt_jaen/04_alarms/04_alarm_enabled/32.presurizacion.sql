UPDATE conf.alarm_configs SET alarm_level_id = 6, enabled = true, treatment = True WHERE element_type_id = 32 AND alarm_config_id = 10032; --LBL_ALARM_CONFIGS_ALARM_PRESSURIZATION_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = false, treatment = True WHERE element_type_id = 32 AND alarm_config_id = 20032; --LBL_ALARM_CONFIGS_ALARM_FAN_ON_OFF
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = false, treatment = True WHERE element_type_id = 32 AND alarm_config_id = 30032; --LBL_ALARM_CONFIGS_ALARM_FAN_FAIL_STOP
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = false, treatment = True WHERE element_type_id = 32 AND alarm_config_id = 40032; --LBL_ALARM_CONFIGS_ALARM_FAN_FAIL_START
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = false, treatment = True WHERE element_type_id = 32 AND alarm_config_id = 50032; --LBL_ALARM_CONFIGS_ALARM_FAN_FAIL
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = false, treatment = True WHERE element_type_id = 32 AND alarm_config_id = 60032; --LBL_ALARM_CONFIGS_ALARM_FIRE_PRESSURIZATION_EMERGENCY_BUTTON_ACTIVATED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = false, treatment = True WHERE element_type_id = 32 AND alarm_config_id = 70032; --LBL_ALARM_CONFIGS_ALARM_FIRE_PRESSURIZATION_FAN_1_FAIL
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = false, treatment = True WHERE element_type_id = 32 AND alarm_config_id = 80032; --LBL_ALARM_CONFIGS_ALARM_FIRE_PRESSURIZATION_FAN_2_FAIL
