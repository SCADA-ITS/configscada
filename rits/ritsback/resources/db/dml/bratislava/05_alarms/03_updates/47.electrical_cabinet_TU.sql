UPDATE conf.alarm_configs 
SET enabled = true
WHERE element_type_id = 47
    AND alarm_config_id in (161,162,163,164,165);