
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
(030201, 201, null, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030221, 201, null, 6, 'RELLENAR BITACORA DEL INCIDENTE', 'Rellenar bitacora del incidente con toda la información disponible', null, true, 2, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(060201, 201, 1, 1, 'SEÑALIZACIÓN', 'Lluvia intensa', null, true, 1, true, 'SendSignallingToStretch', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050221, 201, 1, 6, 'AVISO', 'Equipo de primera intervención - Jefe de Seguridad', null, false, 2, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(080221, 201, 2, 6, 'AVISO', 'Aviso a emergencias si es requerido por la situación', null, true, 1, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(020221, 201, 3, 1, 'SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 2, true, 'SendSignallingToStretch', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--(030221, 201, 3, 2, 'ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 3, false, 'TaskTypeTunnelLighting/SetIlluminationTask', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



