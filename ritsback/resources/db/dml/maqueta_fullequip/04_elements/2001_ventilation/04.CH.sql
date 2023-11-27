-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 4, 1031, NULL,  NULL, 'Calahonda', 'Ventilación tunel Calahonda', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 4, 
'{
	"configs": [
		{
			"alias": "CH Sentido Cádiz",
			"windSpeedSensorsId" :["ElementValue:66:3:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:16:1:2", "ElementValue:18:14:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V3",
						"max": -5.9	
				},
				{
						"id": 2,
						"alias": "V2",
						"min": -5.9,
						"max": -4.6		
				},
				{
						"id": 3,
						"alias": "V1",
						"min": -4.6,
						"max": -2.1	
				},
				{
						"id": 4,
						"alias": "V0",			
						"min": -2.5,
						"max": 1.8		
				},
				{
						"id": 5,
						"alias": "V+1",			
						"min": 1.8,
						"max": 2.2
				},
				{
						"id": 6,
						"alias": "V+2",			
						"min": 2.2,
						"max": 2.8
				},
				{
						"id": 7,
						"alias": "V+3",			
						"min": 2.8
				}
			],
			"defaultRangeId": 4,
			"stretchs": [{
				"stretchId": "Stretch:1038",
				"alias": "CH Sentido Cádiz S1",	
				"order": 1,
				"candidatesFansId": ["Element:9:66", "Element:9:65", "Element:9:61", "Element:9:62", "Element:9:63", "Element:9:64"],
				"fansOrderId": ["Element:9:61", "Element:9:62", "Element:9:63", "Element:9:64", "Element:9:65", "Element:9:66"],
				"fansToStartByRange": [{"rangeId": 7, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1039",
				"alias": "CH Sentido Cádiz S2",	
				"order": 2,
				"fansOrderId": ["Element:9:67", "Element:9:68"],
				"fansToStartByRange": [{"rangeId": 7, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1040",
				"alias": "CH Sentido Cádiz S3",	
				"order": 3,
				"fansToStartByRange": [{"rangeId": 7, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 6, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1041",
				"alias": "CH Sentido Cádiz S4",	
				"order": 4,
				"fansToStartByRange": [ {"rangeId": 7, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			}]
		},
		{
			"alias": "CH Sentido Málaga",
			"windSpeedSensorsId" :[ "ElementValue:66:3:2:2" ],
			"windDirectionSensorsId" :["ElementValue:18:17:1:2", "ElementValue:18:15:1:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V3",
						"max": -5.9	
				},
				{
						"id": 2,
						"alias": "V2",
						"min": -5.9,
						"max": -4.6		
				},
				{
						"id": 3,
						"alias": "V1",
						"min": -4.6,
						"max": -2.1	
				},
				{
						"id": 4,
						"alias": "V0",			
						"min": -2.5,
						"max": 1.8		
				},
				{
						"id": 5,
						"alias": "V+1",			
						"min": 1.8,
						"max": 2.2
				},
				{
						"id": 6,
						"alias": "V+2",			
						"min": 2.2
				}
			],
			"defaultRangeId": 4,
			"stretchs": [{
				"stretchId": "Stretch:1034",
				"alias": "CH Sentido Málaga S1",	
				"order": 1,
				"fansOrderId": [ "Element:9:69", "Element:9:70"],
				"fansToStartByRange": [  {"rangeId": 6, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1035",
				"alias": "CH Sentido Málaga S2",	
				"order": 2,
				"fansOrderId": [ "Element:9:71", "Element:9:72"],
				"fansToStartByRange": [ {"rangeId": 6, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1036",
				"alias": "CH Sentido Málaga S3",	
				"order": 3,
				"fansOrderId": [ "Element:9:73"],
				"fansToStartByRange": [ {"rangeId": 6, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1037",
				"alias": "CH Sentido Málaga S4",	
				"order": 4,
				"fansOrderId": [ "Element:9:74", "Element:9:75", "Element:9:76"],
				"fansToStartByRange": [ {"rangeId": 6, "fans": 0, "fansOtherTubes": 0},
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


