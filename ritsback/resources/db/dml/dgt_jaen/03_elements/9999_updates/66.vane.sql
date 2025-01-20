--ElementType:66
UPDATE master.element_type_params
SET enabled=false
WHERE element_type_id=66 AND param_type_id = 2 AND element_type_param_id NOT IN(1,2);

