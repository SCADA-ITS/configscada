INSERT INTO conf.plans (plan_id, plan_mode_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES 
( 10001, 1, 'CSV - APAGADO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
( 10002, 1, 'CSV ENE - ENCENDIDO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 10003, 1, 'CSV FEB - ENCENDIDO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 10004, 1, 'CSV MAR - ENCENDIDO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 10005, 1, 'CSV ABR - ENCENDIDO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 10006, 1, 'CSV MAY - ENCENDIDO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 10007, 1, 'CSV JUN - ENCENDIDO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.plan_nodes (node_id, alias, description, plan_id, enabled, visible) VALUES 
( 10001, 'CSV', 'CSV', null, true, true),
( 10002, 'CSV - APAGADO', 'CSV - APAGADO', 10001, true, true),
( 10003, 'CSV ENE', 'CSV ENE', null, true, true),
( 10004, 'CSV ENE - ENCENDIDO', 'CSV ENE - ENCENDIDO', 10002, true, true),
( 10005, 'CSV FEB', 'CSV FEB', null, true, true),
( 10006, 'CSV FEB - ENCENDIDO', 'CSV FEB - ENCENDIDO', 10003, true, true),
( 10007, 'CSV MAR', 'CSV MAR', null, true, true),
( 10008, 'CSV MAR - ENCENDIDO', 'CSV MAR - ENCENDIDO', 10004, true, true),
( 10009, 'CSV ABR', 'CSV ABR', null, true, true),
( 10010, 'CSV ABR - ENCENDIDO', 'CSV ABR - ENCENDIDO', 10005, true, true),
( 10011, 'CSV MAY', 'CSV MAY', null, true, true),
( 10012, 'CSV MAY - ENCENDIDO', 'CSV MAY - ENCENDIDO', 10006, true, true),
( 10013, 'CSV JUN', 'CSV JUN', null, true, true),
( 10014, 'CSV JUN - ENCENDIDO', 'CSV JUN - ENCENDIDO', 10007, true, true);

INSERT INTO conf.plan_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES 
( 10001, 10002, true, true),
( 10001, 10003, true, true),
	( 10003, 10004, true, true),
( 10001, 10005, true, true), 
	( 10005, 10006, true, true),
( 10001, 10007, true, true),
	( 10007, 10008, true, true),
( 10001, 10009, true, true),
	( 10009, 10010, true, true),
( 10001, 10011, true, true),
	( 10011, 10012, true, true),
( 10001, 10013, true, true),
	( 10013, 10014, true, true);

INSERT INTO conf.command_elements (command_element_type_id, element_type_id, command_element_id, element_id, enabled, visible, created_at, updated_at) VALUES 
-- APAGADO
( 2, 71, 10001, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10002, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10003, 85, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10004, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10005, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10006, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10007, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10008, 39, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10009, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10010, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10011, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ENE 
( 2, 71, 10012, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10013, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10014, 85, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10015, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10016, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10017, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10018, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10019, 39, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10020, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10021, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10022, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- FEB 
( 2, 71, 10023, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10024, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10025, 85, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10026, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10027, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10028, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10029, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10030, 39, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10031, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10032, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10033, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MAR 
( 2, 71, 10034, 86, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10035, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10036, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10037, 85, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10038, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10039, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10040, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10041, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10042, 39, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10043, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10044, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10045, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ABR 
( 2, 71, 10046, 86, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10047, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10048, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10049, 85, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10050, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10051, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10052, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10053, 39, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10054, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10055, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10056, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MAY 
( 2, 71, 10057, 86, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10058, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10059, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10060, 85, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10061, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10062, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10063, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10064, 39, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10065, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10066, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10067, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- JUN 
( 2, 71, 10068, 86, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10069, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10070, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10071, 85, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10072, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10073, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10074, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10075, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10076, 39, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10077, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10078, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10079, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.command_element_values (command_element_type_id, element_type_id, command_element_type_param_id, command_element_id, value, enabled, visible, created_at, updated_at) VALUES 
-- ENE 
( 2, 71, 1, 10012, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10013, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10014, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10015, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10016, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10017, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10018, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10019, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10020, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10021, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10022, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- FEB 
( 2, 71, 1, 10023, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"NO USES EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL AL","color":"#FFFFFF"},{"id":3,"value":"VOLANTE","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10024, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"USAR EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL SUPONE","color":"#FFFFFF"},{"id":3,"value":"4 PUNTOS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10025, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"NO USES EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL AL","color":"#FFFFFF"},{"id":3,"value":"VOLANTE","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10026, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"USAR EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL SUPONE","color":"#FFFFFF"},{"id":3,"value":"4 PUNTOS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10027, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"NO USES EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL AL","color":"#FFFFFF"},{"id":3,"value":"VOLANTE","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10028, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"USAR EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL SUPONE","color":"#FFFFFF"},{"id":3,"value":"4 PUNTOS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10029, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"NO USES EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL AL","color":"#FFFFFF"},{"id":3,"value":"VOLANTE","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10030, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"USAR EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL SUPONE","color":"#FFFFFF"},{"id":3,"value":"4 PUNTOS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10031, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"NO USES EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL AL","color":"#FFFFFF"},{"id":3,"value":"VOLANTE","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10032, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"USAR EL","color":"#FFFFFF"},{"id":2,"value":"MOVIL SUPONE","color":"#FFFFFF"},{"id":3,"value":"4 PUNTOS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10033, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"","color":"#FFFFFF"},{"id":2,"value":" 0","color":"#FFFFFF"},{"id":3,"value":"","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            
-- MAR 
( 2, 71, 1, 10034, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" NEUMATICOS","color":"#FFFFFF"},{"id":2,"value":"PERFECTOS TU","color":"#FFFFFF"},{"id":3,"value":"MEJOR SEGURO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10035, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"REVISA TUS","color":"#FFFFFF"},{"id":2,"value":"NEUMATICOS","color":"#FFFFFF"},{"id":3,"value":"CADA 15 DIAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10036, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" NEUMATICOS","color":"#FFFFFF"},{"id":2,"value":"PERFECTOS TU","color":"#FFFFFF"},{"id":3,"value":"MEJOR SEGURO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10037, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"REVISA TUS","color":"#FFFFFF"},{"id":2,"value":"NEUMATICOS","color":"#FFFFFF"},{"id":3,"value":"CADA 15 DIAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10038, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" NEUMATICOS","color":"#FFFFFF"},{"id":2,"value":"PERFECTOS TU","color":"#FFFFFF"},{"id":3,"value":"MEJOR SEGURO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10039, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"REVISA TUS","color":"#FFFFFF"},{"id":2,"value":"NEUMATICOS","color":"#FFFFFF"},{"id":3,"value":"CADA 15 DIAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10040, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" NEUMATICOS","color":"#FFFFFF"},{"id":2,"value":"PERFECTOS TU","color":"#FFFFFF"},{"id":3,"value":"MEJOR SEGURO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10041, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" NEUMATICOS","color":"#FFFFFF"},{"id":2,"value":"PERFECTOS TU","color":"#FFFFFF"},{"id":3,"value":"MEJOR SEGURO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10042, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"REVISA TUS","color":"#FFFFFF"},{"id":2,"value":"NEUMATICOS","color":"#FFFFFF"},{"id":3,"value":"CADA 15 DIAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10043, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" NEUMATICOS","color":"#FFFFFF"},{"id":2,"value":"PERFECTOS TU","color":"#FFFFFF"},{"id":3,"value":"MEJOR SEGURO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10044, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"REVISA TUS","color":"#FFFFFF"},{"id":2,"value":"NEUMATICOS","color":"#FFFFFF"},{"id":3,"value":"CADA 15 DIAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10045, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" NEUMATICOS","color":"#FFFFFF"},{"id":2,"value":"PERFECTOS TU","color":"#FFFFFF"},{"id":3,"value":"MEJOR SEGURO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ABR 
( 2, 71, 1, 10046, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" MANTEN LA","color":"#FFFFFF"},{"id":2,"value":" DISTANCIA","color":"#FFFFFF"},{"id":3,"value":"DE SEGURIDAD","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10047, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"SEGURIDAD","color":"#FFFFFF"},{"id":2,"value":" ES MANTENER","color":"#FFFFFF"},{"id":3,"value":"LA DISTANCIA","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10048, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" MANTEN LA","color":"#FFFFFF"},{"id":2,"value":" DISTANCIA","color":"#FFFFFF"},{"id":3,"value":"DE SEGURIDAD","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10049, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"SEGURIDAD","color":"#FFFFFF"},{"id":2,"value":" ES MANTENER","color":"#FFFFFF"},{"id":3,"value":"LA DISTANCIA","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10050, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" MANTEN LA","color":"#FFFFFF"},{"id":2,"value":" DISTANCIA","color":"#FFFFFF"},{"id":3,"value":"DE SEGURIDAD","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10051, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"SEGURIDAD","color":"#FFFFFF"},{"id":2,"value":" ES MANTENER","color":"#FFFFFF"},{"id":3,"value":"LA DISTANCIA","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10052, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" MANTEN LA","color":"#FFFFFF"},{"id":2,"value":" DISTANCIA","color":"#FFFFFF"},{"id":3,"value":"DE SEGURIDAD","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10053, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"SEGURIDAD","color":"#FFFFFF"},{"id":2,"value":" ES MANTENER","color":"#FFFFFF"},{"id":3,"value":"LA DISTANCIA","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10054, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" MANTEN LA","color":"#FFFFFF"},{"id":2,"value":" DISTANCIA","color":"#FFFFFF"},{"id":3,"value":"DE SEGURIDAD","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10055, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"SEGURIDAD","color":"#FFFFFF"},{"id":2,"value":" ES MANTENER","color":"#FFFFFF"},{"id":3,"value":"LA DISTANCIA","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10056, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" MANTEN LA","color":"#FFFFFF"},{"id":2,"value":" DISTANCIA","color":"#FFFFFF"},{"id":3,"value":"DE SEGURIDAD","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MAY 
( 2, 71, 1, 10057, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" ANTES DEL","color":"#FFFFFF"},{"id":2,"value":"VIAJE REVISA","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10058, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  EVITA","color":"#FFFFFF"},{"id":2,"value":"RIESGOS HAZ","color":"#FFFFFF"},{"id":3,"value":"LA REVISION","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10059, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" ANTES DEL","color":"#FFFFFF"},{"id":2,"value":"VIAJE REVISA","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10060, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  EVITA","color":"#FFFFFF"},{"id":2,"value":"RIESGOS HAZ","color":"#FFFFFF"},{"id":3,"value":"LA REVISION","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10061, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" ANTES DEL","color":"#FFFFFF"},{"id":2,"value":"VIAJE REVISA","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10062, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  EVITA","color":"#FFFFFF"},{"id":2,"value":"RIESGOS HAZ","color":"#FFFFFF"},{"id":3,"value":"LA REVISION","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10063, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" ANTES DEL","color":"#FFFFFF"},{"id":2,"value":"VIAJE REVISA","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10064, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  EVITA","color":"#FFFFFF"},{"id":2,"value":"RIESGOS HAZ","color":"#FFFFFF"},{"id":3,"value":"LA REVISION","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10065, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" ANTES DEL","color":"#FFFFFF"},{"id":2,"value":"VIAJE REVISA","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10066, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  EVITA","color":"#FFFFFF"},{"id":2,"value":"RIESGOS HAZ","color":"#FFFFFF"},{"id":3,"value":"LA REVISION","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10067, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":3,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":" ANTES DEL","color":"#FFFFFF"},{"id":2,"value":"VIAJE REVISA","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- JUN 
( 2, 71, 1, 10068, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  SI  BEBES","color":"#FFFFFF"},{"id":2,"value":"    NO","color":"#FFFFFF"},{"id":3,"value":" CONDUZCAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10069, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"CERO ALCOHOL","color":"#FFFFFF"},{"id":2,"value":" AL VOLANTE","color":"#FFFFFF"},{"id":3,"value":"","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10070, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  SI  BEBES","color":"#FFFFFF"},{"id":2,"value":"    NO","color":"#FFFFFF"},{"id":3,"value":" CONDUZCAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10071, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"CERO ALCOHOL","color":"#FFFFFF"},{"id":2,"value":" AL VOLANTE","color":"#FFFFFF"},{"id":3,"value":"","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10072, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  SI  BEBES","color":"#FFFFFF"},{"id":2,"value":"    NO","color":"#FFFFFF"},{"id":3,"value":" CONDUZCAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10073, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"CERO ALCOHOL","color":"#FFFFFF"},{"id":2,"value":" AL VOLANTE","color":"#FFFFFF"},{"id":3,"value":"","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10074, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  SI  BEBES","color":"#FFFFFF"},{"id":2,"value":"    NO","color":"#FFFFFF"},{"id":3,"value":" CONDUZCAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10075, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  SI  BEBES","color":"#FFFFFF"},{"id":2,"value":"    NO","color":"#FFFFFF"},{"id":3,"value":" CONDUZCAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10076, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"CERO ALCOHOL","color":"#FFFFFF"},{"id":2,"value":" AL VOLANTE","color":"#FFFFFF"},{"id":3,"value":"","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10077, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  SI  BEBES","color":"#FFFFFF"},{"id":2,"value":"    NO","color":"#FFFFFF"},{"id":3,"value":" CONDUZCAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10078, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"CERO ALCOHOL","color":"#FFFFFF"},{"id":2,"value":" AL VOLANTE","color":"#FFFFFF"},{"id":3,"value":"","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10079, '[{"zone":1,"graphics":[{"id":1,"value":"3000"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"  SI  BEBES","color":"#FFFFFF"},{"id":2,"value":"    NO","color":"#FFFFFF"},{"id":3,"value":" CONDUZCAS","color":"#FFFFFF"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_orders(plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at) VALUES 
--APAGADO
( 10001, 1, 1, 1, null, null, 'Apagar CAS-PMV-14MA', 'Apagar CAS-PMV-14MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 2, 1, 2, null, null, 'Apagar STM2-PMV-16MA', 'Apagar STM2-PMV-16MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 3, 1, 3, null, null, 'Apagar EST-PMV-26MA', 'Apagar EST-PMV-26MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 4, 1, 4, null, null, 'Apagar EST-PMV-22MA', 'Apagar EST-PMV-22MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 5, 1, 5, null, null, 'Apagar LQ-PMV-3MA', 'Apagar LQ-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 6, 1, 6, null, null, 'Apagar RV-PMV-5MA', 'Apagar RV-PMV-5MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 7, 1, 7, null, null, 'Apagar CAS-PMV-15CA', 'Apagar CAS-PMV-15CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 8, 1, 8, null, null, 'Apagar COR-PMV-17CA', 'Apagar COR-PMV-17CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 9, 1, 9, null, null, 'Apagar EST-PMV-19CA', 'Apagar EST-PMV-19CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 10, 1, 10, null, null, 'Apagar MM-PMV-2CA', 'Apagar MM-PMV-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 11, 1, 11, null, null, 'Apagar RV-PMV-6CA', 'Apagar RV-PMV-6CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,

-- ENE 
( 10002, 1, 1, 1, null, null, 'Enviar Mensaje CAS-PMV-14MA', 'Enviar Mensaje CAS-PMV-14MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 2, 1, 2, null, null, 'Enviar Mensaje STM2-PMV-16MA', 'Enviar Mensaje STM2-PMV-16MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 3, 1, 3, null, null, 'Enviar Mensaje EST-PMV-26MA', 'Enviar Mensaje EST-PMV-26MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 4, 1, 4, null, null, 'Enviar Mensaje EST-PMV-22MA', 'Enviar Mensaje EST-PMV-22MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 5, 1, 5, null, null, 'Enviar Mensaje LQ-PMV-3MA', 'Enviar Mensaje LQ-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 6, 1, 6, null, null, 'Enviar Mensaje RV-PMV-5MA', 'Enviar Mensaje RV-PMV-5MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 7, 1, 7, null, null, 'Enviar Mensaje CAS-PMV-15CA', 'Enviar Mensaje CAS-PMV-15CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 8, 1, 8, null, null, 'Enviar Mensaje COR-PMV-17CA', 'Enviar Mensaje COR-PMV-17CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 9, 1, 9, null, null, 'Enviar Mensaje EST-PMV-19CA', 'Enviar Mensaje EST-PMV-19CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 10, 1, 10, null, null, 'Enviar Mensaje MM-PMV-2CA', 'Enviar Mensaje MM-PMV-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 11, 1, 11, null, null, 'Enviar Mensaje RV-PMV-6CA', 'Enviar Mensaje RV-PMV-6CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,

-- FEB 
( 10003, 1, 1, 1, null, null, 'Enviar Mensaje CAS-PMV-14MA', 'Enviar Mensaje CAS-PMV-14MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 2, 1, 2, null, null, 'Enviar Mensaje STM2-PMV-16MA', 'Enviar Mensaje STM2-PMV-16MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 3, 1, 3, null, null, 'Enviar Mensaje EST-PMV-26MA', 'Enviar Mensaje EST-PMV-26MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 4, 1, 4, null, null, 'Enviar Mensaje EST-PMV-22MA', 'Enviar Mensaje EST-PMV-22MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 5, 1, 5, null, null, 'Enviar Mensaje LQ-PMV-3MA', 'Enviar Mensaje LQ-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 6, 1, 6, null, null, 'Enviar Mensaje RV-PMV-5MA', 'Enviar Mensaje RV-PMV-5MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 7, 1, 7, null, null, 'Enviar Mensaje CAS-PMV-15CA', 'Enviar Mensaje CAS-PMV-15CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 8, 1, 8, null, null, 'Enviar Mensaje COR-PMV-17CA', 'Enviar Mensaje COR-PMV-17CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 9, 1, 9, null, null, 'Enviar Mensaje EST-PMV-19CA', 'Enviar Mensaje EST-PMV-19CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 10, 1, 10, null, null, 'Enviar Mensaje MM-PMV-2CA', 'Enviar Mensaje MM-PMV-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10003, 11, 1, 11, null, null, 'Enviar Mensaje RV-PMV-6CA', 'Enviar Mensaje RV-PMV-6CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,

-- MAR 
( 10004, 1, 1, 1, null, null, 'Enviar Mensaje GDR-PMV-9MA', 'Enviar Mensaje GDR-PMV-9MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 2, 1, 2, null, null, 'Enviar Mensaje CAS-PMV-14MA', 'Enviar Mensaje CAS-PMV-14MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 3, 1, 3, null, null, 'Enviar Mensaje STM2-PMV-16MA', 'Enviar Mensaje STM2-PMV-16MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 4, 1, 4, null, null, 'Enviar Mensaje EST-PMV-26MA', 'Enviar Mensaje EST-PMV-26MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 5, 1, 5, null, null, 'Enviar Mensaje EST-PMV-22MA', 'Enviar Mensaje EST-PMV-22MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 6, 1, 6, null, null, 'Enviar Mensaje LQ-PMV-3MA', 'Enviar Mensaje LQ-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 7, 1, 7, null, null, 'Enviar Mensaje RV-PMV-5MA', 'Enviar Mensaje RV-PMV-5MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 8, 1, 8, null, null, 'Enviar Mensaje CAS-PMV-15CA', 'Enviar Mensaje CAS-PMV-15CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 9, 1, 9, null, null, 'Enviar Mensaje COR-PMV-17CA', 'Enviar Mensaje COR-PMV-17CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 10, 1, 10, null, null, 'Enviar Mensaje EST-PMV-19CA', 'Enviar Mensaje EST-PMV-19CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 11, 1, 11, null, null, 'Enviar Mensaje MM-PMV-2CA', 'Enviar Mensaje MM-PMV-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10004, 12, 1, 12, null, null, 'Enviar Mensaje RV-PMV-6CA', 'Enviar Mensaje RV-PMV-6CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,

-- ABR 
( 10005, 1, 1, 1, null, null, 'Enviar Mensaje GDR-PMV-9MA', 'Enviar Mensaje GDR-PMV-9MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 2, 1, 2, null, null, 'Enviar Mensaje CAS-PMV-14MA', 'Enviar Mensaje CAS-PMV-14MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 3, 1, 3, null, null, 'Enviar Mensaje STM2-PMV-16MA', 'Enviar Mensaje STM2-PMV-16MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 4, 1, 4, null, null, 'Enviar Mensaje EST-PMV-26MA', 'Enviar Mensaje EST-PMV-26MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 5, 1, 5, null, null, 'Enviar Mensaje EST-PMV-22MA', 'Enviar Mensaje EST-PMV-22MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 6, 1, 6, null, null, 'Enviar Mensaje LQ-PMV-3MA', 'Enviar Mensaje LQ-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 7, 1, 7, null, null, 'Enviar Mensaje CAS-PMV-15CA', 'Enviar Mensaje CAS-PMV-15CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 8, 1, 8, null, null, 'Enviar Mensaje COR-PMV-17CA', 'Enviar Mensaje COR-PMV-17CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 9, 1, 9, null, null, 'Enviar Mensaje EST-PMV-19CA', 'Enviar Mensaje EST-PMV-19CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 10, 1, 10, null, null, 'Enviar Mensaje MM-PMV-2CA', 'Enviar Mensaje MM-PMV-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10005, 11, 1, 11, null, null, 'Enviar Mensaje RV-PMV-6CA', 'Enviar Mensaje RV-PMV-6CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,

-- MAY 
( 10006, 1, 1, 1, null, null, 'Enviar Mensaje GDR-PMV-9MA', 'Enviar Mensaje GDR-PMV-9MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 2, 1, 2, null, null, 'Enviar Mensaje CAS-PMV-14MA', 'Enviar Mensaje CAS-PMV-14MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 3, 1, 3, null, null, 'Enviar Mensaje STM2-PMV-16MA', 'Enviar Mensaje STM2-PMV-16MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 4, 1, 4, null, null, 'Enviar Mensaje EST-PMV-26MA', 'Enviar Mensaje EST-PMV-26MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 5, 1, 5, null, null, 'Enviar Mensaje EST-PMV-22MA', 'Enviar Mensaje EST-PMV-22MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 6, 1, 6, null, null, 'Enviar Mensaje LQ-PMV-3MA', 'Enviar Mensaje LQ-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 7, 1, 7, null, null, 'Enviar Mensaje CAS-PMV-15CA', 'Enviar Mensaje CAS-PMV-15CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 8, 1, 8, null, null, 'Enviar Mensaje COR-PMV-17CA', 'Enviar Mensaje COR-PMV-17CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 9, 1, 9, null, null, 'Enviar Mensaje EST-PMV-19CA', 'Enviar Mensaje EST-PMV-19CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 10, 1, 10, null, null, 'Enviar Mensaje MM-PMV-2CA', 'Enviar Mensaje MM-PMV-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10006, 11, 1, 11, null, null, 'Enviar Mensaje RV-PMV-6CA', 'Enviar Mensaje RV-PMV-6CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,

-- JUN 
( 10007, 1, 1, 1, null, null, 'Enviar Mensaje GDR-PMV-9MA', 'Enviar Mensaje GDR-PMV-9MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 2, 1, 2, null, null, 'Enviar Mensaje CAS-PMV-14MA', 'Enviar Mensaje CAS-PMV-14MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 3, 1, 3, null, null, 'Enviar Mensaje STM2-PMV-16MA', 'Enviar Mensaje STM2-PMV-16MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 4, 1, 4, null, null, 'Enviar Mensaje EST-PMV-26MA', 'Enviar Mensaje EST-PMV-26MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 5, 1, 5, null, null, 'Enviar Mensaje EST-PMV-22MA', 'Enviar Mensaje EST-PMV-22MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 6, 1, 6, null, null, 'Enviar Mensaje LQ-PMV-3MA', 'Enviar Mensaje LQ-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 7, 1, 7, null, null, 'Enviar Mensaje RV-PMV-5MA', 'Enviar Mensaje RV-PMV-5MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 8, 1, 8, null, null, 'Enviar Mensaje CAS-PMV-15CA', 'Enviar Mensaje CAS-PMV-15CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 9, 1, 9, null, null, 'Enviar Mensaje COR-PMV-17CA', 'Enviar Mensaje COR-PMV-17CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 10, 1, 10, null, null, 'Enviar Mensaje EST-PMV-19CA', 'Enviar Mensaje EST-PMV-19CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 11, 1, 11, null, null, 'Enviar Mensaje MM-PMV-2CA', 'Enviar Mensaje MM-PMV-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10007, 12, 1, 12, null, null, 'Enviar Mensaje RV-PMV-6CA', 'Enviar Mensaje RV-PMV-6CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ;

INSERT INTO conf.plan_order_command_elements(command_element_type_id, element_type_id, command_element_id, plan_id, plan_order_id, enabled, visible, created_at, updated_at) VALUES 
-- APAGADO
( 2, 71, 10001, 10001, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10002, 10001, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10003, 10001, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10004, 10001, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10005, 10001, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10006, 10001, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10007, 10001, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10008, 10001, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10009, 10001, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10010, 10001, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10011, 10001, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ENE 
( 2, 71, 10012, 10002, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10013, 10002, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10014, 10002, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10015, 10002, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10016, 10002, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10017, 10002, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10018, 10002, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10019, 10002, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10020, 10002, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10021, 10002, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10022, 10002, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- FEB 
( 2, 71, 10023, 10003, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10024, 10003, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10025, 10003, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10026, 10003, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10027, 10003, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10028, 10003, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10029, 10003, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10030, 10003, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10031, 10003, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10032, 10003, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10033, 10003, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MAR 
( 2, 71, 10034, 10004, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10035, 10004, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10036, 10004, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10037, 10004, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10038, 10004, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10039, 10004, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10040, 10004, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10041, 10004, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10042, 10004, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10043, 10004, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10044, 10004, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10045, 10004, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ABR 
( 2, 71, 10046, 10005, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10047, 10005, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10048, 10005, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10049, 10005, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10050, 10005, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10051, 10005, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10052, 10005, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10053, 10005, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10054, 10005, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10055, 10005, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10056, 10005, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MAY 
( 2, 71, 10057, 10006, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10058, 10006, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10059, 10006, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10060, 10006, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10061, 10006, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10062, 10006, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10063, 10006, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10064, 10006, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10065, 10006, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10066, 10006, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10067, 10006, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MAR 
( 2, 71, 10068, 10007, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10069, 10007, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10070, 10007, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10071, 10007, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10072, 10007, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10073, 10007, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10074, 10007, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10075, 10007, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10076, 10007, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10077, 10007, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10078, 10007, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10079, 10007, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.plan_schedules (plan_schedule_id, plan_id, cron_expression, alias, description, enabled, visible, created_at, updated_at) VALUES
-- APAGADO
(1, 10001, '0 0 14,20 ? * MON,TUE,WED,THU *', 'CSV - APAGADO', 'L, M, X Y J', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 10001, '0 0 16,22 ? * FRI,SAT,SUN *', 'CSV - APAGADO', 'V, S Y D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ENE 
(3, 10002, '0 0 12,18 ? JAN MON,TUE,WED,THU *', 'CSV ENE - ENCENDIDO', 'L, M, X Y J', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 10002, '0 0 12,18 ? JAN FRI,SAT,SUN *', 'CSV ENE - ENCENDIDO ', 'V, S Y D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- FEB 
(5, 10003, '0 0 12,18 ? FEB MON,TUE,WED,THU *', 'CSV FEB - ENCENDIDO', 'L, M, X Y J', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 10003, '0 0 12,18 ? FEB FRI,SAT,SUN *', 'CSV FEB - ENCENDIDO ', 'V, S Y D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MAR 
(7, 10004, '0 0 12,18 ? MAR MON,TUE,WED,THU *', 'CSV MAR - ENCENDIDO', 'L, M, X Y J', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 10004, '0 0 12,18 ? MAR FRI,SAT,SUN *', 'CSV MAR - ENCENDIDO ', 'V, S Y D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ABR 
(9, 10005, '0 0 12,18 ? APR MON,TUE,WED,THU *', 'CSV ABR - ENCENDIDO', 'L, M, X Y J', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 10005, '0 0 12,18 ? APR FRI,SAT,SUN *', 'CSV ABR - ENCENDIDO ', 'V, S Y D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- MAY 
(11, 10006, '0 0 12,18 ? APR MON,TUE,WED,THU *', 'CSV MAY - ENCENDIDO', 'L, M, X Y J', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 10006, '0 0 12,18 ? APR FRI,SAT,SUN *', 'CSV MAY - ENCENDIDO ', 'V, S Y D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- JUN 
(13, 10007, '0 0 12,18 ? JUN MON,TUE,WED,THU *', 'CSV JUN - ENCENDIDO', 'L, M, X Y J', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 10007, '0 0 12,18 ? JUN FRI,SAT,SUN *', 'CSV JUN - ENCENDIDO ', 'V, S Y D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

