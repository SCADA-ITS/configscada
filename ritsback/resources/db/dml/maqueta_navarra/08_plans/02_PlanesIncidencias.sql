INSERT INTO conf.plans (plan_id, plan_mode_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES 
( 1, 1, 'Corte Carril Dcho', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_nodes (node_id, alias, description, plan_id, enabled, visible) VALUES 
( 1, 'PARTICULAR PLAN', 'PARTICULAR PLAN', null, true, true),
( 2, 'Corte Carril Dcho', 'Corte Carril Dcho', 1, true, true);


INSERT INTO conf.plan_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES 
( 1, 2, true, true);

INSERT INTO conf.command_elements (command_element_type_id, element_type_id, command_element_id, element_id, enabled, visible, created_at, updated_at) VALUES 
( 2, 71, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 3, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.command_element_values (command_element_type_id, element_type_id, command_element_type_param_id, command_element_id, value, enabled, visible, created_at, updated_at) VALUES 
( 2, 71, 1, 1, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[{"id":1,"value":744,"alternance":744}],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"CARRIL","alternance":"","color":"#FFFFFF"},{"id":2,"value":"DERECHO","alternance":"PRECAUCION","color":"#FFFFFF"},{"id":3,"value":"CORTADO","alternance":"","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[{"id":1,"value":317,"alternance":317}],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 2, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[{"id":1,"value":744,"alternance":744}],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"CARRIL","alternance":"","color":"#FFFFFF"},{"id":2,"value":"DERECHO","alternance":"PRECAUCION","color":"#FFFFFF"},{"id":3,"value":"CORTADO","alternance":"","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[{"id":1,"value":317,"alternance":317}],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 1, 3, '[{"zone":1,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[{"id":1,"value":744,"alternance":744}],"align":"center"},{"zone":2,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[{"id":1,"value":"CARRIL","alternance":"","color":"#FFFFFF"},{"id":2,"value":"DERECHO","alternance":"PRECAUCION","color":"#FFFFFF"},{"id":3,"value":"CORTADO","alternance":"","color":"#FFFFFF"}],"graphics":[],"align":"center"},{"zone":3,"alternance_ms":3000,"flashing_on_ms":0,"flashing_off_ms":0,"texts":[],"graphics":[{"id":1,"value":317,"alternance":317}],"align":"center"}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_orders(plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at) VALUES 
( 1, 1, 1, 1, null, null, 'Enviar Mensaje PMV-1', 'Enviar Mensaje PMV-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 1, 2, 1, 1, null, null, 'Enviar Mensaje PMV-2', 'Enviar Mensaje PMV-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 1, 3, 1, 1, null, null, 'Enviar Mensaje PMV-3', 'Enviar Mensaje PMV-3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.plan_order_command_elements(command_element_type_id, element_type_id, command_element_id, plan_id, plan_order_id, enabled, visible, created_at, updated_at) VALUES 
( 2, 71, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 2, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( 2, 71, 3, 1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

