UPDATE conf.alarm_configs 
SET enabled = false
WHERE element_type_id = 34 
    AND alarm_config_id in (119,120,121,122,123,124,125,126,127);