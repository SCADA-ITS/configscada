UPDATE  master.element_type_params
set data_type_id=1
where element_type_id=1 and param_type_id=2 and element_type_param_id=26;

UPDATE master.element_type_params
set element_type_param_unit_id=42
where element_type_id=1 and param_type_id=2 and element_type_param_id=23;