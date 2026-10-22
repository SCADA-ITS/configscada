
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES

-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-MET-01

(010121, 21, 1, 7, '1 - C04 - CCTV', 'Visualización de cámara en boca', 'La visualización de las imágenes de la cámara de boca sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020121, 21, 1, 2, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTunnelLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121, 21, 1, 1, '3 - S05 - SEÑALIZACIÓN', 'Condiciones meteorológicas adversas', 'Adopción de medidas de precaución en la cercanía de las bocas del túnel como consecuencia de condiciones climáticas en el exterior que pueden suponer un cambio en las condiciones de conducción', true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040121, 21, 1, 6, '4 - AVISO', 'Equipo de primera intervención - Jefe de Seguridad', null, false, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050121, 21, 1, 7, 'DESHABILITAR DAI CA', 'Deshabilitar alarmas del DAI sentido Cadíz', null, false, 5, false, 'DeactiveAlarmsDaiCA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060121, 21, 1, 7, 'DESHABILITAR DAI MA', 'Deshabilitar alarmas del DAI sentido Málaga', null, false, 6, false, 'DeactiveAlarmsDaiMA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010221, 21, 2, 6, '5 - AVISO', 'Equipo de primera intervención - Jefe de Seguridad', null, false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221, 21, 2, 1, '6 - S17 - SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030221, 21, 2, 2, '7 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 3, false, 'TaskTypeTunnelLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040221, 21, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 4, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011121, 21, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(2, 1, 020121, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 020121, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 030221, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
