DELETE FROM conf.alarm_target_elements;
DELETE FROM conf.alarm_config_measures;

update conf.alarm_configs set enabled = false