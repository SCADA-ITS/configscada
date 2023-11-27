DELETE FROM master.plan_mode_types;
DELETE FROM master.plan_state_types;
DELETE FROM master.plan_schedule_state_types;
DELETE FROM master.plan_order_state_types;
DELETE FROM master.plan_order_types;
-- 
-- Table: master.plan_mode_types
--
INSERT INTO master.plan_mode_types
(plan_mode_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'AUTO', 'auto', 'LBL_PLAN_MODE_TYPE_AUTO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'SEMI', 'semi', 'LBL_PLAN_MODE_TYPE_SEMI', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'MANUAL', 'manual', 'LBL_PLAN_MODE_TYPE_MANUAL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.plan_state_types
--
INSERT INTO master.plan_state_types
(plan_state_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(0, 'UNKNOWN', 'unknown', 'LBL_PLAN_STATE_TYPE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'STOPPED', 'stopped', 'LBL_PLAN_STATE_TYPE_STOPPED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'RUNNING', 'running', 'LBL_PLAN_STATE_TYPE_RUNNING', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'SUCCESS', 'success', 'LBL_PLAN_STATE_TYPE_SUCCESS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'FAILED', 'failed', 'LBL_PLAN_STATE_TYPE_FAILED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'PAUSE', 'pause', 'LBL_PLAN_STATE_TYPE_PAUSE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'CANCEL', 'cancel', 'LBL_PLAN_STATE_TYPE_CANCEL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



-- 
-- Table: master.plan_schedule_state_types
--
INSERT INTO master.plan_schedule_state_types
(plan_schedule_state_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(0, 'UNKNOWN', 'unknown', 'LBL_PLAN_SCHEDULE_STATE_TYPE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'PAUSE', 'pause', 'LBL_PLAN_SCHEDULE_STATE_TYPE_PAUSE', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'RUNNING', 'running', 'LBL_PLAN_SCHEDULE_STATE_TYPE_RUNNING', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'FAILED', 'failed', 'LBL_PLAN_SCHEDULE_STATE_TYPE_FAILED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.plan_order_state_types
--
INSERT INTO master.plan_order_state_types
(plan_order_state_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(0, 'UNKNOWN', 'unknown', 'LBL_PLAN_ORDER_STATE_TYPE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'RUNNING', 'running', 'LBL_PLAN_ORDER_STATE_TYPE_RUNNING', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'SUCCESS', 'success', 'LBL_PLAN_ORDER_STATE_TYPE_SUCCESS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'FAILED', 'failed', 'LBL_PLAN_ORDER_STATE_TYPE_FAILED', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.plan_order_types
--
INSERT INTO master.plan_order_types
(plan_order_type_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'SIGNALLING', 'Order to signal a device', 'LBL_PLAN_ORDER_TYPE_SIGNALLING', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'CONFIRMATION', 'Confirmation order', 'LBL_PLAN_ORDER_TYPE_CONFIRMATION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'CHANGE_STATUS', 'Change status order', 'LBL_PLAN_ORDER_TYPE_CHANGE_STATUS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'CHANGE_ELEMENT_PARAM_CONFIG', 'Order to change param config in element', 'LBL_PLAN_ORDER_TYPE_CHANGE_ELEMENT_PARAM_CONFIG', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'WAIT', 'Wait order', 'LBL_PLAN_ORDER_TYPE_WAIT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'PLAY_PLAN', 'Plan plan order', 'LBL_PLAN_ORDER_TYPE_PLAY_PLAM', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'CONDITION', 'Condition order', 'LBL_PLAN_ORDER_TYPE_CONDITION', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'LOOP', 'Loop order', 'LBL_PLAN_ORDER_TYPE_LOOP', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'EVENT', 'Event order', 'LBL_PLAN_ORDER_TYPE_EVENT', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'AUTOMATISM', 'Automatism order', 'LBL_PLAN_ORDER_TYPE_AUTOMATISM', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.plan_order_type_params
(plan_order_type_id, plan_order_type_param_id, data_type_id, threshold_1, threshold_2, plan_order_type_param_unit_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(2, 1, 1, '1', '3600', 22, 'Waiting time', 'Waiting time', 'LBL_PLAN_ORDER_TYPE_PARAM_CONFIRMATION_WAITING_TIME', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, NULL, NULL, NULL, 'Message', 'Message', 'LBL_PLAN_ORDER_TYPE_PARAM_CONFIRMATION_MESSAGE', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 1, '1', '3600', 22, 'Waiting time', 'Waiting time', 'LBL_PLAN_ORDER_TYPE_PARAM_WAIT_WAITING_TIME', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 1, NULL, NULL, NULL, 'Number of times', 'Number of times', 'LBL_PLAN_ORDER_TYPE_PARAM_LOOP_NUMBER_OF_TIMES', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 2, NULL, NULL, NULL, 'Message', 'Message', 'LBL_PLAN_ORDER_TYPE_PARAM_EVENT_MESSAGE', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 1, 2, NULL, NULL, NULL, 'Groovy path', 'Groovy path', 'LBL_PLAN_ORDER_TYPE_PARAM_AUTOMATISM_GROOVY_PATH', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 2, NULL, NULL, NULL, 'Automatism params', 'Groovy path', 'LBL_PLAN_ORDER_TYPE_PARAM_AUTOMATISM_PARAMS', NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
