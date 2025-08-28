DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(1, 'backoffice', NULL, NULL, 'jdbc:postgresql://10.140.31.104:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'backoffice',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 700,
    	"formWidth": 900,
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
	INSERT INTO backoffice.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata) VALUES
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
	}'
	),
	(2, 'vehicles', 'Vehículos', 'Vehículos', 'Vehículos', 'mdi mdi-message-alert', true, true, null),
	(3, 'participants', 'Participantes', 'Participantes', 'Participantes', 'mdi mdi-message-alert', true, true, null),
	(4, 'closures', 'Cierres', 'Cierres', 'Cierres', 'mdi mdi-message-alert', true, true, null),
	(5, 'event', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null),
	(6, 'side', 'Lado', 'Lado', 'Lado', 'mdi mdi-message-alert', true, true, null),
	(7, 'stretch', 'Tramo', 'Tramo', 'Tramo', 'mdi mdi-message-alert', true, true, null),
	(8, 'event_type', 'Tipo de evento', 'Tipo de evento', 'Tipo de evento', 'mdi mdi-message-alert', true, true, null),
	(9, 'collision', 'Colisión', 'Colisión', 'Colisión', 'mdi mdi-message-alert', true, true, null),
	(10, 'object_crash', 'Choque con objeto', 'Choque con objeto', 'Choque con objeto', 'mdi mdi-message-alert', true, true, null),
	(11, 'vehicle_crash', 'Choque con vehículo', 'Choque con vehículo', 'Choque con vehículo', 'mdi mdi-message-alert', true, true, null),
	(12, 'relative_location', 'Ubicación relativa', 'Ubicación relativa', 'Ubicación relativa', 'mdi mdi-message-alert', true, true, null),
	(13, 'vehicle_types', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null),
	(14, 'vehicle_services', 'Servicios', 'Servicios', 'Servicios', 'mdi mdi-message-alert', true, true, null),
	(15, 'vehicle_consecuences', 'Consecuencias', 'Consecuencias', 'Consecuencias', 'mdi mdi-message-alert', true, true, null),
	(16, 'vehicle_manoeuvres', 'Maniobras', 'Maniobras', 'Maniobras', 'mdi mdi-message-alert', true, true, null),
	(17, 'vehicle_brands', 'Marca', 'Marca', 'Marca', 'mdi mdi-message-alert', true, true, null),
	(18, 'participant_types', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null),
	(19, 'participant_injuries', 'Lesión', 'Lesión', 'Lesión', 'mdi mdi-message-alert', true, true, null),
	(20, 'participant_nacionalities', 'Nacionalidad', 'Nacionalidad', 'Nacionalidad', 'mdi mdi-message-alert', true, true, null),
	(21, 'support_services', 'Servicios de apoyo', 'Servicios de apoyo', 'Servicios de apoyo', 'mdi mdi-message-alert', true, true, null),
	(22, 'damages_responsabilities', 'Daños-responsables', 'Daños-responsables', 'Daños-responsables', 'mdi mdi-message-alert', true, true, null),
	(23, 'damages_evaluations', 'Daños-evaluación', 'Daños-evaluación', 'Daños-evaluación', 'mdi mdi-message-alert', true, true, null),
	(24, 'owner_types', 'Tipo propietario', 'Tipo propietario', 'Tipo propietario', 'mdi mdi-message-alert', true, true, null),
	(25, 'support_service_types', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null),
	(26, 'support_service_subtypes', 'Subtipo', 'Subtipo', 'Subtipo', 'mdi mdi-message-alert', true, true, null),
	(27, 'road_conditions', 'Condiciones calzada', 'Condiciones calzada', 'Condiciones calzada', 'mdi mdi-message-alert', true, true, null),
	(28, 'road_types', 'Tipo', 'Tipo', 'Tipo', 'mdi mdi-message-alert', true, true, null),
	(29, 'road_states', 'Estado', 'Estado', 'Estado', 'mdi mdi-message-alert', true, true, null),
	(30, 'weather_states', 'Estado atmosférico', 'Estado atmosférico', 'Estado atmosférico', 'mdi mdi-message-alert', true, true, null),
	(31, 'luminosity', 'Luminosidad', 'Luminosidad', 'Luminosidad', 'mdi mdi-message-alert', true, true, null),
	(32, 'ligth', 'Luz artificial', 'Luz artificial', 'Luz artificial', 'mdi mdi-message-alert', true, true, null),
	(33, 'causes', 'Causa probable', 'Causa probable', 'Causa probable', 'mdi mdi-message-alert', true, true, null);
	
	INSERT INTO backoffice.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(0101, 1, 'incident_report_id', 'Identificador', NULL, true, '{"position": 1, "tableVisible": true, "editable": true, "refName": true}'),
	(0102, 1, 'km', 'km', NULL, true, '{"position": 2, "editable": true, "tableVisible": true}'),
	(0103, 1, 'date', 'Fecha', NULL, true, '{"position": 3, "editable": true, "tableVisible": true}'),
	(0104, 1, 'event_id', 'Evento', NULL, true, '{"position": 4, "editable": true, "tableVisible": true}'),
	(0105, 1, 'side_id', 'Lado carretera', NULL, true, '{"position": 5, "editable": true, "tableVisible": true}'),
	(0106, 1, 'stretch_id', 'Tramo', NULL, true, '{"position": 6, "editable": true, "tableVisible": true}'),
	(0107, 1, 'event_type_id', 'Tipo de evento', NULL, true, '{"position": 7, "editable": true, "tableVisible": false}'),
	(0108, 1, 'collision_id', 'Colisión', NULL, true, '{"position": 8, "editable": true, "tableVisible": false}'),
	(0109, 1, 'object_crash_id', 'Choque con objeto', NULL, true, '{"position": 9, "editable": true, "tableVisible": false}'),
	(0110, 1, 'vehicle_crash_id', 'Choque con vehículo', NULL, true, '{"position": 10, "editable": true, "tableVisible": false}'),
	(0111, 1, 'relative_location_id', 'Ubicación relativa', NULL, true, '{"position": 11, "editable": true, "tableVisible": false}'),
	(0112, 1, 'road_condition_id', 'Condiciones calzada', NULL, true, '{"position": 12, "editable": true, "tableVisible": false}'),
	(0113, 1, 'road_type_id', 'Tipo carpeta', NULL, true, '{"position": 13, "editable": true, "tableVisible": false}'),
	(0114, 1, 'road_state_id', 'Estado carpeta', NULL, true, '{"position": 14, "editable": true, "tableVisible": false}'),
	(0115, 1, 'weather_state_id', 'Estado atmosférico', NULL, true, '{"position": 15, "editable": true, "tableVisible": false}'),
	(0116, 1, 'luminosity_id', 'Luminosidad', NULL, true, '{"position": 16, "editable": true, "tableVisible": false}'),
	(0117, 1, 'ligth_id', 'Luz artificial', NULL, true, '{"position": 17, "editable": true, "tableVisible": false}'),
	(0118, 1, 'cause_id', 'Causa probable', NULL, true, '{"position": 18, "editable": true, "tableVisible": false}'),
	(0119, 1, 'comments', 'Redacción', NULL, true, '{"multiline": 5, "position": 19, "editable": true, "tableVisible": false}'),
	(0120, 1, 'report_by', 'Denuncia realizada por', NULL, true, '{"position": 20, "editable": true, "tableVisible": false}'),
	(0121, 1, 'stamped', 'Estampado de carabineros de', NULL, true, '{"position": 21, "editable": true, "tableVisible": false}'),
	(0122, 1, 'court', 'Citado al juzgado de', NULL, true, '{"position": 22, "editable": true, "tableVisible": false}'),
	(0123, 1, 'citattion_date', 'Fecha de citación', NULL, true, '{"position": 23, "editable": true, "tableVisible": false}'),
	(0124, 1, 'report_number', 'Número de parte', NULL, true, '{"position": 24, "editable": true, "tableVisible": false}'),
	(0125, 1, 'station', 'Plaza', NULL, true, '{"position": 25, "editable": true, "tableVisible": false}'),
	(0126, 1, 'file_number', 'Número de folio', NULL, true, '{"position": 26, "editable": true, "tableVisible": false}'),
	(0127, 1, 'patrol_name', 'Nombre de patrullero', NULL, true, '{"position": 27, "editable": true, "tableVisible": false}'),
	(0128, 1, 'operator_name', 'Nombre de operador de c.control', NULL, true, '{"position": 28, "editable": true, "tableVisible": false}'),
	(0129, 1, 'supervisor_name', 'Nombre supervisor operaciones', NULL, true, '{"position": 29, "editable": true, "tableVisible": false}'),
	 
	(0201, 2, 'incident_report_id', 'Identificador', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(0202, 2, 'vehicle_type_id', 'Tipo', NULL, true, '{"position": 2, "editable": true}'),
	(0203, 2, 'vehicle_service_id', 'Servicio', NULL, true, '{"position": 3, "editable": true}'),
	(0204, 2, 'vehicle_consecuence_id', 'Consecuencia', NULL, true, '{"position": 4, "editable": true}'),
	(0205, 2, 'vehicle_manoeuvre_id', 'Maniobra', NULL, true, '{"position": 5, "editable": true}'),
	(0206, 2, 'patent', 'Patente', NULL, true, '{"position": 6, "editable": true}'),
	(0207, 2, 'lane', 'Vía', NULL, true, '{"position": 7, "editable": true}'),
	(0208, 2, 'vehicle_brand_id', 'Marca', NULL, true, '{"position": 8, "editable": true}'),
	
	(0301, 3, 'incident_report_id', 'Identificador', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(0302, 3, 'participant_name', 'Nombre', NULL, true, '{"position": 2, "editable": true}'),
	(0303, 3, 'participant_rut', 'RUT', NULL, true, '{"position": 3, "editable": true}'),
	(0304, 3, 'participant_age', 'Edad', NULL, true, '{"position": 4, "editable": true}'),
	(0305, 3, 'participant_address', 'Dirección', NULL, true, '{"position": 5, "editable": true}'),
	(0306, 3, 'participant_vehicle', 'Patente', NULL, true, '{"position": 6, "editable": true}'),
	(0307, 3, 'participant_type_id', 'Tipo', NULL, true, '{"position": 7, "editable": true}'),
	(0308, 3, 'participant_injury_id', 'Lesión', NULL, true, '{"position": 8, "editable": true}'),
	(0309, 3, 'participant_nacionality_id', 'Nacionalidad', NULL, true, '{"position": 9, "editable": true}'),

	(0401, 4, 'incident_report_id', 'Identificador', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(0402, 4, 'side_road', 'Lado carretera', NULL, true, '{"position": 2, "editable": true}'),
	(0403, 4, 'lanes', 'Pistas', NULL, true, '{"position": 3, "editable": true}'),
	(0404, 4, 'entity', 'Servicio apoyo', NULL, true, '{"position": 4, "editable": true}'),
	(0405, 4, 'reasons', 'Motivos', NULL, true, '{"position": 5, "editable": true}'),
	(0406, 4, 'start_hour', 'Hora inicio', NULL, true, '{"position": 6, "editable": true}'),
	(0407, 4, 'end_hour', 'Hora fin', NULL, true, '{"position": 7, "editable": true}'),
	(0408, 4, 'total_time', 'Tiempo total', NULL, true, '{"position": 8, "editable": true}'),
	
	(2101, 21, 'incident_report_id', 'Identificador total', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(2102, 21, 'support_service_types', 'Tipo', NULL, true, '{"position":2, "editable": true}'),
	(2103, 21, 'support_service_subtypes', 'Subtipo', NULL, true, '{"position": 3, "editable": true}'),
	(2104, 21, 'arrival_time', 'Llegada', NULL, true, '{"position": 4, "editable": true}'),
	(2105, 21, 'comment', 'Comentarios', NULL, true, '{"multiline": 5, "position": 5, "editable": true}'),
	(2106, 21, 'quantity', 'Cantidad', NULL, true, '{"position": 6, "editable": true}'),
	(2107, 21, 'departure_time', 'Partida', NULL, true, '{"position": 7, "editable": true}'),
	
	(2201, 22, 'incident_report_id', 'Identificador total', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(2202, 22, 'address', 'Dirección', NULL, true, '{"position":2, "editable": true}'),
	(2203, 22, 'phone_number', 'Teléfono', NULL, true, '{"position":3, "editable": true}'),
	(2204, 22, 'owner_types', 'Tipo propietario', NULL, true, '{"position":4, "editable": true}'),
	(2205, 22, 'owner_name', 'Nombre empresa', NULL, true, '{"position":5, "editable": true}'),
	
	(2301, 23, 'incident_report_id', 'Identificador total', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(2302, 23, 'description', 'Descripción', NULL, true, '{"position":2, "editable": true}'),
	(2303, 23, 'quantity', 'Cantidad', NULL, true, '{"position":3, "editable": true}'),
	(2304, 23, 'entity', 'Unidad', NULL, true, '{"position":4, "editable": true}');
	
  END IF;
END $$;
