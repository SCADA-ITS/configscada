
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E2-AVE-01
(010140, 40, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020140, 40, 1, 2, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTunnelLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030140, 40, 1, 6, '3 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040140, 40, 1, 1, '4 - S01 - SEÑALIZACIÓN', 'Cierre de túnel.', null, true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050140, 40, 1, 7, 'DESHABILITAR DAI CA', 'Deshabilitar alarmas del DAI sentido Cadíz', null, false, 7, false, 'DeactiveAlarmsDaiCA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060140, 40, 1, 7, 'DESHABILITAR DAI MA', 'Deshabilitar alarmas del DAI sentido Málaga', null, false, 8, false, 'DeactiveAlarmsDaiMA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010240, 40, 2, 7, '5 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020240, 40, 2, 2, '6 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 2, false, 'TaskTypeTunnelLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030240, 40, 2, 1, '7 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040240, 40, 2, 7, '8 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050240, 40, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 5, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011140, 40, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(2, 1, 020140, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 020140, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 020240, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
