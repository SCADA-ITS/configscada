-- 
-- Table: conf.alarm_configs.OPAC
--
UPDATE conf.alarm_configs SET enabled = false WHERE element_type_id = 23 and alarm_config_id NOT IN (58,59);