/*update 
	master.io_controller_module_types 
set 
	enabled = false 
where	
	module_type_id != 	
		(select distinct
			module_type_id
		from
			conf.io_controller_modules);
		
update	
	master.io_controller_module_type_params 
set 
	enabled = false 
where 
	module_type_id != 	
		(select distinct
			module_type_id
		from
			conf.io_controller_modules);*/
