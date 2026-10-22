-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 1, 1000, NULL,  NULL, 'Río Verde', 'Ventilación tunel Río Verde', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 1, 
'{
	"configs": [
		{
			"alias": "RV Sentido Cádiz",
			"windSpeedSensorsId" :["ElementValue:66:1:2:2"],
			"windDirectionSensorsId" :["ElementValue:18:1:1:2"],
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
						"max": -4.9		
				},
				{
						"id": 3,
						"alias": "V1",
						"min": -4.9,
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
				"stretchId": "Stretch:1005",
				"alias": "RV Sentido Cádiz S1",	
				"order": 1,
				"fansOrderId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4"],
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			},
			{
				"stretchId": "Stretch:1006",
				"alias": "RV Sentido Cádiz S2",	
				"order": 2,
				"fansToStartByRange": [ {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 0, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 3, "fansOtherTubes": 3 }]
			}]
		},
		{
			"alias": "RV Sentido Málaga",
			"windSpeedSensorsId" :[ "ElementValue:66:1:2:2" ],
			"windDirectionSensorsId" :["ElementValue:18:2:1:2"],
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
						"max": -4.3		
				},
				{
						"id": 3,
						"alias": "V1",
						"min": -4.3,
						"max": -2.4		
				},
				{
						"id": 4,
						"alias": "V0",
						"min": -2.4,
						"max": 1.8	
				},
				{
						"id": 5,
						"alias": "V+1",
						"min": 1.8,
						"max": 4.0	
				},
				{
						"id": 6,
						"alias": "V+2",
						"min": 4.0
				}
			],
			"defaultRangeId": 4,
			"stretchs": [{
				"stretchId": "Stretch:1003",
				"alias": "RV Sentido Málaga S1",	
				"order": 1,
				"fansOrderId": [ "Element:9:5", "Element:9:6", "Element:9:7"],
				"fansToStartByRange": [  {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 1, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 2 }]
			},
			{
				"stretchId": "Stretch:1004",
				"alias": "RV Sentido Málaga S2",	
				"order": 2,
				"fansOrderId": [ "Element:9:8", "Element:9:9", "Element:9:10"],
				"fansToStartByRange": [ {"rangeId": 6, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 5, "fans": 0, "fansOtherTubes": 0 },
						  {"rangeId": 4, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 2, "fansOtherTubes": 2 },
						  {"rangeId": 2, "fans": 3, "fansOtherTubes": 2 },
						  {"rangeId": 1, "fans": 4, "fansOtherTubes": 2 }]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


