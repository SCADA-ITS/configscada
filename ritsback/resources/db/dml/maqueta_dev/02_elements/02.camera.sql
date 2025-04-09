-- 
-- Table: conf.elements.CAMERA
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(2, NULL, 1, 15, NULL,  NULL, 'CAM-1', 'CAM-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 2, 15, NULL,  NULL, 'CAM-2', 'CAM-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 3, 15, NULL,  NULL, 'CAM-3', 'CAM-3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 4, 15, NULL,  NULL, 'CAM-4', 'CAM-4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 5, 15, NULL,  NULL, 'CAM-5', 'CAM-5', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 6, 15, NULL,  NULL, 'CAM-6', 'CAM-6', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 7, 15, NULL,  NULL, 'CAM-7', 'CAM-7', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 8, 15, NULL,  NULL, 'CAM-8', 'CAM-8', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 9, 15, NULL,  NULL, 'CAM-9', 'CAM-9', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 10, 15, NULL,  NULL, 'CAM-10', 'CAM-10', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 11, 15, NULL,  NULL, 'CAM-BOSCH_AVO', 'CAM-BOSCH_AVO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 12, 15, NULL,  NULL, 'CAM-HIKVISION_AVO', 'CAM-HIKVISION_AVO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL, 13, 15, NULL,  NULL, 'CAM-HIKVISION_AUSOL', 'CAM-HIKVISION_AUSOL', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 2, 20, 15, NULL,  NULL, 'S1TS-CAM-020', 'S1TS-CAM-020', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 21, 15, NULL,  NULL, 'S1TS-CAM-021', 'S1TS-CAM-021', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 22, 15, NULL,  NULL, 'S1TS-CAM-022', 'S1TS-CAM-022', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 30, 15, NULL,  NULL, 'S1TS-CAM-030', 'S1TS-CAM-030', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 31, 15, NULL,  NULL, 'S1TS-CAM-031', 'S1TS-CAM-031', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 32, 15, NULL,  NULL, 'S1TS-CAM-032', 'S1TS-CAM-032', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.CAMARA
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2,1,1,1,'Foscam',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,1,'C1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,1,'http://109.206.96.58:8080/cam_1.cgi',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,1,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,1,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,6,1,1,
	'[
			{
				"id": 1,
				"alias": "Preset 1",
				"description": "Preset 1 description",
				"default": true
			},
			{
				"id": 2,
				"alias": "Preset 2",
				"description": "Preset 2 description",
				"default": false
			},
			{
				"id": 3,
				"alias": "Preset 3",
				"description": "Preset 3 description",
				"default": false
			}
		]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
		
