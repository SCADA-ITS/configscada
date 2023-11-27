UPDATE conf.alarm_configs 
SET enabled = true
WHERE element_type_id = 45 
    AND alarm_config_id in (151,152,153,154,155);