--Comunes
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 30009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_DISCONNECTED

--Jetfans

UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 40009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_FAIL
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 50009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_DROP
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 80009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_NO_START
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_NO_STOP
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 100009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_DE_OUT_RANGE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 110009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_DE_BROKEN_STRAND
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 120009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_DE_ALARM
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 130009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_DE_DETENTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 140009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_NDE_OUT_RANGE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 150009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_NDE_BROKEN_STRAND
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 160009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_NDE_ALARM
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 170009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_NDE_DETENTION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 180009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_VIBRATION_OUT_RANGE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 190009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_VIBRATION_BROKEN_STRAND
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 200009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_VIBRATION_ALARM
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 210009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_VIBRATION_DETENTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 260009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_LOCAL_REMOTE
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 270009; --LBL_ALARM_CONFIGS_ALARM_JETFAN_AVAILABLE
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 10310009; --LBL_ALARM_CONFIGS_ALARM_PTC_WINDINGS_TEMPERATURE


--Axiales

UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90010; --LBL_ALARM_CONFIGS_ALARM_PRESENT_TRIP
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90011; --LBL_ALARM_CONFIGS_ALARM_PUSH_BUTTON
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90012; --LBL_ALARM_CONFIGS_ALARM_VARIATOR_FAIL
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90013; --LBL_ALARM_CONFIGS_ALARM_RUNNING_TIMEOUT_EXCEEDED
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90014; --LBL_ALARM_CONFIGS_ALARM_PT100_FRONT_BEARING_TEMP
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90015; --LBL_ALARM_CONFIGS_ALARM_PT100_REAR_BEARING_TEMP
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90016; --LBL_ALARM_CONFIGS_ALARM_PTC_WINDINGS_TEMPERATURE
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90017; --LBL_ALARM_CONFIGS_ALARM_ENGINE_VIBRATIONS
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90018; --LBL_ALARM_CONFIGS_ALARM_WARNING_ENGINE_VIBRATIONS
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90019; --LBL_ALARM_CONFIGS_ALARM_ENGINE_VIBRATIONS_SIGNAL_FAIL
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90020; --LBL_ALARM_CONFIGS_ALARM_PTC_WINDINGS_TEMPERATURE_TRIP
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90022; --LBL_ALARM_CONFIGS_ALARM_ACT_1_OPENING_TIME_EXCEEDED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90023; --LBL_ALARM_CONFIGS_ALARM_ACT_1_CLOSING_TIME_EXCEEDED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90024; --LBL_ALARM_CONFIGS_ALARM_ACT_1_ELECTRIC_PROTECTION
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90025; --LBL_ALARM_CONFIGS_ALARM_ACT_2_OPENING_TIME_EXCEEDED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90026; --LBL_ALARM_CONFIGS_ALARM_ACT_2_CLOSING_TIME_EXCEEDED
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90027; --LBL_ALARM_CONFIGS_ALARM_ACT_2_ELECTRIC_PROTECTION
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90030; --LBL_ALARM_CONFIGS_ALARM_WARNING_PLC_STATE
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90031; --LBL_ALARM_CONFIGS_ALARM_DAMPER_PRESENT_TRIP
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90033; --LBL_ALARM_CONFIGS_ALARM_GENERAL_WARNING_PRESENT
UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90034; --LBL_ALARM_CONFIGS_ALARM_AXIALFAN_RELAY_PHASES
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True, treatment = True WHERE element_type_id = 9 AND alarm_config_id = 90036; --LBL_ALARM_CONFIGS_ALARM_WARNING_OVERVOLTAGE
