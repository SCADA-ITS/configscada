UPDATE conf.alarm_configs 
SET enabled = false
WHERE element_type_id = 89
    AND alarm_config_id in (8901,8902);