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
(10, 1, 010606010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 010706010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 010806010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 020606010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 020706010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 020806010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 000608010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 000708010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 000808010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

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
(8, 1, 010607010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 010607010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 010607010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 010707010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 010707010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 010707010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 010807010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 010807010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 010807010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 020607010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 020607010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 020607010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 020707010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 020707010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 020707010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 020807010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 020807010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 020807010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




