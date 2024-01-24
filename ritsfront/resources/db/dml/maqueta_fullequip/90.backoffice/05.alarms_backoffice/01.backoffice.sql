DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'alarms_backoffice') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(5, 'alarms_backoffice', NULL, NULL, 'jdbc:postgresql://192.168.88.71:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'alarms_backoffice',
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
  	-- alarms_backoffice.sg_metadata_tables
  	--
	INSERT INTO alarms_backoffice.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata) VALUES
	(1, 'normalize_procedures', 'LBL_BACKOFFICE_SG_METADATA_TABLES_NORMALIZE_PROCEDURES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_NORMALIZE_PROCEDURES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_NORMALIZE_PROCEDURES_DESCRIPTION', NULL, true, true, NULL),
	(2, 'possible_causes', 'LBL_BACKOFFICE_SG_METADATA_TABLES_POSSIBLE_CAUSES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_POSSIBLE_CAUSES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_POSSIBLE_CAUSES', NULL, true, true, NULL),
	(3, 'resposibilities', 'LBL_BACKOFFICE_SG_METADATA_TABLES_RESPOSIBILITIES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_RESPOSIBILITIES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_RESPOSIBILITIES', NULL, true, true, NULL),
	(4, 'registers', 'LBL_BACKOFFICE_SG_METADATA_TABLES_REGISTERS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_REGISTERS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_REGISTERS', NULL, true, true,
	--metadata
	'{
		"groupImages": [
			{
				"name": "main",
				"label": "LBL_IMAGE_GROUP_ALARMS_IMAGES",
				"description": "LBL_IMAGE_GROUP_ALARMS_IMAGES_DESC",
				"position": 10,
				"height": 300
			}
	  	],
		"groupAttachments": [
			{
				"name": "main",
				"label": "LBL_ATTACHMENT_GROUP_ALARMS",
				"description": "LBL_ATTACHMENT_GROUP_ALARMS_DESC",
				"position": 11,
				"height": 200
	    	}
	  	],
	  	"srcTemplate": "alarmsTemplate.html"
	}');

	-- 
  	-- alarms_backoffice.sg_metadata_columns
  	--
	INSERT INTO alarms_backoffice.sg_metadata_columns (sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	
	(1, 'name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_NORMALIZE_PROCEDURES', NULL, false, NULL),

	(2, 'name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_POSSIBLE_CAUSES', NULL, false, NULL),

	(3, 'name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_RESPONSIBILITIES', NULL, false, NULL),

	(4, 'alarm_id', NULL, NULL, true, '{"formVisible": false, "tableVisible": false}'),
	(4, 'activation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ACTIVATION', NULL, true, '{"editable": false}'),
	(4, 'alarm', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ALARM', NULL, true, '{"editable": false}'),
	(4, 'element_type', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT_TYPE', NULL, true, '{"editable": false}'),
	(4, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, true, '{"editable": false}'),
	(4, 'possible_causes_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_POSSIBLE_CAUSES', NULL, false, '{"refEditButton": false}'),
	(4, 'normalize_procedures_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_NORMALIZE_PROCEDURES', NULL, false, '{"refEditButton": false}'),
	(4, 'responsibilities_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_RESPONSIBILITIES', NULL, false, '{"refEditButton": false}'),
	(4, 'comments', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENTS', NULL, false, '{"multiline": 5}'),
	(4, 'revised_alarm', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_REVISED_ALARMS', NULL, false, NULL);

  END IF;
  
END $$;
