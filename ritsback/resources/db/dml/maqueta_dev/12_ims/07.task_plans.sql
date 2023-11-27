-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES

-- Rio Verde -- P-MET-01
(701, 702, 7, 1000, 701, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(702, 702, 7, 1000, 702, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(703, 702, 7, 1000, 703, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(704, 702, 7, 1000, 704, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(705, 703, 7, 1000, 705, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(707, 710, 7, 1000, 701, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1001703, 703, 7, 1001, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001710, 710, 7, 1001, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1002703, 703, 7, 1002, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1002710, 710, 7, 1002, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);





