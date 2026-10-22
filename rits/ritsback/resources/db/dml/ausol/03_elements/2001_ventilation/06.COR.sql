-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 6, 1053, NULL,  NULL, 'Coromiras', 'Ventilación tunel Coromiras', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 6, 
'{
	"configs": [
		{
			"alias": "Corominas Sentido Cádiz",
			"windSpeedSensorsId" :["ElementValue:66:5:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:26:1:2", "ElementValue:18:25:1:2", "ElementValue:18:24:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V2",
						"max": -5.7	
				},
				{
						"id": 2,
						"alias": "V1",
						"min": -5.7,
						"max": -3.5		
				},
				{
						"id": 3,
						"alias": "V0",
						"min": -3.5,
						"max": 1.8	
				},
				{
						"id": 4,
						"alias": "V+1",			
						"min": 1.8	
				}
			],
			"defaultRangeId": 3,
			"stretchs": [{
				"stretchId": "Stretch:1060",
				"alias": "COR Sentido Cádiz S1",	
				"order": 1,
				"fansOrderId": ["Element:9:43", "Element:9:44", "Element:9:45", "Element:9:46", "Element:9:47"],
				"fansToStartByRange": [ {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1061",
				"alias": "COR Sentido Cádiz S2",	
				"order": 2,
				"fansToStartByRange": [{"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1062",
				"alias": "COR Sentido Cádiz S3",	
				"order": 3,
				"fansOrderId": ["Element:9:48"],
				"fansToStartByRange": [ {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1063",
				"alias": "COR Sentido Cádiz S4",	
				"order": 4,
				"fansOrderId": ["Element:9:49", "Element:9:50"],
				"fansToStartByRange": [ {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			}]
		},
		{
			"alias": "Corominas Sentido Málaga",
			"windSpeedSensorsId" :["ElementValue:66:5:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:37:1:2", "ElementValue:18:28:1:2", "ElementValue:18:27:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V2",
						"max": -5.3	
				},
				{
						"id": 2,
						"alias": "V1",
						"min": -5.3,
						"max": -2.9		
				},
				{
						"id": 3,
						"alias": "V0",
						"min": -2.9,
						"max": 1.8		
				},
				{
						"id": 4,
						"alias": "V+1",			
						"min": 1.8
				}
			],
			"defaultRangeId": 3,
			"stretchs": [{
				"stretchId": "Stretch:1056",
				"alias": "COR Sentido Málaga S1",	
				"order": 1,
				"fansOrderId": ["Element:9:51", "Element:9:52", "Element:9:53", "Element:9:54", "Element:9:55", "Element:9:56"],
				"fansToStartByRange": [  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1057",
				"alias": "COR Sentido Málaga S2",	
				"order": 2,
				"fansToStartByRange": [{"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1058",
				"alias": "COR Sentido Málaga S3",	
				"order": 3,
				"fansToStartByRange": [ {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 4, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1059",
				"alias": "COR Sentido Málaga S4",	
				"order": 4,
				"fansOrderId": ["Element:9:57", "Element:9:58", "Element:9:59", "Element:9:60"],
				"fansToStartByRange": [{"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


