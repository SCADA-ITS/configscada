-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 1, 1000, NULL,  NULL, 'Despeñaperros', 'Ventilación tunel Despeñaperros', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, NULL, 2, 3000, NULL,  NULL, 'El Corzo', 'Ventilación tunel El Corzo', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 1, 
'{
	"configs": [
		{
			"alias": "Despeñaperros Sentido Madrid",
			"windSpeedSensorsId" :["ElementValue:18:1:1:2", "ElementValue:18:2:1:2", "ElementValue:18:3:1:2", "ElementValue:18:4:1:2"],
			"defaultRangeId": 1,
			"elementTubeId":"Element:2000:1",
			"stretchs": [{
				"stretchId": "Stretch:1003",
				"locationId": 15011001,
				"alias": "ZI-1-01-DP",	
				"order": 1,
				"fansOrderId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", 
								 "Element:9:6", "Element:9:7", "Element:9:8", "Element:9:9", "Element:9:10" ],
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:5", "Element:9:4", "Element:9:3", "Element:9:1", "Element:9:2"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15021001,
				"alias": "ZI-1-02-DP",	
				"order": 2,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:5", "Element:9:4", "Element:9:3", "Element:9:1", "Element:9:2"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15031001,
				"alias": "ZI-1-03-DP",	
				"order": 3,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:5", "Element:9:4", "Element:9:1", "Element:9:2", "Element:9:3"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15041001,
				"alias": "ZI-1-04-DP",	
				"order": 4,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:5", "Element:9:1", "Element:9:2", "Element:9:4", "Element:9:3"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15051001,
				"alias": "ZI-1-05-DP",	
				"order": 5,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:1", "Element:9:2", "Element:9:5", "Element:9:3", "Element:9:4"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15061001,
				"alias": "ZI-1-06-DP",	
				"order": 6,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15071001,
				"alias": "ZI-1-07-DP",	
				"order": 7,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15081001,
				"alias": "ZI-1-08-DP",	
				"order": 8,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15091001,
				"alias": "ZI-1-09-DP",	
				"order": 9,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:1", "Element:9:2", "Element:9:7", "Element:9:3", "Element:9:6", "Element:9:4", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15101001,
				"alias": "ZI-1-10-DP",	
				"order": 10,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:7", "Element:9:4", "Element:9:6", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15111001,
				"alias": "ZI-1-11-DP",	
				"order": 11,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:1", "Element:9:2", "Element:9:8", "Element:9:3", "Element:9:4", "Element:9:7", "Element:9:5", "Element:9:6"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15121001,
				"alias": "ZI-1-12-DP",	
				"order": 12,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:8", "Element:9:4", "Element:9:5", "Element:9:7", "Element:9:6"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15131001,
				"alias": "ZI-1-13-DP",	
				"order": 13,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:9", "Element:9:10", "Element:9:4", "Element:9:8", "Element:9:5", "Element:9:7", "Element:9:6"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15141001,
				"alias": "ZI-1-14-DP",	
				"order": 14,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:9", "Element:9:10", "Element:9:5", "Element:9:8", "Element:9:7", "Element:9:6"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15151001,
				"alias": "ZI-1-15-DP",	
				"order": 15,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:6", "Element:9:7"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15161001,
				"alias": "ZI-1-16-DP",	
				"order": 16,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:6", "Element:9:7"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15171001,
				"alias": "ZI-1-17-DP",	
				"order": 17,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:6", "Element:9:7"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15181001,
				"alias": "ZI-1-18-DP",	
				"order": 18,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:9", "Element:9:10", "Element:9:6", "Element:9:7", "Element:9:8"]
			},
			{
				"stretchId": "Stretch:1003",
				"locationId": 15191001,
				"alias": "ZI-1-19-DP",	
				"order": 19,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:6", "Element:9:7", "Element:9:9", "Element:9:10", "Element:9:8"]
			}]
		},
		{
			"alias": "Despeñaperros Sentido Cordoba",
			"windSpeedSensorsId" :["ElementValue:18:5:1:2", "ElementValue:18:6:1:2", "ElementValue:18:7:1:2", "ElementValue:18:8:1:2"],
			"defaultRangeId": 1,
			"elementTubeId":"Element:2000:2",
			"stretchs": [{
				"stretchId": "Stretch:1004",
				"locationId": 200015,
				"alias": "ZI-2-01-DP",	
				"order": 1,
				"fansOrderId": [ "Element:9:36", "Element:9:35", "Element:9:34", "Element:9:33", "Element:9:32", 
								 "Element:9:31", "Element:9:30", "Element:9:29", "Element:9:28", "Element:9:27", 
								 "Element:9:26", "Element:9:25", "Element:9:24", "Element:9:23", "Element:9:22",
								 "Element:9:21", "Element:9:20", "Element:9:19", "Element:9:18", "Element:9:17",
								 "Element:9:16", "Element:9:15", "Element:9:14", "Element:9:13", "Element:9:12", 
								 "Element:9:11" ],
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:25", "Element:9:26"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 210015,
				"alias": "ZI-2-02-DP",	
				"order": 2,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:35", "Element:9:36"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 220015,
				"alias": "ZI-2-03-DP",	
				"order": 3,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:35", "Element:9:36"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 230015,
				"alias": "ZI-2-04-DP",	
				"order": 4,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:35", "Element:9:36"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 240015,
				"alias": "ZI-2-05-DP",	
				"order": 5,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:35", "Element:9:36"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 250015,
				"alias": "ZI-2-06-DP",	
				"order": 6,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:35", "Element:9:36", "Element:9:21", "Element:9:22", "Element:9:33", "Element:9:34"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 260015,
				"alias": "ZI-2-07-DP",	
				"order": 7,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:35", "Element:9:36", "Element:9:17", "Element:9:18", "Element:9:33", "Element:9:34", "Element:9:19", "Element:9:20", "Element:9:31", "Element:9:32"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 270015,
				"alias": "ZI-2-08-DP",	
				"order": 8,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:35", "Element:9:36", "Element:9:13", "Element:9:14", "Element:9:33", "Element:9:34", "Element:9:15", "Element:9:16", "Element:9:31", "Element:9:32", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 280015,
				"alias": "ZI-2-09-DP",	
				"order": 9,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:11", "Element:9:12", "Element:9:31", "Element:9:32", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 290015,
				"alias": "ZI-2-10-DP",	
				"order": 10,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:29", "Element:9:30"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 300015,
				"alias": "ZI-2-11-DP",	
				"order": 11,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:29", "Element:9:30", "Element:9:15", "Element:9:16", "Element:9:27", "Element:9:28"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 310015,
				"alias": "ZI-2-12-DP",	
				"order": 12,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:11", "Element:9:12", "Element:9:27", "Element:9:28", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 320015,
				"alias": "ZI-2-13-DP",	
				"order": 13,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 330015,
				"alias": "ZI-2-14-DP",	
				"order": 14,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 340015,
				"alias": "ZI-2-15-DP",	
				"order": 15,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 350015,
				"alias": "ZI-2-16-DP",	
				"order": 16,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:11", "Element:9:12", "Element:9:23", "Element:9:24"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 360015,
				"alias": "ZI-2-17-DP",	
				"order": 17,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:23", "Element:9:24", "Element:9:21", "Element:9:22"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 370015,
				"alias": "ZI-2-18-DP",	
				"order": 18,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:23", "Element:9:24", "Element:9:21", "Element:9:22"]
			},
			{
				"stretchId": "Stretch:1004",
				"locationId": 380015,
				"alias": "ZI-2-19-DP",	
				"order": 19,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:23", "Element:9:24", "Element:9:21", "Element:9:22"]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2001, 1, 1, 2, 
'{
	"configs": [
		{
			"alias": "El CORZO Sentido Madrid",
			"windSpeedSensorsId" :["ElementValue:18:9:1:2"],
			"defaultRangeId": 1,
			"elementTubeId":"Element:2000:5",
			"stretchs": [{
				"stretchId": "Stretch:3003",
				"locationId": 470015,
				"alias": "ZI-1-01-CZ",	
				"order": 1,
				"fansOrderId": [ "Element:9:37", "Element:9:38", "Element:9:39", "Element:9:40", "Element:9:41", 
								 "Element:9:42", "Element:9:43", "Element:9:44"],
				"candidatesFansId": [ "Element:9:43", "Element:9:44", "Element:9:41", "Element:9:42", "Element:9:39", "Element:9:40", "Element:9:37", "Element:9:38"]
			},
			{
					"stretchId": "Stretch:3003",
					"locationId": 480015,
					"alias": "ZI-1-02-CZ",	
					"order": 2,
					"candidatesFansId": [ "Element:9:43", "Element:9:44", "Element:9:41", "Element:9:42", "Element:9:37", "Element:9:38", "Element:9:39", "Element:9:40"]
			},
			{
					"stretchId": "Stretch:3003",
					"locationId": 490015,
					"alias": "ZI-1-03-CZ",	
					"order": 3,
					"candidatesFansId": [ "Element:9:37", "Element:9:38", "Element:9:43", "Element:9:44", "Element:9:39", "Element:9:40", "Element:9:41", "Element:9:42"]
			},
			{
					"stretchId": "Stretch:3003",
					"locationId": 500015,
					"alias": "ZI-1-04-CZ",	
					"order": 4,
					"candidatesFansId": [ "Element:9:37", "Element:9:38", "Element:9:39", "Element:9:40", "Element:9:41", "Element:9:42", "Element:9:43", "Element:9:44"]
			}]
		},
		{
			"alias": "El CORZO Sentido Cordoba",
			"windSpeedSensorsId" :["ElementValue:18:9:1:2"],
			"defaultRangeId": 1,
			"elementTubeId":"Element:2000:6",
			"stretchs": [{
				"stretchId": "Stretch:3004",
				"locationId": 510015,
				"alias": "ZI-2-01-CZ",	
				"order": 1
			},
			{
				"stretchId": "Stretch:3004",
				"locationId": 520015,
				"alias": "ZI-2-02-CZ",	
				"order": 2
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);