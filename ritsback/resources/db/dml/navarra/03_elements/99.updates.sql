update 
	master.element_types
set 
	enabled = false 
where	
	element_type_id not in
		(select distinct
			element_type_id
		from
			conf.elements);				
			
update 
	master.element_type_params
set 
	enabled = false 
where	
	element_type_id not in	
		(select distinct
			element_type_id
		from
			conf.elements);
			
update 
	master.element_subtypes
set 
	enabled = false 
where	
	element_type_id not in	
		(select distinct
			element_type_id
		from
			conf.elements);	

update master.element_subtypes set enabled = false where element_type_id = 2 and element_subtype_id not in (1,3);
			
update 
	master.element_subtype_active_params
set 
	enabled = false 
where	
	element_type_id not in	
		(select distinct
			element_type_id
		from
			conf.elements);	
			
update 
	master.element_type_states
set 
	enabled = false 
where	
	element_type_id not in	
		(select distinct
			element_type_id
		from
			conf.elements);		
			
update 
	master.element_type_state_transitions
set 
	enabled = false 
where	
	element_type_id not in	
		(select distinct
			element_type_id
		from
			conf.elements);		
			
update 
	master.element_hierarchies_allowed
set 
	enabled = false 
where	
	parent_element_type_id not in	
		(select distinct
			element_type_id
		from
			conf.elements)
or child_element_type_id not in	
		(select distinct
			element_type_id
		from
			conf.elements);		
			
update 
	master.io_controller_module_type_element_types
set 
	enabled = false 
where	
	element_type_id not in 	
		(select distinct
			element_type_id
		from
			conf.elements);		
			
update 
	master.command_element_types
set 
	enabled = false 
where	
	element_type_id not in
		(select distinct
			element_type_id
		from
			conf.elements);	
			
update 
	master.command_element_type_params
set 
	enabled = false 
where	
	element_type_id not in
		(select distinct
			element_type_id
		from
			conf.elements);	
			
update 
	master.element_subtype_active_commands
set 
	enabled = false 
where	
	element_type_id not in
		(select distinct
			element_type_id
		from
			conf.elements);	
			
			
			
			