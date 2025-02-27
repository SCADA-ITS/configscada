-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(01010101, 101, 1, 6, 'AVISO', 'Aviso a emergencias (112)', 'Aviso a emergencias (112)', false, 1, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02010101, 101, 1, 6, 'AVISO', 'Aviso a agentes de movilidad', 'Aviso a agentes de movilidad', false, 2, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03010101, 101, 1, 6, 'AVISO', 'Aviso a supervisor de centro de control', 'Aviso a supervisor de centro de control', false, 3, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(01020101, 101, 2, 1, 'SEÑALIZACIÓN', 'Señalización de evento', 'Señalización de evento', false, 1, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(01030101, 101, 3, 1, 'SEÑALIZACIÓN', 'Señalización de campaña de tráfico', 'Señalización de campaña de tráfico', false, 1, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030101, 101, 3, 6, 'AVISO', 'Aviso de retirada a emergencias (112)', 'Aviso de retirada a emergencias (112)', false, 2, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030101, 101, 3, 6, 'AVISO', 'Aviso de retirada a agentes de movilidad', 'Aviso de retirada a agentes de movilidad', false, 3, false, null, null ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




