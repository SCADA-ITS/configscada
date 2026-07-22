UPDATE master.element_type_params
SET element_type_param_unit_id=38
WHERE element_type_param_id=1 AND element_type_id=21 AND param_type_id=2;

UPDATE master.command_element_types
SET enabled = true
WHERE element_type_id = 21 and command_element_type_id = 1;

UPDATE master.command_element_types
SET visible = true
WHERE element_type_id = 21 and command_element_type_id = 1;

UPDATE master.command_element_types
SET enabled = true
WHERE element_type_id = 21 and command_element_type_id = 2;

UPDATE master.command_element_types
SET visible = true
WHERE element_type_id = 21 and command_element_type_id = 2;