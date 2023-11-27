INSERT INTO conf."plans"
(plan_id, plan_mode_type_id, alias, description, management_area_id, enabled, visible, created_at, updated_at)
VALUES(10110, 1, 'Señalización de semaforo con intermitencia', 'Señalización de semaforo con intermitencia', 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_orders(plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at)VALUES
(10110, 1, 1, 1, NULL, NULL, 'SIGNALLING - RV-SMF-AA-1CA', 'SIGNALLING - RV-SMF-AA-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10110, 2, 5, 2, NULL, NULL, 'WAIT', 'WAIT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10110, 3, 1, 3, NULL, NULL, 'SIGNALLING - RV-SMF-AA-1CA', 'SIGNALLING - RV-SMF-AA-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10110, 4, 5, 4, NULL, NULL, 'WAIT', 'WAIT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.command_elements(command_element_type_id, element_type_id, command_element_id, element_id, enabled, visible, created_at, updated_at)VALUES
(2, 53, 4055, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 53, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_values(plan_id, plan_order_id, plan_order_type_id, plan_order_type_param_id, value, enabled, visible, created_at, updated_at)VALUES
(10110, 2, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10110, 4, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_command_elements(command_element_type_id, element_type_id, command_element_id, plan_id, plan_order_id, enabled, visible, created_at, updated_at)VALUES
(2, 53, 4055, 10110, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 53, 1, 10110, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_nodes(node_id, alias, description, plan_id, management_area_id, enabled, visible)VALUES
(10109, 'Señalización de semaforo con intermitencia', 'Señalización de semaforo con intermitencia', 10110, 1, true, true);

INSERT INTO conf.plan_node_hierarchies(node_parent_id, node_child_id, enabled, visible)VALUES
(10104, 10109, true, true);