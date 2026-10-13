-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010289, 28, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCamFireZone', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02012819, 28, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02022819, 28, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02032819, 28, 2, 6, 'COORDINACIÓN CENCO', 'Aviso y coordinación con CENCO', false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02042819, 28, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02052819, 28, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION 
(03022819, 28, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03032819, 28, 3, 6, 'AVISAR CIERRE INCIDENTE A SVI', 'Aviso de termino de incidente a móviles de SVI', false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03042819, 28, 3, 6, 'AVISAR CIERRE INCIDENTE A CENCO', 'Aviso de termino de incidente a CENCO', false, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
