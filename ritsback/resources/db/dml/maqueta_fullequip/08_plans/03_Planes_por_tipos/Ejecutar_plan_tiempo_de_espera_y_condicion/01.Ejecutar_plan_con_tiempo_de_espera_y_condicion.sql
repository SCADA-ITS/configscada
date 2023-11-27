INSERT INTO conf."plans"(plan_id, plan_mode_type_id, alias, description, management_area_id, enabled, visible, created_at, updated_at)VALUES
(10109, 1, 'Ejecutar plan con tiempo de espera y condición', 'Ejecutar plan con tiempo de espera y condición', 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_orders(plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(10109, 1, 6, 1, NULL, 10103, 'PLAY_PLAN', 'PLAY_PLAN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10109, 2, 5, 2, NULL, NULL, 'WAIT', 'WAIT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10109, 3, 7, 3, NULL, NULL, 'CONDITION', 'CONDITION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10109, 4, 9, 4, NULL, NULL, 'EVENT', 'EVENT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_conditions(plan_order_condition_id, plan_id, plan_order_id, element_type_id, element_id, param_type_id, element_type_param_id, threshold_operation_id, threshold_val1, threshold_val2, element_type_state_id, plan_order_condition_order, nexus_and, enabled, visible, created_at, updated_at)VALUES
(2, 10109, 3, 71, 1, NULL, NULL, NULL, NULL, NULL, 4, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 10109, 3, 71, 2, NULL, NULL, NULL, NULL, NULL, 4, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_values (plan_id, plan_order_id, plan_order_type_id, plan_order_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
(10109, 2, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10109, 4, 9, 1, 'Paneles cambiados a modo mantenimiento', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_nodes(node_id, alias, description, plan_id, management_area_id, enabled, visible)VALUES
(10108, 'Ejecutar plan con tiempo de espera y condición', 'Ejecutar plan con tiempo de espera y condición', 10109, 1, true, true);

INSERT INTO conf.plan_node_hierarchies(node_parent_id, node_child_id, enabled, visible)VALUES
(10104, 10108, true, true);