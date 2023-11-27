-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
(01010919, 91, null, 1, 'SEÑALIZACION FALLO SISTEMA CCTV', 'Plan de señalización en caso de fallo en el CCV', true, 1, false, 'SendSignallingToSpath', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02010919, 91, 2, 6, 'COMPROBACION', 'Confirmación de que se ha producido la serie de fallos que generan incidente', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020919, 91, 2, 6, 'AVISO A MANTENIMIENTO', 'Aviso al personal de mantenimiento para ejecución de trabajos', false, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030919, 91, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040919, 91, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050919, 91, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02060919, 91, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--ETAPA = RESTITUCION 
(03010919, 91, 3, 1, 'SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 1, false, 'SendSignallingToSpath', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020919, 91, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030919, 91, 3, 7, 'REESTABLECER SUBSISTEMA', 'Restablecimiento del funcionamiento tras fallo en el sistema de CCTV', false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
