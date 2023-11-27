-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00070749, 74, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCamFireZone', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01010749, 74, 1, 1, 'SEÑALIZAR DISTURBIOS-PROTESTAS', 'Plan de señalización y megafonía de disturbios o protestas', true, 1, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020749, 74, 1, 1, 'BAJAR BARRERA', 'Corte de entrada del túnel con barrera', false, 2, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010749, 74, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020749, 74, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030749, 74, 2, 6, 'ALERTA ASISTENCIA VIAL', 'Alerta a móviles de Asistencia Vial', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040749, 74, 2, 6, 'DESPACHO ASISTENCIA VIAL', 'Despacho móviles de Asistencia Vial', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050749, 74, 2, 6, 'COORDINACIÓN CENCO', 'Aviso y coordinación con CENCO', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02060749, 74, 2, 6, 'COORDINACIÓN UOCT', 'Aviso y coordinación con UOCT', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02070749, 74, 2, 6, 'ALERTA IF', 'Aviso a Inspector Fiscal', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02080749, 74, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 8, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02090749, 74, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 9, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION 
(03010749, 74, 3, 1, 'SEÑALIZACION', 'Restitución de señalizacióna y megafonía a estado normalizado', true, 1, true, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020749, 74, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030749, 74, 3, 6, 'AVISAR CIERRE INCIDENTE A SVI', 'Aviso de termino de incidente a móviles de SVI', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040749, 74, 3, 6, 'AVISAR CIERRE INCIDENTE A VIAL', 'Aviso de termino de incidente a móviles de Asistencia Vial', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03050749, 74, 3, 6, 'AVISAR CIERRE INCIDENTE A CENCO', 'Aviso de termino de incidente a CENCO', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03060749, 74, 3, 6, 'AVISAR CIERRE INCIDENTE A UOCT', 'Aviso de termino de incidente a UOCT', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03070749, 74, 3, 6, 'AVISAR CIERRE INCIDENTE A IF', 'Aviso de termino de incidente a Inspector Fiscal', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03080749, 74, 3, 1, 'SUBIR BARRERA', 'Apertura de dentrada del túnel subiendo barrera', false, 8, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
