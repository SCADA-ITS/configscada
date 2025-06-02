INSERT INTO conf.ims_incident_type_task_values (task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at) VALUES
--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 01010108, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 01010108, '@TraficoNavarra', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 01010108, '🚨 ¡INICIO DE EVENTO DE TRIATLÓN! 🚨', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 02010108, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 02010108, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 02010108, '🚨 ¡INICIO DE EVENTO DE TRIATLÓN! 🚨', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendMailTask tiene tres parámetros
--1 addressTO = Destinatarios
--2 addressCC = Destinatarios en copia
--3 addressBCC = Destinatarios en copia oculta
--4 subject = Asunto del correo electrónico
--5 body = Cuerpo del mensaje a enviar
(8, 1, 03010108, 'jcaballeroa@revenga.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 03010108, 'Aviso de inicio de incidencia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 03010108, '🚨 ¡INICIO DE EVENTO DE TRIATLÓN! 🚨', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




