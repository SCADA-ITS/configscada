-----
--
-- INSERTS FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.alarm_types
--
INSERT INTO master.alarm_types (alarm_type_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,'ALARM','ELEMENT ALARM','LBL_ALARM_TYPE_ALARM',NULL,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,'STATE_ALERT','STATE ALERT','LBL_ALARM_TYPE_STATE_ALERT',NULL,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (3,'MEASURE_ALERT','MEASURE ALERT','LBL_ALARM_TYPE_MEASURE_ALERT',NULL,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (4,'NOTIFICATION','NOTIFICATION','LBL_ALARM_TYPE_NOTIFICATION',NULL,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- 
-- Table: master.alarm_levels
--
INSERT INTO master.alarm_levels
(alarm_level_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'LEVEL_MAINTENANCE', 'ALARM LEVEL MAINTENANCE', 'LBL_ALARM_LEVEL_MAINTENANCE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'LEVEL_0', 'ALARM LEVEL 0', 'LBL_ALARM_LEVEL_0', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'LEVEL_1', 'ALARM LEVEL 1', 'LBL_ALARM_LEVEL_1', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'LEVEL_2', 'ALARM LEVEL 2', 'LBL_ALARM_LEVEL_2', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'LEVEL_3', 'ALARM LEVEL 3', 'LBL_ALARM_LEVEL_3', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'LEVEL_4', 'ALARM LEVEL 4', 'LBL_ALARM_LEVEL_4', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);