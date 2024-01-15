update master.element_types set maintenance_element_type_state_id = 4 
where element_type_id not in (2000,2001,2002,2003)