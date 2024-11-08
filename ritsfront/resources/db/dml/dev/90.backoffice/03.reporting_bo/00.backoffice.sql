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
    	"showIdTable": false,
    	"showIdSearch": false,
    	"gridFilters": true
	}',
	-- default_metadata_column
	'{
	    "required": false,
	    "editable": false,
	    "refAddButton": false
	}',
	true);
	
INSERT INTO reporting_bo.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
		(1, 'c4_ext_entities_with_values', 'c4_ext_entities_with_values', 'LBL_EXT_ENTIES_C4', 'LBL_EXT_ENTIES_C4_DESCRIPTION', NULL, false, false, 
			-- sql_view 
			'SELECT id, categoria, tipo, titulo, affecion, carretera, localizacion, fecha, fecha_actualizacion, estado, created_at 
				FROM reporting_bo.c4_ext_entities_with_values'
			, NULL, null);
			
  END IF;
END $$;