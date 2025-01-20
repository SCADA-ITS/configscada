--ElementType:8
UPDATE master.command_element_types
SET enabled=false
WHERE element_type_id=8 AND command_element_type_id NOT IN(1,2);
