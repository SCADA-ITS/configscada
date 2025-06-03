INSERT INTO conf.ims_incident_type_task_values (task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at) VALUES
--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 001403010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001403010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001403010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001503010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001503010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001503010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 001406010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001406010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001406010401, 'El estado actual de la circulación es @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001506010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001506010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001506010401, 'El estado actual de la circulación es @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 001408010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001408010401, '@TraficoNavarra', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001408010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001508010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001508010401, '@TraficoNavarra', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001508010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendMailTask tiene tres parámetros
--1 addressTO = Destinatarios
--2 addressCC = Destinatarios en copia
--3 addressBCC = Destinatarios en copia oculta
--4 subject = Asunto del correo electrónico
--5 body = Cuerpo del mensaje a enviar
(8, 1, 001404010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001404010401, 'Aviso de accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001404010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 001504010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001504010401, 'Aviso de accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001504010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendMailTask tiene tres parámetros
--1 addressTO = Destinatarios
--2 addressCC = Destinatarios en copia
--3 addressBCC = Destinatarios en copia oculta
--4 subject = Asunto del correo electrónico
--5 body = Cuerpo del mensaje a enviar
(8, 1, 001407010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001407010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001407010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 001507010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001507010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001507010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




