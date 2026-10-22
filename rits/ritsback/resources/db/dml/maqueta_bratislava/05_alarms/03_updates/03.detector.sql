UPDATE conf.alarm_configs SET treatment = true WHERE alarm_config_id IN (34,35) and element_type_id = 3;

UPDATE conf.alarm_configs SET enabled = true WHERE alarm_config_id IN (34,35) and element_type_id = 3;