UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True WHERE element_type_id = 51 AND label_alias = 'LBL_ALARM_CONFIGS_ALARM_ETD_NO_CONNECTION';
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = False WHERE element_type_id = 51 AND label_alias = 'LBL_ALARM_CONFIGS_ALARM_ETD_BATTERY_LOW';
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = False WHERE element_type_id = 51 AND label_alias = 'LBL_ALARM_CONFIGS_ALARM_ETD_POWER_FAIL';
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = False WHERE element_type_id = 51 AND label_alias = 'LBL_ALARM_CONFIGS_ALARM_ETD_DOOR_OPEN';
