-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>


 -- E1-MET-01
 -- 3 - S05 SEÑALIZACIÓN
(030121102101, 030121, 201, 1021, 411, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121102102, 030121, 201, 1021, 412, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121102103, 030121, 201, 1021, 413, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121102104, 030121, 201, 1021, 414, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

 -- 6 - S17 SEÑALIZACIÓN
(020221102101, 020221, 201, 1021, 92, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221102102, 020221, 201, 1021, 158, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221102103, 020221, 201, 1021, 159, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);










