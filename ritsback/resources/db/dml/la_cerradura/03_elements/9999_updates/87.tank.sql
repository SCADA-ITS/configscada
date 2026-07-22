--ElementType:87
UPDATE master.element_type_params
SET enabled=false
WHERE element_type_id=87 AND param_type_id = 2 and element_type_param_id NOT IN (4);

