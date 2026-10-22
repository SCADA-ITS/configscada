UPDATE conf.alarm_configs 
SET enabled = true
WHERE element_type_id = 43 
    AND alarm_config_id in (141,142,143,144,145);