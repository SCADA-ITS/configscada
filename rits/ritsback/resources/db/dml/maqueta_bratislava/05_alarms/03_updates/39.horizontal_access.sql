UPDATE conf.alarm_configs 
SET enabled = true
WHERE element_type_id = 39 
    AND alarm_config_id in (10039,20039);