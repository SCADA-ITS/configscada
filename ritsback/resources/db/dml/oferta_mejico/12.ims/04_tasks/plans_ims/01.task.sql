
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES

-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-MET-01

(010121, 201, null, 7, 'CCTV', 'Visualización de cámara en boca', 'La visualización de las imágenes de la cámara de boca sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060121, 201, null, 6, 'RELLENAR BITACORA DEL INCIDENTE', 'Rellenar bitacora del incidente con toda la información disponible', null, true, 2, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(030121, 201, 1, 1, 'SEÑALIZACIÓN', 'Condiciones meteorológicas adversas', 'Adopción de medidas de precaución en la cercanía de las bocas del túnel como consecuencia de condiciones climáticas en el exterior que pueden suponer un cambio en las condiciones de conducción', true, 3, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040121, 201, 1, 6, 'AVISO', 'Equipo de primera intervención - Jefe de Seguridad', null, false, 4, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(050121, 201, 2, 6, 'AVISO', 'Aviso a emergencias si es requerido por la situación', null, true, 1, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(020221, 201, 3, 1, 'SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 2, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



