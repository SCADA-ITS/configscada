--ElementType:13
UPDATE master.command_element_types
SET enabled=false
WHERE element_type_id=13 AND command_element_type_id NOT IN(1,3,4,5);

--ElementType:71
UPDATE master.element_subtype_active_commands
SET enabled=false
WHERE element_type_id=71 AND element_subtype_id IN(11,12,18) AND command_element_type_id NOT IN(2,3);