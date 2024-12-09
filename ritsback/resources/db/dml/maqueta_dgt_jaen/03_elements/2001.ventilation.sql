-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 1, 1000, NULL,  NULL, 'Despeñaperros', 'Ventilación tunel Despeñaperros', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
			"windSpeedSensorsId" :["ElementValue:2000:1:2:9"],
			"windDirectionSensorsId": ["ElementValue:18:1:1:2","ElementValue:18:2:1:2","ElementValue:18:3:1:2","ElementValue:18:4:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "Ligero Vc1",
						"max": 1.7		
				},
				{
						"id": 2,
						"alias": "Camión Vc2",
						"min": 1.7,
						"max": 2.5		
				},
				{
						"id": 3,
						"alias": "Mercancía peligrosa Vc3",
						"min": 2.5,
						"max": 3.17	
				}
			],
			"defaultRangeId": 1,
			"stretchs": [{
				"stretchId": "Stretch:1001",
				"alias": "Despeñaperros Sentido Madrid",	
				"order": 1,
				"fansOrderId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", 
								 "Element:9:6", "Element:9:7", "Element:9:8", "Element:9:9", "Element:9:10" ],
				"fansToStartByRange": [ 
                    {"rangeId": 3, "fans": 10, "fansOtherTubes": 4 },
                    {"rangeId": 2, "fans": 10, "fansOtherTubes": 4 },
                    {"rangeId": 1, "fans": 10, "fansOtherTubes": 4 }
                ]
			}]
		},
		{
			"alias": "Despeñaperros Sentido Córdoba",
			"windSpeedSensorsId" :["ElementValue:2000:2:2:9"],
			"windDirectionSensorsId": ["ElementValue:18:5:1:2","ElementValue:18:6:1:2","ElementValue:18:7:1:2","ElementValue:18:8:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "Ligero Vc1",
						"max": 1.7		
				},
				{
						"id": 2,
						"alias": "Camión Vc2",
						"min": 1.7,
						"max": 2.5		
				},
				{
						"id": 3,
						"alias": "Mercancía peligrosa Vc3",
						"min": 2.5,
						"max": 3.17	
				}
			],
			"defaultRangeId": 1,
			"stretchs": [{
				"stretchId": "Stretch:1002",
				"alias": "Despeñaperros Sentido Córdoba",	
				"order": 1,
				"fansOrderId": [ "Element:9:11", "Element:9:12", "Element:9:13", "Element:9:14", "Element:9:15", 
								 "Element:9:16", "Element:9:17", "Element:9:18", "Element:9:19", "Element:9:20",
								 "Element:9:21", "Element:9:22", "Element:9:23", "Element:9:24", "Element:9:25",
								 "Element:9:26", "Element:9:27", "Element:9:28", "Element:9:29", "Element:9:30",
								 "Element:9:31", "Element:9:32", "Element:9:33", "Element:9:34", "Element:9:35",
								 "Element:9:36" ],
				"fansToStartByRange": [ 
                    {"rangeId": 3, "fans": 26, "fansOtherTubes": 4 },
                    {"rangeId": 2, "fans": 26, "fansOtherTubes": 4 },
                    {"rangeId": 1, "fans": 0, "fansOtherTubes": 4 }
                ]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
