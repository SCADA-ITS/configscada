UPDATE conf.alarm_configs 
SET enabled = false
WHERE element_type_id = 44 
    AND alarm_config_id in (146,147,148,149,150);