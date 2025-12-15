DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_incidents') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(2, 'incidents', NULL, NULL, 'jdbc:postgresql://10.238.4.128:5432/controltrafico', 'dbo_controltrafico', 'HQK1Ix3eeT1oNm0DK5SeFw==', 'incidents',
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
	INSERT INTO incidents.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata, sql_view, grid_id) VALUES
	(1, 'incidents', 'LBL_INCIDENTS', 'LBL_INCIDENT', 'LBL_INCIDENTS_DESCRIPTION', 'mdi mdi-message-alert', true, true,'{
		"formHeight": 800,
    	"formWidth": 1000,
	  	 "groupImages": [
	      	{
		      "name": "photos",
		      "label": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS",
		      "description": "LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC",
		      "position": 18,
		      "height": 300
	    	},
	      	{
		      "name": "sketch",
		      "label": "LBL_IMAGE_GROUP_SKETCH_ACCIDENT",
		      "description": "LBL_IMAGE_GROUP_SKETCH_ACCIDENT_DESC",
		      "position": 20,
		      "height": 300
	    	}
	  	],
	  	"groupAttachments": [
	    	{
	      	"name": "documents",
		    "label": "LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS",
		    "description": "LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS_DESC",
	      	"position": 19,
	      	"height": 200
	    	}
	  	],
		"initialTabLabel": "LBL_INCIDENT_SECTION_MAIN",
		"srcTemplate": "incidentFormTemplate.html"
	}',null, null),
