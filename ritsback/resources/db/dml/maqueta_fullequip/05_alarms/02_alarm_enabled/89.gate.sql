UPDATE conf.alarm_configs SET alarm_level_id = 1, enabled = True, treatment = False WHERE element_type_id = 89 AND alarm_config_id = 010089; --LBL_ALARM_CONFIGS_GATE_DISCONNECTED_DESC
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = True WHERE element_type_id = 89 AND alarm_config_id = 020089; --LBL_ALARM_CONFIGS_GATE_OPEN_DESC
