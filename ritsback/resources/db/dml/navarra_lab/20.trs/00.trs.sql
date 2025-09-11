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
	 
-- master.transit_states
INSERT INTO master.transit_states (transit_state_id,transit_state_code,alias,description,label_alias,label_description,user_transit_enable,enabled,visible,created_at,updated_at) VALUES
(1,'IN','Initial','Initial','LBL_TRANSIT_STATE_INITIAL','LBL_TRANSIT_STATE_INITIAL_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'OC','OCR','OCR','LBL_TRANSIT_STATE_OCR','LBL_TRANSIT_STATE_OCR_DESC',false,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(3,'IR','In review','In review','LBL_TRANSIT_STATE_IN_REVIEW','LBL_TRANSIT_STATE_IN_REVIEW_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(4,'VD','Vehicle data','Vehicle data','LBL_TRANSIT_STATE_VEHICLE_DATA','LBL_TRANSIT_STATE_VEHICLE_DATA_DESC',false,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(5,'PD','Pending dispatch','Pending dispatch','LBL_TRANSIT_STATE_PENDING_DISPATCH','LBL_TRANSIT_STATE_PENDING_DISPATCH_DESC',false,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(6,'PS','Propose for sanction','Propose for sanction','LBL_TRANSIT_STATE_PROPOSE_FOR_SANCTION','LBL_TRANSIT_STATE_PROPOSE_FOR_SANCTION_DESC',false,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(7,'DI','Discarded','Discarded','LBL_TRANSIT_STATE_DISCARDED','LBL_TRANSIT_STATE_DISCARDED_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(8,'AR','Archived','Archived','LBL_TRANSIT_STATE_ARCHIVED','LBL_TRANSIT_STATE_ARCHIVED_DESC',false,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- static.transit_types
INSERT INTO static.transit_types (transit_type_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'Radar infraction transit','Radar infraction transit','LBL_TRANSIT_RADAR_INFRACTION','LBL_TRANSIT_RADAR_INFRACTION_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'LPR transit','LPR transit','LBL_TRANSIT_LPR','LBL_TRANSIT_LPR_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- static.transit_type_params
INSERT INTO static.transit_type_params (transit_type_id, transit_type_param_id, data_type_id, transit_type_param_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 1, 2, null, 'Road Speed limit', 'Road Speed limit', 'LBL_TRANSIT_PARAM_ROAD_SPEED_LIMIT', 'LBL_TRANSIT_PARAM_ROAD_SPEED_LIMIT_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
	
-- static.transit_type_state_transitions
INSERT INTO static.transit_type_state_transitions (transit_type_id,parent_transit_state_id,child_transit_state_id,auto,groovy_file,enabled,visible,created_at,updated_at) VALUES
--RADAR INFRACTION TRANSIT
--Initial
(1,1,2,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --OCR
(1,1,3,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --In Review
(1,1,4,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Vehicle data
(1,1,5,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Pending dispatch
(1,1,7,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Discarded

--OCR
(1,2,3,true,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --In Review

--In Review
(1,3,2,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --OCR
(1,3,4,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Vehicle data
(1,3,5,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Pending dispatch
(1,3,7,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Discarded

--Vehicle data
(1,4,3,true,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --In Review

--Pending dispatch
(1,5,6,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Propose for sanction

--Propose for sanction
(1,6,8,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP); --Archived

-- static.vehicle_classes 
INSERT INTO static.vehicle_classes (vehicle_class_id,alias,label_alias,enabled,visible,created_at,updated_at) VALUES
(1, 'light', 'LBL_VEHICLE_CLASS_LIGHT', true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),	 
(2, 'heavy', 'LBL_VEHICLE_CLASS_HEAVY', true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

INSERT INTO conf.element_transit_types (element_type_id, element_id, transit_type_id, infraction_manager_id, enabled, visible, created_at, updated_at) VALUES
(2, 28, 2, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(99, 1, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(99, 2, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(99, 3, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(99, 4, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
