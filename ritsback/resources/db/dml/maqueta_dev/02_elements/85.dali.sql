-- 
-- Table: conf.elements.DALI
--
INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(85, NULL, 1, 18, NULL, NULL, 'DALI_1', 'DALI_1', NULL, 'SendStateToIllumination.groovy', NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(85, NULL, 2, 18, NULL, NULL, 'DALI_2', 'DALI_2', NULL, 'SendStateToIllumination.groovy', NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(85, NULL, 3, 18, NULL, NULL, 'DALI_3', 'DALI_3', NULL, 'SendStateToIllumination.groovy', NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.DALI
--
INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(85, 1, 1, 1, NULL, '{"regimes": [{
			"id": 1,
			"alias": "REGIMEN_0",
			"configs": [{
				"id": 1,
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:1",
					"commandElementValues": [{
						"value": "Regime_0.json",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}]	
			}]
		},
		{
			"id": 2,
			"alias": "REGIMEN_1",
			"configs": [{
				"id": 2,
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:1",
					"commandElementValues": [{
						"value": "Regime_1.json",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}]	
			}]
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(85, 1003, 1, 1, NULL, 'DALI_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(85, 1, 1, 2, NULL, '{"regimes": [{
			"id": 1,
			"alias": "REGIMEN_0",
			"configs": [{
				"id": 1,
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:2",
					"commandElementValues": [{
						"value": "Regime_0.json",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}]	
			}]
		},
		{
			"id": 2,
			"alias": "REGIMEN_1",
			"configs": [{
				"id": 2,
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:2",
					"commandElementValues": [{
						"value": "Regime_1.json",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}]	
			}]
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(85, 1003, 1, 2, NULL, 'DALI_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(85, 1, 1, 3, NULL, '{"regimes": [{
			"id": 1,
			"alias": "REGIMEN_0",
			"configs": [{
				"id": 1,
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:3",
					"commandElementValues": [{
						"value": "Regime_0.json",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}]	
			}]
		},
		{
			"id": 2,
			"alias": "REGIMEN_1",
			"configs": [{
				"id": 2,
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:3",
					"commandElementValues": [{
						"value": "Regime_1.json",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}]	
			}]
		}
	]}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(85, 1003, 1, 3, NULL, 'DALI_3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);