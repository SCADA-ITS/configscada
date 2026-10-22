-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description,label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(4, 'SECTION', 'Section type','LBL_ELEMENT_TYPE_SECTION', 'LBL_ELEMENT_TYPE_SECTION_DESC', 0, 1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params
--
INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, element_type_param_unit_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES

(4,2,1, NULL,4, null, 'date', 'date', 'LBL_ELEMENT_TYPE_PARAM_DATE', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,2,9,1, null, 'period', 'period of detection', 'LBL_ELEMENT_TYPE_PARAM_PERIOD', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,3, NULL,5, null, 'current_direction', 'current_direction', 'LBL_ELEMENT_TYPE_PARAM_CURRENT_DIRECTION', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,4, NULL,1, null, 'n_vehicles', 'total number of vehicles', 'LBL_ELEMENT_TYPE_PARAM_N_VEHICLES', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,5,24,1, null, 'intensity', 'vehicles per hour', 'LBL_ELEMENT_TYPE_PARAM_INTENSITY', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,6,3,1, null, 'occupancy', 'occupancy (percent of occupancy),', 'LBL_ELEMENT_TYPE_PARAM_OCCUPANCY', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,7, NULL,5, null, 'congestion', 'congestion detection', 'LBL_ELEMENT_TYPE_PARAM_CONGESTION', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,8, NULL,5, null, 'kamikaze', 'kamikaze detection', 'LBL_ELEMENT_TYPE_PARAM_KAMIKAZE', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,9,10,1, null, 'speed', 'Average speed', 'LBL_ELEMENT_TYPE_PARAM_AVG_SPEED', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,10,11,1, null, 'gap', 'Average distante between bumps', 'LBL_ELEMENT_TYPE_PARAM_GAP', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,11,11,1, null, 'headway', 'Average distante between front bumps', 'LBL_ELEMENT_TYPE_PARAM_HEADWAY', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,12, NULL,1, null, 'los', 'level_of_service', 'LBL_ELEMENT_TYPE_PARAM_LEVEL_OF_SERVICE', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,13, NULL,1, null, 'los_percent', 'level_of_service_percent', 'LBL_ELEMENT_TYPE_PARAM_LEVEL_OF_SERVICE_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,14, NULL,1, null, 'quality', 'quality of data in percent', 'LBL_ELEMENT_TYPE_PARAM_QUALITY', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,15, NULL,1, null, 'flow', 'Traffic flow', 'LBL_ELEMENT_TYPE_PARAM_FLOW', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,16, NULL,1, null, 'density', 'density', 'LBL_ELEMENT_TYPE_PARAM_DENSITY', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,17, NULL,1, null, 'adt_hourly', 'Average daily traffic by hour', 'LBL_ELEMENT_TYPE_PARAM_ADT_HOURLY', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,18,11,1, null, 'length', 'Average length', 'LBL_ELEMENT_TYPE_PARAM_LENGTH', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,2,60,null,1, null, 'veh_length_1', 'veh_length_1', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_1', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,61,null,1, null, 'veh_length_2', 'veh_length_2', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_2', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,62,null,1, null, 'veh_length_3', 'veh_length_3', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_3', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,63,null,1, null, 'veh_length_4', 'veh_length_4', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_4', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,64,null,1, null, 'veh_length_5', 'veh_length_5', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_5', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,65,null,1, null, 'veh_length_6', 'veh_length_6', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_6', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,66,null,1, null, 'veh_length_7', 'veh_length_7', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_7', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,67,null,1, null, 'veh_length_8', 'veh_length_8', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_8', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,68,null,1, null, 'veh_length_9', 'veh_length_9', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_9', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,69,null,1, null, 'veh_length_10', 'veh_length_10', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_10', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,70,null,1, null, 'veh_length_11', 'veh_length_11', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_11', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,71,null,1, null, 'veh_length_12', 'veh_length_12', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_12', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,72,null,1, null, 'veh_length_13', 'veh_length_13', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_13', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,73,null,1, null, 'veh_length_14', 'veh_length_14', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_14', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,74,null,1, null, 'veh_length_15', 'veh_length_15', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_15', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,75,null,1, null, 'veh_length_16', 'veh_length_16', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_16', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,2,80,null,1, null, 'veh_speed_1', 'veh_speed_1', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_1', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,81,null,1, null, 'veh_speed_2', 'veh_speed_2', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_2', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,82,null,1, null, 'veh_speed_3', 'veh_speed_3', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_3', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,83,null,1, null, 'veh_speed_4', 'veh_speed_4', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_4', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,84,null,1, null, 'veh_speed_5', 'veh_speed_5', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_5', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,85,null,1, null, 'veh_speed_6', 'veh_speed_6', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_6', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,86,null,1, null, 'veh_speed_7', 'veh_speed_7', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_7', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,87,null,1, null, 'veh_speed_8', 'veh_speed_8', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_8', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,88,null,1, null, 'veh_speed_9', 'veh_speed_9', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_9', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,89,null,1, null, 'veh_speed_10', 'veh_speed_10', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_10', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,90,null,1, null, 'veh_speed_11', 'veh_speed_11', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_11', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,91,null,1, null, 'veh_speed_12', 'veh_speed_12', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_12', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,92,null,1, null, 'veh_speed_13', 'veh_speed_13', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_13', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,93,null,1, null, 'veh_speed_14', 'veh_speed_14', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_14', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,94,null,1, null, 'veh_speed_15', 'veh_speed_15', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_15', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,95,null,1, null, 'veh_speed_16', 'veh_speed_16', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_16', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,2,100,3,1, null, 'veh_length_1_percent', 'veh_length_1_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_1_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,101,3,1, null, 'veh_length_2_percent', 'veh_length_2_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_2_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,102,3,1, null, 'veh_length_3_percent', 'veh_length_3_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_3_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,103,3,1, null, 'veh_length_4_percent', 'veh_length_4_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_4_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,104,3,1, null, 'veh_length_5_percent', 'veh_length_5_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_5_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,105,3,1, null, 'veh_length_6_percent', 'veh_length_6_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_6_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,106,3,1, null, 'veh_length_7_percent', 'veh_length_7_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_7_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,107,3,1, null, 'veh_length_8_percent', 'veh_length_8_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_8_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,108,3,1, null, 'veh_length_9_percent', 'veh_length_9_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_9_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,109,3,1, null, 'veh_length_10_percent', 'veh_length_10_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_10_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,110,3,1, null, 'veh_length_11_percent', 'veh_length_11_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_11_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,111,3,1, null, 'veh_length_12_percent', 'veh_length_12_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_12_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,112,3,1, null, 'veh_length_13_percent', 'veh_length_13_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_13_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,113,3,1, null, 'veh_length_14_percent', 'veh_length_14_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_14_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,114,3,1, null, 'veh_length_15_percent', 'veh_length_15_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_15_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,115,3,1, null, 'veh_length_16_percent', 'veh_length_16_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_LENGTH_16_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,2,120,3,1, null, 'veh_speed_1_percent', 'veh_speed_1_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_1_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,121,3,1, null, 'veh_speed_2_percent', 'veh_speed_2_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_2_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,122,3,1, null, 'veh_speed_3_percent', 'veh_speed_3_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_3_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,123,3,1, null, 'veh_speed_4_percent', 'veh_speed_4_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_4_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,124,3,1, null, 'veh_speed_5_percent', 'veh_speed_5_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_5_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,125,3,1, null, 'veh_speed_6_percent', 'veh_speed_6_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_6_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,126,3,1, null, 'veh_speed_7_percent', 'veh_speed_7_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_7_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,127,3,1, null, 'veh_speed_8_percent', 'veh_speed_8_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_8_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,128,3,1, null, 'veh_speed_9_percent', 'veh_speed_9_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_9_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,129,3,1, null, 'veh_speed_10_percent', 'veh_speed_10_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_10_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,130,3,1, null, 'veh_speed_11_percent', 'veh_speed_11_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_11_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,131,3,1, null, 'veh_speed_12_percent', 'veh_speed_12_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_12_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,132,3,1, null, 'veh_speed_13_percent', 'veh_speed_13_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_13_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,133,3,1, null, 'veh_speed_14_percent', 'veh_speed_14_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_14_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,134,3,1, null, 'veh_speed_15_percent', 'veh_speed_15_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_15_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,135,3,1, null, 'veh_speed_16_percent', 'veh_speed_16_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_16_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,2,140, NULL,1, null, 'veh_pa_1', 'veh_pa_1', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_1', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,141, NULL,1, null, 'veh_pa_2', 'veh_pa_2', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_2', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,142, NULL,1, null, 'veh_pa_3', 'veh_pa_3', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_3', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,143, NULL,1, null, 'veh_pa_4', 'veh_pa_4', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_4', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,144, NULL,1, null, 'veh_pa_5', 'veh_pa_5', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_5', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,145, NULL,1, null, 'veh_pa_6', 'veh_pa_6', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_6', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,146, NULL,1, null, 'veh_pa_7', 'veh_pa_7', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_7', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,147, NULL,1, null, 'veh_pa_8', 'veh_pa_8', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_8', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,148, NULL,1, null, 'veh_pa_9', 'veh_pa_9', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_9', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,149, NULL,1, null, 'veh_pa_10', 'veh_pa_10', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_10', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,150, NULL,1, null, 'veh_pa_11', 'veh_pa_11', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_11', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,151, NULL,1, null, 'veh_pa_12', 'veh_pa_12', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_12', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,152, NULL,1, null, 'veh_pa_13', 'veh_pa_13', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_13', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,2,160,3,1, null, 'veh_pa_1_percent', 'veh_pa_1_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_1_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,161,3,1, null, 'veh_pa_2_percent', 'veh_pa_2_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_2_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,162,3,1, null, 'veh_pa_3_percent', 'veh_pa_3_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_3_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,163,3,1, null, 'veh_pa_4_percent', 'veh_pa_4_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_4_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,164,3,1, null, 'veh_pa_5_percent', 'veh_pa_5_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_5_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,165,3,1, null, 'veh_pa_6_percent', 'veh_pa_6_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_6_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,166,3,1, null, 'veh_pa_7_percent', 'veh_pa_7_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_7_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,167,3,1, null, 'veh_pa_8_percent', 'veh_pa_8_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_8_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,168,3,1, null, 'veh_pa_9_percent', 'veh_pa_9_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_9_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,169,3,1, null, 'veh_pa_10_percent', 'veh_pa_10_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_10_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,170,3,1, null, 'veh_pa_11_percent', 'veh_pa_11_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_11_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,171,3,1, null, 'veh_pa_12_percent', 'veh_pa_12_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_12_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,2,172,3,1, null, 'veh_pa_13_percent', 'veh_pa_13_percent', 'LBL_ELEMENT_TYPE_PARAM_VEH_PA_13_PERCENT', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,1,1, NULL,5, null, 'direction', 'direction', 'LBL_ELEMENT_TYPE_PARAM_DIRECTION', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,2, NULL,5, null, 'virtual', 'flag virtual section', 'LBL_ELEMENT_TYPE_PARAM_VIRTUAL', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,3, NULL,1, null, 'los_type', 'los_type', 'LBL_ELEMENT_TYPE_PARAM_LOS_TYPE', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,4, NULL,5, null, 'los_soften', 'los_soften', 'LBL_ELEMENT_TYPE_PARAM_LOS_SOFTEN', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,5, NULL,5, null, 'los_flatten', 'los_flatten', 'LBL_ELEMENT_TYPE_PARAM_LOS_FLATTEB', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,6, NULL,2, null, 'los_threshold_speed', 'los_threshold_speed', 'LBL_ELEMENT_TYPE_PARAM_LOS_THRESHOLD_SPEED', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,7, NULL,2, null, 'los_threshold_occupancy', 'los_threshold_occupancy', 'LBL_ELEMENT_TYPE_PARAM_LOS_THRESHOLD_OCCUPANCY', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,8, NULL,2, null, 'imd_graphics', 'imd_graphics', 'LBL_ELEMENT_TYPE_PARAM_IMD_GRAPHICS', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,9, NULL,2, null, 'vel_media_graphics', 'vel_media_graphics', 'LBL_ELEMENT_TYPE_PARAM_VEL_MEDIA_GRAPHICS', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,1,60,11,2, null, 'cat_veh_length_1', 'cat_veh_length_1', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_1', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,61,11,2, null, 'cat_veh_length_2', 'cat_veh_length_2', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_2', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,62,11,2, null, 'cat_veh_length_3', 'cat_veh_length_3', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_3', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,63,11,2, null, 'cat_veh_length_4', 'cat_veh_length_4', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_4', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,64,11,2, null, 'cat_veh_length_5', 'cat_veh_length_5', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_5', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,65,11,2, null, 'cat_veh_length_6', 'cat_veh_length_6', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_6', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,66,11,2, null, 'cat_veh_length_7', 'cat_veh_length_7', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_7', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,67,11,2, null, 'cat_veh_length_8', 'cat_veh_length_8', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_8', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,68,11,2, null, 'cat_veh_length_9', 'cat_veh_length_9', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_9', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,69,11,2, null, 'cat_veh_length_10', 'cat_veh_length_10', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_10', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,70,11,2, null, 'cat_veh_length_11', 'cat_veh_length_11', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_11', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,71,11,2, null, 'cat_veh_length_12', 'cat_veh_length_12', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_12', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,72,11,2, null, 'cat_veh_length_13', 'cat_veh_length_13', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_13', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,73,11,2, null, 'cat_veh_length_14', 'cat_veh_length_14', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_14', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,74,11,2, null, 'cat_veh_length_15', 'cat_veh_length_15', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_15', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,75,11,2, null, 'cat_veh_length_16', 'cat_veh_length_16', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_LENGTH_16', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,1,80,10,2, null, 'cat_veh_speed_1', 'cat_veh_speed_1', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_1', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,81,10,2, null, 'cat_veh_speed_2', 'cat_veh_speed_2', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_2', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,82,10,2, null, 'cat_veh_speed_3', 'cat_veh_speed_3', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_3', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,83,10,2, null, 'cat_veh_speed_4', 'cat_veh_speed_4', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_4', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,84,10,2, null, 'cat_veh_speed_5', 'cat_veh_speed_5', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_5', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,85,10,2, null, 'cat_veh_speed_6', 'cat_veh_speed_6', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_6', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,86,10,2, null, 'cat_veh_speed_7', 'cat_veh_speed_7', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_7', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,87,10,2, null, 'cat_veh_speed_8', 'cat_veh_speed_8', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_8', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,88,10,2, null, 'cat_veh_speed_9', 'cat_veh_speed_9', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_9', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,89,10,2, null, 'cat_veh_speed_10', 'cat_veh_speed_10', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_10', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,90,10,2, null, 'cat_veh_speed_11', 'cat_veh_speed_11', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_11', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,91,10,2, null, 'cat_veh_speed_12', 'cat_veh_speed_12', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_12', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,92,10,2, null, 'cat_veh_speed_13', 'cat_veh_speed_13', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_13', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,93,10,2, null, 'cat_veh_speed_14', 'cat_veh_speed_14', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_14', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,94,10,2, null, 'cat_veh_speed_15', 'cat_veh_speed_15', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_15', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,95,10,2, null, 'cat_veh_speed_16', 'cat_veh_speed_16', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_SPEED_16', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,1,100, NULL,2, null, 'cat_veh_pa_1', 'cat_veh_pa_1', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_1', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,101, NULL,2, null, 'cat_veh_pa_2', 'cat_veh_pa_2', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_2', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,102, NULL,2, null, 'cat_veh_pa_3', 'cat_veh_pa_3', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_3', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,103, NULL,2, null, 'cat_veh_pa_4', 'cat_veh_pa_4', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_4', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,104, NULL,2, null, 'cat_veh_pa_5', 'cat_veh_pa_5', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_5', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,105, NULL,2, null, 'cat_veh_pa_6', 'cat_veh_pa_6', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_6', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,106, NULL,2, null, 'cat_veh_pa_7', 'cat_veh_pa_7', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_7', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,107, NULL,2, null, 'cat_veh_pa_8', 'cat_veh_pa_8', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_8', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,108, NULL,2, null, 'cat_veh_pa_9', 'cat_veh_pa_9', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_9', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,109, NULL,2, null, 'cat_veh_pa_10', 'cat_veh_pa_10', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_10', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,110, NULL,2, null, 'cat_veh_pa_11', 'cat_veh_pa_11', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_11', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,111, NULL,2, null, 'cat_veh_pa_12', 'cat_veh_pa_12', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_12', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,1,112, NULL,2, null, 'cat_veh_pa_13', 'cat_veh_pa_13', 'LBL_ELEMENT_TYPE_PARAM_CAT_VEH_PA_13', null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(1, 4, 'Sec KINEO', 'Section with Kineo detector', 'LBL_ELEMENT_SUBTYPE_SECTION_KINEO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 'Sec REVENGA', 'Section with Revenga detecto', 'LBL_ELEMENT_SUBTYPE_SECTION_REVENGA', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params Sec 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at) VALUES
--KINEO
(1, 4, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 140, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 141, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 142, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 143, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 4, 2, 160, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 161, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- REVENGA
(2, 4, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 4, 2, 60, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 61, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 4, 2, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 82, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 4, 2, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 101, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 4, 2, 120, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 121, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, 122, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(4, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'VALID', 'valid', 'LBL_ELEMENT_TYPE_STATE_VALID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'INVALID', 'invalid', 'LBL_ELEMENT_TYPE_STATE_INVALID', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 'ALARMS', 'The state of the equipment is: with alarms','LBL_ELEMENT_TYPE_STATE_ALARMS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_state_transitions
--
INSERT INTO master.element_type_state_transitions
(element_type_id, current_element_type_state_id, next_element_type_state_id, enabled, visible, editable, created_at, updated_at) VALUES
(4, 3, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 0, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 0, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 3, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 1, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_hierarchies_allowed
INSERT INTO master.element_hierarchies_allowed
(parent_element_type_id, child_element_type_id, enabled, visible, created_at, updated_at) VALUES
(4, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 51, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 54, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 68, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.io_controller_module_type_element_types
--
INSERT INTO master.io_controller_module_type_element_types
(module_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
