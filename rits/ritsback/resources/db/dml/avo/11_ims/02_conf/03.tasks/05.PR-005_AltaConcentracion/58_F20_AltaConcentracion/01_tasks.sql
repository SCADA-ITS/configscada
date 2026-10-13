-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(01020589, 58, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 2, true, 'LockCamFireZone', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01010589, 58, 1, 6, 'VENTILACIÓN SANITARIA', 'Comprobación de que se ha lanzado la pauta automática de ventilación sanitaria', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010589, 58, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020589, 58, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030589, 58, 2, 6, 'COMPROBAR VENTILACION', 'Comprobar la activación de la ventilación sanitaria', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(03010589, 58, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
