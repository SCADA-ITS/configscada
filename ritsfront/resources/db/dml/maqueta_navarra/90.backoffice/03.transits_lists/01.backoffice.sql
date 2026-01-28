DO $$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(3, 'transits_bo', NULL, NULL, 'jdbc:postgresql://192.168.88.160:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'transits_bo',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 700,
    	"formWidth": 900,
    	"tableHeight": 700,
    	"tableWidth": 900,
    	"gridFilters": true,
		"editable": true,
		"importable": true
	}',
	-- default_metadata_column
	'{
	    "required": false,
	    "editable": false,
	    "refAddButton": false
	}',
	true);
	
	INSERT INTO transits_bo.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) 
	VALUES
		(1, 'white_list', 'LBL_WHITE_LIST', 'LBL_WHITE_LIST', 'LBL_WHITE_LIST', NULL, false, false, null, null, null);			
			
	--INSERT INTO reporting_bo.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata, ref_view_column_id) VALUES
	--	(0101, 1, 'id', 'LBL_EXT_ENTITY_C4_COLUMN_ID', NULL, true, '{"position": 1, "editable": false, "refName": true}', NULL);
  END IF;
END $$;
