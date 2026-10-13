-- 
-- Table: master.element_types
--
INSERT INTO master.element_types(element_type_id, alias, description, label_alias, label_description, initial_element_type_state_id, connected_element_type_state_id, disconnected_element_type_state_id, enabled, visible, created_at, updated_at)
VALUES(50, 'ROADWAY', 'Roadway', 'LBL_ELEMENT_TYPE_ROADWAY', 'LBL_ELEMENT_TYPE_ROADWAY_DESC', 0, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: master.element_type_params.ROADWAY
--
INSERT INTO master.element_type_params
(element_type_id, param_type_id, element_type_param_id, data_type_id, default_value, alias, description, label_alias, label_description, enabled, visible, editable, created_at, updated_at) VALUES

(50, 1, 1, 1, null, 'distance', 'distance', 'LBL_ELEMENT_TYPE_PARAM_DISTANCE', 'LBL_ELEMENT_TYPE_PARAM_DISTANCE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(50, 1, 2, 1, null, 'road_type', 'type of road', 'LBL_ELEMENT_TYPE_PARAM_ROAD_TYPE', 'LBL_ELEMENT_TYPE_PARAM_ROAD_TYPE_DESC', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_type_states
--
INSERT INTO master.element_type_states(element_type_id, element_type_state_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(50, 0, 'UNKNOWN', 'Unknown state', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN', 'LBL_ELEMENT_TYPE_STATE_UNKNOWN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

