UPDATE master.element_subtype_active_commands
SET enabled=false
WHERE element_type_id=13 and element_subtype_id=2 and command_element_type_id=2; 