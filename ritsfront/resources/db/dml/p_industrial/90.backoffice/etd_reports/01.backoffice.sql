DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'hist') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(3, 'etd_reports', NULL, NULL, 'jdbc:sqlserver://10.140.31.104:1433;databaseName=PuenteIndustrial', 'sa', 'TkMFCQOQzi92f6CXerDDUg==', 'dbo',
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
	INSERT INTO reports.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
	(3, 'ETD_hist_data', 'Histórico datos ETD', 'Histórico datos ETD', 'Histórico datos ETD', NULL, false, false, 
			-- sql_view 
			'SELECT 
			    id,
			    NroCarril, 
				Fecha,
				Hora,
				Volumen
			FROM dbo.Reports r
			ORDER BY Fecha desc, Hora desc'
			, null, null);
  END IF;
END $$;
