-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>


 -- E1-MET-01
 -- 3 - S05 SEÑALIZACIÓN
(030121100001, 030121, 201, 1000, 307, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121100002, 030121, 201, 1000, 308, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121100003, 030121, 201, 1000, 309, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121100004, 030121, 201, 1000, 310, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

 -- 6 - S17 SEÑALIZACIÓN
(020221100001, 020221, 201, 1000, 108, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221100002, 020221, 201, 1000, 160, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


