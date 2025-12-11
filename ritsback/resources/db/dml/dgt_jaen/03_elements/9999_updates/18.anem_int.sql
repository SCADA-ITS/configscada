--ElementType:18
UPDATE master.element_type_params 
SET enabled = false 
WHERE element_type_id=18 and param_type_id  = 2 and element_type_param_id = 2;

UPDATE master.command_element_types
SET enabled = true
WHERE element_type_id = 18 and command_element_type_id = 1;

UPDATE master.command_element_types
SET visible = true
WHERE element_type_id = 18 and command_element_type_id = 1;

UPDATE master.command_element_types
SET enabled = true
WHERE element_type_id = 18 and command_element_type_id = 2;

UPDATE master.command_element_types
SET visible = true
WHERE element_type_id = 18 and command_element_type_id = 2;
