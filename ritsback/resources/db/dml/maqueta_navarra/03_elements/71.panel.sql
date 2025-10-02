INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(71, 22, 1, 0010071, NULL, NULL, 'PMV-VIRTUAL-1', 'PMV-VIRTUAL-1', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 22, 2, 0020071, NULL, NULL, 'PMV-VIRTUAL-2', 'PMV-VIRTUAL-2', NULL, NULL, 'ChangeMeasureHistorical.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(71, 1, 1, 1, NULL, 'Virtual', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 1, NULL, 'Virtual', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 1, NULL, '[{"zone":1,"type":"graphic","vms_group_id":22},{"zone":2,"type":"text","maxCharsPerLine":16},{"zone":3,"type":"graphic","vms_group_id":22}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 9, 1, 1, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71, 1, 1, 2, NULL, 'Virtual', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 2, NULL, 'Virtual', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 2, NULL, '[{"zone":1,"type":"graphic","vms_group_id":22},{"zone":2,"type":"text","maxCharsPerLine":16},{"zone":3,"type":"graphic","vms_group_id":22}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 9, 1, 2, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

update master.command_element_types
set on_change = 'VirtualPMVSignalling'
where element_type_id = 71 and command_element_type_id = 2;

update master.command_element_types
set on_change = 'VirtualPMVSwitchOff'
where element_type_id = 71 and command_element_type_id = 3;

update master.element_subtype_active_commands
set enabled = false
where element_type_id = 71 and element_subtype_id = 22 and command_element_type_id in (8,9);

update master.element_types
set check_if_connected = null 
where element_type_id = 71;

update master.element_types
set initial_element_type_state_id = 1
where element_type_id = 71;

delete from master.element_type_state_transitions 
where element_type_id = 71;

delete from conf.element_state_alarms
where element_type_id = 71;

delete from conf.alarm_configs 
where element_type_id = 71;

delete from master.element_type_states
where element_type_id = 71 and element_type_state_id != 1;

