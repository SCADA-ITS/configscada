INSERT INTO conf.ims_incident_type_task_values (task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at) VALUES
--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 001603010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001603010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001603010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001703010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001703010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001703010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001803010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001803010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001803010401, '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 001606010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001606010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001606010401, 'El estado actual de la circulación es @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001706010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001706010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001706010401, 'El estado actual de la circulación es @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001806010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001806010401, '-1002691923172', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001806010401, 'El estado actual de la circulación es @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--SendToTelegramNotification tiene tres parámetros:
--1 = TOKEN BOT que usamos para la notifiación
--2 = CHAT_ID del canal donde vamos a mandar la incidencia
--3 = MESSAGE_TEXT Definición del texto a enviar en la notificación
(10, 1, 001608010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001608010401, '@TraficoNavarra', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001608010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001708010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001708010401, '@TraficoNavarra', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001708010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 1, 001808010401, '7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 001808010401, '@TraficoNavarra', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 001808010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

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
(8, 1, 001607010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001607010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001607010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 001707010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001707010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001707010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 1, 001807010401, 'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 001807010401, 'Estado de circulación accidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		
(8, 5, 001807010401, 'Accidente en @localizacion con estado de la circulación @afeccion', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




