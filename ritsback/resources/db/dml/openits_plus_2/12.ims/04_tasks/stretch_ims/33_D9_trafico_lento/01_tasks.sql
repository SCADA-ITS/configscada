 -- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010339, 101, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCamera', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01040339, 101, null, 6, 'RELLENAR BITACORA DEL INCIDENTE', 'Rellenar bitacora del incidente con toda la información disponible', true, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01010339, 101, 1, 1, 'SEÑALIZACION ALTO FLUJO VEHICULAR', 'Plan de señalización de alto flujo vehicular', true, 1, false, 'SendSignallingToStretch', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020339, 101, 1, 10, 'NOTIFICACION TELEGRAM', 'Envio de notificacion por telegram', true, 2, false, 'SendToTelegramNotificationPublic', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01030339, 101, 1, 6, 'COMPROBAR FALSA ALARMA', 'Verificación que no se trata de un vehículo lento o falsa alarma de DAI o ETD', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(03020339, 101, 3, 1, 'RESTITUCIÓN SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 2, false, 'SendSignallingToStretch', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
