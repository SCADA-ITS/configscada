DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(1, 'backoffice', NULL, NULL, 'jdbc:postgresql://192.168.88.81:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'backoffice',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 850,
    	"formWidth": 1100,
    	"tableHeight": 700,
    	"tableWidth": 900,
    	"showIdForm": false,
    	"showIdTable": false,
    	"showIdSearch": false,
    	"gridFilters": true
	}',
	-- default_metadata_column
	'{
	    "required": false,
	    "editable": true,
	    "refAddButton": false
	}',
	true);
	
	-- 
  	-- smartgen.sg_metadata_tables
  	--
	INSERT INTO backoffice.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata, sql_view) VALUES
	(1, 'incidents', 'Incidencias', 'Incidencias', 'LBL_INCIDENTS_DESCRIPTION', 'mdi mdi-message-alert', true, true, 
	'{
		  "tabs": [
		    {
		      "label": "LBL_SECTION_ACCUSATION",
		      "position": 2,
		      "fields": [
		        {
		          "name": "report_by",
		          "position": 1
		        },
		        {
		          "name": "stamped",
		          "position": 2
		        },
		        {
		          "name": "court",
		          "position": 3
		        },
		        {
		          "name": "citattion_date",
		          "position": 4
		        },
		        {
		          "name": "report_number",
		          "position": 5
		        },
		        {
		          "name": "station",
		          "position": 6
		        },
		        {
		          "name": "file_number",
		          "position": 7
		        },
		        {
		          "name": "patrol_name",
		          "position": 8
		        },
		        {
		          "name": "operator_name",
		          "position": 9
		        },
		        {
		          "name": "supervisor_name",
		          "position": 10
		        }
		      ]
			}
		  ],
		  "groupImages": [
		      	{
			      "name": "photos",
			      "label": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS",
			      "description": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC",
			      "position": 30,
			      "height": 300
		    	},
		      	{
			      "name": "sketch",
			      "label": "LBL_IMAGE_GROUP_SKETCH_ACCIDENT",
			      "description": "LBL_IMAGE_GROUP_SKETCH_ACCIDENT_DESC",
			      "position": 32,
			      "height": 300
		    	}
		  	],
		  	"groupAttachments": [
		    	{
		      	"name": "documents",
			    "label": "LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS",
			    "description": "LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS_DESC",
		      	"position": 31,
		      	"height": 200
		    	}
		  ],
		  "initialTabLabel": "LBL_SECTION_IDENTIFICATION",
		  "srcTemplate": "incidentFormTemplate.html"
	}',
	null),
	(2, 'vehicles', 'Vehículos', 'Vehículos', 'Vehículos', 'mdi mdi-message-alert', true, true, null, null),
	(3, 'participants', 'Participantes', 'Participantes', 'Participantes', 'mdi mdi-message-alert', true, true, null, null),
	(4, 'closures', 'Cierres', 'Cierres', 'Cierres', 'mdi mdi-message-alert', true, true, null, null),
	(5, 'event', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null, null),
	(6, 'side', 'Lado', 'Lado', 'Lado', 'mdi mdi-message-alert', true, true, null, null),
	(7, 'stretch', 'Tramo', 'Tramo', 'Tramo', 'mdi mdi-message-alert', true, true, null, null),
	(8, 'event_type', 'Tipo de evento', 'Tipo de evento', 'Tipo de evento', 'mdi mdi-message-alert', true, true, null, null),
	(9, 'collision', 'Colisión', 'Colisión', 'Colisión', 'mdi mdi-message-alert', true, true, null, null),
	(10, 'object_crash', 'Choque con objeto', 'Choque con objeto', 'Choque con objeto', 'mdi mdi-message-alert', true, true, null, null),
	(11, 'vehicle_crash', 'Choque con vehículo', 'Choque con vehículo', 'Choque con vehículo', 'mdi mdi-message-alert', true, true, null, null),
	(12, 'relative_location', 'Ubicación relativa', 'Ubicación relativa', 'Ubicación relativa', 'mdi mdi-message-alert', true, true, null, null),
	(13, 'vehicle_types', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null, null),
	(14, 'vehicle_services', 'Servicios', 'Servicios', 'Servicios', 'mdi mdi-message-alert', true, true, null, null),
	(15, 'vehicle_consecuences', 'Consecuencias', 'Consecuencias', 'Consecuencias', 'mdi mdi-message-alert', true, true, null, null),
	(16, 'vehicle_manoeuvres', 'Maniobras', 'Maniobras', 'Maniobras', 'mdi mdi-message-alert', true, true, null, null),
	(17, 'vehicle_brands', 'Marca', 'Marca', 'Marca', 'mdi mdi-message-alert', true, true, null, null),
	(18, 'participant_types', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null, null),
	(19, 'participant_injuries', 'Lesión', 'Lesión', 'Lesión', 'mdi mdi-message-alert', true, true, null, null),
	(20, 'participant_nacionalities', 'Nacionalidad', 'Nacionalidad', 'Nacionalidad', 'mdi mdi-message-alert', true, true, null, null),
	(21, 'support_services', 'Servicios de apoyo', 'Servicios de apoyo', 'Servicios de apoyo', 'mdi mdi-message-alert', true, true, null, null),
	(22, 'damages_responsabilities', 'Daños-responsables', 'Daños-responsables', 'Daños-responsables', 'mdi mdi-message-alert', true, true, null, null),
	(23, 'damages_evaluations', 'Daños-evaluación', 'Daños-evaluación', 'Daños-evaluación', 'mdi mdi-message-alert', true, true, null, null),
	(24, 'owner_types', 'Tipo propietario', 'Tipo propietario', 'Tipo propietario', 'mdi mdi-message-alert', true, true, null, null),
	(25, 'support_service_types', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null, null),
	(26, 'support_service_subtypes', 'Subtipo', 'Subtipo', 'Subtipo', 'mdi mdi-message-alert', true, true, null, null),
	(27, 'road_conditions', 'Condiciones calzada', 'Condiciones calzada', 'Condiciones calzada', 'mdi mdi-message-alert', true, true, null, null),
	(28, 'road_types', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null, null),
	(29, 'road_states', 'Estado', 'Estado', 'Estado', 'mdi mdi-message-alert', true, true, null, null),
	(30, 'weather_states', 'Estado atmosférico', 'Estado atmosférico', 'Estado atmosférico', 'mdi mdi-message-alert', true, true, null, null),
	(31, 'luminosity', 'Luminosidad', 'Luminosidad', 'Luminosidad', 'mdi mdi-message-alert', true, true, null, null),
	(32, 'ligth', 'Luz artificial', 'Luz artificial', 'Luz artificial', 'mdi mdi-message-alert', true, true, null, null),
	(33, 'causes', 'Causa probable', 'Causa probable', 'Causa probable', 'mdi mdi-message-alert', true, true, null, null),
	(34, 'annual_summary', 'Resumen anual', 'Resumen anual', 'Resumen anual', 'mdi mdi-message-alert', true, true, 
	'{
		"srcTemplate": "summaryFormTemplate.html",
		"referTables": ["month_summary", "incidents_summary"]
	}',
	'SELECT 
		id, anio, total_accidentes, atropello, caida, volcadura, con_lesionados, leves, menos_graves, graves, muertos, lesionados, colision, choque, otros
	FROM 
		backoffice.annual_summary'),
	(35, 'month_summary', 'Resumen mensual', 'Resumen mensual', 'Resumen mensual', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, anio, mes, total_accidentes, con_lesionados, muertos, graves, menos_graves, leves, lesionados, atropello, caida, volcadura, colision, choque, otros
	FROM 
		backoffice.month_summary'),
	(36, 'incidents_summary', 'Resumen incidencias', 'Resumen incidencias', 'Resumen incidencias', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.incidents_summary'),
	(37, 'm_january', 'Incidencias enero', 'Incidencias enero', 'Incidencias enero', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_january'),
	(38, 'm_february', 'Incidencias febrero', 'Incidencias febrero', 'Incidencias febrero', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_february'),
	(39, 'm_march', 'Incidencias marzo', 'Incidencias marzo', 'Incidencias marzo', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_march'),
	(40, 'm_april', 'Incidencias abril', 'Incidencias abril', 'Incidencias abril', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_april'),
	(41, 'm_may', 'Incidencias mayo', 'Incidencias mayo', 'Incidencias mayo', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_may'),
	(42, 'm_june', 'Incidencias junio', 'Incidencias junio', 'Incidencias junio', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_june'),
	(43, 'm_july', 'Incidencias julio', 'Incidencias julio', 'Incidencias julio', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_july'),
	(44, 'm_august', 'Incidencias agosto', 'Incidencias agosto', 'Incidencias agosto', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_august'),
	(45, 'm_september', 'Incidencias septiembre', 'Incidencias septiembre', 'Incidencias septiembre', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_september'),
	(46, 'm_october', 'Incidencias octubre', 'Incidencias octubre', 'Incidencias octubre', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_october'),
	(47, 'm_november', 'Incidencias noviembre', 'Incidencias noviembre', 'Incidencias noviembre', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_november'),
	(48, 'm_december', 'Incidencias deciembre', 'Incidencias deciembre', 'Incidencias deciembre', 'mdi mdi-message-alert', true, true, null,
	'SELECT 
		id, num_registro, anio, mes, dia, hora, km, tipo_accidente, ilesos, muertos, graves, menos_graves, leves, lesionados, causa, ubicacion_relativa, internas, externas
	FROM 
		backoffice.m_december');
	
	INSERT INTO backoffice.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata, ref_view_column_id) VALUES
	(0101, 1, 'incident_report_id', 'Identificador', NULL, true, '{"position": 1, "tableVisible": true, "editable": true, "refName": true}', null),
	(0102, 1, 'km', 'km', NULL, true, '{"position": 2, "editable": true, "tableVisible": true}', null),
	(0103, 1, 'date', 'Fecha', NULL, true, '{"position": 3, "editable": true, "tableVisible": true}', null),
	(0104, 1, 'event_id', 'Evento', NULL, true, '{"position": 4, "editable": true, "tableVisible": true}', null),
	(0105, 1, 'side_id', 'Lado carretera', NULL, true, '{"position": 5, "editable": true, "tableVisible": true}', null),
	(0106, 1, 'stretch_id', 'Tramo', NULL, true, '{"position": 6, "editable": true, "tableVisible": true}', null),
	(0107, 1, 'event_type_id', 'Tipo de evento', NULL, true, '{"position": 7, "editable": true, "tableVisible": false}', null),
	(0108, 1, 'collision_id', 'Colisión', NULL, true, '{"position": 8, "editable": true, "tableVisible": false}', null),
	(0109, 1, 'object_crash_id', 'Choque con objeto', NULL, true, '{"position": 9, "editable": true, "tableVisible": false}', null),
	(0110, 1, 'vehicle_crash_id', 'Choque con vehículo', NULL, true, '{"position": 10, "editable": true, "tableVisible": false}', null),
	(0111, 1, 'relative_location_id', 'Ubicación relativa', NULL, true, '{"position": 11, "editable": true, "tableVisible": false}', null),
	(0112, 1, 'road_condition_id', 'Condiciones calzada', NULL, true, '{"position": 12, "editable": true, "tableVisible": false}', null),
	(0113, 1, 'road_type_id', 'Tipo carpeta', NULL, true, '{"position": 13, "editable": true, "tableVisible": false}', null),
	(0114, 1, 'road_state_id', 'Estado carpeta', NULL, true, '{"position": 14, "editable": true, "tableVisible": false}', null),
	(0115, 1, 'weather_state_id', 'Estado atmosférico', NULL, true, '{"position": 15, "editable": true, "tableVisible": false}', null),
	(0116, 1, 'luminosity_id', 'Luminosidad', NULL, true, '{"position": 16, "editable": true, "tableVisible": false}', null),
	(0117, 1, 'ligth_id', 'Luz artificial', NULL, true, '{"position": 17, "editable": true, "tableVisible": false}', null),
	(0118, 1, 'cause_id', 'Causa probable', NULL, true, '{"position": 18, "editable": true, "tableVisible": false}', null),
	(0119, 1, 'comments', 'Redacción', NULL, true, '{"multiline": 5, "position": 19, "editable": true, "tableVisible": false}', null),
	(0120, 1, 'report_by', 'Denuncia realizada por', NULL, true, '{"position": 20, "editable": true, "tableVisible": false}', null),
	(0121, 1, 'stamped', 'Estampado de carabineros de', NULL, true, '{"position": 21, "editable": true, "tableVisible": false}', null),
	(0122, 1, 'court', 'Citado al juzgado de', NULL, true, '{"position": 22, "editable": true, "tableVisible": false}', null),
	(0123, 1, 'citattion_date', 'Fecha de citación', NULL, true, '{"position": 23, "editable": true, "tableVisible": false}', null),
	(0124, 1, 'report_number', 'Número de parte', NULL, true, '{"position": 24, "editable": true, "tableVisible": false}', null),
	(0125, 1, 'station', 'Plaza', NULL, true, '{"position": 25, "editable": true, "tableVisible": false}', null),
	(0126, 1, 'file_number', 'Número de folio', NULL, true, '{"position": 26, "editable": true, "tableVisible": false}', null),
	(0127, 1, 'patrol_name', 'Nombre de patrullero', NULL, true, '{"position": 27, "editable": true, "tableVisible": false}', null),
	(0128, 1, 'operator_name', 'Nombre de operador de c.control', NULL, true, '{"position": 28, "editable": true, "tableVisible": false}', null),
	(0129, 1, 'supervisor_name', 'Nombre supervisor operaciones', NULL, true, '{"position": 29, "editable": true, "tableVisible": false}', null),
	 
	(0201, 2, 'incident_report_id', 'Identificador', NULL, true, '{"position": 1, "editable": true, "refName": true}', null),
	(0202, 2, 'vehicle_type_id', 'Tipo', NULL, true, '{"position": 2, "editable": true}', null),
	(0203, 2, 'vehicle_service_id', 'Servicio', NULL, true, '{"position": 3, "editable": true}', null),
	(0204, 2, 'vehicle_consecuence_id', 'Consecuencia', NULL, true, '{"position": 4, "editable": true}', null),
	(0205, 2, 'vehicle_manoeuvre_id', 'Maniobra', NULL, true, '{"position": 5, "editable": true}', null),
	(0206, 2, 'patent', 'Patente', NULL, true, '{"position": 6, "editable": true}', null),
	(0207, 2, 'lane', 'Vía', NULL, true, '{"position": 7, "editable": true}', null),
	(0208, 2, 'vehicle_brand_id', 'Marca', NULL, true, '{"position": 8, "editable": true}', null),
	
	(0301, 3, 'incident_report_id', 'Identificador', NULL, true, '{"position": 1, "editable": true, "refName": true}', null),
	(0302, 3, 'participant_name', 'Nombre', NULL, true, '{"position": 2, "editable": true}', null),
	(0303, 3, 'participant_rut', 'RUT', NULL, true, '{"position": 3, "editable": true}', null),
	(0304, 3, 'participant_age', 'Edad', NULL, true, '{"position": 4, "editable": true}', null),
	(0305, 3, 'participant_address', 'Dirección', NULL, true, '{"position": 5, "editable": true}', null),
	(0306, 3, 'participant_vehicle', 'Patente', NULL, true, '{"position": 6, "editable": true}', null),
	(0307, 3, 'participant_type_id', 'Tipo', NULL, true, '{"position": 7, "editable": true}', null),
	(0308, 3, 'participant_injury_id', 'Lesión', NULL, true, '{"position": 8, "editable": true}', null),
	(0309, 3, 'participant_nacionality_id', 'Nacionalidad', NULL, true, '{"position": 9, "editable": true}', null),

	(0401, 4, 'incident_report_id', 'Identificador', NULL, true, '{"position": 1, "editable": true, "refName": true}', null),
	(0402, 4, 'side_road', 'Lado carretera', NULL, true, '{"position": 2, "editable": true}', null),
	(0403, 4, 'lanes', 'Pistas', NULL, true, '{"position": 3, "editable": true}', null),
	(0404, 4, 'entity', 'Servicio apoyo', NULL, true, '{"position": 4, "editable": true}', null),
	(0405, 4, 'reasons', 'Motivos', NULL, true, '{"position": 5, "editable": true}', null),
	(0406, 4, 'start_hour', 'Hora inicio', NULL, true, '{"position": 6, "editable": true}', null),
	(0407, 4, 'end_hour', 'Hora fin', NULL, true, '{"position": 7, "editable": true}', null),
	(0408, 4, 'total_time', 'Tiempo total', NULL, true, '{"position": 8, "editable": true}', null),
	
	(2101, 21, 'incident_report_id', 'Identificador total', NULL, true, '{"position": 1, "editable": true, "refName": true}', null),
	(2102, 21, 'support_service_types', 'Tipo', NULL, true, '{"position":2, "editable": true}', null),
	(2103, 21, 'support_service_subtypes', 'Subtipo', NULL, true, '{"position": 3, "editable": true}', null),
	(2104, 21, 'arrival_time', 'Llegada', NULL, true, '{"position": 4, "editable": true}', null),
	(2105, 21, 'comment', 'Comentarios', NULL, true, '{"multiline": 5, "position": 5, "editable": true}', null),
	(2106, 21, 'quantity', 'Cantidad', NULL, true, '{"position": 6, "editable": true}', null),
	(2107, 21, 'departure_time', 'Partida', NULL, true, '{"position": 7, "editable": true}', null),
	
	(2201, 22, 'incident_report_id', 'Identificador total', NULL, true, '{"position": 1, "editable": true, "refName": true}', null),
	(2202, 22, 'address', 'Dirección', NULL, true, '{"position":2, "editable": true}', null),
	(2203, 22, 'phone_number', 'Teléfono', NULL, true, '{"position":3, "editable": true}', null),
	(2204, 22, 'owner_types', 'Tipo propietario', NULL, true, '{"position":4, "editable": true}', null),
	(2205, 22, 'owner_name', 'Nombre empresa', NULL, true, '{"position":5, "editable": true}', null),
	
	(2301, 23, 'incident_report_id', 'Identificador total', NULL, true, '{"position": 1, "editable": true, "refName": true}', null),
	(2302, 23, 'description', 'Descripción', NULL, true, '{"position":2, "editable": true}', null),
	(2303, 23, 'quantity', 'Cantidad', NULL, true, '{"position":3, "editable": true}', null),
	(2304, 23, 'entity', 'Unidad', NULL, true, '{"position":4, "editable": true}', null),
	
	(3401, 34, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', null),
	(3402, 34, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(3403, 34, 'total_accidentes', 'Total', NULL, true, '{"position": 3, "editable": false}', null),
	(3404, 34, 'con_lesionados', 'Con lesionados', NULL, true, '{"position": 4, "editable": false}', null),
	(3405, 34, 'muertos', 'Muertos', NULL, true, '{"position": 5, "editable": false}', null),
	(3406, 34, 'graves', 'Graves', NULL, true, '{"position": 6, "editable": false}', null),
	(3407, 34, 'menos_graves', 'M Graves', NULL, true, '{"position": 7, "editable": false}', null),
	(3408, 34, 'leves', 'Leves', NULL, true, '{"position": 8, "editable": false}', null),
	(3409, 34, 'lesionados', 'Total lesionados', NULL, true, '{"position": 9, "editable": false}', null),
	(3410, 34, 'atropello', 'Atropello', NULL, true, '{"position": 10, "editable": false}', null),
	(3411, 34, 'caida', 'Caida', NULL, true, '{"position": 11, "editable": false}', null),
	(3412, 34, 'volcadura', 'Volcadura', NULL, true, '{"position": 12, "editable": false}', null),
	(3413, 34, 'colision', 'Colision', NULL, true, '{"position": 13, "editable": false}', null),
	(3414, 34, 'choque', 'Choque', NULL, true, '{"position": 14, "editable": false}', null),
	(3415, 34, 'otros', 'Otros', NULL, true, '{"position": 15, "editable": false}', null),
	
	(3501, 35, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(3502, 35, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(3503, 35, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(3504, 35, 'total_accidentes', 'Total', NULL, true, '{"position": 4, "editable": false}', null),
	(3505, 35, 'con_lesionados', 'Con lesionados', NULL, true, '{"position": 5, "editable": false}', null),
	(3506, 35, 'muertos', 'Muertos', NULL, true, '{"position": 6, "editable": false}', null),
	(3507, 35, 'graves', 'Graves', NULL, true, '{"position": 7, "editable": false}', null),
	(3508, 35, 'menos_graves', 'M Graves', NULL, true, '{"position": 8, "editable": false}', null),
	(3509, 35, 'leves', 'Leves', NULL, true, '{"position": 9, "editable": false}', null),
	(3510, 35, 'lesionados', 'Total lesionados', NULL, true, '{"position": 10, "editable": false}', null),
	(3511, 35, 'atropello', 'Atropello', NULL, true, '{"position": 11, "editable": false}', null),
	(3512, 35, 'caida', 'Caida', NULL, true, '{"position": 12, "editable": false}', null),
	(3513, 35, 'volcadura', 'Volcadura', NULL, true, '{"position": 13, "editable": false}', null),
	(3514, 35, 'colision', 'Colsión', NULL, true, '{"position": 14, "editable": false}', null),
	(3515, 35, 'choque', 'Choque', NULL, true, '{"position": 15, "editable": false}', null),
	(3516, 35, 'otros', 'Otros', NULL, true, '{"position": 16, "editable": false}', null),
	
	(3601, 36, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(3602, 36, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(3603, 36, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(3604, 36, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(3605, 36, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(3606, 36, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(3607, 36, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(3608, 36, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(3609, 36, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(3610, 36, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(3611, 36, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(3612, 36, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(3613, 36, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(3614, 36, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(3615, 36, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(3616, 36, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(3617, 36, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(3618, 36, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(3701, 37, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(3702, 37, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(3703, 37, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(3704, 37, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(3705, 37, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(3706, 37, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(3707, 37, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(3708, 37, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(3709, 37, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(3710, 37, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(3711, 37, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(3712, 37, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(3713, 37, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(3714, 37, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(3715, 37, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(3716, 37, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(3717, 37, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(3718, 37, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(3801, 38, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(3802, 38, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(3803, 38, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(3804, 38, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(3805, 38, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(3806, 38, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(3807, 38, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(3808, 38, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(3809, 38, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(3810, 38, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(3811, 38, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(3812, 38, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(3813, 38, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(3814, 38, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(3815, 38, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(3816, 38, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(3817, 38, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(3818, 38, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(3901, 39, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(3902, 39, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(3903, 39, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(3904, 39, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(3905, 39, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(3906, 39, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(3907, 39, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(3908, 39, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(3909, 39, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(3910, 39, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(3911, 39, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(3912, 39, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(3913, 39, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(3914, 39, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(3915, 39, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(3916, 39, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(3917, 39, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(3918, 39, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(4001, 40, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(4002, 40, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(4003, 40, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(4004, 40, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(4005, 40, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(4006, 40, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(4007, 40, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(4008, 40, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(4009, 40, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(4010, 40, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(4011, 40, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(4012, 40, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(4013, 40, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(4014, 40, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(4015, 40, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(4016, 40, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(4017, 40, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(4018, 40, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(4101, 41, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(4102, 41, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(4103, 41, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(4104, 41, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(4105, 41, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(4106, 41, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(4107, 41, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(4108, 41, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(4109, 41, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(4110, 41, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(4111, 41, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(4112, 41, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(4113, 41, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(4114, 41, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(4115, 41, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(4116, 41, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(4117, 41, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(4118, 41, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(4201, 42, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(4202, 42, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(4203, 42, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(4204, 42, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(4205, 42, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(4206, 42, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(4207, 42, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(4208, 42, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(4209, 42, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(4210, 42, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(4211, 42, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(4212, 42, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(4213, 42, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(4214, 42, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(4215, 42, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(4216, 42, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(4217, 42, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(4218, 42, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(4301, 43, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(4302, 43, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(4303, 43, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(4304, 43, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(4305, 43, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(4306, 43, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(4307, 43, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(4308, 43, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(4309, 43, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(4310, 43, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(4311, 43, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(4312, 43, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(4313, 43, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(4314, 43, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(4315, 43, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(4316, 43, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(4317, 43, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(4318, 43, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(4401, 44, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(4402, 44, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(4403, 44, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(4404, 44, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(4405, 44, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(4406, 44, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(4407, 44, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(4408, 44, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(4409, 44, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(4410, 44, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(4411, 44, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(4412, 44, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(4413, 44, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(4414, 44, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(4415, 44, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(4416, 44, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(4417, 44, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(4418, 44, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(4501, 45, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(4502, 45, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(4503, 45, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(4504, 45, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(4505, 45, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(4506, 45, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(4507, 45, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(4508, 45, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(4509, 45, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(4510, 45, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(4511, 45, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(4512, 45, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(4513, 45, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(4514, 45, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(4515, 45, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(4516, 45, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(4517, 45, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(4518, 45, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(4601, 46, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(4602, 46, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(4603, 46, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(4604, 46, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(4605, 46, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(4606, 46, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(4607, 46, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(4608, 46, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(4609, 46, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(4610, 46, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(4611, 46, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(4612, 46, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(4613, 46, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(4614, 46, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(4615, 46, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(4616, 46, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(4617, 46, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(4618, 46, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(4701, 47, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(4702, 47, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(4703, 47, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(4704, 47, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(4705, 47, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(4706, 47, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(4707, 47, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(4708, 47, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(4709, 47, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(4710, 47, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(4711, 47, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(4712, 47, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(4713, 47, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(4714, 47, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(4715, 47, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(4716, 47, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(4717, 47, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(4718, 47, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null),
	
	(4801, 48, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', 3401),
	(4802, 48, 'anio', 'Año', NULL, true, '{"position": 2, "editable": false}', null),
	(4803, 48, 'mes', 'Mes', NULL, true, '{"position": 3, "editable": false}', null),
	(4804, 48, 'dia', 'Dia', NULL, true, '{"position": 4, "editable": false}', null),
	(4805, 48, 'hora', 'Hora', NULL, true, '{"position": 5, "editable": false}', null),
	(4806, 48, 'km', 'Km', NULL, true, '{"position": 6, "editable": false}', null),
	(4807, 48, 'tipo_accidente', 'Tipo accidente', NULL, true, '{"position": 7, "editable": false}', null),
	(4808, 48, 'ilesos', 'Ilesos', NULL, true, '{"position": 8, "editable": false}', null),
	(4809, 48, 'muertos', 'Muertos', NULL, true, '{"position": 9, "editable": false}', null),
	(4810, 48, 'graves', 'Graves', NULL, true, '{"position": 10, "editable": false}', null),
	(4811, 48, 'menos_graves', 'Menos graves', NULL, true, '{"position": 11, "editable": false}', null),
	(4812, 48, 'leves', 'Leves', NULL, true, '{"position": 12, "editable": false}', null),
	(4813, 48, 'lesionados', 'Lesionados', NULL, true, '{"position": 13, "editable": false}', null),
	(4814, 48, 'causa', 'Causa', NULL, true, '{"position": 14, "editable": false}', null),
	(4815, 48, 'ubicacion_relativa', 'Ubicación relativa', NULL, true, '{"position": 15, "editable": false}', null),
	(4816, 48, 'internas', 'Internas', NULL, true, '{"position": 16, "editable": false}', null),
	(4817, 48, 'externas', 'Externas', NULL, true, '{"position": 17, "editable": false}', null),
	(4818, 48, 'num_registro', 'Numero', NULL, true, '{"tableVisible": false}', null);
	
  END IF;
END $$;
