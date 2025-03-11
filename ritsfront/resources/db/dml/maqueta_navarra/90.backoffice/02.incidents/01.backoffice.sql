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
	(1, 'actions', 'LBL_ACTIONS', 'LBL_ACTION', 'LBL_ACTIONS_DESCRIPTION', NULL, false, false, NULL),
	(2, 'agents', 'LBL_AGENTS', 'LBL_AGENT', 'LBL_AGENTS_DESCRIPTION', NULL, false, false, NULL),
	(3, 'assigned_pdas', 'LBL_ASSIGNED_PDAS', 'LBL_ASSIGNED_PDA', 'LBL_ASSIGNED_PDAS_DESCRIPTION', NULL, false, false, NULL),
	(4, 'assigned_resources', 'LBL_ASSIGNED_RESOURCES', 'LBL_ASSIGNED_RESOURCE', 'LBL_ASSIGNED_RESOURCES_DESCRIPTION', NULL, false, false, NULL),
	(5, 'assistance_types', 'LBL_ASSISTANCE_TYPES', 'LBL_ASSISTANCE_TYPE', 'LBL_ASSISTANCE_TYPES_DESCRIPTION', NULL, false, false, NULL),
	--(6, 'atmospheric_states', 'LBL_ATMOSPHERIC_STATES', 'LBL_ATMOSPHERIC_STATE', 'LBL_ATMOSPHERIC_STATES_DESCRIPTION', NULL, false, false, NULL),
	(7, 'attendeds', 'LBL_ATTENDEDS', 'LBL_ATTENDED', 'LBL_ATTENDEDS_DESCRIPTION', NULL, false, false, NULL),
	(8, 'causes', 'LBL_CAUSES', 'LBL_CAUSE', 'LBL_CAUSES_DESCRIPTION', NULL, false, false, NULL),
	(9, 'conditions', 'LBL_CONDITIONS', 'LBL_CONDITION', 'LBL_CONDITIONS_DESCRIPTION', NULL, false, false, NULL),
	(10, 'elements', 'LBL_ELEMENTS', 'LBL_ELEMENT', 'LBL_ELEMENTS_DESCRIPTION', NULL, false, false, NULL),
	(11, 'genders', 'LBL_GENDERS', 'LBL_GENDER', 'LBL_GENDERS_DESCRIPTION', NULL, false, false, NULL),
	(12, 'incidents', 'LBL_INCIDENTS', 'LBL_INCIDENT', 'LBL_INCIDENTS_DESCRIPTION', 'mdi mdi-message-alert', true, true, 
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
	}'),
	(13, 'infrastructure_damages', 'LBL_INFRASTRUCTURE_DAMAGES', 'LBL_INFRASTRUCTURE_DAMAGE', 'LBL_INFRASTRUCTURE_DAMAGES_DESCRIPTION', NULL, false, false, NULL),
	(14, 'injury_levels', 'LBL_INJURY_LEVELS', 'LBL_INJURY_LEVEL', 'LBL_INJURY_LEVELS_DESCRIPTION', NULL, false, false, NULL),
	(15, 'injury_types', 'LBL_INJURY_TYPES', 'LBL_INJURY_TYPE', 'LBL_INJURY_TYPES_DESCRIPTION', NULL, false, false, NULL),
	--(16, 'interrupt_agents', 'LBL_INTERRUPT_AGENTS', 'LBL_INTERRUPT_AGENT', 'LBL_INTERRUPT_AGENTS_DESCRIPTION', NULL, false, false, NULL),
	--(17, 'lanes', 'LBL_LANES', 'LBL_LANE', 'LBL_LANES_DESCRIPTION', NULL, false, false, NULL),
	--(18, 'lanes_actions', 'LBL_LANES_ACTIONS', 'LBL_LANES_ACTION', 'LBL_LANES_ACTIONS_DESCRIPTION', NULL, false, false, NULL),
	--(19, 'lanes_states', 'LBL_LANES_STATES', 'LBL_LANES_STATE', 'LBL_LANES_STATES_DESCRIPTION', NULL, false, false, NULL),
	--(20, 'luminosities', 'LBL_LUMINOSITIES', 'LBL_LUMINOSITIE', 'LBL_LUMINOSITIES_DESCRIPTION', NULL, false, false, NULL),
	(21, 'maneuvers', 'LBL_MANEUVERS', 'LBL_MANEUVER', 'LBL_MANEUVERS_DESCRIPTION', NULL, false, false, NULL),
	(22, 'participant_assistance_types', 'LBL_PARTICIPANT_ASSISTANCE_TYPES', 'LBL_PARTICIPANT_ASSISTANCE_TYPE', 'LBL_PARTICIPANT_ASSISTANCE_TYPES_DESCRIPTION', NULL, false, false, NULL),
	(23, 'participant_transfer_places', 'LBL_PARTICIPANT_TRANSFER_PLACES', 'LBL_PARTICIPANT_TRANSFER_PLACE', 'LBL_PARTICIPANT_TRANSFER_PLACES_DESCRIPTION', NULL, false, false, NULL),
	--(24, 'participant_types', 'LBL_PARTICIPANT_TYPES', 'LBL_PARTICIPANT_TYPE', 'LBL_PARTICIPANT_TYPES_DESCRIPTION', NULL, false, false, NULL),
	(25, 'participants', 'LBL_PARTICIPANTS', 'LBL_PARTICIPANT', 'LBL_PARTICIPANTS_DESCRIPTION', NULL, false, false, NULL),
	(26, 'pdas', 'LBL_PDAS', 'LBL_PDA', 'LBL_PDAS_DESCRIPTION', NULL, false, false, NULL),
	(27, 'resources', 'LBL_RESOURCES', 'LBL_RESOURCE', 'LBL_RESOURCES_DESCRIPTION', NULL, false, false, NULL),
	--(28, 'road_impacts', 'LBL_ROAD_IMPACTS', 'LBL_ROAD_IMPACT', 'LBL_ROAD_IMPACTS_DESCRIPTION', NULL, false, false, NULL),
	(29, 'sources', 'LBL_SOURCES', 'LBL_SOURCE', 'LBL_SOURCES_DESCRIPTION', NULL, false, false, NULL),
	(30, 'stages', 'LBL_STAGES', 'LBL_STAGE', 'LBL_STAGES_DESCRIPTION', NULL, false, false, NULL),
	(31, 'transfer_places', 'LBL_TRANSFER_PLACES', 'LBL_TRANSFER_PLACE', 'LBL_TRANSFER_PLACES_DESCRIPTION', NULL, false, false, NULL),
	(32, 'typologies', 'LBL_TYPOLOGIES', 'LBL_TYPOLOGIE', 'LBL_TYPOLOGIES_DESCRIPTION', NULL, false, false, NULL),
	--(33, 'unavailable_lanes', 'LBL_UNAVAILABLE_LANES', 'LBL_UNAVAILABLE_LANE', 'LBL_UNAVAILABLE_LANES_DESCRIPTION', NULL, false, false, NULL),
	(34, 'user_types', 'LBL_USER_TYPES', 'LBL_USER_TYPE', 'LBL_USER_TYPES_DESCRIPTION', NULL, false, false, NULL),
	(35, 'vehicle_brands', 'LBL_VEHICLE_BRANDS', 'LBL_VEHICLE_BRAND', 'LBL_VEHICLE_BRANDS_DESCRIPTION', NULL, false, false, NULL),
	(36, 'vehicle_damages', 'LBL_VEHICLE_DAMAGES', 'LBL_VEHICLE_DAMAGE', 'LBL_VEHICLE_DAMAGES_DESCRIPTION', NULL, false, false, NULL),
	(37, 'vehicle_models', 'LBL_VEHICLE_MODELS', 'LBL_VEHICLE_MODEL', 'LBL_VEHICLE_MODELS_DESCRIPTION', NULL, false, false, NULL),
	(38, 'vehicle_types', 'LBL_VEHICLE_TYPES', 'LBL_VEHICLE_TYPE', 'LBL_VEHICLE_TYPES_DESCRIPTION', NULL, false, false, NULL),
	(39, 'vehicles', 'LBL_VEHICLES', 'LBL_VEHICLE', 'LBL_VEHICLES_DESCRIPTION', NULL, false, false, NULL);

	
	INSERT INTO incidents.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(1201, 12, 'incident_report_id', 'LBL_ID', NULL, true, '{"position": 1, "editable": false, "refName": true}'),
	(1202, 12, 'incident_type', 'LBL_INCIDENT_TYPE', NULL, true, '{"position": 2, "editable": false,  "badgeInHeader": "mdi mdi-exclamation-thick"}'),
	(1203, 12, 'incident_sub_type', 'LBL_INCIDENT_SUB_TYPE', NULL, false, '{"position": 3, "editable": false, "badgeInHeader": "mdi mdi-exclamation-thick"}'),
	(1204, 12, 'level', 'LBL_LEVEL', NULL, true, '{"position": 4, "editable": false}'),
	(1205, 12, 'location', 'LBL_LOCATION', NULL, false, '{ "position": 5, "editable": false, "badgeInHeader": "mdi mdi-map-marker"}'),
	(1206, 12, 'type_detection', 'LBL_TYPE_DETECTION', NULL, true, '{"position": 6, "editable": false}'),
	(1207, 12, 'source_id', 'LBL_SOURCE_FIELD', NULL, false, '{"position": 7 }'),
	(1208, 12, 'element_id', 'LBL_ELEMENT_FIELD', NULL, false, '{"position": 8 }'),
	(1209, 12, 'user_n', 'LBL_USER', NULL, false, '{"position": 9 }'),
	(1210, 12, 'phone', 'LBL_PHONE', NULL, false, '{"position": 10 }'),
    (1211, 12, 'comment', 'LBL_COMMENT', NULL, false, '{ "multiline": 5, "position": 11 }'),
	(1212, 12, 'created_at', 'LBL_CREATE_AT', NULL, false, '{"position": 12 }'),

	(3901, 39, 'incident_report_id', NULL, NULL, true, '{"position": 1}'),
	(3902, 39, 'vehicle_type_id', NULL, NULL, true, '{"position": 2}'),
	(3903, 39, 'vehicle_brand_id', NULL, NULL, true, '{"position": 3}'),
	(3904, 39, 'vehicle_model_id', NULL, NULL, true, '{"position": 4, "associatedFieldName": "vehicle_brand_id"}'),

	(3905, 39, 'patent', 'LBL_PATENT', NULL, true, '{"position":5}'),
	(3906, 39, 'color', 'LBL_COLOR', NULL, true, '{"position": 6}'),
	(3907, 39, 'vehicle_damage_id', NULL, NULL, true, '{"position": 7}'),
	(3908, 39, 'attended_id', NULL, NULL, true, '{"position": 8}'),
	(3909, 39, 'assistance_type_id', NULL, NULL, true, '{"position":9}'),
	(3910, 39, 'transfer_place_id', NULL, NULL, true, '{"position": 10}'),
	(3911, 39, 'comment', 'LBL_COMMENT', NULL, false, '{ "multiline": 5, "position": 11}');
  END IF;
END $$;
