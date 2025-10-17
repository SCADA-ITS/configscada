--ElementType:3
UPDATE master.element_type_params 
SET enabled = false
WHERE element_type_id = 3 and param_type_id = 2 and element_type_param_id NOT IN (1,2,3,4,5,6,7,8,9,10,13,17,20,21,40,41,42);

