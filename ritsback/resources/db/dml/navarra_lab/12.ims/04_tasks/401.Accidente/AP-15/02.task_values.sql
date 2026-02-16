INSERT INTO conf.ims_incident_type_task_values (task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at) VALUES
--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 001603010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001703010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001803010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 011606010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 011706010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 011806010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 021606010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 021706010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 021806010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 001608010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001708010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001808010401, '@alias
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
(8, 1, 001604010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001604010401, 'Aviso de accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001604010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 001704010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001704010401, 'Aviso de accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001704010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 001804010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001804010401, 'Aviso de accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001804010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendMailTask tiene tres parámetros
--1 addressTO = Destinatarios
--2 addressCC = Destinatarios en copia
--3 addressBCC = Destinatarios en copia oculta
--4 subject = Asunto del correo electrónico
--5 body = Cuerpo del mensaje a enviar
(8, 1, 011607010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 011607010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 011607010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 011707010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 011707010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 011707010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 011807010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 011807010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 011807010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 021607010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 021607010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 021607010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 021707010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 021707010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 021707010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 021807010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 021807010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 021807010401, '@alias
@tipo
@localizacion
@afeccion
@fecha', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




