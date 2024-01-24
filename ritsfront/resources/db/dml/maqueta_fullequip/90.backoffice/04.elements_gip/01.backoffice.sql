DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'elements_gip') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(4, 'elements_gip', NULL, NULL, 'jdbc:postgresql://192.168.88.71:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'elements_gip',
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
	INSERT INTO elements_gip.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata) VALUES
	(1,'partes_trabajos', 'LBL_PARTES', 'LBL_PARTES', 'LBL_PARTES_DESCRIPTION', null, true, false, '{"groupImages": [
	      	{
		      "name": "photos",
		      "label": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS",
		      "description": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC",
		      "position": 12,
		      "height": 300
	    	}
	  	],
		"srcTemplate": "elementFormTemplate.html"}');


	INSERT INTO elements_gip.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(101, 1, 'id', 'LBL_ID', null, false, '{"position": 1, "editable":false, "refName": true}'),
	(102, 1, 'id_element',null, null, false, '{"formVisible":false, "tableVisible":false}');
	--(103, 1, 'Elements',null, null, false, null);


	--INSERT INTO elements_gip.sg_metadata_column_fillers (id, sg_metadata_column_id, "name", groovy) VALUES
	--(1, 103, 'Elements', 'config/groovy/backoffice/partes_trabajos/filler/automaticElement.groovy');
--

  END IF;
END $$;
