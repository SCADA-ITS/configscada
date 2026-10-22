UPDATE conf.alarm_configs
SET enabled = true
WHERE element_type_id = 46
    AND alarm_config_id in (156,157,158,159,160);