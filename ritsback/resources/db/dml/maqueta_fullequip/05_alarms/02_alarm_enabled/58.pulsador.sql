UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 58 AND alarm_config_id = 010058; --LBL_ALARM_CONFIGS_ALARM_PULSADOR_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 6, enabled = True, treatment = True WHERE element_type_id = 58 AND alarm_config_id = 020058; --LBL_ALARM_CONFIGS_ALARM_PULSADOR_PUSH_BUTTON_ACTIVATED
UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 58 AND alarm_config_id = 030058; --LBL_ALARM_CONFIGS_ALARM_PULSADOR_FAIL
