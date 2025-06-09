
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- corte carril
(010101, 301, null, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, 'LockCamera', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090121, 301, null, 6, 'RELLENAR BITACORA DEL INCIDENTE', 'Rellenar bitacora del incidente con toda la información disponible', null, true, 2, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020101, 301, 1, 5, 'VENTILACIÓN', 'Ventilación modo Mantenimiento', 'Ventilación modo Mantenimiento', true, 1, true, 'TaskTypeVentilation/SetVentilationTubeTask', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030101, 301, 1, 3, 'ILUMINACIÓN', 'Reforzar iluminación en túnel 100% soleado', 'Encendido de todos los circuitos de alumbrado del túnel con objeto de mejorar las condiciones de visibilidad por existencia de humos en caso de fuego.', true, 3, true, 'TaskTypeTubeLighting/SetIlluminationTask', null ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--carril derecho
(050101, 301, 1, 1, 'SEÑALIZACIÓN', 'Corte de carril derecho en túnel por obras', null, true, 5, false, 'SendSignallingToStretch', 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--carril izq
(080101, 301, 1, 1, 'SEÑALIZACIÓN', 'Corte de carril izquierdo en túnel por obras', null, true, 5, false, 'SendSignallingToStretch', 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--restitucion
(020131, 301, 3, 5, 'VENTILACIÓN', 'Ventilación modo Mantenimiento', 'Ventilación modo Mantenimiento', true, 1, true, 'TaskTypeVentilation/SetVentilationTubeTask', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030131, 301, 3, 3, 'ILUMINACIÓN', 'Reforzar iluminación en túnel 100% soleado', 'Encendido de todos los circuitos de alumbrado del túnel con objeto de mejorar las condiciones de visibilidad por existencia de humos en caso de fuego.', true, 3, true, 'TaskTypeTubeLighting/SetIlluminationTask', null ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--restutucion carril derecho
(010201, 301, 3, 1, 'SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 1, false, 'SendSignallingToStretch', 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--restitucion carril izq
(050201, 301, 3, 1, 'SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 1, false, 'SendSignallingToStretch', 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


