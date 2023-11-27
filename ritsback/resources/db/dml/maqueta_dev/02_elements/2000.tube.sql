-- 
-- Table: conf.elements.tube
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2000, NULL, 1, 1, NULL,  NULL, 'TUBE_1', 'TUBE_1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 2, 1, NULL,  NULL, 'TUBE_2', 'TUBE_2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.METEO
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2000, 1, 1, 1,
'{	
	"windDirectionId": "ElementTypeParam:3:2:4",
	"measuresValidityPeriod_s": 60,
	"periodTime_s": 60,
	"ventilationRegimes": [{
				"id": 1,
				"alias": "REGIMEN_PARADO",
				"description": "Regimen 0 tunel Rio Verde",
				"labelAlias": "LBL_REGIME_0",
				"fans": 0
			},
			{
				"id": 2,
				"alias": "REGIMEN_BAJO",
				"description": "Regimen bajo tunel Rio Verde",
				"labelAlias": "LBL_REGIME_1",
				"fans": 3
			},
			{
				"id": 3,
				"alias": "REGIMEN_MEDIO",
				"description": "Regimen medio tunel Rio Verde",
				"labelAlias": "LBL_REGIME_2",
				"fans": 4
			},
			{
				"id": 4,
				"alias": "REGIMEN_ALTO",
				"description": "Regimen alto tunel Rio Verde",
				"labelAlias": "LBL_REGIME_3",
				"fans": 6
			},
			{
				"id": 5,
				"alias": "EMERGENCIA",
				"description": "Emergencia tunel Rio Verde",
				"labelAlias": "LBL_REGIME_EMERGENCIA",
				"incidentTypeId": "ImsIncidentType:1",
				"locationId": "Location:1"
			}],
	"smConfigs": [
		{
			"id": 1,
			"alias": "MANUAL_VENTILATION_SERVICE",
			"description": "Config to calculate service regime",
			"mode": 0,
			"stateMachineId": 1,
			"changeCurrentRegime": false,
			"smConfigsRegimes": [
				{
					"regimeId": 1,
					"stateIds": [1]	
				},
				{
					"regimeId": 2,
					"stateIds": [2]	
				},
				{
					"regimeId": 3,
					"stateIds": [3, 4]	
				},
				{
					"regimeId": 4,
					"stateIds": [5, 6]	
				},
				{
					"regimeId": 5,
					"stateIds": [7]	
				}
			]
		},
		{
			"id": 2,
			"alias": "VENTILATION_SERVICE",
			"description": "Config to calculate service regime",
			"mode": 1,
			"stateMachineId": 1,
			"changeCurrentRegime": true,
			"smConfigsRegimes": [
				{
					"regimeId": 1,
					"stateIds": [1]	
				},
				{
					"regimeId": 2,
					"stateIds": [2]	
				},
				{
					"regimeId": 3,
					"stateIds": [3, 4]	
				},
				{
					"regimeId": 4,
					"stateIds": [5, 6]	
				},
				{
					"regimeId": 5,
					"stateIds": [7]	
				}
			]
		},
		{
			"id": 3,
			"alias": "VENTILATION_MAINTENANCE",
			"description": "Config to calculate maintenance regime",
			"mode": 2,
			"stateMachineId": 1
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 2, 1, 1,
'{
	"regimes": [{
			"id": 1,
			"alias": "REGIMEN_0",
			"description": "Regimen 0",
			"labelAlias": "LBL_REGIME_0",
			"documentUrl": "./swagger-ui.html",
			"configs": [{
				"id": 1,
				"alias": "REGIMEN_0",
				"description": "Regimen 0",
				"labelAlias": "LBL_REGIME_0",
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:1:84",
					"elementId": "Element:84:1"
				},
				{
					"commandElementTypeId": "CommandElementType:2:84",
					"elementId": "Element:84:2"
				},
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:1",
					"commandElementValues": [{
						"value": "Json info Regime 0",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}	
				]	
			}]
		},
		{
			"id": 2,
			"alias": "REGIMEN_1",
			"description": "Regimen 1",
			"labelAlias": "LBL_REGIME_1",
			"documentUrl": "./swagger-ui.html",
			"configs": [{
				"id": 2,
				"alias": "REGIMEN_1",
				"description": "Regimen 1",
				"labelAlias": "LBL_REGIME_1",
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:1:84",
					"elementId": "Element:84:2"
				},
				{
					"commandElementTypeId": "CommandElementType:2:84",
					"elementId": "Element:84:1"
				},
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:1",
					"commandElementValues": [{
						"value": "Json info Regime 1",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}	
				]	
			}]
		}
	],
	"smConfigs": [

		{
			"id": 1,
			"alias": "ILUMINATION_SERVICE",
			"description": "Config to calculate service regime",
			"mode": 0,
			"changeCurrentRegime": false,
			"stateMachineId": 1,
			"smConfigsRegimes": [
				{
					"regimeId": 1,
					"stateIds": [1]	
				},
				{
					"regimeId": 2,
					"stateIds": [2]	
				}
			]
		},
		{
			"id": 1,
			"alias": "ILUMINATION_SERVICE",
			"description": "Config to calculate service regime",
			"mode": 1,
			"changeCurrentRegime": true,
			"stateMachineId": 1,
			"smConfigsRegimes": [
				{
					"regimeId": 1,
					"stateIds": [1]	
				},
				{
					"regimeId": 2,
					"stateIds": [2]	
				}
			]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1, 1, 2,
'{
	"windDirectionId": "ElementTypeParam:3:2:4",
	"measuresValidityPeriod_s": 60,
	"periodTime_s": 60,
	"ventilationRegimes": [{
				"id": 1,
				"alias": "REGIMEN_PARADO",
				"description": "Regimen 0 tunel Rio Verde",
				"labelAlias": "LBL_REGIME_0",
				"fans": 0
			},
			{
				"id": 2,
				"alias": "REGIMEN_BAJO",
				"description": "Regimen bajo tunel Rio Verde",
				"labelAlias": "LBL_REGIME_1",
				"fans": 2
			},
			{
				"id": 3,
				"alias": "REGIMEN_MEDIO",
				"description": "Regimen medio tunel Rio Verde",
				"labelAlias": "LBL_REGIME_2",
				"fans": 3
			},
			{
				"id": 4,
				"alias": "REGIMEN_ALTO",
				"description": "Regimen alto tunel Rio Verde",
				"labelAlias": "LBL_REGIME_3",
				"fans": 4
			},
			{
				"id": 5,
				"alias": "EMERGENCIA",
				"description": "Emergencia tunel Rio Verde",
				"labelAlias": "LBL_REGIME_EMERGENCIA",
				"incidentTypeId": "ImsIncidentType:1",
				"locationId": "Location:1"
			}]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 2,
'{
	"measuresValidityPeriod_s": 60,
	"periodTime_s": 60,
	"regimes": [{
			"id": 1,
			"alias": "REGIMEN_0",
			"description": "Regimen 0",
			"labelAlias": "LBL_REGIME_0",
			"documentUrl": "./swagger-ui.html",
			"configs": [{
				"id": 1,
				"alias": "REGIMEN_0",
				"description": "Regimen 0",
				"labelAlias": "LBL_REGIME_0",
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:1:84",
					"elementId": "Element:84:3"
				},
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:3",
					"commandElementValues": [{
						"value": "Json info Regime 0",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}	
				]	
			}]
		},
		{
			"id": 2,
			"alias": "REGIMEN_1",
			"description": "Regimen 1",
			"labelAlias": "LBL_REGIME_1",
			"documentUrl": "./swagger-ui.html",
			"configs": [{
				"id": 2,
				"alias": "REGIMEN_1",
				"description": "Regimen 1",
				"labelAlias": "LBL_REGIME_1",
				"commands": [
				{
					"commandElementTypeId": "CommandElementType:2:84",
					"elementId": "Element:84:3"
				},
				{
					"commandElementTypeId": "CommandElementType:2:85",
					"elementId": "Element:85:3",
					"commandElementValues": [{
						"value": "Json info Regime 1",
						"commandElementTypeParamId": "CommandElementTypeParam:2:85:1"
					}]	
				}	
				]	
			}]
		}
	],
	"smConfigs": [
		{
			"id": 1,
			"alias": "ILUMINATION_SERVICE",
			"description": "Config to calculate service regime",
			"mode": 1,
			"stateMachineId": 1,
			"smConfigsRegimes": [
				{
					"regimeId": 1,
					"stateIds": [1]	
				},
				{
					"regimeId": 2,
					"stateIds": [2]	
				}
			]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

