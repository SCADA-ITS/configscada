-- 
-- Table: conf.ims_incident_type_tasks
--

-- Tareas sobrescritas
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, stretch_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para sobrescribir la tarea incluyo al inicio del id el <stetch_id>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(001401010401, 22, 401, 1, 6, 'INFORMAR A ENTIDAD RESPONSABLE', 'Informar a Autopista Vasco-Aragonesa', 'Llamar al teléfono 2222-222-22 para informar de que existe una incidencia la AP-68', false, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(001501010401, 23, 401, 1, 6, 'INFORMAR A ENTIDAD RESPONSABLE', 'Informar a Autopista Vasco-Aragonesa', 'Llamar al teléfono 2222-222-22 para informar de que existe una incidencia la AP-68', false, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(001402010401, 22, 401, 1, 6, 'RECABAR INFORMACIÓN', 'Llamar a CMC/COTA para informarse', 'Realizar una llamada telefónica a CMC/COTA para recabar la información completa de lo ocurrido en el accidente', false, 2, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(001502010401, 23, 401, 1, 6, 'RECABAR INFORMACIÓN', 'Llamar a CMC/COTA para informarse', 'Realizar una llamada telefónica a CMC/COTA para recabar la información completa de lo ocurrido en el accidente', false, 2, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(001403010401, 22, 401, 1, 10, 'TELEGRAM HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante Telegram de manera interna la información de los heridos graves y los fallecidos', false, 3, false, 'SendToTelegramNotificationPrivate', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(001503010401, 23, 401, 1, 10, 'TELEGRAM HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante Telegram de manera interna la información de los heridos graves y los fallecidos', false, 3, false, 'SendToTelegramNotificationPrivate', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(001404010401, 22, 401, 1, 8, 'CORREO HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante correo electrónico de manera interna la información de los heridos graves y los fallecidos', false, 4, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(001504010401, 23, 401, 1, 8, 'CORREO HERIDOS/FALLECIDOS', 'Envio de información interna de heridos y fallecidos', 'Enviar mediante correo electrónico de manera interna la información de los heridos graves y los fallecidos', false, 4, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(001405010401, 22, 401, 1, 6, 'SELECCIONAR AFECCIÓN', 'Indicar estado de la circulación', 'Seleccionar el estado de la circulación del tráfico indicándolo a través de la afección de la incidencia', false, 5, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(001505010401, 23, 401, 1, 6, 'SELECCIONAR AFECCIÓN', 'Indicar estado de la circulación', 'Seleccionar el estado de la circulación del tráfico indicándolo a través de la afección de la incidencia', false, 5, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(001406010401, 22, 401, 1, 10, 'TELEGRAM ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante Telegram de manera interna la información del estado de la circulación', false, 6, false, 'SendToTelegramNotificationPrivate', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(001506010401, 23, 401, 1, 10, 'TELEGRAM ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante Telegram de manera interna la información del estado de la circulación', false, 6, false, 'SendToTelegramNotificationPrivate', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(001407010401, 22, 401, 1, 8, 'CORREO ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante correo electrónico de manera interna la información del estado de la circulación', false, 7, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(001507010401, 23, 401, 1, 8, 'CORREO ESTADO CIRCULACIÓN', 'Envio de información interna del estado de circulación', 'Enviar mediante correo electrónico de manera interna la información del estado de la circulación', false, 7, false, 'SendMailTask', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(001408010401, 22, 401, 1, 10, 'INFORMACIÓN AL CIUDADANO', 'Publicar Telegram del accidente', 'Publicar en el canal público del Gobierno de Navarra la información al ciudadano del estado del accidente', false, 8, false, 'SendToTelegramNotificationPublic', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(001508010401, 23, 401, 1, 10, 'INFORMACIÓN AL CIUDADANO', 'Publicar Telegram del accidente', 'Publicar en el canal público del Gobierno de Navarra la información al ciudadano del estado del accidente', false, 8, false, 'SendToTelegramNotificationPublic', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
