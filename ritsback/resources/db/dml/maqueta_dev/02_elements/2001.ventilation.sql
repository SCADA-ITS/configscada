-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 1, 1000, NULL,  NULL, 'VENTILATION_1', 'VENTILATION_1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 1, 1, 
'{
	"configs": [
		{
			"alias": "TUBO_1",
			"windSpeedSensorsId" :[ "ElementValue:3:1:4:2", "ElementValue:9:1:1:2"],
			"windDirectionSensorsId" :["ElementValue:9:1:2:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V0",
						"min": -6,
						"max": 2		
				},
				{
						"id": 2,
						"alias": "V1",
						"min": 2,
						"max": 4.25		
				},
				{
						"id": 3,
						"alias": "V2",
						"min": 4.25,
						"max": 5.8	
				},
				{
						"id": 4,
						"alias": "V3",			
						"min": 5.8,
						"max": 6.5		
				}
			],
			"defaultRangeId": 2,
			"stretchs": [{
				"stretchId": "Stretch:1",
				"alias": "TUBO_1_S1",	
				"order": 1,
				"fansOrderId": [ "Element:9:1", "Element:9:2", "Element:9:3"],
				"candidatesFansId": [ "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:6"],
				"fansToStartByRange": [ {"rangeId": 1, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 3, "fansOtherTubes": 1 },
						  {"rangeId": 4, "fans": 3, "fansOtherTubes": 1 }]
			},
			{
				"stretchId": "Stretch:2",
				"alias": "TUBO_1_S2",	
				"order": 2,
				"fansOrderId": [ "Element:9:4", "Element:9:5", "Element:9:6"],
				"candidatesFansId": [ "Element:9:4", "Element:9:5", "Element:9:6", "Element:9:1", "Element:9:2", "Element:9:3"],
				"fansToStartByRange": [ {"rangeId": 1, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 3, "fansOtherTubes": 1 },
						  {"rangeId": 4, "fans": 3, "fansOtherTubes": 1 }]
			}]
		},
		{
			"alias": "TUBO_2",
			"windSpeedSensorsId" :[ "ElementValue:3:1:4:2" ],
			"windDirectionSensorsId" :["ElementValue:9:1:2:2"],
			"windSpeedRanges":[
				{
						"id": 1,
						"alias": "V0",
						"min": 0,
						"max": 1.5		
				},
				{
						"id": 2,
						"alias": "V1",
						"min": 1.7,
						"max": 4.25		
				},
				{
						"id": 3,
						"alias": "V2",
						"min": 4.25,
						"max": 5.8	
				},
				{
						"id": 4,
						"alias": "V3",			
						"min": 5.8,
						"max": 6.5		
				}
			],
			"defaultRangeId": 2,
			"stretchs": [{
				"stretchId": "Stretch:3",
				"alias": "TUBO_2_S1",	
				"order": 1,
				"fansOrderId": [ "Element:9:7", "Element:9:8", "Element:9:9", "Element:9:10"],
				"fansToStartByRange": [ {"rangeId": 1, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 3, "fansOtherTubes": 1 },
						  {"rangeId": 4, "fans": 3, "fansOtherTubes": 1 }]
			},
			{
				"stretchId": "Stretch:4",
				"alias": "TUBO_2_S2",	
				"order": 2,
				"fansToStartByRange": [ {"rangeId": 1, "fans": 1, "fansOtherTubes": 1 },
						  {"rangeId": 2, "fans": 2, "fansOtherTubes": 1 },
						  {"rangeId": 3, "fans": 3, "fansOtherTubes": 1 },
						  {"rangeId": 4, "fans": 3, "fansOtherTubes": 1 }]
			}]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.element_hierarchies
(parent_element_type_id, parent_element_id, child_element_type_id, child_element_id, child_inheritable_state_id, enabled, visible, created_at, updated_at) VALUES
(2001, 1, 2000, 1, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 1, 2000, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


