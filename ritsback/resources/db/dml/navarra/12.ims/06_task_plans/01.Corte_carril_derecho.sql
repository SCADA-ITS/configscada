-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>
(01030101, 030101, 101, 1000, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01030201, 030201, 201, 1000, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01030301, 030301, 301, 1000, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01030401, 030401, 401, 1000, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01030501, 030501, 501, 1000, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
