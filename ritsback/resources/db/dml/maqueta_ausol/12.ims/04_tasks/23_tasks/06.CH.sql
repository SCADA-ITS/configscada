
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, stretch_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-AMB-02
(0101231032, 23, 1, 1032, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0201231032, 23, 1, 1032, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0301231032, 23, 1, 1032, 6, '3 - AVISO', 'Equipo de primera intervención', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0401231032, 23, 1, 1032, 5, '4 - V07 - VENTILACIÓN', 'Comprobación del funcionamiento del sistema de ventilacion', 'Comprobación de si el sistema de ventilación funciona correctamente', false, 4, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0601231032, 23, 1, 1032, 1, '6 - S08 - SEÑALIZACIÓN', 'Visibilidad reducida en el túnel', null, true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0701231032, 23, 1, 1032, 6, '7 - AVISO', 'Jefe de seguridad - Jefe de Explotación - Guardia Civil de Trafico - Equipo primera intervención - CECEM-112', null, true, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801231032, 23, 1, 1032, 1, '8 - S01 - SEÑALIZACIÓN', 'Cierre de túnel', null, true, 8, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0901231032, 23, 1, 1032, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 9, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 0201231032, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 0201231032, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, stretch_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-AMB-02
(0101231033, 23, 1, 1033, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0201231033, 23, 1, 1033, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0301231033, 23, 1, 1033, 6, '3 - AVISO', 'Equipo de primera intervención', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0401231033, 23, 1, 1033, 5, '4 - V07 - VENTILACIÓN', 'Comprobación del funcionamiento del sistema de ventilacion', 'Comprobación de si el sistema de ventilación funciona correctamente', false, 4, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0601231033, 23, 1, 1033, 1, '6 - S08 - SEÑALIZACIÓN', 'Visibilidad reducida en el túnel', null, true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0701231033, 23, 1, 1033, 6, '7 - AVISO', 'Jefe de seguridad - Jefe de Explotación - Guardia Civil de Trafico - Equipo primera intervención - CECEM-112', null, true, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801231033, 23, 1, 1033, 1, '8 - S01 - SEÑALIZACIÓN', 'Cierre de túnel', null, true, 8, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0901231033, 23, 1, 1033, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 9, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 0201231033, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 0201231033, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);