
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E2-FUE-01
(010144, 44, 1, 6, '2 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - ', false, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020144, 44, 1, 1, '3 - S07b - SEÑALIZACIÓN', 'Precaución en túnel', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030144, 44, 1, 7, '1 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040144, 44, 1, 7, 'DESHABILITAR DAI CA', 'Deshabilitar alarmas del DAI sentido Cadíz', null, false, 4, false, 'DeactiveAlarmsDaiCA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050144, 44, 1, 7, 'DESHABILITAR DAI MA', 'Deshabilitar alarmas del DAI sentido Málaga', null, false, 5, false, 'DeactiveAlarmsDaiMA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010244, 44, 2, 1, '5 - S17 - SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020244, 44, 2, 7, '6 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030244, 44, 2, 6, '4 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - ', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050244, 44, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 4, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011144, 44, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


