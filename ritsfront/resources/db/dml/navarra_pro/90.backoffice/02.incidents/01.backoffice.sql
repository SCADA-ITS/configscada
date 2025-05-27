DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_incidents') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(2, 'incidents', NULL, NULL, 'jdbc:postgresql://10.253.4.23:5432/controltrafico', 'dbo_controltrafico', 'ftNpUo1PR5Pmu1iNfim/Tw==', 'incidents',
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
	INSERT INTO incidents.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata) VALUES
	(1, 'incidents', 'LBL_INCIDENTS', 'LBL_INCIDENT', 'LBL_INCIDENTS_DESCRIPTION', 'mdi mdi-message-alert', true, true,NULL);

	
	INSERT INTO incidents.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(0101, 1, 'incident_report_id', 'id incidencia', NULL, true, '{"position": 1, "editable": false, "refName": true}'),
	(0102, 1, 'incident_type', 'tipo incidencia', NULL, true, '{"position": 2, "editable": false}'),
	(0103, 1, 'incident_sub_type', 'subtipo incidencia', NULL, true, '{"position": 3, "editable": false}'),
	(0104, 1, 'level', 'nivel', NULL, true, '{"position": 4, "editable": false}'),
	(0105, 1, 'location', 'localización', NULL, true, '{"position": 5, "editable": false}'),
	(0106, 1, 'municipalities', 'municipios', NULL, true, '{"position": 6, "editable": false}'),
	(0107, 1, 'requesting_entities', 'entidad solicitante', NULL, true, '{"position": 7, "editable": false}'),
	(0108, 1, 'comment', 'comentarios', NULL, true, '{"position": 8, "editable": false}'),
	(0109, 1, 'created_at', 'fecha', NULL, true, '{"position": 9, "editable": false}');
  END IF;
END $$;