--	'SELECT 
--		*
--	FROM
--		incidents.incidents_extended', 5000),
	(2, 'assigned_entities', 'LBL_ASSIGNED_ENTITIES', 'LBL_ASSIGNED_ENTITY', 'LBL_ASSIGNED_ENTITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true, NULL, NULL, NULL),
    (3, 'assigned_municipalities', 'LBL_ASSIGNED_MUNICIPALITIES', 'LBL_ASSIGNED_MUNICIPALITY', 'LBL_ASSIGNED_MUNICIPALITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true, NULL, NULL, NULL),
    (4, 'delayed_assigned_entities', 'LBL_ASSIGNED_ENTITIES', 'LBL_ASSIGNED_ENTITY', 'LBL_ASSIGNED_ENTITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true,NULL, NULL, NULL),
    (5, 'delayed_assigned_municipalities', 'LBL_ASSIGNED_MUNICIPALITIES', 'LBL_ASSIGNED_MUNICIPALITY', 'LBL_ASSIGNED_MUNICIPALITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true,NULL, NULL, NULL),
    (6, 'municipalities', 'LBL_ASSIGNED_ENTITIES', 'LBL_ASSIGNED_ENTITY', 'LBL_ASSIGNED_ENTITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true,
	'{
		"referTables": []
	}', NULL, NULL),
	(7, 'entities', 'LBL_ASSIGNED_ENTITIES', 'LBL_ASSIGNED_ENTITY', 'LBL_ASSIGNED_ENTITIES_DESCRIPTION', 'mdi mdi-message-alert', true, true,
	'{
		"referTables": []
	}', NULL, NULL),
	(8, 'vehicle_brands', 'marcas', 'marcas', 'marcas', NULL, false, false,  NULL, NULL, NULL),
	(9, 'vehicle_damages', 'daños', 'daños', 'daños', NULL, false, false,  NULL, NULL, NULL),
	(10, 'vehicle_models', 'modelos', 'modelos', 'modelos', NULL, false, false,  NULL, NULL, NULL),
	(11, 'vehicle_types', 'tipos', 'tipos', 'tipos', NULL, false, false,  NULL, NULL, NULL),
	(12, 'vehicles', 'vehículos', 'vehículos', 'vehículos', NULL, false, false,  NULL, NULL, NULL),
	(13, 'affection_details', 'Detalle afección', 'Detalle afección', 'Detalle afección', NULL, false, false,  NULL, NULL, NULL);
	
	INSERT INTO incidents.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(0101, 1, 'id', 'id', NULL, true, '{"position": 1, "editable": false, "refName": true}'),
	(0102, 1, 'incident_report_id', 'id incidencia', NULL, true, '{"position": 2, "editable": false}'),
	(0103, 1, 'incident_type', 'tipo incidencia', NULL, true, '{"position": 3, "editable": false}'),
	(0104, 1, 'incident_sub_type', 'descripción', NULL, true, '{"position": 4, "editable": false}'),
	(0105, 1, 'level', 'nivel', NULL, true, '{"position": 5, "editable": false}'),
	(0106, 1, 'stretch', 'tramo', NULL, true, '{"position": 6, "editable": false}'),
	(0107, 1, 'location', 'localización', NULL, true, '{"position": 7, "editable": false}'),
	(0108, 1, 'lat', 'latitud', NULL, true, '{"position": 8, "editable": false}'),
	(0109, 1, 'lon', 'longitud', NULL, true, '{"position": 9, "editable": false}'),
	(0110, 1, 'comment', 'comentarios', NULL, true, '{"multiline": 8, "position": 10, "editable": true}'),
	(0111, 1, 'created_at', 'inicio', NULL, true, '{"position": 11, "editable": false}'),
	(0112, 1, 'finish_at', 'final', NULL, true, '{"position": 12, "editable": false}'),
	(0113, 1, 'end_value', 'afección máxima', NULL, true, '{"position": 13, "editable": false}'),
	(0114, 1, 'affection_detail_id', 'detalle afección', NULL, true, '{"position": 14, "editable": true}'),
	(0115, 1, 'deceassed', 'victimas', NULL, true, '{"position": 15, "editable": true}'),
	(0116, 1, 'num_decesseased', 'nº fallecidos', NULL, true, '{"position": 16, "editable": true}'),
	(0117, 1, 'max_affection', 'afección máxima', NULL, true, '{"position": 17, "editable": true}'),
	
	(0201, 2, 'incident_id', 'id incident_id', NULL, true, '{"position": 1, "editable": false, "refName": true}'),
	(0202, 2, 'entity_id', 'entity_id', NULL, true, 
		'{
			"position": 2, 
			"editable": false,
			"simpleMultiInsert": true
		 }'),
	
	(0301, 3, 'incident_id', 'id incident_id', NULL, true, '{"position": 1, "editable": false, "refName": true}'),
	(0302, 3, 'municipality_id', 'municipality_id', NULL, true, 
		'{
			"position": 2, 
			"editable": false,
			"simpleMultiInsert": true
		 }'),

	(1201, 12, 'incident_report_id', NULL, NULL, true, '{"position": 1}'),
	(1202, 12, 'vehicle_type_id', 'tipo', NULL, true, '{"position": 2}'),
	(1203, 12, 'vehicle_brand_id', 'marca', NULL, true, '{"position": 3}'),
	(1204, 12, 'vehicle_model_id', 'modelo', NULL, true, '{"position": 4, "associatedFieldName": "vehicle_brand_id"}'),
	(1205, 12, 'patent', 'matrícula', NULL, true, '{"position":5}'),
	(1206, 12, 'color', 'color', NULL, true, '{"position": 6}'),
	(1207, 12, 'vehicle_damage_id', 'daños', NULL, true, '{"position": 7}'),
	(1208, 12, 'comment', 'comentarios', NULL, false, '{ "multiline": 5, "position": 8}');
	
	-- 
  	-- smartgen.sg_metadata_tasks
  	--
	INSERT INTO incidents.sg_metadata_tasks (id, name, cron_expression, groovy, params) VALUES
	(1, 'MaxAffection', '0 0/1 * ? * * *', 'config/groovy/backoffice/common/task/MaxAffection.groovy', null);
	
  END IF;
END $$;
