SET client_min_messages TO WARNING;

DO $$
BEGIN
	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_logs'
    ) THEN
        DELETE FROM rt.transit_logs;
    END IF;
	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_attachments'
    ) THEN
        DELETE FROM rt.transit_attachments;
    END IF;
	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_images'
    ) THEN
        DELETE FROM rt.transit_images;
    END IF;
    
    IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_values'
    ) THEN
        DELETE FROM rt.transit_values;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_driver_values'
    ) THEN
        DELETE FROM rt.transit_driver_values;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_drivers'
    ) THEN
        DELETE FROM rt.transit_drivers;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_vehicle_values'
    ) THEN
        DELETE FROM rt.transit_vehicle_values;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_vehicles'
    ) THEN
        DELETE FROM rt.transit_vehicles;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transits'
    ) THEN
        DELETE FROM rt.transits;
    END IF;
END $$;

SELECT FROM conf.element_transit_types;
DELETE FROM static.driver_params;
DELETE FROM static.driver_types;
DELETE FROM static.vehicle_params;
DELETE FROM static.transit_type_state_transitions;
DELETE FROM static.transit_type_params;
DELETE FROM static.transit_types;
DELETE FROM master.transit_states;
DELETE FROM master.vehicle_types;


-- master.vehicle_types
INSERT INTO master.vehicle_types (vehicle_type_id,alias,enabled,visible,created_at,updated_at) VALUES
	 (1,'Vehicle Type I',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
	 
-- master.transit_states
INSERT INTO master.transit_states (transit_state_id,transit_state_code,alias,description,label_alias,label_description,user_transit_enable,enabled,visible,created_at,updated_at) VALUES
	 (1,'IN','Initial','Initial','LBL_TRANSIT_STATE_INITIAL','LBL_TRANSIT_STATE_INITIAL_DESC',false,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,'PE','Pending','Pending','LBL_TRANSIT_STATE_PENDING','LBL_TRANSIT_STATE_PENDING_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (3,'VA','Validated','Validated','LBL_TRANSIT_STATE_VALIDATED','LBL_TRANSIT_STATE_VALIDATED_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (4,'DI','Discarded','Discarded','LBL_TRANSIT_STATE_DISCARDED','LBL_TRANSIT_STATE_DISCARDED_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (5,'AR','Archived','Archived','LBL_TRANSIT_STATE_ARCHIVED','LBL_TRANSIT_STATE_ARCHIVED_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- static.transit_types
INSERT INTO static.transit_types (transit_type_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,'Generic Transit','Generic Transit','LBL_TRANSIT_GENERIC','LBL_TRANSIT_GENERIC_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,'LPR Transit','LPR Transit','LBL_TRANSIT_LPR','LBL_TRANSIT_LPR_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- static.transit_type_params
INSERT INTO static.transit_type_params (transit_type_id, transit_type_param_id, data_type_id, transit_type_param_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
	(1, 1, 2, null, 'Transit type Param 1', 'Transit type Param 1', 'LBL_TRANSIT_PARAM_1', 'LBL_TRANSIT_PARAM_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
	
-- static.transit_type_state_transitions
INSERT INTO static.transit_type_state_transitions (transit_type_id,parent_transit_state_id,child_transit_state_id,enabled,visible,created_at,updated_at) VALUES
	 (1,1,2,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,2,3,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,2,4,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,3,5,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,4,5,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


-- "static".vehicle_params
INSERT INTO static.vehicle_params (vehicle_type_id,vehicle_param_id,data_type_id,vehicle_param_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,1,2,NULL,'Vehicle Type I Param 1','Vehicle Type I Param 1','LBL_VEHICLE_TYPE_I_PARAM_I','LBL_VEHICLE_TYPE_I_PARAM_I_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


INSERT INTO static.driver_types (driver_type_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,'Driver Type I','Driver Type I','LBL_DRIVER_TYPE_I','LBL_DRIVER_TYPE_I_DESCRIPTION',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

INSERT INTO static.driver_params (driver_type_id,driver_param_id,data_type_id,driver_param_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,1,2,NULL,'Driver Type I Param 1','Driver Type I Param 1','LBL_DRIVER_TYPE_I_PARAM_1','LBL_DRIVER_TYPE_I_PARAM_1_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
	
	
-- static.vehicle_classes 
INSERT INTO static.vehicle_classes (vehicle_class_id,alias,label_alias,enabled,visible,created_at,updated_at) VALUES
	 (1, 'light', 'LBL_VEHICLE_CLASS_LIGHT', true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),	 
	 (2, 'heavy', 'LBL_VEHICLE_CLASS_HEAVY', true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

INSERT INTO conf.element_transit_types (element_type_id, element_id, transit_type_id, infraction_manager_id, enabled, visible, created_at, updated_at) VALUES
	(2, 1, 2, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(99, 1, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(99, 2, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(99, 3, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(99, 4, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
