DELETE FROM master.ims_incident_groups;

-- 
-- Table: conf.ims_incident_types
--
INSERT INTO master.ims_incident_groups (incident_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'Accidente','Accidente','LBL_IMS_INCIDENT_GROUP_TYPE_ACCIDENTE', 'LBL_IMS_INCIDENT_GROUP_TYPE_ACCIDENTE_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'Cortes de ruta','Cortes de ruta','LBL_IMS_INCIDENT_GROUP_TYPE_CORTES_RUTA', 'LBL_IMS_INCIDENT_GROUP_TYPE_CORTES_RUTA_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(3,'Atochamiento de tráfico','Atochamiento de tráfico','LBL_IMS_INCIDENT_GROUP_TYPE_ATOCHAMIENTO_TRAFICO', 'LBL_IMS_INCIDENT_GROUP_TYPE_ATOCHAMIENTO_TRAFICO_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(4,'Vehículo denetido','Vehículo denetido','LBL_IMS_INCIDENT_GROUP_TYPE_VEHICULO_DETENIDO', 'LBL_IMS_INCIDENT_GROUP_TYPE_VEHICULO_DETENIDO_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(5,'Elementos no motorizados en la calzada','Elementos no motorizados en la calzada','LBL_IMS_INCIDENT_GROUP_TYPE_ELEMENTOS_NO_MOTORIZADOS_CALZADA', 'LBL_IMS_INCIDENT_GROUP_TYPE_ELEMENTOS_NO_MOTORIZADOS_CALZADA_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(6,'Incidentes','Incidentes','LBL_IMS_INCIDENT_GROUP_TYPE_INCIDENTES', 'LBL_IMS_INCIDENT_GROUP_TYPE_INCIDENTES_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

INSERT INTO conf.ims_incident_types (incident_type_id, incident_group_id, ini_incident_state_id, alias, description, incident_level_id, default_user_id, auto_confirm_incident, cancel_incident, valid_for_min, on_create_execute, enabled, visible, created_at, updated_at) VALUES
(0101, 1, 1, 'A01', 'Alto impacto público con víctimas fatales', 3, 1, 300, false, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0102, 1, 1, 'A02', 'Alto impacto público sin víctimas fatales', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0103, 1, 1, 'A03', 'Sustancias peligrosas que implican cierre de la ruta', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0104, 1, 1, 'A04', 'Alto impacto no público con víctimas fatales', 2, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0105, 1, 1, 'A05', 'Alto impacto no público sin víctimas fatales', 2, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0106, 1, 1, 'A06', 'Menores con o sin lesionados', 1, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(0201, 2, 1, 'B01', 'Por reclamo social contra la concesión', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0202, 2, 1, 'B02', 'Por reclamo social ajeno a la concesión', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0203, 2, 1, 'B03', 'Por razones meteorológicas', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0204, 2, 1, 'B04', 'Por deficiencias estructurales', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0205, 2, 1, 'B05', 'Por indencio en área de concesión', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0206, 2, 1, 'B06', 'Por incendio fuera del área de concesión', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0207, 2, 1, 'B07', 'Por catástrofes naturales', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0208, 2, 1, 'B08', 'Por cualquier otro evento', 2, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(0301, 3, 1, 'C01', 'Por causas externas a la concesionaria', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0302, 3, 1, 'C02', 'Por actividades de construcción o mantenimiento', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0303, 3, 1, 'C03', 'Por otras causas', 2, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(0401, 4, 1, 'D01', 'Por emergencia médica en la calzada', 2, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0402, 4, 1, 'D02', 'Con desperfecto mecánico sobre la calzada', 2, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0403, 4, 1, 'D03', 'Por emergencia médica fuera de la calzada', 1, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0404, 4, 1, 'D04', 'Con desperfecto mecánico fuera de la calzada', 1, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(0501, 5, 1, 'E01', 'Cargas que requieran maquinaria para su retiro', 2, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0502, 5, 1, 'E02', 'Peatones, ciclistas, animales, vehículos de tracción a sangre en la vía expresa', 1, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0503, 5, 1, 'E03', 'Material sobre la calzada (para retiro manual)', 1, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(0601, 6, 1, 'F01', 'Comisión de actos delictivos a usuarios de la autopista', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0602, 6, 1, 'F02', 'Acciones judiciales que afecten a la operatividad plena de la autopista', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0603, 6, 1, 'F03', 'Cortes de energía eléctrica', 3, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0604, 6, 1, 'F04', 'Detección de comercio ilegal en el área de la concesión', 1, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0605, 6, 1, 'F05', 'Accesos a predio, irregulares o no autorizados', 1, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0606, 6, 1, 'F06', 'Trabajos de terceros no autorizados en el área de concesión', 1, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0607, 6, 1, 'F07', 'Trabajos autorizados en el área de concesión', 1, 1, 300, true, 2880, 'BackOfficeInitIncident.groovy' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


