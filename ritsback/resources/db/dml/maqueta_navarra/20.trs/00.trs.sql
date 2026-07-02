-- static.transit_types
INSERT INTO static.transit_types (transit_type_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'Radar infraction transit','Radar infraction transit','LBL_TRANSIT_RADAR_INFRACTION','LBL_TRANSIT_RADAR_INFRACTION_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'LPR transit','LPR transit','LBL_TRANSIT_LPR','LBL_TRANSIT_LPR_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- static.transit_type_params
INSERT INTO static.transit_type_params (transit_type_id, transit_type_param_id, data_type_id, transit_type_param_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(1, 1, 2, null, 'Road Speed Limit', 'Road Speed Limit', 'LBL_TRANSIT_PARAM_ROAD_SPEED_LIMIT', 'LBL_TRANSIT_PARAM_ROAD_SPEED_LIMIT_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 2, null, 'API Vehicle ITV Date', 'API Vehicle ITV Date', 'LBL_TRANSIT_PARAM_API_VEHICLE_ITV_DATE', 'LBL_TRANSIT_PARAM_API_VEHICLE_ITV_DATE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, null, 'Speed Threshold Light', 'Speed Threshold Light', 'LBL_TRANSIT_PARAM_SPEED_THRESHOLD_LIGTH', 'LBL_TRANSIT_PARAM_SPEED_THRESHOLD_LIGTH_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, null, 'Speed Threshold Heavy', 'Speed Threshold Heavy', 'LBL_TRANSIT_PARAM_SPEED_THRESHOLD_HEAVY', 'LBL_TRANSIT_PARAM_SPEED_THRESHOLD_HEAVY_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 2, null, 'API Vehicle Insurance Date', 'API Vehicle Insurance Date', 'LBL_TRANSIT_PARAM_API_VEHICLE_INSURANCE_DATE', 'LBL_TRANSIT_PARAM_API_VEHICLE_INSURANCE_DATE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 6, 2, null, 'API Vehicle MMA', 'API Vehicle MMA', 'LBL_TRANSIT_PARAM_API_VEHICLE_MMA', 'LBL_TRANSIT_PARAM_API_VEHICLE_MMA_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 7, 2, null, 'Corrected speed', 'Corrected speed', 'LBL_TRANSIT_PARAM_CORRECTED_SPEED', 'LBL_TRANSIT_PARAM_CORRECTED_SPEED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 8, 2, null, 'Gravity', 'Gravity', 'LBL_TRANSIT_PARAM_GRAVITY', 'LBL_TRANSIT_PARAM_GRAVITY_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 9, 2, null, 'Article', 'Article', 'LBL_TRANSIT_PARAM_ARTICLE', 'LBL_TRANSIT_PARAM_ARTICLE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 10, 2, null, 'Calculated DBOID', 'Calculated DBOID', 'LBL_TRANSIT_PARAM_CALCULATED_DBOID', 'LBL_TRANSIT_PARAM_CALCULATED_DBOID_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 11, 2, null, 'Location Type', 'Location Type', 'LBL_TRANSIT_PARAM_LOCATION_TYPE', 'LBL_TRANSIT_PARAM_LOCATION_TYPE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 12, 2, null, 'Road Type', 'Road Type', 'LBL_TRANSIT_PARAM_ROAD_TYPE', 'LBL_TRANSIT_PARAM_ROAD_TYPE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 13, 2, null, 'Limitation Type', 'Limitation Type', 'LBL_TRANSIT_PARAM_LIMITATION_TYPE', 'LBL_TRANSIT_PARAM_LIMITATION_TYPE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 14, 2, null, 'Corrected factor', 'Corrected factor', 'LBL_TRANSIT_PARAM_CORRECTED_FACTOR', 'LBL_TRANSIT_PARAM_CORRECTED_FACTOR_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 15, 2, null, 'Vehicle limitation speed', 'Vehicle limitation speed', 'LBL_TRANSIT_PARAM_VEHICLE_LIMITATION_SPEED', 'LBL_TRANSIT_PARAM_VEHICLE_LIMITATION_SPEED_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 16, 2, null, 'Judicial Process', 'Judicial Process', 'LBL_TRANSIT_PARAM_JUDICIAL_PROCESS', 'LBL_TRANSIT_PARAM_JUDICIAL_PROCESS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 17, 2, null, 'Vehicle Observations', 'Vehicle Observations', 'LBL_TRANSIT_PARAM_OBSERVATIONS', 'LBL_TRANSIT_PARAM_OBSERVATIONS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 18, 2, null, 'Infraction Observations', 'Infraction Observations', 'LBL_TRANSIT_PARAM_OBSERVATIONS', 'LBL_TRANSIT_PARAM_OBSERVATIONS_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 19, 2, null, 'OCR Plate', 'OCR Plate', 'LBL_TRANSIT_OCR_PLATE', 'LBL_TRANSIT_OCR_PLATE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- static.transit_type_state_transitions
INSERT INTO static.transit_type_state_transitions (transit_type_id,parent_transit_state_id,child_transit_state_id,auto,groovy_file,enabled,visible,created_at,updated_at) VALUES
--RADAR INFRACTION TRANSIT
--Initial
(1,1,3,true,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --In Review

--In Review
(1,3,4,false,'getVehicleData.groovy',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Vehicle data
(1,3,5,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Pending dispatch
(1,3,7,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Discarded

--Vehicle data
(1,4,3,true,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Check lists


--Pending dispatch
(1,5,6,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Propose for sanction
(1,5,7,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Discarded
(1,5,9,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Failed

--Propose for sanction
(1,6,7,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Discarded
(1,6,8,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Archived
(1,6,9,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Failed

--Discarded
(1,7,8,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Archived    

--Failed
(1,9,3,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --In Review
(1,9,7,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Discarded

--Checklists
(1,10,3,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --In Review
(1,10,11,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --White list
(1,10,12,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Black list

--Black list
(1,12,6,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), --Propose for sanction
(1,12,7,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP); --Discarded

-- static.vehicle_classes 
INSERT INTO static.vehicle_classes (vehicle_class_id,alias,label_alias,enabled,visible,created_at,updated_at) VALUES
(1, 'light', 'LBL_VEHICLE_CLASS_LIGHT', true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),	 
(2, 'heavy', 'LBL_VEHICLE_CLASS_HEAVY', true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

INSERT INTO conf.element_transit_types (element_type_id, element_id, transit_type_id, infraction_manager_id, enabled, visible, created_at, updated_at) VALUES
--(2, 28, 2, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(99, 1, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(99, 2, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(99, 3, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(99, 4, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(99, 5, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
