DELETE FROM master.element_subtype_active_commands WHERE element_type_id = 53;

UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 53 AND alarm_config_id = 174; --LBL_ALARM_CONFIGS_ALARM_SEM_NO_CONNECTION
