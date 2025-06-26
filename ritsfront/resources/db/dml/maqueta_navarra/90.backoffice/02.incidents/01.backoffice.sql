DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(2, 'incidents', NULL, NULL, 'jdbc:postgresql://192.168.88.160:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'incidents',
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
	(1, 'incidents', 'LBL_INCIDENTS', 'LBL_INCIDENT', 'LBL_INCIDENTS_DESCRIPTION', 'mdi mdi-message-alert', true, true,'{
		"formHeight": 800,
    	"formWidth": 1000,
	  	 "groupImages": [
	      	{
		      "name": "photos",
		      "label": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS",
		      "description": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC",
		      "position": 8,
		      "height": 300
	    	},
	      	{
		      "name": "sketch",
		      "label": "LBL_IMAGE_GROUP_SKETCH_ACCIDENT",
		      "description": "LBL_IMAGE_GROUP_SKETCH_ACCIDENT_DESC",
		      "position": 10,
		      "height": 300
	    	}
	  	],
	  	"groupAttachments": [
	    	{
	      	"name": "documents",
		    "label": "LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS",
		    "description": "LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS_DESC",
	      	"position": 9,
	      	"height": 200
	    	}
	  	],
		"initialTabLabel": "LBL_INCIDENT_SECTION_MAIN",
		"srcTemplate": "incidentFormTemplate.html"
	}'),
	(2, 'assigned_entities', 'LBL_ASSIGNED_ENTITIES', 'LBL_ASSIGNED_ENTITY', 'LBL_ASSIGNED_ENTITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true, NULL),
    (3, 'assigned_municipalities', 'LBL_ASSIGNED_MUNICIPALITIES', 'LBL_ASSIGNED_MUNICIPALITY', 'LBL_ASSIGNED_MUNICIPALITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true,NULL),
    (4, 'delayed_assigned_entities', 'LBL_ASSIGNED_ENTITIES', 'LBL_ASSIGNED_ENTITY', 'LBL_ASSIGNED_ENTITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true,NULL),
    (5, 'delayed_assigned_municipalities', 'LBL_ASSIGNED_MUNICIPALITIES', 'LBL_ASSIGNED_MUNICIPALITY', 'LBL_ASSIGNED_MUNICIPALITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true,NULL),
    (6, 'municipalities', 'LBL_ASSIGNED_ENTITIES', 'LBL_ASSIGNED_ENTITY', 'LBL_ASSIGNED_ENTITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true,
	'{
		"referTables": []
	}'),
	(7, 'entities', 'LBL_ASSIGNED_ENTITIES', 'LBL_ASSIGNED_ENTITY', 'LBL_ASSIGNED_ENTITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true,
	'{
		"referTables": []
	}');
	
	INSERT INTO incidents.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(0101, 1, 'incident_report_id', 'id incidencia', NULL, true, '{"position": 1, "editable": false, "refName": true}'),
	(0102, 1, 'incident_type', 'tipo incidencia', NULL, true, '{"position": 2, "editable": false}'),
	(0103, 1, 'incident_sub_type', 'subtipo incidencia', NULL, true, '{"position": 3, "editable": false}'),
	(0104, 1, 'level', 'nivel', NULL, true, '{"position": 4, "editable": false}'),
	(0105, 1, 'location', 'localización', NULL, true, '{"position": 5, "editable": false}'),
	(0106, 1, 'comment', 'comentarios', NULL, true, '{"multiline": 8, "position": 6, "editable": true}'),
	(0107, 1, 'created_at', 'fecha', NULL, true, '{"position": 7, "editable": false}');
  END IF;
END $$;
