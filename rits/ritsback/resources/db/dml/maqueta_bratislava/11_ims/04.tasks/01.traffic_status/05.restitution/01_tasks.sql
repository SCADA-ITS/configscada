-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = EN MARCHA
(01010105, 0105, 1, 1, 'SIGNALLING', 'Swith off VMS', true, 1, true, 'SendSignallingToStretchDisable', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);