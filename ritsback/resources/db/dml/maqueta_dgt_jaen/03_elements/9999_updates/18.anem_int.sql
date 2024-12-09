--ElementType:18
UPDATE master.element_type_params 
SET enabled = false 
WHERE element_type_id=18 and param_type_id  = 2 and element_type_param_id = 2;

