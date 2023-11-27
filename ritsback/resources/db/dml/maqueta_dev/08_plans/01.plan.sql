DELETE FROM conf.command_elements;
DELETE FROM conf.plan_order_command_elements;
DELETE FROM conf.plan_orders;
DELETE FROM conf.plans;

-- 
-- Table: conf.plans
--
INSERT INTO conf.plans
(plan_id, plan_mode_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1, 3, 'OPEN TUNNEL', 'Open Rio Verde tunnel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'CLOSE TUNNEL', 'Close Rio Verde tunnel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Rio Verde -- P-MET-01
(701, 1, 'S4A', 'Señalización tuenel rio verde - Peligro lluvia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(702, 1, 'S4B', 'Señalización tuenel rio verde - Peligro niebla', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(703, 1, 'S4C', 'Señalización tuenel rio verde - Peligro viento', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(704, 1, 'S4D', 'Señalización tuenel rio verde - Peligro nieve o hielo', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(705, 1, 'S17', 'Señalización tuenel rio verde - Retirar señalización de emergencia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Automatism Plans
(3, 1, 'AUtomatism Plan TEST', 'Plan test de lanzamiento de groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(706, 1, 'TEST', 'Test', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.plan_orders
--
INSERT INTO conf.plan_orders 
(plan_id, plan_order_id, plan_order_type_id, plan_order_order, next_plan_order_id, next_plan_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(701, 1, 5, 1, NULL, NULL, 'WAIT', 'WAIT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(705, 1, 5, 1, NULL, NULL, 'WAIT', 'WAIT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(705, 2, 5, 2, NULL, NULL, 'WAIT', 'WAIT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 1, 1, 1, NULL, NULL, 'Orden 1', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 2, 5, 2, NULL, NULL, 'Orden 2', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 3, 1, 3, NULL, NULL, 'Orden 3', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 4, 5, 4, NULL, NULL, 'Orden 4', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 5, 2, 5, NULL, NULL, 'Orden 5', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 6, 1, 6, NULL, NULL, 'Orden 6', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 7, 5, 7, NULL, NULL, 'Orden 7', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 8, 8, 8, 6, NULL, 'Orden 8', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 9, 6, 9, NULL, 1, 'Orden 9', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 10, 3, 10, NULL, NULL, 'Orden 10', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 11, 5, 11, NULL, NULL, 'Orden 11', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 12, 3, 12, NULL, NULL, 'Orden 12', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 13, 5, 13, NULL, NULL, 'Orden 13', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 14, 3, 14, NULL, NULL, 'Orden 14', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 15, 4, 15, NULL, NULL, 'Orden 15', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 16, 5, 16, NULL, NULL, 'Orden 16', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 17, 7, 17, NULL, NULL, 'Orden 17', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 18, 5, 18, NULL, NULL, 'Orden 18', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 19, 4, 19, NULL, NULL, 'Orden 19', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 20, 7, 20, NULL, NULL, 'Orden 20', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 21, 5, 21, NULL, NULL, 'Orden 21', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 22, 1, 22, NULL, NULL, 'Signalling Panel VMS 1', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 23, 1, 23, NULL, NULL, 'Reset Panel VMS 1', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 24, 1, 24, NULL, NULL, 'PAS', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 10, 1, NULL, NULL, 'Automatism Order', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.plan_order_values
--
INSERT INTO conf.plan_order_values (plan_id, plan_order_id, plan_order_type_id, plan_order_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
(701, 1, 5, 1, '5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(705, 1, 5, 1, '5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(705, 2, 5, 1, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 2, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 4, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 5, 2, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 5, 2, 2, 'CONTINUAMOS???', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 7, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 8, 8, 1, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 11, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 13, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 16, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 18, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 21, 5, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 10, 1, 'automatism_test.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 10, 2, 'Esoty imprimiendo esto porque funciona! Prodia ser cualquier parametro que ha llegado correctamente al groovy!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);





-- 
-- Table: conf.plan_order_element_values
--
INSERT INTO conf.plan_order_element_values (plan_id, plan_order_id, element_type_id, param_type_id, element_type_param_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(706, 15, 2001, 1, 1, 1, '10', true, true,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 19, 2001, 1, 1, 1, '11', true, true,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.plan_order_element_type_states
--
INSERT INTO conf.plan_order_element_type_states (plan_id, plan_order_id, element_type_id, element_type_state_id, element_id, enabled, visible, created_at, updated_at) VALUES
(706, 10, 2001, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 12, 2001, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(706, 14, 2001, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.plan_order_conditions
--
INSERT INTO conf.plan_order_conditions (plan_order_condition_id, plan_id, plan_order_id, element_type_id, element_id, param_type_id, element_type_param_id, threshold_operation_id, threshold_val1, threshold_val2, element_type_state_id, plan_order_condition_order, nexus_and, enabled, visible, created_at, updated_at) VALUES
(1, 706, 17, 2001, 1, 1, 1, 2, '10', NULL, NULL, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 706, 20, 2001, 1, 1, 1, 2, '10', NULL, NULL, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.plan_order_command_elements
--

INSERT INTO conf.command_elements (command_element_type_id, element_type_id, command_element_id, element_id, enabled, visible, created_at, updated_at) VALUES
(1, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 71, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 71, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.command_element_values (command_element_type_id, element_type_id, command_element_type_param_id, command_element_id, value, enabled, visible, created_at, updated_at) VALUES
(2, 71, 1, 1, '[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":81,"alternance":744}]},{"zone":2,"alternance_ms":5000,"align": "left","texts":[{"id":1,"value":"ACCIDENTE","alternance":"REDUZCA"},{"id":2,"value":"A 3 KM EN","alternance":"LA VELOCIDAD"},{"id":3,"value":"CARRIL DER.","alternance":"USE CAR. IZD"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphics":[{"id":1,"value":85}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.plan_order_command_elements (command_element_type_id, element_type_id, command_element_id, plan_id, plan_order_id, enabled, visible, created_at, updated_at) VALUES
(1, 9, 1, 706, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 8, 1, 706, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 1, 706, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 71, 1, 706, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 71, 1, 706, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 1, 706, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: config.plan_nodes
--
INSERT INTO conf.plan_nodes (node_id, alias, description, plan_id, enabled, visible) VALUES
(1, 'PARTICULAR PLAN', 'Particular plan', null, true, true),
(2, 'INC EXPLOTACION', 'Planes para incidendias de explotacion', null, true, true),
(3, 'PREEMERGENCIA', 'Planes para preemergencias', null, true, true),
(4, 'EMERGENCIA NIVEL 1', 'Planes para emergencias de nivel 1', null, true, true),
(5, 'EMERGENCIA NIVEL 2', 'Planes para emergencias de nivel 2', null, true, true),
(6, 'TEST', 'Test', null, true, true),

(7, 'PARTICULAR PLAN GROUP 1', 'Particular plan', null, true, true),
(8, 'INC EXPLOTACION GROUP 1', 'Planes para incidendias de explotacion', null, true, true),
(9, 'PREEMERGENCIA GROUP 1', 'Planes para preemergencias', null, true, true),
(10, 'EMERGENCIA NIVEL 1 GROUP 1', 'Planes para emergencias de nivel 1', null, true, true),
(11, 'EMERGENCIA NIVEL 2 GROUP 1', 'Planes para emergencias de nivel 2', null, true, true),
(12, 'TEST GROUP 1', 'Test GROUP 1', null, true, true),

(13, 'OPEN TUNNEL', 'Open Rio Verde tunnel', 1, true, true),
(14, 'CLOSE TUNNEL', 'Close Rio Verde tunnel', 2, true, true),
(15, 'S4A', 'Señalización tuenel rio verde - Peligro lluvia', 701, true, true),
(16, 'S4B', 'Señalización tuenel rio verde - Peligro niebla', 702, true, true),
(17, 'S4C', 'Señalización tuenel rio verde - Peligro viento', 703, true, true),
(18, 'S4D', 'Señalización tuenel rio verde - Peligro nieve o hielo', 704, true, true),
(19, 'S17', 'Señalización tuenel rio verde - Retirar señalización de emergencia', 705, true, true),
(20, 'TEST', 'Test', 706, true, true);

-- 
-- Table: config.plan_node_hierarchies
--
INSERT INTO conf.plan_node_hierarchies (node_parent_id, node_child_id, enabled, visible) VALUES
(1, 7, true, true),
(2, 8, true, true),
(3, 9, true, true),
(4, 10, true, true),
(5, 11, true, true),
(6, 12, true, true),

(7, 13, true, true),
(7, 14, true, true),

(9, 15, true, true),
(9, 16, true, true),
(9, 17, true, true),
(9, 18, true, true),
(9, 19, true, true),

(12, 20, true, true);