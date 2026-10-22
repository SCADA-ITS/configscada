UPDATE master.command_element_types
SET enabled = false
WHERE element_type_id = 2
 AND command_element_type_id in (1,2,3,4,5,6,7,8,9,10);