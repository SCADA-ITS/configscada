
---- 
---- Table: master.pm_queues
----
INSERT INTO master.pm_queues(queue_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 'QUEUE VMS', 'QUEUE VMS', 'LBL_PRIORITY_QUEUE_VMS', 'LBL_PRIORITY_QUEUE_VMS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

---- 
---- Table: master.pm_queue_priorities
----
INSERT INTO master.pm_queue_priorities (queue_id, priority_id, alias, description, label_alias, label_description, priority, default_priority, plan_priority, incident_priority, travel_time_priority, check_conflict, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'FORCED', 'FORCED', 'LBL_PRIORITY_FORCED', 'LBL_PRIORITY_FORCED_DESC', 1, false, false, false, false, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'INCIDENT', 'INCIDENT', 'LBL_PRIORITY_INCIDENT', 'LBL_PRIORITY_INCIDENT_DESC', 2, false, false, true, false, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 'PLAN', 'PLAN', 'LBL_PRIORITY_PLAN', 'LBL_PRIORITY_PLAN_DESC', 3, false, true, false, false, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 'NORMAL', 'NORMAL', 'LBL_PRIORITY_NORMAL', 'LBL_PRIORITY_NORMAL_DESC', 5, true, false, false, false, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 'TRAVEL TIME', 'TRAVEL TIME', 'LBL_PRIORITY_TRAVEL_TIME', 'LBL_PRIORITY_TRAVEL_TIME_DESC', 4, true, false, false, true, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- 
-- Table: master.pm_element_type_queues
--
--INSERT INTO master.pm_element_type_queues
--(element_type_id, queue_id, enabled, visible, created_at, updated_at)
--VALUES(71, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);