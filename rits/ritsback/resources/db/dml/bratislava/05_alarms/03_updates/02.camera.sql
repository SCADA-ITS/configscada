UPDATE conf.alarm_configs 
SET enabled = true
WHERE element_type_id = 2 
    AND alarm_config_id in (33,29,30,31,32,2220002);