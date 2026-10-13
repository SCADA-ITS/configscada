-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010479, 47, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCamFireZone', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01010479, 47, 1, 1, 'SEÑALIZACION ANIMAL VIVO', 'Plan de señalización de animal vivo', true, 1, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020479, 47, 1, 1, 'BAJAR BARRERA', 'Corte de entrada del túnel con barrera', false, 2, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010479, 47, 2, 6, 'COORDINACIÓN CENCO', 'Aviso y coordinación con CENCO', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020479, 47, 2, 6, 'ALERTA ASISTENCIA VIAL', 'Alerta a móviles de Asistencia Vial', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030479, 47, 2, 6, 'DESPACHO ASISTENCIA VIAL', 'Despacho móviles de Asistencia Vial', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040479, 47, 2, 6, 'ESCALAMIENTO JEFE INTERNO', 'Escalamiento con Jefe de emergencia interno de ser necesario', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050479, 47, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02060479, 47, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02070479, 47, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02080479, 47, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 8, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION 
(03010479, 47, 3, 6, 'AVISAR CIERRE INCIDENTE A SVI', 'Aviso de termino de incidente a móviles de SVI', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020479, 47, 3, 6, 'AVISAR CIERRE INCIDENTE A ASISTENCIA VIAL', 'Aviso de termino de incidente a móviles de Asistencia Vial', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030479, 47, 3, 6, 'AVISAR CIERRE INCIDENTE A CENCO', 'Aviso de termino de incidente a CENCO', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040479, 47, 3, 1, 'SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 4, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03050479, 47, 3, 1, 'SUBIR BARRERA', 'Apertura de dentrada del túnel subiendo barrera', false, 5, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03060479, 47, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
