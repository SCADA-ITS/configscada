
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- P-AMB-02
(010109, 9, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020109, 9, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030109, 9, 1, 5, '3 - V01 - VENTILACIÓN', 'Poner la ventilación en modo servicio', 'Cambio del modo de operación de la ventilación a servicio.', true, 3, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040109, 9, 1, 1, '4 - S11 - SEÑALIZACIÓN', 'Retencion en túnel', null, false, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050109, 9, 1, 1, '5 - S08 - SEÑALIZACIÓN', 'Visibilidad reducida en túnel', null, true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060109, 9, 1, 5, '6 - V05 - VENTILACIÓN', 'Poner la ventilación en modo manual', 'Cambio del modo de operación de la ventilación a manual.', false, 6, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070109, 9, 1, 6, '7 - AVISO', 'Apertura orden de trabajo', 'Apertura orden de trabajo.', false, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080109, 9, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 8, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010209, 9, 2, 3, '8 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 1, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020209, 9, 2, 1, '9 - S17 - SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030209, 9, 2, 5, '10 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 3, false,  'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040209, 9, 2, 7, '11 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', false, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050209, 9, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 4, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011109, 9, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES


(3, 1, 020109, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020109, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 030109, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 060109, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 010209, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 030209, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 030209, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);









-- P-TRA-04 IZQ:
--(1301, 130, 1, 2, 'ILUMINACIÓN - I01', 'Reforzar iluminación nocturna', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1302, 130, 2, 1, 'SEÑALIZACIÓN', 'Ejecutar planes de señalizacion arcen izquierdo', true, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1303, 130, 3, 5, 'SEÑALIZACIÓN', 'Ejecutar plan automatico de restitución', true, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- P-TRA-04 DECH:
--(1311, 131, 1, 2, 'ILUMINACIÓN - I01', 'Reforzar iluminación nocturna', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1312, 131, 1, 4, 'AVISO 1A INTERVENCION', 'Aviso Equipo Primera Intervencion', true, 2, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1313, 131, 1, 1, 'SEÑALIZACIÓN - S22', 'Ejecutar planes de señalizacion arcen derecho', true, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1314, 131, 1, 1, 'MEGAFONIA - M05', 'Obstaculo en Calzada', true, 4, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1315, 131, 2, 4, 'ALERTA JEFE SEGURIDAD', 'Alerta de Maquinaria Requerida. Jefe de Seguridad', false, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1316, 131, 2, 4, 'ALERTA ENCARG MANTENIMIENTO', 'Alerta de Maquinaria Requerida. Encargado de Mantenimiento', false, 2, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1317, 131, 2, 4, 'ORDEN DE TRABAJO', 'Alerta de Maquinaria Requerida. Apertura de orden de trabajo', false, 3, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1318, 131, 2, 1, 'OBJETO SOSPECHOSO', 'Objeto Sospechoso. E2-ILI-01', false, 4, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1319, 131, 3, 4, 'AVISO RESTIT 1A INTERVENCION', 'Aviso Restitucion. Equipo 1º Intervencion.', false, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1320, 131, 3, 4, 'AVISO RESTIT JEFE SEGURIDAD', 'Aviso Restitucion. Jefe de Seguridad', false, 2, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1321, 131, 3, 1, 'MEGAFONIA - M017', 'Interrumpir Mensajes', true, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1322, 131, 3, 2, 'ILUMINACIÓN - I03', 'Iluminacion modo automatico', true, 4, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1323, 131, 3, 1, 'SEÑALIZACIÓN - S17', 'Ejecutar plan automatico de restitución', true, 5, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--
---- P-TRA-05
--(1307, 14, 1, 2, 'ILUMINACIÓN - I01', 'Reforzar iluminación nocturna', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1308, 14, 2, 1, 'SEÑALIZACIÓN', 'Ejecutar planes de señalizacion arcen', true, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
----(1307, 14, 2, 1, 'SEÑALIZACIÓN', 'Ejecutar planes de señalizacion arcen derecho', true, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1309, 14, 3, 1, 'SEÑALIZACIÓN', 'Ejecutar plan automatico de restitución', true, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--
---- E2-FUE-03
--(461, 46, 1, 3, 'VENTILACIÓN - V02', 'Detener Ventilacion', true, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(462, 46, 1, 4, 'AVISO 1A INTERVENCION', 'Aviso Equipo 1º Intervencion', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(463, 46, 1, 4, 'AVISO GUARDIA CIVIL', 'Aviso a Guardia Civil', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(464, 46, 1, 4, 'AVISO JEFE EMERGENCIA', 'Aviso a Jefe de Emergencia', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(465, 46, 1, 4, 'AVISO JEFE SEGURIDAD', 'Aviso a Jefe de Seguridad', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(466, 46, 1, 4, 'AVISO CECEM-112', 'Aviso Centro Emergencias 112', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(467, 46, 2, 2, 'ILUMINACIÓN - I01', 'Reforzar iluminación nocturna', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(468, 46, 2, 1, 'SEÑALIZACIÓN - S07', 'Ejecutar planes de señalizacion. Precaución en tunel', true, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(469, 46, 4, 4, 'EVACUACION USUARIOS', 'Comprobación Evacuación Usuarios Aguas Abajo', true, 3, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(470, 46, 4, 3, 'VENTILACION - V03a', 'Ventilacion Modo Evacuacion', true, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(471, 46, 4, 4, 'ACTIVAR PLAN EVACUACION', 'Activar Plan de Evacuacion', true, 3, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(472, 46, 5, 3, 'VENTILACION - V03b', 'Ventilacion Modo Extraccion', true, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(473, 46, 3, 4, 'AVISO RESTIT 1A INTERVENCION', 'Aviso Restitucion. Equipo 1º Intervencion.', true, 1, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(474, 46, 3, 4, 'AVISO RESTIT GUARDIA CIVIL', 'Aviso Restitucion. Guardia Civil', true, 2, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(475, 46, 3, 4, 'AVISO RESTIT JEFE EMERGENCIA', 'Aviso Restitucion. Jefe de Emergencia', true, 2, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(476, 46, 3, 4, 'AVISO RESTIT JEFE SEGURIDAD', 'Aviso Restitucion. Jefe de Seguridad', true, 2, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(477, 46, 3, 4, 'AVISO RESTIT CECEM-112', 'Aviso Restitucion. Centro Control de Emergencias 112', true, 2, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(478, 46, 3, 2, 'ILUMINACIÓN - I03', 'Iluminacion modo automatico', true, 4, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(479, 46, 3, 1, 'SEÑALIZACIÓN - S17', 'Ejecutar plan automatico de restitución', true, 5, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(480, 46, 3, 3, 'VENTILACIÓN - V01', 'Ventilacion en Servicio', true, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(481, 46, 3, 4, 'DESACTIVAR PLAN EVACUACION', 'Desactivar Plan de Evacuacion', true, 3, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

