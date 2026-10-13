UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 25 AND alarm_config_id = 69; --LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_DOOR_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 25 AND alarm_config_id = 74; --LBL_ALARM_CONFIGS_ALARM_PLC_CABINET_NO_CONNECTION
