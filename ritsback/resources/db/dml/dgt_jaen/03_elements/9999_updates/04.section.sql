--ElementType:4
UPDATE master.element_type_params 
SET enabled = false
WHERE element_type_id = 4 and param_type_id = 1 and element_type_param_id NOT IN (1,2,3,4,5,6,7,60,61,80,81,82);

--ElementType:4
UPDATE master.element_type_params 
SET enabled = false
WHERE element_type_id = 4 and param_type_id = 2 and element_type_param_id NOT IN (1,2,3,4,5,6,7,8,9,10,12,13,18,60,61,80,81,82);

