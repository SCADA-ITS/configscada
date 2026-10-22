UPDATE conf.alarm_configs 
SET enabled = true
WHERE element_type_id = 41 
    AND alarm_config_id in (131,132,133,134,135);