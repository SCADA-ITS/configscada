INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(2, 1, 1, 0010002, NULL, NULL, 'CAM-1', 'CAM-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 2, 0020002, NULL, NULL, 'CAM-2', 'CAM-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 3, 0030002, NULL, NULL, 'CAM-3', 'CAM-3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 4, 0040002, NULL, NULL, 'CAM-4', 'CAM-4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 5, 0050002, NULL, NULL, 'CAM-5', 'CAM-5', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 6, 0060002, NULL, NULL, 'CAM-6', 'CAM-6', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 7, 0070002, NULL, NULL, 'CAM-7', 'CAM-7', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1, 8, 0080002, NULL, NULL, 'CAM-8', 'CAM-8', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(2, 1, 1, 1, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 1, NULL, 'VG4-AutoDome', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 1, NULL, 'http://109.206.96.58:8080/cam_1.cgi', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 1, NULL, 'CAM-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 2, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 2, NULL, 'VG4-AutoDome', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 2, NULL, 'http://109.206.96.58:8080/cam_1.cgi', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 2, NULL, 'CAM-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 3, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 3, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 3, NULL, 'http://109.206.96.58:8080/cam_1.cgi', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 3, NULL, 'CAM-3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 4, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 4, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 4, NULL, 'http://109.206.96.58:8080/cam_1.cgi', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 4, NULL, 'CAM-4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 5, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 5, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 5, NULL, 'http://109.206.96.58:8080/cam_1.cgi', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 5, NULL, 'CAM-5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 6, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 6, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 6, NULL, 'http://109.206.96.58:8080/cam_1.cgi', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 6, NULL, 'CAM-6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 7, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 7, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 7, NULL, 'http://109.206.96.58:8080/cam_1.cgi', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 7, NULL, 'CAM-7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(2, 1, 1, 8, NULL, 'BOSCH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 8, NULL, 'VideoJet-X20-SN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 8, NULL, 'http://109.206.96.58:8080/cam_1.cgi', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 1003, 1, 8, NULL, 'CAM-8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(2, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);