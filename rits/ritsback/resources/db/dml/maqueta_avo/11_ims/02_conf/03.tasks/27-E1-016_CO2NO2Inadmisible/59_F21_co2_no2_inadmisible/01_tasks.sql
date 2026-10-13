-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
(01010599, 59, 1, 1, 'SEÑALIZAR CO2-NO2 INADMISIBLE', 'Plan de señalización de CO2/NO2 inadmisible', true, 1, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01070599, 59, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 7, true, 'LockCamFireZone', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02010599, 59, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020599, 59, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030599, 59, 2, 6, 'ALERTA ASISTENCIA VIAL', 'Alerta a móviles de Asistencia Vial', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040599, 59, 2, 6, 'DESPACHO ASISTENCIA VIAL', 'Despacho móviles de Asistencia Vial', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050599, 59, 2, 6, 'COORDINACIÓN CENCO', 'Aviso y coordinación con CENCO', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02060599, 59, 2, 6, 'COORDINACIÓN UOCT', 'Aviso y coordinación con UOCT', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02070599, 59, 2, 6, 'ALERTA IF', 'Aviso a Inspector Fiscal', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02080599, 59, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 8, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02090599, 59, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 9, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--ETAPA = RESTITUCION 
(03010599, 59, 3, 1, 'SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020599, 59, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030599, 59, 3, 6, 'AVISAR CIERRE INCIDENTE A SVI', 'Aviso de termino de incidente a móviles de SVI', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040599, 59, 3, 6, 'AVISAR CIERRE INCIDENTE A VIAL', 'Aviso de termino de incidente a móviles de Asistencia Vial', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03050599, 59, 3, 6, 'AVISAR CIERRE INCIDENTE A CENCO', 'Aviso de termino de incidente a CENCO', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03060599, 59, 3, 6, 'AVISAR CIERRE INCIDENTE A UOCT', 'Aviso de termino de incidente a UOCT', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03070599, 59, 3, 6, 'AVISAR CIERRE INCIDENTE A IF', 'Aviso de termino de incidente a Inspector Fiscal', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
