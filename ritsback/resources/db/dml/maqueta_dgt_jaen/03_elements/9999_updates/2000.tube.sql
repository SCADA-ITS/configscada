--ElementType:2000
UPDATE master.command_element_types
SET enabled=false
WHERE element_type_id=2000 AND command_element_type_id IN(13,14,15,16,17,18);

UPDATE master.element_type_params
SET default_value='0'
WHERE param_type_id= 2 AND element_type_param_id = 3;
