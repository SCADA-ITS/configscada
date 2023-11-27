-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 2, 1007, NULL,  NULL, 'Monte Mayor', 'Ventilación tunel Monte Mayor', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 2, 
'{
	"maxNfansStart" : 12,
	"configs": [
		{
			"alias": "MM Sentido Cádiz",
			"windSpeedSensorsId" :["ElementValue:66:2:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:4:1:2", "ElementValue:18:3:1:2"],
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
						"max": -4.8		
				},
				{
						"id": 3,
						"alias": "V1",
						"min": -4.8,
						"max": -2.8	
				},
				{
						"id": 4,
						"alias": "V0",			
						"min": -2.8,
						"max": 1.8		
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
						"min": 2.8	
				}

			],
			"defaultRangeId": 4,
			"stretchs": [{
				"stretchId": "Stretch:1014",
				"alias": "MM Sentido Cádiz S1",	
				"order": 1,
				"fansOrderId": [ "Element:9:11", "Element:9:12"],
				"fansToStartByRange": [ {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1015",
				"alias": "MM Sentido Cádiz S2",	
				"order": 2,
				"fansToStartByRange": [ {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1016",
				"alias": "MM Sentido Cádiz S3",	
				"order": 3,
				"fansOrderId": [ "Element:9:13", "Element:9:14", "Element:9:15"],
				"fansToStartByRange": [ {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1017",
				"alias": "MM Sentido Cádiz S4",	
				"order": 4,
				"fansOrderId": [ "Element:9:16", "Element:9:17", "Element:9:18"],
				"fansToStartByRange": [ {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			}]
		},
		{
			"alias": "MM Sentido Málaga",
			"windSpeedSensorsId" :[ "ElementValue:66:2:2:2" ],
			"windDirectionSensorsId" :["ElementValue:18:6:1:2", "ElementValue:18:5:1:2"],
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
						"max": -5.0		
				},
				{
						"id": 3,
						"alias": "V1",
						"min": -5.0,
						"max": -2.8		
				},
				{
						"id": 4,
						"alias": "V0",
						"min": -2.8,
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
				"stretchId": "Stretch:1010",
				"alias": "MM Sentido Málaga S1",	
				"order": 1,
				"fansOrderId": [ "Element:9:19", "Element:9:20", "Element:9:21", "Element:9:22", "Element:9:23"],
				"fansToStartByRange": [  {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1011",
				"alias": "MM Sentido Málaga S2",	
				"order": 2,
				"fansOrderId": ["Element:9:24"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1012",
				"alias": "MM Sentido Málaga S3",	
				"order": 3,
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1013",
				"alias": "MM Sentido Málaga S4",	
				"order": 4,
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


