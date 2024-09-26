INSERT INTO conf.plans (plan_id, plan_mode_type_id, management_area_id, alias, description, enabled, visible, created_at, updated_at) VALUES 
( 10001, 1, 1, 'CSV - APAGADO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
( 10002, 1, 1, 'CSV ENE - ENCENDIDO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_nodes (node_id, management_area_id, alias, description, plan_id, enabled, visible) VALUES 
( 10001, 1, 'CSV', 'CSV', null, true, true),
( 10002, 1, 'CSV - APAGADO', 'CSV - APAGADO', 10001, true, true),
( 10003, 1, 'CSV ENE', 'CSV ENE', null, true, true),
( 10004, 1, 'CSV ENE - ENCENDIDO', 'CSV ENE - ENCENDIDO', 10002, true, true);

INSERT INTO conf.plan_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES 
( 10001, 10002, true, true),
( 10001, 10003, true, true),
	( 10003, 10004, true, true);

INSERT INTO conf.command_elements (command_element_type_id, element_type_id, command_element_id, element_id, enabled, visible, created_at, updated_at) VALUES 
-- APAGADO
( 2, 71, 10001, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10002, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10003, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10004, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10005, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10006, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10007, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10008, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10009, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10010, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10011, 82, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10012, 83, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ENE 
( 2, 71, 10013, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10014, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10015, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10016, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10017, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10018, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10019, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10020, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10021, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10022, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10023, 82, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10024, 83, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.command_element_values (command_element_type_id, element_type_id, command_element_type_param_id, command_element_id, value, enabled, visible, created_at, updated_at) VALUES 
-- ENE 
( 2, 71, 1, 10013, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10014, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10015, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10016, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10017, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10018, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10019, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10020, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10021, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10022, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"REVISA LAS","color":"#FFFFFF"},{"id":2,"value":"LUCES DE","color":"#FFFFFF"},{"id":3,"value":"TU VEHICULO","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10023, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 10024, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"LUCES","color":"#FFFFFF"},{"id":2,"value":"PERFECTAS 0","color":"#FFFFFF"},{"id":3,"value":"INCIDENCIAS","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_orders(plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at) VALUES 
--APAGADO
( 10001, 1, 1, 1, null, null, 'Apagar RV-PMV-5MA', 'Apagar RV-PMV-5MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 2, 1, 2, null, null, 'Apagar RV-PMV-6CA', 'Apagar RV-PMV-6CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 3, 1, 3, null, null, 'Apagar MM-PMV-1MA', 'Apagar MM-PMV-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 4, 1, 4, null, null, 'Apagar MM-PMV-2CA', 'Apagar MM-PMV-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 5, 1, 5, null, null, 'Apagar STMI-PMV-24CA', 'Apagar STMI-PMV-24CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 6, 1, 6, null, null, 'Apagar STMI-PMV-23MA', 'Apagar STMI-PMV-23MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 7, 1, 7, null, null, 'Apagar LQ-PMV-4CA', 'Apagar LQ-PMV-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 8, 1, 8, null, null, 'Apagar LQ-PMV-3MA', 'Apagar LQ-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 9, 1, 9, null, null, 'Apagar CH-PMV-7MA', 'Apagar CH-PMV-7MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 10, 1, 10, null, null, 'Apagar CH-PMV-8CA', 'Apagar CH-PMV-8CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 11, 1, 11, null, null, 'Apagar NG-PMV-21CA', 'Apagar NG-PMV-21CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10001, 12, 1, 12, null, null, 'Apagar NG-PMV-25MA', 'Apagar NG-PMV-25MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,

-- ENE 
( 10002, 1, 1, 1, null, null, 'Enviar Mensaje RV-PMV-5MA', 'Enviar Mensaje RV-PMV-5MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 2, 1, 2, null, null, 'Enviar Mensaje RV-PMV-6CA', 'Enviar Mensaje RV-PMV-6CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 3, 1, 3, null, null, 'Enviar Mensaje MM-PMV-1MA', 'Enviar Mensaje MM-PMV-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 4, 1, 4, null, null, 'Enviar Mensaje MM-PMV-2CA', 'Enviar Mensaje MM-PMV-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 5, 1, 5, null, null, 'Enviar Mensaje STMI-PMV-24CA', 'Enviar Mensaje STMI-PMV-24CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 6, 1, 6, null, null, 'Enviar Mensaje STMI-PMV-23MA', 'Enviar Mensaje STMI-PMV-23MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 7, 1, 7, null, null, 'Enviar Mensaje LQ-PMV-4CA', 'Enviar Mensaje LQ-PMV-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 8, 1, 8, null, null, 'Enviar Mensaje LQ-PMV-3MA', 'Enviar Mensaje LQ-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 9, 1, 9, null, null, 'Enviar Mensaje CH-PMV-7MA', 'Enviar Mensaje CH-PMV-7MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 10, 1, 10, null, null, 'Enviar Mensaje CH-PMV-8CA', 'Enviar Mensaje CH-PMV-8CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 11, 1, 11, null, null, 'Enviar Mensaje NG-PMV-21CA', 'Enviar Mensaje NG-PMV-21CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ,
( 10002, 12, 1, 12, null, null, 'Enviar Mensaje NG-PMV-25MA', 'Enviar Mensaje NG-PMV-25MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ;

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
( 2, 71, 10011, 10001, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ENE 
( 2, 71, 10013, 10002, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10014, 10002, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10015, 10002, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10016, 10002, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10017, 10002, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10018, 10002, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10019, 10002, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10020, 10002, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10021, 10002, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10022, 10002, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10023, 10002, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 10024, 10002, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.plan_schedules (plan_schedule_id, plan_id, cron_expression, alias, description, enabled, visible, created_at, updated_at) VALUES
-- APAGADO
(1, 10001, '0 0 14,20 ? * MON,TUE,WED,THU *', 'CSV - APAGADO', 'L, M, X Y J', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 10001, '0 0 16,22 ? * FRI,SAT,SUN *', 'CSV - APAGADO', 'V, S Y D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ENE 
(3, 10002, '0 0 12,18 ? JAN MON,TUE,WED,THU *', 'CSV ENE - ENCENDIDO', 'L, M, X Y J', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 10002, '0 0 12,18 ? JAN FRI,SAT,SUN *', 'CSV ENE - ENCENDIDO ', 'V, S Y D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);