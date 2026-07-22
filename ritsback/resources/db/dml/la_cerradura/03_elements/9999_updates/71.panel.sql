--ElementType:71
UPDATE master.element_subtype_active_commands
SET enabled=false
WHERE element_type_id=71 AND element_subtype_id IN(11,12,18,22) AND command_element_type_id NOT IN(2,3);
