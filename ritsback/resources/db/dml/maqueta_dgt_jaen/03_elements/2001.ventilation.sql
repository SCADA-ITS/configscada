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
				"stretchId": "Stretch:15011001",
				"alias": "ZI-1-01-DP",	
				"order": 1,
				"fansOrderId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", 
								 "Element:9:6", "Element:9:7", "Element:9:8", "Element:9:9", "Element:9:10" ],
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:5", "Element:9:4", "Element:9:3", "Element:9:1", "Element:9:2"]
			},
			{
				"stretchId": "Stretch:15021001",
				"alias": "ZI-1-02-DP",	
				"order": 2,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:5", "Element:9:4", "Element:9:3", "Element:9:1", "Element:9:2"]
			},
			{
				"stretchId": "Stretch:15031001",
				"alias": "ZI-1-03-DP",	
				"order": 3,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:5", "Element:9:4", "Element:9:1", "Element:9:2", "Element:9:3"]
			},
			{
				"stretchId": "Stretch:15041001",
				"alias": "ZI-1-04-DP",	
				"order": 4,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:5", "Element:9:1", "Element:9:2", "Element:9:4", "Element:9:3"]
			},
			{
				"stretchId": "Stretch:15051001",
				"alias": "ZI-1-05-DP",	
				"order": 5,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:1", "Element:9:2", "Element:9:5", "Element:9:3", "Element:9:4"]
			},
			{
				"stretchId": "Stretch:15061001",
				"alias": "ZI-1-06-DP",	
				"order": 6,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:15071001",
				"alias": "ZI-1-07-DP",	
				"order": 7,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:15081001",
				"alias": "ZI-1-08-DP",	
				"order": 8,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:7", "Element:9:6", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:15091001",
				"alias": "ZI-1-09-DP",	
				"order": 9,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:1", "Element:9:2", "Element:9:7", "Element:9:3", "Element:9:6", "Element:9:4", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:15101001",
				"alias": "ZI-1-10-DP",	
				"order": 10,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:7", "Element:9:4", "Element:9:6", "Element:9:5"]
			},
			{
				"stretchId": "Stretch:15111001",
				"alias": "ZI-1-11-DP",	
				"order": 11,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:1", "Element:9:2", "Element:9:8", "Element:9:3", "Element:9:4", "Element:9:7", "Element:9:5", "Element:9:6"]
			},
			{
				"stretchId": "Stretch:15121001",
				"alias": "ZI-1-12-DP",	
				"order": 12,
				"candidatesFansId": [ "Element:9:9", "Element:9:10", "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:8", "Element:9:4", "Element:9:5", "Element:9:7", "Element:9:6"]
			},
			{
				"stretchId": "Stretch:15131001",
				"alias": "ZI-1-13-DP",	
				"order": 13,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:9", "Element:9:10", "Element:9:4", "Element:9:8", "Element:9:5", "Element:9:7", "Element:9:6"]
			},
			{
				"stretchId": "Stretch:15141001",
				"alias": "ZI-1-14-DP",	
				"order": 14,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:9", "Element:9:10", "Element:9:5", "Element:9:8", "Element:9:7", "Element:9:6"]
			},
			{
				"stretchId": "Stretch:15151001",
				"alias": "ZI-1-15-DP",	
				"order": 15,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:6", "Element:9:7"]
			},
			{
				"stretchId": "Stretch:15161001",
				"alias": "ZI-1-16-DP",	
				"order": 16,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:6", "Element:9:7"]
			},
			{
				"stretchId": "Stretch:15171001",
				"alias": "ZI-1-17-DP",	
				"order": 17,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:9", "Element:9:10", "Element:9:8", "Element:9:6", "Element:9:7"]
			},
			{
				"stretchId": "Stretch:15181001",
				"alias": "ZI-1-18-DP",	
				"order": 18,
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:9", "Element:9:10", "Element:9:6", "Element:9:7", "Element:9:8"]
			},
			{
				"stretchId": "Stretch:15191001",
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
				"stretchId": "Stretch:15011002",
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
				"stretchId": "Stretch:15021002",
				"alias": "ZI-2-02-DP",	
				"order": 2,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:35", "Element:9:36"]
			},
			{
				"stretchId": "Stretch:15031002",
				"alias": "ZI-2-03-DP",	
				"order": 3,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:35", "Element:9:36"]
			},
			{
				"stretchId": "Stretch:15041002",
				"alias": "ZI-2-04-DP",	
				"order": 4,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:35", "Element:9:36"]
			},
			{
				"stretchId": "Stretch:15051002",
				"alias": "ZI-2-05-DP",	
				"order": 5,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:35", "Element:9:36"]
			},
			{
				"stretchId": "Stretch:15061002",
				"alias": "ZI-2-06-DP",	
				"order": 6,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20", "Element:9:35", "Element:9:36", "Element:9:21", "Element:9:22", "Element:9:33", "Element:9:34"]
			},
			{
				"stretchId": "Stretch:15071002",
				"alias": "ZI-2-07-DP",	
				"order": 7,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:35", "Element:9:36", "Element:9:17", "Element:9:18", "Element:9:33", "Element:9:34", "Element:9:19", "Element:9:20", "Element:9:31", "Element:9:32"]
			},
			{
				"stretchId": "Stretch:15081002",
				"alias": "ZI-2-08-DP",	
				"order": 8,
				"candidatesFansId": [ "Element:9:11", "Element:9:12", "Element:9:35", "Element:9:36", "Element:9:13", "Element:9:14", "Element:9:33", "Element:9:34", "Element:9:15", "Element:9:16", "Element:9:31", "Element:9:32", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20"]
			},
			{
				"stretchId": "Stretch:15091002",
				"alias": "ZI-2-09-DP",	
				"order": 9,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:11", "Element:9:12", "Element:9:31", "Element:9:32", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20"]
			},
			{
				"stretchId": "Stretch:15101002",
				"alias": "ZI-2-10-DP",	
				"order": 10,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:29", "Element:9:30"]
			},
			{
				"stretchId": "Stretch:15111002",
				"alias": "ZI-2-11-DP",	
				"order": 11,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:29", "Element:9:30", "Element:9:15", "Element:9:16", "Element:9:27", "Element:9:28"]
			},
			{
				"stretchId": "Stretch:15121002",
				"alias": "ZI-2-12-DP",	
				"order": 12,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:11", "Element:9:12", "Element:9:27", "Element:9:28", "Element:9:13", "Element:9:14", "Element:9:15", "Element:9:16"]
			},
			{
				"stretchId": "Stretch:15131002",
				"alias": "ZI-2-13-DP",	
				"order": 13,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14"]
			},
			{
				"stretchId": "Stretch:15141002",
				"alias": "ZI-2-14-DP",	
				"order": 14,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14"]
			},
			{
				"stretchId": "Stretch:15151002",
				"alias": "ZI-2-15-DP",	
				"order": 15,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14"]
			},
			{
				"stretchId": "Stretch:15161002",
				"alias": "ZI-2-16-DP",	
				"order": 16,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:11", "Element:9:12", "Element:9:23", "Element:9:24"]
			},
			{
				"stretchId": "Stretch:15171002",
				"alias": "ZI-2-17-DP",	
				"order": 17,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:23", "Element:9:24", "Element:9:21", "Element:9:22"]
			},
			{
				"stretchId": "Stretch:15181002",
				"alias": "ZI-2-18-DP",	
				"order": 18,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:23", "Element:9:24", "Element:9:21", "Element:9:22"]
			},
			{
				"stretchId": "Stretch:15191002",
				"alias": "ZI-2-19-DP",	
				"order": 19,
				"candidatesFansId": [ "Element:9:35", "Element:9:36", "Element:9:33", "Element:9:34", "Element:9:31", "Element:9:32", "Element:9:29", "Element:9:30", "Element:9:27", "Element:9:28", "Element:9:25", "Element:9:26", "Element:9:23", "Element:9:24", "Element:9:21", "Element:9:22"]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

