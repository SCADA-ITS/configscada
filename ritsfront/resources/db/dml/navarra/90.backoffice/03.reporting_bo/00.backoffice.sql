DO $$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(1, 'reporting_bo', NULL, NULL, 'jdbc:postgresql://10.253.5.80:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'reporting_bo',
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
		(1, 'c4_ext_entities_with_values', 'c4_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, affecion, carretera, localizacion, fecha, fecha_actualizacion, estado, created_at 
				FROM reporting_bo.c4_ext_entities_with_values'
			, NULL, null),
		(2, 'c4_works_ext_entities_with_values', 'c4_works_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, affecion, carretera, localizacion, fecha, fecha_actualizacion, estado, created_at 
				FROM reporting_bo.c4_ext_entities_with_values where categoria = ''Obras'''
			, NULL, null),
		(3, 'c4_weather_ext_entities_with_values', 'c4_weather_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, affecion, carretera, localizacion, fecha, fecha_actualizacion, estado, created_at 
				FROM reporting_bo.c4_ext_entities_with_values where categoria = ''Meteorológicas'''
			, NULL, null),
		(4, 'c4_road_ext_entities_with_values', 'c4_road_ext_entities_with_values', 'LBL_EXT_ENTITY_C4', 'LBL_EXT_ENTITY_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, affecion, carretera, localizacion, fecha, fecha_actualizacion, estado, created_at 
				FROM reporting_bo.c4_ext_entities_with_values where categoria = ''Red Viaria'''
			, NULL, null);
			
  END IF;
END $$;