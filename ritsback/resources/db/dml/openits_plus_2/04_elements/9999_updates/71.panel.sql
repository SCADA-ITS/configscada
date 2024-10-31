--CLV
UPDATE master.element_subtype_active_commands
SET enabled=false
WHERE element_subtype_id=11 AND command_element_type_id in(5,6,7) AND element_type_id=71;

--AF
UPDATE master.element_subtype_active_commands
SET enabled=false
WHERE element_subtype_id=12 AND command_element_type_id in(5,6,7) AND element_type_id=71;

--PMV GR-TXT
UPDATE master.element_subtype_active_commands
SET enabled=false
WHERE element_subtype_id=19 AND command_element_type_id in(5,7) AND element_type_id=71;