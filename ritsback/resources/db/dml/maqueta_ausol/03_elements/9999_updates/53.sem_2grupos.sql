UPDATE master.element_subtype_active_commands
SET enabled=false, visible=true, created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP
WHERE element_subtype_id=1 AND command_element_type_id=2 AND element_type_id=53;

UPDATE master.element_subtype_active_commands
SET enabled=false, visible=true, created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP
WHERE element_subtype_id=1 AND command_element_type_id=5 AND element_type_id=53;