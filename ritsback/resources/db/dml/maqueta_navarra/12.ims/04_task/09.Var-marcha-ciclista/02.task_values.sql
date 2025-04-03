INSERT INTO conf.ims_incident_type_task_values (task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at) VALUES
--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 01010109, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 01010109, '@TraficoNavarra', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 01010109, '🚨 ¡INICIO DE EVENTO MARCHA CICLISTA! 🚨', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 02010109, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 02010109, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 02010109, '🚨 ¡INICIO DE EVENTO MARCHA CICLISTA! 🚨', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendMailTask tiene tres parámetros
--1 addressTO = Destinatarios
--2 addressCC = Destinatarios en copia
--3 addressBCC = Destinatarios en copia oculta
--4 subject = Asunto del correo electrónico
--5 body = Cuerpo del mensaje a enviar
(8, 3, 03010109, 'jcaballeroa@revenga.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 03010109, 'Aviso de inicio de incidencia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 03010109, '🚨 ¡INICIO DE EVENTO MARCHA CICLISTA! 🚨', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




