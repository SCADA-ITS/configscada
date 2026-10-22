-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 5, 1042, NULL,  NULL, 'Casares', 'Ventilación tunel Casares', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 5, 
'{
	"configs": [
		{
			"alias": "Casares Sentido Cádiz",
			"windSpeedSensorsId" :["ElementValue:66:8:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:23:1:2", "ElementValue:18:22:1:2", "ElementValue:18:21:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V2",
						"max": -5.8	
				},
				{
						"id": 2,
						"alias": "V1",
						"min": -5.8,
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
						"min": 1.8,
						"max": 2.8		
				},
				{
						"id": 5,
						"alias": "V+2",			
						"min": 2.8
				}
			],
			"defaultRangeId": 3,
			"stretchs": [{
				"stretchId": "Stretch:1049",
				"alias": "CAS Sentido Cádiz S1",	
				"order": 1,
				"fansOrderId": ["Element:9:35", "Element:9:36", "Element:9:37", "Element:9:38", "Element:9:39", "Element:9:40"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1050",
				"alias": "CAS Sentido Cádiz S2",	
				"order": 2,
				"fansToStartByRange": [{"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1051",
				"alias": "CAS Sentido Cádiz S3",	
				"order": 3,
				"fansToStartByRange": [{"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1052",
				"alias": "CAS Sentido Cádiz S4",	
				"order": 4,
				"fansOrderId": ["Element:9:41", "Element:9:42"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			}]
		},
		{
			"alias": "CAS Sentido Málaga",
			"windSpeedSensorsId" :["ElementValue:66:8:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:20:1:2", "ElementValue:18:19:1:2", "ElementValue:18:18:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V2",
						"max": -5.8	
				},
				{
						"id": 2,
						"alias": "V1",
						"min": -5.8,
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
						"min": 1.8,
						"max": 4.0
				},
				{
						"id": 5,
						"alias": "V+2",			
						"min": 4.0
				}
			],
			"defaultRangeId": 3,
			"stretchs": [{
				"stretchId": "Stretch:1045",
				"alias": "CAS Sentido Málaga S1",	
				"order": 1,
				"fansOrderId": ["Element:9:25", "Element:9:26", "Element:9:27", "Element:9:28", "Element:9:29", "Element:9:30"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 2, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1046",
				"alias": "CAS Sentido Málaga S2",	
				"order": 2,
				"fansOrderId": ["Element:9:31"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1047",
				"alias": "CAS Sentido Málaga S3",	
				"order": 3,
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1048",
				"alias": "CAS Sentido Málaga S4",	
				"order": 4,
				"fansOrderId": ["Element:9:32", "Element:9:33", "Element:9:34"],
				"fansToStartByRange": [{"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


