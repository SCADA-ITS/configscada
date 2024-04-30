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
		      "position": 9,
		      "height": 300
	    	}
	  	],
		"srcTemplate": "elementFormTemplate.html"}');




	INSERT INTO dgt_gip.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(101, 1, 'conductor', 'LBL_conductor', NULL, true, '{"position": 1, "editable": false, "badgeInHeader": "mdi mdi-account"}'),
	(102, 1, 'itv', 'LBL_itv', NULL, true, '{"position": 2, "editable": false}'),
	(103, 1, 'seguro', 'LBL_seguro', NULL, true, '{"position": 3, "editable": false}'),
	(104, 1, 'marca_vehiculo_id', 'LBL_marca_vehiculo', NULL, true, '{"position": 4, "editable": true, "refEditButton": true}'),
	(105, 1, 'modelo_vehiculo_id', 'LBL_modelo_vehiculo_id', NULL, true, '{"position": 5, "editable": true, "associatedFieldName": "marca_vehiculo_id"}'),
	(106, 1, 'categoria_vehiculo', 'LBL_categoria_vehiculo', NULL, true, '{"position": 6, "editable": false}'),
	(107, 1, 'nacionalidad_vehiculo', 'LBL_nacionalidad_vehiculo', NULL, true, '{"position": 7, "editable": false}'),
	(108, 1, 'matricula', 'LBL_matricula', NULL, true, '{"position": 8, "editable": true, "badgeInHeader": "mdi mdi-information"}'),
	(110, 1, 'valoracion', 'LBL_valoracion', NULL, true, '{"position": 10, "editable": false,"formVisible":false}'),
	(111, 1, 'fecha', 'LBL_fecha', NULL, true, '{"position": 10, "editable": false}');

	INSERT INTO dgt_gip.sg_metadata_table_commands (id, sg_metadata_table_id, "name", "label", label_description, mdi_icon, require_confirmation, available_in_form, available_in_table, groovy, custom_js) VALUES
	(1, 1, 'Confirmar datos', 'LBL_CONFIRMAR_DATOS', 'LBL_CONFIRMAR_DATOS', 'mdi mdi-checkbox-marked', true, true, false, 'config/groovy/backoffice/dgt_gip/command/ConfirmCommand.groovy', null);


  END IF;
END $$;
