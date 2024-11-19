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
		(1, 'c4_all_ext_entities_with_values', 'c4_all_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, afeccion, carretera, localizacion, fecha, fecha_actualizacion, estado 
				FROM reporting_bo.c4_ext_entities_with_values'
			, NULL, null),
		(2, 'c4_my_ext_entities_with_values', 'c4_my_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, afeccion, carretera, localizacion, fecha, fecha_actualizacion, estado 
				FROM reporting_bo.c4_ext_entities_with_values'
			, 4001, null);
			
			
	INSERT INTO reporting_bo.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
		(0101, 1, 'id', 'LBL_EXT_ENTITY_C4_COLUMN_ID', NULL, true, '{"position": 1, "editable": false, "refName": true}'),
		(0102, 1, 'categoria', 'LBL_EXT_ENTITY_C4_COLUMN_CATEGORY', NULL, true, '{"position": 2, "editable": false, "refName": true}'),
		(0103, 1, 'tipo', 'LBL_EXT_ENTITY_C4_COLUMN_TYPE', NULL, true, '{"position": 3, "editable": false, "refName": true}'),
		(0104, 1, 'titulo', 'LBL_EXT_ENTITY_C4_COLUMN_TITLE', NULL, true, '{"position": 4, "editable": false, "refName": true}'),
		(0105, 1, 'afeccion', 'LBL_EXT_ENTITY_C4_COLUMN_ROAD_IMPACT', NULL, true, '{"position": 5, "editable": false, "refName": true}'),
		(0106, 1, 'carretera', 'LBL_EXT_ENTITY_C4_COLUMN_ROAD', NULL, true, '{"position": 6, "editable": false, "refName": true}'),
		(0107, 1, 'localizacion', 'LBL_EXT_ENTITY_C4_COLUMN_LOCATION', NULL, true, '{"position": 7, "editable": false, "refName": true}'),
		(0108, 1, 'fecha', 'LBL_EXT_ENTITY_C4_COLUMN_DATE', NULL, true, '{"position": 8, "editable": false, "refName": true}'),
		(0109, 1, 'fecha_actualizacion', 'LBL_EXT_ENTITY_C4_UPDATE_DATE', NULL, true, '{"position": 9, "editable": false, "refName": true}'),
		(0110, 1, 'estado', 'LBL_EXT_ENTITY_C4_COLUMN_STATE', NULL, true, '{"position": 10, "editable": false, "refName": true}');
		
		
  END IF;
END $$;
