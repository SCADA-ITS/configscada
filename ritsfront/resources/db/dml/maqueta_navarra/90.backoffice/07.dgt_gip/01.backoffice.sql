DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'dgt_gip') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(7, 'dgt_gip', NULL, NULL, 'jdbc:postgresql://192.168.88.131:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'dgt_gip',
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
	INSERT INTO dgt_gip.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata) VALUES
	(1,'transitos', 'LBL_DGT', 'LBL_DGT', 'LBL_DGT_DESCRIPTION', null, true, false, '{"groupImages": [
	      	{
		      "name": "photos",
		      "label": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS",
		      "description": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC",
		      "position": 11,
		      "height": 300
	    	}
	  	],
		"srcTemplate": "elementFormTemplate.html"}');

  END IF;
END $$;
