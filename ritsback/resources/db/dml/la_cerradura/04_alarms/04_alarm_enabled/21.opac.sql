UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = true, treatment = True WHERE element_type_id = 21 AND alarm_config_id = 10021; --LBL_ALARM_CONFIGS_ALARM_OPAC_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = false, treatment = True WHERE element_type_id = 21 AND alarm_config_id = 20021; --LBL_ALARM_CONFIGS_ALARM_OPAC_SENSOR
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = false, treatment = True WHERE element_type_id = 21 AND alarm_config_id = 30021; --LBL_ALARM_CONFIGS_ALARM_OPAC_FA
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = true, treatment = True WHERE element_type_id = 21 AND alarm_config_id = 3010021; --LBL_ALARM_CONFIGS_ALARM_OPAC_VISIBILITY
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = true, treatment = True WHERE element_type_id = 21 AND alarm_config_id = 3020021; --LBL_ALARM_CONFIGS_ALARM_OPAC_REDUCED_VISIBILITY
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = true, treatment = True WHERE element_type_id = 21 AND alarm_config_id = 3030021; --LBL_ALARM_CONFIGS_ALARM_OPAC_EXCESSIVE_OPACITY
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = true, treatment = True WHERE element_type_id = 21 AND alarm_config_id = 40021; --LBL_ALARM_CONFIGS_ALARM_OPAC_INHIBIR
