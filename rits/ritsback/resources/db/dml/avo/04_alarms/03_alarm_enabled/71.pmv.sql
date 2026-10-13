UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 71 AND alarm_config_id = 336; --LBL_ALARM_CONFIGS_ALARM_PMV_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 337; --LBL_ALARM_CONFIGS_ALARM_PMV_DOOR_OPEN
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 338; --LBL_ALARM_CONFIGS_ALARM_PMV_DISPLAY_ERROR
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 340; --LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_0
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 341; --LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_1
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 342; --LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_2
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 343; --LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_3
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 344; --LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_4
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 345; --LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_TEST_BOARD_FAIL_5
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 71 AND alarm_config_id = 71346; --LBL_ALARM_CONFIGS_PMV_PHOTOCELL_ERROR
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 71 AND alarm_config_id = 71348; --LBL_ALARM_CONFIGS_CRITICAL_TEMPERATURE_ERROR
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 71350; --LBL_ALARM_CONFIGS_ALARM_PMV_PIXEL_ERROR
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 71351; --LBL_ALARM_CONFIGS_ALARM_PMV_LUXES_SENSOR_ERROR
UPDATE conf.alarm_configs SET alarm_level_id = 2, enabled = True, treatment = False WHERE element_type_id = 71 AND alarm_config_id = 71353; --LBL_ALARM_CONFIGS_ALARM_PMV_IMAGE_MODIFICATION_STATE

INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(16, 5, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 7, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
