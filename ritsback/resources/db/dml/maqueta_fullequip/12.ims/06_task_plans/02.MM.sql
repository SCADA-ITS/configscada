-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>
-- I-TRA-01
-- E1-MET-01
 -- 3 - S05 SEÑALIZACIÓN
(030121100701, 030121, 201, 1007, 329, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121100702, 030121, 201, 1007, 330, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121100703, 030121, 201, 1007, 331, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121100704, 030121, 201, 1007, 332, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

 -- 6 - S17 SEÑALIZACIÓN
(020221100701, 020221, 201, 1007, 70, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221100702, 020221, 201, 1007, 71, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);









