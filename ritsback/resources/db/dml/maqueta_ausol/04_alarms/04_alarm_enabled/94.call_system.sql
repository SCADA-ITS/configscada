
-- 
-- Table: conf.alarm_configs.BUS_DALI
--
UPDATE conf.alarm_configs SET enabled = false WHERE element_type_id = 94 and alarm_config_id NOT IN (9401);