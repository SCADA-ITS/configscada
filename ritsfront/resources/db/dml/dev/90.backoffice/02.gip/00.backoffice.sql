-- smartgen
DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(2, 'gip', NULL, NULL, 'jdbc:postgresql://192.168.243.30:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'gip',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 700,
    	"formWidth": 900,
    	"tableHeight": 700,
    	"tableWidth": 900,
    	"showIdForm": true,
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
  	
  	INSERT INTO gip.sg_metadata_tables (name,"label",label_singular,label_description,mdi_icon,support_images,support_attachments,sql_view,grid_id,metadata) VALUES
	 ('vehicle_brands','LBL_VEHICLES_BRANDS','LBL_VEHICLE_BRAND','LBL_VEHICLES_BRANDS_DESCRIPTION',NULL,false,false,NULL,NULL,NULL),
	 ('vehicle_models','LBL_VEHICLES_MODELS','LBL_VEHICLE_MODEL','LBL_VEHICLES_MODELS_DESCRIPTION',NULL,false,false,NULL,NULL,NULL),
	 ('vehicles','LBL_VEHICLES','LBL_VEHICLE','LBL_VEHICLES_DESCRIPTION',NULL,false,false,NULL,NULL,
	 '{
		"multiInsert": {
			"enabled": true,
			"label": "LBL_XXX"
				}
			}
		');

	INSERT INTO gip.sg_metadata_columns (sg_metadata_table_id,column_name,"label",label_description,needs_translation,metadata,ref_view_column_id) VALUES
	 (3,'vehicle_brand_id','LBL_VEHICLE_BRAND_ID','LBL_VEHICLE_BRAND_ID_DESCRIPTION',false,
	 '{
		"multiInsert": {
			"enabled": true,
			"label": "LBL_XXX"
		},
		"refAddButton": true
		}'
	 ,NULL),
	 (3,'vehicle_model_id','LBL_VEHICLE_BRAND_ID','LBL_VEHICLE_BRAND_ID_DESCRIPTION',false,
	 '{
		"multiInsert": {
			"enabled": true,
			"label": "LBL_XXX"
		},
		"refAddButton": true
		}'
	 ,NULL);


  END IF;
END $$;