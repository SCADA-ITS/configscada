DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(1, 'backoffice', NULL, NULL, 'jdbc:postgresql://192.168.88.81:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'backoffice',
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
	(1, 'incidents', 'LBL_INCIDENTS', 'LBL_INCIDENT', 'LBL_INCIDENTS_DESCRIPTION', 'mdi mdi-message-alert', true, true, 
	'{
		  "tabs": [
		    {
		      "label": "LBL_SECTION_EVENT",
		      "position": 2,
		      "fields": [
		        {
		          "name": "event_type_id",
		          "position": 1
		        },
		        {
		          "name": "collision_id",
		          "position": 2
		        },
		        {
		          "name": "object_crash_id",
		          "position": 3
		        },
		        {
		          "name": "vehicle_crash_id",
		          "position": 4
		        },
		        {
		          "name": "relative_location_id",
		          "position": 5
		        }
		      ]
		    }
		  ],
		  "initialTabLabel": "LBL_SECTION_IDENTIFICATION"
	}'
	),
	(2, 'vehicles', 'Vehículos', 'Vehículos', 'Vehículos', 'mdi mdi-message-alert', true, true, null),
	(3, 'participants', 'Participantes', 'Participantes', 'Participantes', 'mdi mdi-message-alert', true, true, null),
	(4, 'closures', 'Cierres', 'Cierres', 'Cierres', 'mdi mdi-message-alert', true, true, null),
	(5, 'event', 'tipo', 'tipo', 'tipo', 'mdi mdi-message-alert', true, true, null),
	(6, 'side', 'lado', 'lado', 'lado', 'mdi mdi-message-alert', true, true, null),
	(7, 'stretch', 'tramo', 'tramo', 'tramo', 'mdi mdi-message-alert', true, true, null),
	(8, 'event_type', 'tipo de evento', 'tipo de evento', 'tipo de evento', 'mdi mdi-message-alert', true, true, null),
	(9, 'collision', 'colisión', 'colisión', 'colisión', 'mdi mdi-message-alert', true, true, null),
	(10, 'object_crash', 'choque con objeto', 'choque con objeto', 'choque con objeto', 'mdi mdi-message-alert', true, true, null),
	(11, 'vehicle_crash', 'choque con vehículo', 'choque con vehículo', 'choque con vehículo', 'mdi mdi-message-alert', true, true, null),
	(12, 'relative_location', 'ubicación relativa', 'ubicación relativa', 'ubicación relativa', 'mdi mdi-message-alert', true, true, null),
	(13, 'vehicle_types', 'tipo', 'tipo', 'tipo', 'mdi mdi-message-alert', true, true, null),
	(14, 'vehicle_services', 'servicios', 'servicios', 'servicios', 'mdi mdi-message-alert', true, true, null),
	(15, 'vehicle_consecuences', 'consecuencias', 'consecuencias', 'consecuencias', 'mdi mdi-message-alert', true, true, null),
	(16, 'vehicle_manoeuvres', 'maniobras', 'maniobras', 'maniobras', 'mdi mdi-message-alert', true, true, null),
	(17, 'vehicle_brands', 'marca', 'marca', 'marca', 'mdi mdi-message-alert', true, true, null),
	(18, 'participant_types', 'tipo', 'tipo', 'tipo', 'mdi mdi-message-alert', true, true, null),
	(19, 'participant_injuries', 'lesión', 'lesión', 'lesión', 'mdi mdi-message-alert', true, true, null),
	(20, 'participant_nacionalities', 'nacionalidad', 'nacionalidad', 'nacionalidad', 'mdi mdi-message-alert', true, true, null);
	

	INSERT INTO backoffice.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(0101, 1, 'incident_report_id', 'LBL_ID', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(0102, 1, 'km', 'km', NULL, true, '{"position": 2, "editable": true}'),
	(0103, 1, 'date', 'fecha', NULL, true, '{"position": 3, "editable": true}'),
	(0104, 1, 'event_id', 'evento', NULL, true, '{"position": 4, "editable": true}'),
	(0105, 1, 'side_id', 'lado carretera', NULL, true, '{"position": 5, "editable": true}'),
	(0106, 1, 'stretch_id', 'tramo', NULL, true, '{"position": 6, "editable": true}'),
	(0107, 1, 'event_type_id', 'tipo de evento', NULL, true, '{"position": 7, "editable": true}'),
	(0108, 1, 'collision_id', 'colisión', NULL, true, '{"position": 8, "editable": true}'),
	(0109, 1, 'object_crash_id', 'choque con objeto', NULL, true, '{"position": 9, "editable": true}'),
	(0110, 1, 'vehicle_crash_id', 'choque con vehículo', NULL, true, '{"position": 10, "editable": true}'),
	(0111, 1, 'relative_location_id', 'ubicación relativa', NULL, true, '{"position": 11, "editable": true}'),
	(0112, 1, 'comment', 'redacción', NULL, true, '{"multiline": 5, "position": 11, "editable": true}'),
	 
	(0201, 2, 'incident_report_id', 'LBL_ID', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(0202, 2, 'vehicle_type_id', 'tipo', NULL, true, '{"position": 2, "editable": true}'),
	(0203, 2, 'vehicle_service_id', 'servicio', NULL, true, '{"position": 3, "editable": true}'),
	(0204, 2, 'vehicle_consecuence_id', 'consecuencia', NULL, true, '{"position": 4, "editable": true}'),
	(0205, 2, 'vehicle_manoeuvre_id', 'maniobra', NULL, true, '{"position": 5, "editable": true}'),
	(0206, 2, 'patent', 'patente', NULL, true, '{"position": 6, "editable": true}'),
	(0207, 2, 'vehicle_brand_id', 'marca', NULL, true, '{"position": 7, "editable": true}'),
	
	(0301, 3, 'incident_report_id', 'LBL_ID', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(0302, 3, 'participant_name', 'nombre', NULL, true, '{"position": 2, "editable": true}'),
	(0303, 3, 'participant_rut', 'RUT', NULL, true, '{"position": 3, "editable": true}'),
	(0304, 3, 'participant_age', 'edad', NULL, true, '{"position": 4, "editable": true}'),
	(0305, 3, 'participant_address', 'dirección', NULL, true, '{"position": 5, "editable": true}'),
	(0306, 3, 'participant_vehicle', 'patente', NULL, true, '{"position": 6, "editable": true}'),
	(0307, 3, 'participant_type_id', 'tipo', NULL, true, '{"position": 7, "editable": true}'),
	(0308, 3, 'participant_injury_id', 'lesión', NULL, true, '{"position": 8, "editable": true}'),
	(0309, 3, 'participant_nacionality_id', 'nacionalidad', NULL, true, '{"position": 9, "editable": true}'),

	(0401, 4, 'incident_report_id', 'LBL_ID', NULL, true, '{"position": 1, "editable": true, "refName": true}'),
	(0402, 4, 'side_road', 'lado carretera', NULL, true, '{"position": 2, "editable": true}'),
	(0403, 4, 'lanes', 'pistas', NULL, true, '{"position": 3, "editable": true}'),
	(0404, 4, 'entity', 'servicio apoyo', NULL, true, '{"position": 4, "editable": true}'),
	(0405, 4, 'reasons', 'motivos', NULL, true, '{"position": 5, "editable": true}'),
	(0406, 4, 'comments', 'comentarios', NULL, true, '{"position": 6, "editable": true}');
  END IF;
END $$;