(2,7,1,1,'PTZ',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,9,1,1,'192.168.88.251',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,10,1,1,'admin',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,11,1,1,'(R4D7)Revenga',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
--(2, 12, 1, 1, '[
--	{"id":1, "label":"LBL_X0", "up":0.02,"down":-0.02,"right":0.02,"left":-0.02,"zoom_in":0.02,"zoom_out":-0.02},
--	{"id":2, "label":"LBL_X1", "up":0.04,"down":-0.04,"right":0.04,"left":-0.04,"zoom_in":0.04,"zoom_out":-0.4},
--	{"id":3, "label":"LBL_X2", "up":0.08,"down":-0.08,"right":0.8,"left":-0.8,"zoom_in":0.08,"zoom_out":-0.8}
--]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 12, 1, 1, '{"up":0.02,"down":-0.02,"right":0.02,"left":-0.02,"zoom_in":0.02,"zoom_out":-0.02}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2,1003,1,1,'CAM_1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,16,1,1,'http://109.206.96.58:8080/cam_1.jpg?',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,2,'GO2RTC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,2,'WEBRTC_16_9',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,2,'http://192.168.243.30:1984/stream.html?src=camera1&mode=webrtc',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
--(2,3,1,2,'http://192.168.243.30:1984/webrtc.html?src=camera1&media=video',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,4,1,2,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,2,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,2,'CAM_2',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,3,'GO2RTC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,3,'WEBRTC_16_9',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,3,'http://192.168.243.30:1984/stream.html?src=camera2&mode=webrtc',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,3,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,3,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,1003,1,3,'CAM_3',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,4,'GO2RTC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,4,'WEBRTC_4_3',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,4,'http://192.168.243.30:1984/stream.html?src=camera3&mode=webrtc',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,4,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,4,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,4,'CAM_4',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,5,'GO2RTC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,5,'WEBRTC_4_3',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,5,'http://192.168.243.30:1984/stream.html?src=camera4&mode=webrtc',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,5,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,5,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,5,'CAM_5',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,6,'Dahua',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,6,'Interchange',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,6,'http://192.168.0.198:55622/stream',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,6,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,6,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,7,1,6,'PTZ',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,6,'CAM_6',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,7,'Dahua',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,7,'Interchange',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,7,'http://192.168.0.198:55623/stream',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,7,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,7,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,6,1,7,'PTZ',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,7,'CAM_7',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,8,'Dahua',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,8,'Interchange',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,8,'http://192.168.0.198:55624/stream',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,8,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,8,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,6,1,8,'PTZ',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,8,'CAM_8',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,9,'Dahua',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,9,'Interchange',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,9,'http://192.168.0.198:55625/stream',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,9,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,9,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,7,1,9,'PTZ',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,9,'CAM_9',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,10,'Dahua',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,10,'Interchange',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,10,'http://192.168.0.198:55626/stream',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,10,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,10,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,7,1,10,'PTZ',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,10,'CAM_10',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,11,'Bosch',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,11,'NBE-5503-AL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,11,'http://ITS_Admin:VO1-cam22.@10.50.1.1/snap.jpg?JpegCam=1&JpegSize=X',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,11,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,11,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,6,1,11,
	'[
			{
				"id": 1,
				"alias": "Preset 1",
				"description": "Preset 1 description",
				"default": true
			},
			{
				"id": 2,
				"alias": "Preset 2",
				"description": "Preset 2 description",
				"default": false
			},
			{
				"id": 3,
				"alias": "Preset 3",
				"description": "Preset 3 description",
				"default": false
			}
		]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,7,1,11,'PTZ',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,9,1,11,'10.50.1.1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,10,1,11,'ITS_Admin',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,11,1,11,'VO1-cam22.',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,12,1,11,'{"up":0.02,"down":-0.02,"right":0.02,"left":-0.02,"zoom_in":0.02,"zoom_out":-0.02}',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,11,'CAM_11',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,12,'Hikvision',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,12,'DS-2DF8242IX-AELW_SNAP',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,12,'http://admin:WSS4Bosch99..,@186.10.86.98/ISAPI/Streaming/channels/1/picture?rnd=1651672097944',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,12,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,12,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,6,1,12,
	'[
			{
				"id": 1,
				"alias": "Preset 1",
				"description": "Preset 1 description",
				"default": true
			},
			{
				"id": 2,
				"alias": "Preset 2",
				"description": "Preset 2 description",
				"default": false
			},
			{
				"id": 3,
				"alias": "Preset 3",
				"description": "Preset 3 description",
				"default": false
			}
		]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,7,1,12,'PTZ',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,9,1,12,'186.10.86.98',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,10,1,12,'admin',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,11,1,12,'WSS4Bosch99..,',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,12,'CAM_12',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1,1,13,'Hikvision',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,2,1,13,'DS-2CD2665FWD-IZS_SNAP',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,3,1,13,'http://admin:RevengaAUSOL@192.168.56.161/ISAPI/Streaming/channels/1/picture?rnd=1651757121456',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,4,1,13,'124',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(2,5,1,13,'RKDJS24PI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,6,1,13,
	'[
			{
				"id": 1,
				"alias": "Preset 1",
				"description": "Preset 1 description",
				"default": true
			},
			{
				"id": 2,
				"alias": "Preset 2",
				"description": "Preset 2 description",
				"default": false
			},
			{
				"id": 3,
				"alias": "Preset 3",
				"description": "Preset 3 description",
				"default": false
			}
		]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,7,1,13,'PTZ',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,9,1,13,'192.168.56.161',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,10,1,13,'admin',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,11,1,13,'RevengaAUSOL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1003,1,13,'CAM_13',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),


(2,1003,1,20,'S1TS-CAM-020',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1004,1,20,'20',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1003,1,21,'S1TS-CAM-021',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1004,1,21,'21',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1003,1,22,'S1TS-CAM-022',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1004,1,22,'22',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1003,1,30,'S1TS-CAM-030',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1004,1,30,'30',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1003,1,31,'S1TS-CAM-031',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1004,1,31,'31',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(2,1003,1,32,'S1TS-CAM-032',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,1004,1,32,'32',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
