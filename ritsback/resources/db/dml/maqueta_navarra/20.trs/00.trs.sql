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
(1,1,4,false,'getVehicleData.groovy',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Vehicle data
(1,1,5,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Pending dispatch
(1,1,7,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Discarded

--OCR
(1,2,3,true,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --In Review

--In Review
(1,3,2,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --OCR
(1,3,4,false,'getVehicleData.groovy',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Vehicle data
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
