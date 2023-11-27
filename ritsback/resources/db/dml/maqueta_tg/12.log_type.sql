
-- 
-- Table: master.log_subtypes
--
INSERT INTO master.log_subtypes (log_subtype_id, log_type_id, alias, description, label_alias, label_description, level_output_db, level_output_stomp, level_treatment, enabled, visible, created_at, updated_at) VALUES
(1018, 1, 'ASSET_DAMAGE', 'Asset damage', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_ASSET_DAMAGE', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1019, 1, 'INCIDENT_DETAILS', 'Incident details', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_INCIDENT_DETAILS', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1020, 1, 'INCIDENT_TYPE', 'Incident type', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_INCIDENT_TYPE', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1021, 1, 'IR_ARRIVED', 'IR arrived', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_IR_ARRIVED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1022, 1, 'IR_DISPATCHED', 'IR dispatched', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_IR_DISPATCHED', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1023, 1, 'LANE_CLOSURES', 'Lane closures', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_LANE_CLOSURES', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1024, 1, 'LEARNINGS', 'Learnings', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_LEARNINGS', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1025, 1, 'NOTIFICATION', 'Notification', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_NOTIFICATION', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1026, 1, 'OTHER', 'Other', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_OTHER', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1027, 1, 'RESPONSE', 'Response', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_RESPONSE', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1028, 1, 'RESPONSIBLE_AGENCY', 'Responsible agency', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_RESPONSIBLE_AGENCY', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1029, 1, 'STATUS', 'Status', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_STATUS', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1030, 1, 'SUPPORTING_AGENCY', 'Supporting agency', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_SUPPORTING_AGENCY', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1031, 1, 'SUPPORTING_INFO', 'Supporting info', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_SUPPORTING_INFO', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1032, 1, 'UPDATE', 'Update', 'LBL_LOG_SUBTYPE_INCIDENT_LOG_UPDATE', null, 3, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);