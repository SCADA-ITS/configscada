UPDATE conf.alarm_configs 
SET enabled = false
WHERE element_type_id = 54
    AND alarm_config_id = 175;