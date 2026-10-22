
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, stretch_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-AMB-01
(0101221008, 22, 1, 1008, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0201221008, 22, 1, 1008, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0301221008, 22, 1, 1008, 6, '3 - AVISO', 'Equipo de primera intervención', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0401221008, 22, 1, 1008, 5, '4 - V07 - VENTILACIÓN', 'Comprobación del funcionamiento del sistema de ventilación', 'Comprobación de si el sistema de ventilación funciona correctamente', false, 4, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0601221008, 22, 1, 1008, 6, '6 - AVISO', 'Jefe de seguridad - Guardia Civil de Trafico - Equipo primera intervención - CECEM-112', null, false, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(0701221008, 22, 1, 1008, 1, '7 - EV01 - PLAN DE EVACUACIÓN', 'Activar plan de evacuación', null, false, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(0801221008, 22, 1, 1008, 1, '8 - EV02 - PLAN DE EVACUACIÓN', 'Desactivar plan de evacuación', null, false, 8, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
(0701221008, 22, 1, 1008, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 7, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 0201221008, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 0201221008, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, stretch_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-AMB-01
(0101221009, 22, 1, 1009, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0201221009, 22, 1, 1009, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0301221009, 22, 1, 1009, 6, '3 - AVISO', 'Equipo de primera intervención', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0401221009, 22, 1, 1009, 5, '4 - V07 - VENTILACIÓN', 'Comprobación del funcionamiento del sistema de ventilación', 'Comprobación de si el sistema de ventilación funciona correctamente', false, 4, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0601221009, 22, 1, 1009, 6, '6 - AVISO', 'Jefe de seguridad - Guardia Civil de Trafico - Equipo primera intervención - CECEM-112', null, false, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(0701221009, 22, 1, 1009, 1, '7 - EV01 - PLAN DE EVACUACIÓN', 'Activar plan de evacuación', null, false, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(0801221009, 22, 1, 1009, 1, '8 - EV02 - PLAN DE EVACUACIÓN', 'Desactivar plan de evacuación', null, false, 8, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
(0701221009, 22, 1, 1009, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 7, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 0201221009, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 0201221009, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);