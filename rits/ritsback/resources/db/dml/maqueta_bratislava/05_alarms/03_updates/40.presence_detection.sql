UPDATE conf.alarm_configs 
SET enabled = true
WHERE element_type_id = 40 
    AND alarm_config_id in (10040,20040);