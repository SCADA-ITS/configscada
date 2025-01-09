DO $$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(3, 'reporting_bo', NULL, NULL, 'jdbc:postgresql://localhost:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'reporting_bo',
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
	

			
INSERT INTO reporting_bo.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
		(1, 'c4_all_ext_entities_with_values', 'c4_all_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, afeccion, carretera, localizacion, fecha, fecha_actualizacion, estado 
				FROM reporting_bo.c4_ext_entities_with_values'
			, NULL, null),
		(2, 'c4_works_ext_entities_with_values', 'c4_works_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, afeccion, carretera, localizacion, fecha, fecha_actualizacion, estado 
				FROM reporting_bo.c4_ext_entities_with_values where categoria = ''Obras'''
			, NULL, null),
		(3, 'c4_weather_ext_entities_with_values', 'c4_weather_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, afeccion, carretera, localizacion, fecha, fecha_actualizacion, estado 
				FROM reporting_bo.c4_ext_entities_with_values where categoria = ''Meteorológicas'''
			, NULL, null),
		(4, 'all_112_ext_entities_with_values', 'all_112_ext_entities_with_values', 'LBL_EXT_ENTITY_TYPE_112', 'LBL_EXT_ENTITY_TYPE_112_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT * FROM reporting_bo.e112_ext_entities_with_values'
			, NULL, null),		
		(5, 'all_112_resources_ext_entities_with_values', 'all_112_resources_ext_entities_with_values', 'LBL_EXT_ENTITY_TYPE_112_RESOURCES', 'LBL_EXT_ENTITY_TYPE_112_RESOURCES_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT * FROM reporting_bo.e112_resources_ext_entities'
			, NULL, null),
		(6, 'all_112_vehicles_ext_entities_with_values', 'all_112_vehicles_ext_entities_with_values', 'LBL_EXT_ENTITY_TYPE_112_VEHICLES', 'LBL_EXT_ENTITY_TYPE_112_VEHICLES_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT * FROM reporting_bo.e112_vehicles_ext_entities'
			, NULL, null),		
		(7, 'c4_road_ext_entities_with_values', 'c4_road_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, afeccion, carretera, localizacion, fecha, fecha_actualizacion, estado
				FROM reporting_bo.c4_ext_entities_with_values where categoria = ''Red Viaria'''
			, NULL, null),
		(8, 'all_waze_traffic_alert_ext_entities_with_values', 'all_waze_traffic_alert_ext_entities_with_values', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_ALERT', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_ALERT_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT * FROM reporting_bo.waze_traffic_alert_ext_entities_with_values'
			, NULL, null),
		(9, 'all_waze_traffic_jam_ext_entities_with_values', 'all_waze_traffic_jam_ext_entities_with_values', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_JAM', 'LBL_EXT_ENTITY_WAZE_TRAFFIC_JAM_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT * FROM reporting_bo.waze_traffic_jam_ext_entities_with_values'
			, NULL, null);	
			
			
	INSERT INTO reporting_bo.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata, ref_view_column_id) VALUES
		(0401, 4, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', NULL),
		(0402, 4, 'uid', 'uid', NULL, true, '{"position": 2, "editable": false, "refName": true}', NULL),
		(0403, 4, 'categoria', 'categoria', NULL, true, '{"position": 3, "editable": false, "refName": true}', NULL),
		(0404, 4, 'tipo', 'tipo', NULL, true, '{"position": 4, "editable": false, "refName": true}', NULL),
		(0405, 4, 'fecha', 'fecha', NULL, true, '{"position": 5, "editable": false, "refName": true}', NULL),
		(0406, 4, 'localizacion', 'localizacion', NULL, true, '{"position": 6, "editable": false, "refName": true}', NULL),
		(0407, 4, 'estado', 'estado', NULL, true, '{"position": 7, "editable": false, "refName": true}', NULL),
		
		(0501, 5, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', NULL),
		(0502, 5, 'uid', 'uid', NULL, true, '{"position": 2, "editable": false, "refName": true}', NULL),
		(0503, 5, 'id_incidente', 'id_incidente', NULL, true, '{"position": 3, "editable": false, "refName": true}', 401),
		(0504, 5, 'nombre', 'nombre', NULL, true, '{"position": 4, "editable": false, "refName": true}', NULL),
		(0505, 5, 'fecha_salida', 'fecha_salida', NULL, true, '{"position": 5, "editable": false, "refName": true}', NULL),
		(0506, 5, 'agencia', 'agencia', NULL, true, '{"position": 6, "editable": false, "refName": true}', NULL),
		(0507, 5, 'estacion', 'estacion', NULL, true, '{"position": 7, "editable": false, "refName": true}', NULL),
		(0508, 5, 'estado', 'estado', NULL, true, '{"position": 8, "editable": false, "refName": true}', NULL),
		(0509, 5, 'fecha_ultimo_estado', 'fecha_ultimo_estado', NULL, true, '{"position": 9, "editable": false, "refName": true}', NULL),
		
		(0601, 6, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}', NULL),
		(0602, 6, 'uid', 'uid', NULL, true, '{"position": 2, "editable": false, "refName": true}', NULL),
		(0603, 6, 'id_incidente', 'id_incidente', NULL, true, '{"position": 3, "editable": false, "refName": true}', 401),
		(0604, 6, 'matricula', 'matricula', NULL, true, '{"position": 4, "editable": false, "refName": true}', NULL),
		(0605, 6, 'marca', 'marca', NULL, true, '{"position": 5, "editable": false, "refName": true}', NULL),
		(0606, 6, 'modelo', 'modelo', NULL, true, '{"position": 6, "editable": false, "refName": true}', NULL),
		(0607, 6, 'color', 'color', NULL, true, '{"position": 7, "editable": false, "refName": true}', NULL);
  END IF;
END $$;