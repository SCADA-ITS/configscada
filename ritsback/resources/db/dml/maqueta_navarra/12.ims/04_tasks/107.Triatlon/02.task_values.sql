INSERT INTO conf.ims_incident_type_task_values (task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at) VALUES
--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 01010107, 'Inicio de evento de atletismo: Triatlón
@alias
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




