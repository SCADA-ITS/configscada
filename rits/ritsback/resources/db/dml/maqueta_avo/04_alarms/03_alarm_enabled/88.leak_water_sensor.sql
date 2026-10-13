UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 88 AND alarm_config_id = 8801; --LBL_ALARM_CONFIGS_LEAK_WATER_SENSOR_DISCONNECTED
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 88 AND alarm_config_id = 8802; --LBL_ALARM_CONFIGS_ALARM_LEAK_WATER_DETECTED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 88 AND alarm_config_id = 10010088; --LBL_ALARM_CONFIGS_ALARM_WATER_FLOW_SENSOR_PUMP_1
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 88 AND alarm_config_id = 10020088; --LBL_ALARM_CONFIGS_ALARM_WATER_FLOW_SENSOR_PUMP_2
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 88 AND alarm_config_id = 10030088; --LBL_ALARM_CONFIGS_ALARM_ROOM_LINE_PRESSURE_SENSOR
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 88 AND alarm_config_id = 10040088; --LBL_ALARM_CONFIGS_ALARM_PUMP_ROOM_FLOOD_SENSOR
