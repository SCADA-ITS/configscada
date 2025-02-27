DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(2, 'incidents_gip', NULL, NULL, 'jdbc:postgresql://192.168.88.160:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'incidents_gip',
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
	INSERT INTO nogales_gip.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata) VALUES
	(1, 'incidents', 'LBL_INCIDENTS', 'LBL_INCIDENT', 'LBL_INCIDENTS_DESCRIPTION', 'mdi mdi-message-alert', true, true, 
	'{
		"formHeight": 800,
    	"formWidth": 1000,
	  	 "groupImages": [
	      	{
		      "name": "photos",
		      "label": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS",
		      "description": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC",
		      "position": 13,
		      "height": 300
	    	},
	      	{
		      "name": "sketch",
		      "label": "LBL_IMAGE_GROUP_SKETCH_ACCIDENT",
		      "description": "LBL_IMAGE_GROUP_SKETCH_ACCIDENT_DESC",
		      "position": 15,
		      "height": 300
	    	}
	  	],
	  	"groupAttachments": [
	    	{
	      	"name": "documents",
		    "label": "LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS",
		    "description": "LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS_DESC",
	      	"position": 14,
	      	"height": 200
	    	}
	  	],
		"initialTabLabel": "LBL_INCIDENT_SECTION_MAIN",
		"srcTemplate": "incidentFormTemplate.html"
	}');

	
	INSERT INTO nogales_gip.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(1201, 1, 'incident_report_id', 'LBL_ID', NULL, true, '{"position": 1, "editable": false, "refName": true}'),
	(1202, 1, 'incident_type', 'LBL_INCIDENT_TYPE', NULL, true, '{"position": 2, "editable": false,  "badgeInHeader": "mdi mdi-exclamation-thick"}'),
	(1203, 1, 'incident_sub_type', 'LBL_INCIDENT_SUB_TYPE', NULL, false, '{"position": 3, "editable": false, "badgeInHeader": "mdi mdi-exclamation-thick"}'),
	(1204, 1, 'level', 'LBL_LEVEL', NULL, true, '{"position": 4, "editable": false}'),
	(1205, 1, 'location', 'LBL_LOCATION', NULL, false, '{ "position": 5, "editable": false, "badgeInHeader": "mdi mdi-map-marker"}');
  END IF;
END $$;
