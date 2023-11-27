-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 3, 1021, NULL,  NULL, 'La Quinta', 'Ventilación tunel La Quinta', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 3, 
'{
	"configs": [
		{
			"alias": "LQ Sentido Cádiz",
			"windSpeedSensorsId" :["ElementValue:66:4:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:9:1:2", "ElementValue:18:8:1:2", "ElementValue:18:7:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V3",
						"max": -5.8	
				},
				{
						"id": 2,
						"alias": "V2",
						"min": -5.8,
						"max": -4.2		
				},
				{
						"id": 3,
						"alias": "V1",
						"min": -4.2,
						"max": -2.1	
				},
				{
						"id": 4,
						"alias": "V0",			
						"min": -2.1,
						"max": 1.8		
				},
				{
						"id": 5,
						"alias": "V+1",			
						"min": 1.8
				}

			],
			"defaultRangeId": 4,
			"stretchs": [{
				"stretchId": "Stretch:1027",
				"alias": "LQ Sentido Cádiz S1",	
				"order": 1,
				"fansOrderId": [ "Element:9:91", "Element:9:92", "Element:9:93", "Element:9:94"],
				"fansToStartByRange": [{"rangeId": 5, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 4, "fansOtherTubes": 3 },
						  {"rangeId": 1, "fans": 6, "fansOtherTubes": 5 }]
			},
			{
				"stretchId": "Stretch:1028",
				"alias": "LQ Sentido Cádiz S2",	
				"order": 2,
				"candidatesFansId": ["Element:9:91", "Element:9:92", "Element:9:106", "Element:9:105", "Element:9:104", "Element:9:103", "Element:9:102", "Element:9:101", "Element:9:100", "Element:9:99", "Element:9:98", "Element:9:97", "Element:9:96", "Element:9:95", "Element:9:94", "Element:9:93"],
				"fansOrderId": [ "Element:9:95", "Element:9:96", "Element:9:97", "Element:9:98"],
				"fansToStartByRange": [{"rangeId": 5, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 4, "fansOtherTubes": 3 },
						  {"rangeId": 1, "fans": 6, "fansOtherTubes": 5 }]
			},
			{
				"stretchId": "Stretch:1029",
				"alias": "LQ Sentido Cádiz S3",	
				"order": 3,
				"fansOrderId": [ "Element:9:99", "Element:9:100", "Element:9:101", "Element:9:102"],
				"fansToStartByRange": [{"rangeId": 5, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 3 },
						  {"rangeId": 1, "fans": 6, "fansOtherTubes": 5 }]
			},
			{
				"stretchId": "Stretch:1030",
				"alias": "LQ Sentido Cádiz S4",	
				"order": 4,
				"fansOrderId": [ "Element:9:103", "Element:9:104", "Element:9:105", "Element:9:106"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 4, "fansOtherTubes": 3 },
						  {"rangeId": 1, "fans": 5, "fansOtherTubes": 5 }]
			}]
		},
		{
			"alias": "LQ Sentido Málaga",
			"windSpeedSensorsId" :[ "ElementValue:66:4:2:2" ],
			"windDirectionSensorsId" :["ElementValue:18:13:1:2", "ElementValue:18:12:1:2", "ElementValue:18:11:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V3",			
						"max": -6.1		
				},
				{
						"id": 2,
						"alias": "V2",
						"min": -6.1,
						"max": -5.1		
				},
				{
						"id": 3,
						"alias": "V1",
						"min": -5.1,
						"max": -3.0		
				},
				{
						"id": 4,
						"alias": "V0",
						"min": -3.0,
						"max": 1.8
				},
				{
						"id": 5,
						"alias": "V0+1",
						"min": 1.8
				}
			],
			"defaultRangeId": 4,
			"stretchs": [{
				"stretchId": "Stretch:1024",
				"alias": "LQ Sentido Málaga S1",	
				"order": 1,
				"fansOrderId": [ "Element:9:107", "Element:9:108", "Element:9:109", "Element:9:110"],
				"fansToStartByRange": [  {"rangeId": 5, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 4, "fansOtherTubes": 4 },
						  {"rangeId": 1, "fans": 5, "fansOtherTubes": 5 }]
			},
			{
				"stretchId": "Stretch:1025",
				"alias": "LQ Sentido Málaga S2",	
				"order": 2,
				"fansOrderId": [ "Element:9:111", "Element:9:112"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 4, "fansOtherTubes": 4 },
						  {"rangeId": 1, "fans": 5, "fansOtherTubes": 5 }]
			},
			{
				"stretchId": "Stretch:1026",
				"alias": "LQ Sentido Málaga S3",	
				"order": 3,
				"fansOrderId": [ "Element:9:113", "Element:9:114", "Element:9:115", "Element:9:116"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 4, "fansOtherTubes": 4 },
						  {"rangeId": 1, "fans": 5, "fansOtherTubes": 5 }]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


