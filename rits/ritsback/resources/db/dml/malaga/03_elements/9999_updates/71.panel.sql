UPDATE master.element_subtype_active_commands SET enabled=false, visible=false WHERE element_subtype_id=12 AND command_element_type_id in (5,6,7) AND element_type_id=71;
UPDATE conf.alarm_configs SET alarm_level_id=3 WHERE alarm_config_id=336;
