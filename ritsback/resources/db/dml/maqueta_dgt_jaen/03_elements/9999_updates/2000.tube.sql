--ElementType:2000
UPDATE master.command_element_types
SET enabled=false
WHERE element_type_id=2000 AND command_element_type_id IN(13,14,15,16,17,18);
