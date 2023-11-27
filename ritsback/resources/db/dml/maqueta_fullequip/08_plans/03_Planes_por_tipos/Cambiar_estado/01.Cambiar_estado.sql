INSERT INTO conf."plans" (plan_id, plan_mode_type_id, alias, description, management_area_id, enabled, visible, created_at, updated_at) VALUES
(10103, 1, 'Cambio estado mantenimiento PMV Rio Verde', 'Cambio estado mantenimiento PMV Rio Verde', 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_orders (plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(10103, 3, 2, 2, NULL, NULL, 'CONFIRMATION', 'CONFIRMATION', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10103, 2, 3, 3, NULL, NULL, 'CHANGE_STATUS', 'CHANGE_STATUS', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_values(plan_id, plan_order_id, plan_order_type_id, plan_order_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
(10103, 3, 2, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10103, 3, 2, 2, '¿Está seguro que desea cambiar el estado de los paneles a modo mantenimiento?', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_element_type_states (plan_id, plan_order_id, element_type_id, element_type_state_id, element_id, enabled, visible, created_at, updated_at) VALUES
(10103, 2, 71, 4, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10103, 2, 71, 4, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_nodes (node_id, alias, description, plan_id, management_area_id, enabled, visible) VALUES
(10105, 'Cambiar estado', 'Cambiar estado', 10103, 1, true, true);

INSERT INTO conf.plan_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(10104, 10105, true, true);