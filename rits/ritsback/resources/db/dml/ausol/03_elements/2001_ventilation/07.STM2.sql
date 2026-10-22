-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 7, 1064, NULL,  NULL, 'Santa María II', 'Ventilación tunel Santa María II', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 7, 
'{
	"configs": [
		{
			"alias": "Santa María II Sentido Cádiz",
			"windSpeedSensorsId" :["ElementValue:66:7:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:30:1:2", "ElementValue:18:29:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V2",
						"max": -6.0	
				},
				{
						"id": 2,
						"alias": "V1",
						"min": -6.0,
						"max": -2.8		
				},
				{
						"id": 3,
						"alias": "V0",
						"min": -2.8,
						"max": 0.0	
				},
				{
						"id": 4,
						"alias": "V+1",			
						"min": 0.0,
						"max": 1.8	
	
				},
				{
						"id": 5,
						"alias": "V+2",			
						"min": 1.8
	
				}
			],
			"defaultRangeId": 3,
			"stretchs": [{
				"stretchId": "Stretch:1071",
				"alias": "STM2 Sentido Cádiz S1",
				"order": 1,
				"fansOrderId": ["Element:9:117", "Element:9:118"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1072",
				"alias": "STM2 Sentido Cádiz S2",
				"order": 2,
				"fansToStartByRange": [{"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1073",
				"alias": "STM2 Sentido Cádiz S3",
				"order": 3,
				"fansOrderId": ["Element:9:119"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1074",
				"alias": "STM2 Sentido Cádiz S4",
				"order": 4,
				"fansOrderId": ["Element:9:120", "Element:9:121", "Element:9:122"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			}]
		},
		{
			"alias": "Santa María II Sentido Málaga",
			"windSpeedSensorsId": ["ElementValue:66:7:2:2"],
			"windDirectionSensorsId": ["ElementValue:18:32:1:2", "ElementValue:18:31:1:2"],
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
						"max": -3.8		
				},
				{
						"id": 3,
						"alias": "V0",
						"min": -3.8,
						"max": 1.8		
				},
				{
						"id": 4,
						"alias": "V+1",			
						"min": 1.8,
						"max": 2.2	
				},
				{
						"id": 5,
						"alias": "V+2",			
						"min": 2.2
				}
			],
			"defaultRangeId": 3,
			"stretchs": [{
				"stretchId": "Stretch:1067",
				"alias": "STM2 Sentido Málaga S1",	
				"order": 1,
				"candidatesFansId": ["Element:9:128", "Element:9:123", "Element:9:124", "Element:9:125", "Element:9:126", "Element:9:127"],
				"fansOrderId": ["Element:9:123", "Element:9:124", "Element:9:125", "Element:9:126", "Element:9:127"],
				"fansToStartByRange": [  {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1068",
				"alias": "STM2 Sentido Málaga S2",	
				"order": 2,
				"fansOrderId": ["Element:9:128"],
				"fansToStartByRange": [{"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1069",
				"alias": "STM2 Sentido Málaga S3",	
				"order": 3,
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1070",
				"alias": "STM2 Sentido Málaga S4",	
				"order": 4,
				"fansToStartByRange": [{"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


