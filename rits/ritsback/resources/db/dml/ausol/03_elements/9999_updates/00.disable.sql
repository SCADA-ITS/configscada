update conf.alarm_configs set enabled = false
where element_type_id not in
	(select distinct element_type_id from conf.elements e 
	order by element_type_id);
	
update conf.alarm_configs set treatment = false
where alarm_level_id = 2;

update master.element_subtype_active_commands set enabled = false
where element_subtype_id in(11,12) and command_element_type_id in (5,6,7);

update master.command_element_types set enabled = false
where command_element_type_id in(8,9) and element_type_id = 71;
