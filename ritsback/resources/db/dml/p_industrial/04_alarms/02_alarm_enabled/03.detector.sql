UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True WHERE element_type_id = 3 AND alarm_config_id=010004; --'no connection';
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True WHERE element_type_id = 3 AND alarm_config_id=020004; --'fail_data';
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True WHERE element_type_id = 3 AND alarm_config_id=030004; --'congestion';
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True WHERE element_type_id = 3 AND alarm_config_id=040004; --'fail_piezo';
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True WHERE element_type_id = 3 AND alarm_config_id=050004; --'fail_loop1';
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True WHERE element_type_id = 3 AND alarm_config_id=060004; --'fail_loop2';
