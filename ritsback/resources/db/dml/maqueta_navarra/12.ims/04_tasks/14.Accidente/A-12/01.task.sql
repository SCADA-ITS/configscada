-- 
-- Table: conf.ims_incident_type_tasks
--

-- Tareas sobrescritas
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, stretch_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para sobrescribir la tarea incluyo al inicio del id el <stetch_id>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(000601010401, 6, 401, 1, 6, 'INFORMAR A ENTIDAD RESPONSABLE', 'Informar a Autovía Camino', 'Llamar al teléfono 4444-444-44 para informar de que existe una incidencia la A-12', false, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000701010401, 7, 401, 1, 6, 'INFORMAR A ENTIDAD RESPONSABLE', 'Informar a Autovía Camino', 'Llamar al teléfono 4444-444-44 para informar de que existe una incidencia la A-12', false, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000801010401, 8, 401, 1, 6, 'INFORMAR A ENTIDAD RESPONSABLE', 'Informar a Autovía Camino', 'Llamar al teléfono 4444-444-44 para informar de que existe una incidencia la A-12', false, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(000602010401, 6, 401, 1, 6, 'RECABAR INFORMACIÓN', 'Llamar a CMC/COTA para informarse', 'Realizar una llamada telefónica a CMC/COTA para recabar la información completa de lo ocurrido en el accidente', false, 2, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000702010401, 7, 401, 1, 6, 'RECABAR INFORMACIÓN', 'Llamar a CMC/COTA para informarse', 'Realizar una llamada telefónica a CMC/COTA para recabar la información completa de lo ocurrido en el accidente', false, 2, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000802010401, 8, 401, 1, 6, 'RECABAR INFORMACIÓN', 'Llamar a CMC/COTA para informarse', 'Realizar una llamada telefónica a CMC/COTA para recabar la información completa de lo ocurrido en el accidente', false, 2, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(000603010401, 6, 401, 1, 10, 'TELEGRAM HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante Telegram de manera interna la información de los heridos graves y los fallecidos', false, 3, false, 'SendToTelegramNotification', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000703010401, 7, 401, 1, 10, 'TELEGRAM HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante Telegram de manera interna la información de los heridos graves y los fallecidos', false, 3, false, 'SendToTelegramNotification', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000803010401, 8, 401, 1, 10, 'TELEGRAM HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante Telegram de manera interna la información de los heridos graves y los fallecidos', false, 3, false, 'SendToTelegramNotification', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(000604010401, 6, 401, 1, 8, 'CORREO HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante correo electrónico de manera interna la información de los heridos graves y los fallecidos', false, 4, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000704010401, 7, 401, 1, 8, 'CORREO HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante correo electrónico de manera interna la información de los heridos graves y los fallecidos', false, 4, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000804010401, 8, 401, 1, 8, 'CORREO HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante correo electrónico de manera interna la información de los heridos graves y los fallecidos', false, 4, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(000605010401, 6, 401, 1, 6, 'SELECCIONAR AFECCIÓN', 'Indicar estado de la circulación', 'Seleccionar el estado de la circulación del tráfico indicándolo a través de la afección de la incidencia', false, 5, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000705010401, 7, 401, 1, 6, 'SELECCIONAR AFECCIÓN', 'Indicar estado de la circulación', 'Seleccionar el estado de la circulación del tráfico indicándolo a través de la afección de la incidencia', false, 5, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000805010401, 8, 401, 1, 6, 'SELECCIONAR AFECCIÓN', 'Indicar estado de la circulación', 'Seleccionar el estado de la circulación del tráfico indicándolo a través de la afección de la incidencia', false, 5, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(000606010401, 6, 401, 1, 10, 'TELEGRAM ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante Telegram de manera interna la información del estado de la circulación', false, 6, false, 'SendToTelegramNotification', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000706010401, 7, 401, 1, 10, 'TELEGRAM ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante Telegram de manera interna la información del estado de la circulación', false, 6, false, 'SendToTelegramNotification', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000806010401, 8, 401, 1, 10, 'TELEGRAM ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante Telegram de manera interna la información del estado de la circulación', false, 6, false, 'SendToTelegramNotification', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(000607010401, 6, 401, 1, 8, 'CORREO ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante correo electrónico de manera interna la información del estado de la circulación', false, 7, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000707010401, 7, 401, 1, 8, 'CORREO ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante correo electrónico de manera interna la información del estado de la circulación', false, 7, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000807010401, 8, 401, 1, 8, 'CORREO ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante correo electrónico de manera interna la información del estado de la circulación', false, 7, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(000608010401, 6, 401, 1, 10, 'INFORMACIÓN AL CIUDADANO', 'Publicar Telegram del accidente', 'Publicar en el canal público del Gobierno de Navarra la información al ciudadano del estado del accidente', false, 8, false, 'SendToTelegramNotification', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000708010401, 7, 401, 1, 10, 'INFORMACIÓN AL CIUDADANO', 'Publicar Telegram del accidente', 'Publicar en el canal público del Gobierno de Navarra la información al ciudadano del estado del accidente', false, 8, false, 'SendToTelegramNotification', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(000808010401, 8, 401, 1, 10, 'INFORMACIÓN AL CIUDADANO', 'Publicar Telegram del accidente', 'Publicar en el canal público del Gobierno de Navarra la información al ciudadano del estado del accidente', false, 8, false, 'SendToTelegramNotification', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
