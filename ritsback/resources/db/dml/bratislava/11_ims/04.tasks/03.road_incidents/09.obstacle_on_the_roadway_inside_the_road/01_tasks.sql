      -- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = INICIAL
--Afección = Pista izquierda
(01010309, 309, 1, 1, 'SIGNALLING', 'Send signage', true, 1, false, 'SendSignallingToStretch', true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha
(01020309, 309, 1, 1, 'SIGNALLING', 'Send signage', true, 1, false, 'SendSignallingToStretch', true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista izquierda + central
(01030309, 309, 1, 1, 'SIGNALLING', 'Send signage', true, 1, false, 'SendSignallingToStretch', true, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha + central
(01040309, 309, 1, 1, 'SIGNALLING', 'Send signage', true, 1, false, 'SendSignallingToStretch', true, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(02010309, 0309, 2, 1, 'SIGNALLING RESTITUTION', 'Swith off VMS', true, 2, false, 'SendSignallingToStretch', true, null,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03010309, 0309, 2, 1, 'SIGNALLING RESTITUTION', 'Send signage', true, 1, false, 'SendSignallingToStretch', true, null,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


