--ElementType:75
UPDATE master.element_type_params
SET enabled=false
WHERE element_type_id=75 AND param_type_id = 2;

