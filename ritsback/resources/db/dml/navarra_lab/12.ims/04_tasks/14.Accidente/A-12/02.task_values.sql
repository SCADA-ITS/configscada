INSERT INTO conf.ims_incident_type_task_values (task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at) VALUES
--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 000603010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 000703010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 000803010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 000606010401, 'El estado actual de la circulación es @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 000706010401, 'El estado actual de la circulación es @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 000806010401, 'El estado actual de la circulación es @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 000608010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 000708010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 000808010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendMailTask tiene tres parámetros
--1 addressTO = Destinatarios
--2 addressCC = Destinatarios en copia
--3 addressBCC = Destinatarios en copia oculta
--4 subject = Asunto del correo electrónico
--5 body = Cuerpo del mensaje a enviar
(8, 1, 000604010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 000604010401, 'Aviso de accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 000604010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 000704010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 000704010401, 'Aviso de accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 000704010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 000804010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 000804010401, 'Aviso de accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 000804010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendMailTask tiene tres parámetros
--1 addressTO = Destinatarios
--2 addressCC = Destinatarios en copia
--3 addressBCC = Destinatarios en copia oculta
--4 subject = Asunto del correo electrónico
--5 body = Cuerpo del mensaje a enviar
(8, 1, 000607010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 000607010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 000607010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 000707010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 000707010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 000707010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 000807010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 000807010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 000807010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




