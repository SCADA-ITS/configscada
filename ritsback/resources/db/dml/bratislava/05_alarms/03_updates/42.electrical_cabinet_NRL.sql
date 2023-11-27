UPDATE conf.alarm_configs 
SET enabled = true
WHERE element_type_id = 42 
    AND alarm_config_id in (136,137,138,139,140);