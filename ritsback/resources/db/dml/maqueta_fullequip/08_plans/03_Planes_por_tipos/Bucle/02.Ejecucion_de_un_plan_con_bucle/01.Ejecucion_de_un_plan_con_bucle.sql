INSERT INTO conf."plans"(plan_id, plan_mode_type_id, alias, description, management_area_id, enabled, visible, created_at, updated_at)VALUES
(10111, 1, 'Ejecución de un plan con bucle', 'Ejecución de un plan con bucle', 1, true, true, '2023-11-09 13:31:24.964', '2023-11-09 13:31:24.964');

INSERT INTO conf.plan_orders(plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at)VALUES
(10111, 1, 6, 1, NULL, 10110, 'PLAY_PLAN', 'PLAY_PLAN', true, true, '2023-11-09 13:31:28.723', '2023-11-09 13:31:41.625'),
(10111, 2, 8, 2, 1, NULL, 'LOOP', 'LOOP', true, true, '2023-11-09 13:31:44.350', '2023-11-09 13:32:06.502');

INSERT INTO conf.plan_order_values(plan_id, plan_order_id, plan_order_type_id, plan_order_type_param_id, value, enabled, visible, created_at, updated_at)VALUES
(10111, 2, 8, 1, '3', true, true, '2023-11-09 13:31:44.350', '2023-11-09 13:32:06.502');

INSERT INTO conf.plan_nodes(node_id, alias, description, plan_id, management_area_id, enabled, visible)VALUES
(10110, 'Ejecución de un plan con bucle', 'Ejecución de un plan con bucle', 10111, 1, true, true);

INSERT INTO conf.plan_node_hierarchies(node_parent_id, node_child_id, enabled, visible)VALUES
(10104, 10110, true, true);