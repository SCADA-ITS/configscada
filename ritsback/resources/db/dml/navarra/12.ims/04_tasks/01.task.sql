
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
(040121, 101, null, 7, 'CCTV', 'Visualización de cámara', 'La visualización de las imágenes de la cámara con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, 'SendWarning', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010101, 101, 1, 6, '1 - AVISO AUTORIDADES', 'Perdir permiso a las autoridades', 'Solicitar el permiso a la autoridad competente para el corte de carril de la carreta que permita realizar los trabajos pertinentes.', true, 2, false, 'SendWarningAC', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020101, 101, 1, 6, '2 - AVISO MANTENIMIENTO', 'Avisar a personal de mantenimiento', 'Indicar al personal local de mantenimiento que pueden comenzar a realizar los trabajos estipulados', false, 32, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030101, 101, 1, 1, '3 - SEÑALIZACION', 'Indiación a través de PMV', 'Señalizar el corte de carril en el PMV inmediatamente al incidente para avisar a los conductores.', true, 4, true, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(040201, 201, null, 7, 'CCTV', 'Visualización de cámara', 'La visualización de las imágenes de la cámara con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, 'SendWarning', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010201, 201, 1, 6, '1 - AVISO AUTORIDADES', 'Perdir permiso a las autoridades', 'Solicitar el permiso a la autoridad competente para el corte de carril de la carreta que permita realizar los trabajos pertinentes.', true, 2, false, 'SendWarningVD', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020201, 201, 1, 6, '2 - AVISO MANTENIMIENTO', 'Avisar a personal de mantenimiento', 'Indicar al personal local de mantenimiento que pueden comenzar a realizar los trabajos estipulados', false, 3, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030201, 201, 1, 1, '3 - SEÑALIZACION', 'Indiación a través de PMV', 'Señalizar el corte de carril en el PMV inmediatamente al incidente para avisar a los conductores.', true, 4, true, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(040301, 301, null, 7, 'CCTV', 'Visualización de cámara', 'La visualización de las imágenes de la cámara con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, 'SendWarning', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010301, 301, 1, 6, '1 - AVISO AUTORIDADES', 'Perdir permiso a las autoridades', 'Solicitar el permiso a la autoridad competente para el corte de carril de la carreta que permita realizar los trabajos pertinentes.', true, 2, false, 'SendWarningPD', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020301, 301, 1, 6, '2 - AVISO MANTENIMIENTO', 'Avisar a personal de mantenimiento', 'Indicar al personal local de mantenimiento que pueden comenzar a realizar los trabajos estipulados', false, 3, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030301, 301, 1, 1, '3 - SEÑALIZACION', 'Indiación a través de PMV', 'Señalizar el corte de carril en el PMV inmediatamente al incidente para avisar a los conductores.', true, 4, true, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(040401, 401, null, 7, 'CCTV', 'Visualización de cámara', 'La visualización de las imágenes de la cámara con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, 'SendWarning', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010401, 401, 1, 6, '1 - AVISO AUTORIDADES', 'Perdir permiso a las autoridades', 'Solicitar el permiso a la autoridad competente para el corte de carril de la carreta que permita realizar los trabajos pertinentes.', true, 2, false, 'SendWarningO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020401, 401, 1, 6, '2 - AVISO MANTENIMIENTO', 'Avisar a personal de mantenimiento', 'Indicar al personal local de mantenimiento que pueden comenzar a realizar los trabajos estipulados', false, 3, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030401, 401, 1, 1, '3 - SEÑALIZACION', 'Indiación a través de PMV', 'Señalizar el corte de carril en el PMV inmediatamente al incidente para avisar a los conductores.', true, 4, true, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(040501, 501, null, 7, 'CCTV', 'Visualización de cámara', 'La visualización de las imágenes de la cámara con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, 'SendWarning', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(010501, 501, 1, 6, '1 - AVISO AUTORIDADES', 'Perdir permiso a las autoridades', 'Solicitar el permiso a la autoridad competente para el corte de carril de la carreta que permita realizar los trabajos pertinentes.', true, 2, false, 'SendWarningVL', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020501, 501, 1, 6, '2 - AVISO MANTENIMIENTO', 'Avisar a personal de mantenimiento', 'Indicar al personal local de mantenimiento que pueden comenzar a realizar los trabajos estipulados', false, 3, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030501, 501, 1, 1, '3 - SEÑALIZACION', 'Indiación a través de PMV', 'Señalizar el corte de carril en el PMV inmediatamente al incidente para avisar a los conductores.', true, 4, true, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

