INSERT INTO conf."plans"
(plan_id, plan_mode_type_id, alias, description, management_area_id, enabled, visible, created_at, updated_at)
VALUES(10108, 1, 'Automatismo cambio valor parámetros', 'Automatismo cambio valor parámetros', 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_orders
(plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at)
VALUES(10108, 1, 10, 1, NULL, NULL, 'AUTOMATISM', 'AUTOMATISM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_values(plan_id, plan_order_id, plan_order_type_id, plan_order_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
(10108, 1, 10, 2, 'Marca: Marca de prueba
Modelo: Modelo de prueba', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10108, 1, 10, 1, 'ChangeElementValue.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_nodes
(node_id, alias, description, plan_id, management_area_id, enabled, visible)
VALUES(10107, 'Automatismo cambio valor parámetros', 'Automatismo cambio valor parámetros', 10108, 1, true, true);

INSERT INTO conf.plan_node_hierarchies
(node_parent_id, node_child_id, enabled, visible)
VALUES(10104, 10107, true, true);