--ElementType:14
UPDATE master.element_type_params
SET enabled=false
WHERE element_type_id=14 AND param_type_id = 2 AND element_type_param_id NOT IN(2);
