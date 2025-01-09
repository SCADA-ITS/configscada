DO $$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(1, 'reporting_bo', NULL, NULL, 'jdbc:postgresql://192.168.88.160:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'reporting_bo',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 700,
    	"formWidth": 900,
    	"tableHeight": 700,
    	"tableWidth": 900,
    	"showIdForm": true,
    	"showIdTable": true,
    	"showIdSearch": false,
    	"gridFilters": true,
		"editable": false
	}',
	-- default_metadata_column
	'{
	    "required": false,
	    "editable": false,
	    "refAddButton": false
	}',
	true);
	
	INSERT INTO reporting_bo.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) 
	VALUES
		(1, 'c4_all_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, afeccion, carretera, localizacion, fecha, fecha_actualizacion, estado 
				FROM reporting_bo.c4_ext_entities_with_values'
			, 4001, null),
		(2, 'Incidencias', 'LBL_EXT_ENTITY_112', 'LBL_EXT_ENTITY_112', 'LBL_EXT_ENTITY_112_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, localizacion, fecha, estado 
				FROM reporting_bo.e112_ext_entities_with_values'
			, 4002, null),
		(3, 'Recursos', 'LBL_EXT_ENTITY_TYPE_112_RESOURCES', 'LBL_EXT_ENTITY_TYPE_112_RESOURCES', 'LBL_EXT_ENTITY_TYPE_112_RESOURCES_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id_incidente,nombre,fecha_salida,agencia,estacion,estado,fecha_ultimo_estado FROM reporting_bo.e112_resources_ext_entities'
			, 4003, null),
		(4, 'Vehículos', 'LBL_EXT_ENTITY_TYPE_112_VEHICLES', 'LBL_EXT_ENTITY_TYPE_112_VEHICLES', 'LBL_EXT_ENTITY_TYPE_112_VEHICLES_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id_incidente,matricula,marca,modelo,color FROM reporting_bo.e112_vehicles_ext_entities'
			, 4004, null),	
		(5, 'all_waze_traffic_alert_ext_entities_with_values', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_ALERT', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_ALERT', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_ALERT_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT * FROM reporting_bo.waze_traffic_alert_ext_entities_with_values'
			, NULL, null),
		(6, 'all_waze_traffic_jam_ext_entities_with_values', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_JAM', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_JAM', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_JAM_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT * FROM reporting_bo.waze_traffic_jam_ext_entities_with_values'
			, NULL, null);			
			
	INSERT INTO reporting_bo.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata, ref_view_column_id) VALUES
		(0101, 1, 'id', 'LBL_EXT_ENTITY_C4_COLUMN_ID', NULL, true, '{"position": 1, "editable": false, "refName": true}', NULL),
		(0102, 1, 'categoria', 'LBL_EXT_ENTITY_C4_COLUMN_CATEGORY', NULL, true, '{"position": 2, "editable": false}', NULL),
		(0103, 1, 'tipo', 'LBL_EXT_ENTITY_C4_COLUMN_TYPE', NULL, true, '{"position": 3, "editable": false}', NULL),
		(0104, 1, 'titulo', 'LBL_EXT_ENTITY_C4_COLUMN_TITLE', NULL, true, '{"position": 4, "editable": false}', NULL),
		(0105, 1, 'afeccion', 'LBL_EXT_ENTITY_C4_COLUMN_ROAD_IMPACT', NULL, true, '{"position": 5, "editable": false}', NULL),
		(0106, 1, 'carretera', 'LBL_EXT_ENTITY_C4_COLUMN_ROAD', NULL, true, '{"position": 6, "editable": false}', NULL),
		(0107, 1, 'localizacion', 'LBL_EXT_ENTITY_C4_COLUMN_LOCATION', NULL, true, '{"position": 7, "editable": false}', NULL),
		(0108, 1, 'fecha', 'LBL_EXT_ENTITY_C4_COLUMN_DATE', NULL, true, '{"position": 8, "editable": false}', NULL),
		(0109, 1, 'fecha_actualizacion', 'LBL_EXT_ENTITY_C4_UPDATE_DATE', NULL, true, '{"position": 9, "editable": false}', NULL),
		(0110, 1, 'estado', 'LBL_EXT_ENTITY_C4_COLUMN_STATE', NULL, true, '{"position": 10, "editable": false}', NULL),
		
		(0201, 2, 'id', 'LBL_EXT_ENTITY_112_COLUMN_ID', NULL, true, '{"position": 1, "editable": false, "refName": true}', NULL),
		(0202, 2, 'categoria', 'LBL_EXT_ENTITY_112_COLUMN_ID', NULL, true, '{"position": 2, "editable": false}', NULL),
		(0203, 2, 'tipo', 'LBL_EXT_ENTITY_112_COLUMN_TYPE', NULL, true, '{"position": 3, "editable": false}', NULL),
		(0204, 2, 'localizacion', 'LBL_EXT_ENTITY_112_COLUMN_LOCATION', NULL, true, '{"position": 4, "editable": false}', NULL),
		(0205, 2, 'fecha', 'LBL_EXT_ENTITY_112_COLUMN_DATE', NULL, true, '{"position": 5, "editable": false}', NULL),
		(0206, 2, 'estado', 'LBL_EXT_ENTITY_112_COLUMN_STATE', NULL, true, '{"position": 6, "editable": false}', NULL),
		
		(0501, 3, 'id_incidente', 'id_incidente', NULL, true, '{"position": 1, "editable": false, "refName": true}', 201),
		(0502, 3, 'nombre', 'nombre', NULL, true, '{"position": 2, "editable": false, "refName": true}', NULL),
		(0503, 3, 'fecha_salida', 'fecha_salida', NULL, true, '{"position": 3, "editable": false}', NULL),
		(0504, 3, 'agencia', 'agencia', NULL, true, '{"position": 4, "editable": false}', NULL),
		(0505, 3, 'estacion', 'estacion', NULL, true, '{"position": 5, "editable": false}', NULL),
		(0506, 3, 'estado', 'estado', NULL, true, '{"position": 6, "editable": false}', NULL),
		(0507, 3, 'fecha_ultimo_estado', 'fecha_ultimo_estado', NULL, true, '{"position": 7, "editable": false}', NULL),
		
		(0601, 4, 'id_incidente', 'id_incidente', NULL, true, '{"position": 1, "editable": false, "refName": true}', 201),
		(0602, 4, 'matricula', 'matricula', NULL, true, '{"position": 2, "editable": false}', NULL),
		(0603, 4, 'marca', 'marca', NULL, true, '{"position": 3, "editable": false}', NULL),
		(0604, 4, 'modelo', 'modelo', NULL, true, '{"position": 4, "editable": false}', NULL),
		(0605, 4, 'color', 'color', NULL, true, '{"position": 5, "editable": false}', NULL);
  END IF;
END $$;
