DELETE FROM master.ims_incident_groups;

-- 
-- Table: conf.ims_incident_types
--
INSERT INTO master.ims_incident_groups (incident_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
(1,'Traffic status','Traffic status','LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_STATUS', 'LBL_IMS_INCIDENT_GROUP_TYPE_TRAFFIC_STATUS_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'Weather data','Weather data','LBL_IMS_INCIDENT_GROUP_TYPE_WEATHER_DATA', 'LBL_IMS_INCIDENT_GROUP_TYPE_WEATHER_DATA_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(3,'Road incidents','Road incidents','LBL_IMS_INCIDENT_GROUP_TYPE_ROAD_INCIDENTS', 'LBL_IMS_INCIDENT_GROUP_TYPE_ROAD_INCIDENTS_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

INSERT INTO conf.ims_incident_types (incident_type_id, incident_group_id, ini_incident_state_id, alias, description, incident_level_id, default_user_id, auto_confirm_incident, cancel_incident, valid_for_min, enabled, visible, created_at, updated_at) VALUES
(0101, 1, 1, 'T-1', 'Traffic density high',  1, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0102, 1, 1, 'T-2', 'Traffic density critical',  1, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0103, 1, 1, 'T-3', 'Traffic jam slow moving',  1, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0104, 1, 1, 'T-4', 'Traffic jam standing',  1, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0105, 1, 1, 'T-5', 'Restitution',  1, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0106, 1, 1, 'T-6', 'Slowing of traffic flow (Suddenly)',  1, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0107, 1, 1, 'T-7', 'Slowing of traffic flow (Collision)',  1, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(0201, 2, 1, 'W-1', 'Snow or ice coating, creating conditions',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0202, 2, 1, 'W-2', 'Snow or ice coating, positive detection',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0203, 2, 1, 'W-3', 'Decreased adhesion risk of skidding',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0204, 2, 1, 'W-4', 'Decreased adhesion hihgh risk of skidding',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0205, 2, 1, 'W-5', 'Reduced visibility, reduced',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0206, 2, 1, 'W-6', 'Reduced visibility, low',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0207, 2, 1, 'W-7', 'Reduced visibility, critical',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0208, 2, 1, 'W-8', 'Wind strong side',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0209, 2, 1, 'W-9', 'Wind gusty',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0210, 2, 1, 'W-10', 'Rain, snow or icing restitution',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0211, 2, 1, 'W-11', 'Wind restitution',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0212, 2, 1, 'W-12', 'Visibility restitution',  2, 6, 30, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(0301, 3, 1, 'R-1', 'Traffic accident secured',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0302, 3, 1, 'R-2', 'Traffic accident unsecured',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0303, 3, 1, 'R-3', 'Traffic accident serious',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0304, 3, 1, 'R-4', 'Traffic accident very serious',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0305, 3, 1, 'R-5', 'Maintenance work secured',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0306, 3, 1, 'R-6', 'Maintenance work unsecured',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0307, 3, 1, 'R-7', 'Maintenance work with section closing',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0308, 3, 1, 'R-8', 'Obstacle on the road outside the lane',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0309, 3, 1, 'R-9', 'Obstacle on the road in the lane',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0310, 3, 1, 'R-10', 'Vehicle in the opposite direction',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0311, 3, 1, 'R-11', 'Closed section',  3, 6, 300, true, 2880, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

