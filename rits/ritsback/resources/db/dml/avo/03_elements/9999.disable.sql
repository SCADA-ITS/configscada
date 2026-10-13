update conf.alarm_configs set enabled = false
where element_type_id not in
	(select distinct element_type_id from conf.elements e 
	order by element_type_id);
	
update master.plan_order_types set enabled = false
where plan_order_type_id in (4,7,8,9,10);

update master.command_element_types set enabled = false
where command_element_type_id = 1 and element_type_id = 14;

update master.command_element_types set enabled = false
where command_element_type_id in (1,2,3,4,5) and element_type_id = 53;

update master.command_element_types set enabled = false
where command_element_type_id in (8,9) and element_type_id = 2000;

update master.command_element_types set enabled = false
where command_element_type_id in (1,2,3,4) and element_type_id = 2001;
