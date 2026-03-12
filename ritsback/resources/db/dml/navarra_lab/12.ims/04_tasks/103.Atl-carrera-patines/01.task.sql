-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(01010103, 103, 1, 10, 'ENVIAR TELEGRAM PUBLICO', 'Notificación usuarios por Telegram', 'Enviar información a canal público de Telegram de la incidencia para informar a los ciudadanos.', false, 1, false, 'SendToTelegramNotificationPublic', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02010103, 103, 1, 10, 'ENVIAR TELEGRAM INTERNO', 'Notificación interna por Telegram', 'Enviar información a canal interno de Telegram de la incidencia.', false, 2, false, 'SendToTelegramNotificationPrivate', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03010103, 103, 1, 8, 'ENVIAR CORREO', 'Enviar correo de aviso', 'Enviar correo eléctronico a personal para informar de ocurencia de incidencia', false, 3, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011000103, 103, 100, 1, 'FINALIZAR BITÁCORA', 'Guardar fecha de fin en bitácora', 'Guardar fecha de fin en bitácora', true, 2, true, 'SendEndIncident', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



