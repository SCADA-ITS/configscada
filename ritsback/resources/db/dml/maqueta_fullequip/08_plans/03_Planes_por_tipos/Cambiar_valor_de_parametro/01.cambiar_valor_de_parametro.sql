INSERT INTO conf."plans" (plan_id, plan_mode_type_id, alias, description, management_area_id, enabled, visible, created_at, updated_at) VALUES
(10104, 1, 'Cambio configuración de parámetros PMV Río Verde', 'Cambio configuración de parámetros PMV Río Verde', 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_orders (plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(10104, 1, 4, 1, NULL, NULL, 'CHANGE_ELEMENT_PARAM_CONFIG', 'CHANGE_ELEMENT_PARAM_CONFIG', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10104, 2, 9, 2, NULL, NULL, 'EVENT', 'EVENT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_element_values(plan_id, plan_order_id, element_type_id, param_type_id, element_type_param_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(10104, 1, 71, 1, 1, 1, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10104, 1, 71, 1, 1, 2, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_values(plan_id, plan_order_id, plan_order_type_id, plan_order_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
(10104, 2, 9, 1, 'Parámetros cambiados', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_nodes (node_id, alias, description, plan_id, management_area_id, enabled, visible) VALUES
(10106, 'Cambiar configuración de parámetro', 'Cambiar configuración de parámetro', 10104, 1, true, true);

INSERT INTO conf.plan_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(10104, 10106, true, true);