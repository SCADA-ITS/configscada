-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 8, 1078, NULL,  NULL, 'Nagüelles', 'Ventilación tunel Nagüelles', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 8, 
'{
	"configs": [
		{
			"alias": "Nagüelles Sentido Cádiz",
			"windSpeedSensorsId" :["ElementValue:66:6:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:34:1:2", "ElementValue:18:33:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V3",
						"max": -6.0	
				},
				{
						"id": 2,
						"alias": "V2",
						"min": -6.0,
						"max": -5.0	
				},
				{
						"id": 3,
						"alias": "V1",			
						"min": -5.0,
						"max": -3.10	
	
				},
				{
						"id": 4,
						"alias": "V0",			
						"min": -3.10,
						"max": 1.80
	
				},
				{
						"id": 5,
						"alias": "V+1",			
						"min": 1.8,
						"max": 2.8
	
				},
				{
						"id": 6,
						"alias": "V+2",			
						"min": 2.8,
						"max": 4.5
	
				},
				{
						"id": 7,
						"alias": "V+3",			
						"min": 4.5
	
				}
			],
			"defaultRangeId": 4,
			"stretchs": [{
				"stretchId": "Stretch:1085",
				"alias": "NG Sentido Cádiz S1",
				"order": 1,
				"fansOrderId": ["Element:9:77", "Element:9:78", "Element:9:79", "Element:9:80", "Element:9:81", "Element:9:82"],
				"fansToStartByRange": [ {"rangeId": 7, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 6, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1086",
				"alias": "NG Sentido Cádiz S2",
				"order": 2,
				"fansOrderId": ["Element:9:83"],
				"fansToStartByRange": [ {"rangeId": 7, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 6, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1087",
				"alias": "NG Sentido Cádiz S3",
				"order": 3,
				"fansOrderId": ["Element:9:84"],
				"fansToStartByRange": [ {"rangeId": 7, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 6, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1088",
				"alias": "NG Sentido Cádiz S4",
				"order": 4,
				"fansToStartByRange": [ {"rangeId": 7, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			}]
		},
		{
			"alias": "Nagüelles Sentido Málaga",
			"windSpeedSensorsId": ["ElementValue:66:6:2:2"],
			"windDirectionSensorsId": ["ElementValue:18:36:1:2", "ElementValue:18:35:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V2",
						"max": -5.9	
				},
				{
						"id": 2,
						"alias": "V1",
						"min": -5.9,
						"max": -3.7		
				},
				{
						"id": 3,
						"alias": "V0",
						"min": -3.7,
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
				"stretchId": "Stretch:1081",
				"alias": "NG Sentido Málaga S1",	
				"order": 1,
				"fansOrderId": ["Element:9:85", "Element:9:86"],
				"fansToStartByRange": [{"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1082",
				"alias": "NG Sentido Málaga S2",	
				"order": 2,
				"fansToStartByRange": [{"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1083",
				"alias": "NG Sentido Málaga S3",	
				"order": 3,
				"fansOrderId": ["Element:9:87"],
				"fansToStartByRange": [{"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1084",
				"alias": "NG Sentido Málaga S4",	
				"order": 4,
				"fansOrderId": ["Element:9:88", "Element:9:89", "Element:9:90"],
				"fansToStartByRange": [{"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


