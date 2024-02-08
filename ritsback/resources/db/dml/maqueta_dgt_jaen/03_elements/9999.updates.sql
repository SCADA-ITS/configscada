--ElementType:9
UPDATE master.element_subtype_active_commands
SET enabled=false
WHERE element_type_id=9 AND element_subtype_id=4 AND command_element_type_id NOT IN(1,2,3);

--ElementType:13
UPDATE master.command_element_types
SET enabled=false
WHERE element_type_id=13 AND command_element_type_id NOT IN(1,3,4,5);

--ElementType:14
UPDATE master.element_type_params
SET enabled=false
WHERE element_type_id=14 AND param_type_id = 2 AND element_type_param_id NOT IN(2);

--ElementType:18
UPDATE master.element_type_params 
SET enabled = false 
WHERE element_type_id=18 and param_type_id  = 2 and element_type_param_id = 2;

--ElementType:71
UPDATE master.element_subtype_active_commands
SET enabled=false
WHERE element_type_id=71 AND element_subtype_id IN(11,12,18) AND command_element_type_id NOT IN(2,3);