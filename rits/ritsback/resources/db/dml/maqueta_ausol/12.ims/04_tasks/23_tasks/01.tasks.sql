
-- vv
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-AMB-02
(010123, 23, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020123, 23, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030123, 23, 1, 6, '3 - AVISO', 'Equipo de primera intervención', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040123, 23, 1, 5, '4 - V07 - VENTILACIÓN', 'Comprobación del funcionamiento del sistema de ventilacion', 'Comprobación de si el sistema de ventilación funciona correctamente', false, 4, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050123, 23, 1, 1, '5 - S20 - SEÑALIZACIÓN', 'Retención en el interior del túnel', null, false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060123, 23, 1, 1, '6 - S08 - SEÑALIZACIÓN', 'Visibilidad reducida en el túnel', null, true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070123, 23, 1, 6, '7 - AVISO OBLIGATORIO', 'Jefe de seguridad - Jefe de Explotación - Guardia Civil de Trafico - Equipo primera intervención - CECEM-112', null, true, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080123, 23, 1, 1, '8 - S01 - SEÑALIZACIÓN', 'Cierre de túnel', null, true, 8, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090123, 23, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 9, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010223, 23, 2, 6, '9 - AVISO', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', null, false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020223, 23, 2, 3, '10 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030223, 23, 2, 5, '11 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 3, false,  'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040223, 23, 2, 1, '12 - S18 - SEÑALIZACIÓN', 'Retirar señalización de cierre de tunel', 'Retirar la señalización de cierre de accesos al túnel', true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050223, 23, 2, 7, '13 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060223, 23, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 6, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011123, 23, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 020123, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020123, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 020223, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 030223, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 030223, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
