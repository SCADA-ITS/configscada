
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2000, 1, 1, 7,
'{	
	"windDirectionId": "ElementTypeParam:18:2:1",
	"measuresValidityPeriod_s": 60,
	"periodTime_s": 60,
	"ventilationRegimes": [{
				"id": 1,
				"alias": "REGIMEN_PARADO",
				"description": "Regimen 0 tunel Rio Verde para el sentido Norte",
				"labelAlias": "LBL_REGIME_0",
				"fans": 0
			},
			{
				"id": 2,
				"alias": "REGIMEN_BAJO",
				"description": "Regimen bajo tunel Rio Verde  para el sentido Norte",
				"labelAlias": "LBL_REGIME_1",
				"fans": 2
			},
			{
				"id": 3,
				"alias": "REGIMEN_MEDIO",
				"description": "Regimen medio tunel Rio Verde para el sentido Norte",
				"labelAlias": "LBL_REGIME_2",
				"fans": 3
			},
			{
				"id": 4,
				"alias": "REGIMEN_ALTO",
				"description": "Regimen alto tunel Rio Verde  para el sentido Norte",
				"labelAlias": "LBL_REGIME_3",
				"fans": 4
			},
			{
				"id": 5,
				"alias": "EMERGENCIA E1-AMB-01",
				"description": "Emergencia E1-AMB-01 tunel Rio Verde  para el sentido Norte",
				"labelAlias": "LBL_REGIME_EMERGENCIA_E1_AMB_01",
				"fans": 4,
				"incidentTypeId": "ImsIncidentType:22",
				"locationId": "Location:1002"
			},
			{
				"id": 6,
				"alias": "EMERGENCIA E1-AMB-02",
				"description": "Emergencia E1-AMB-02 tunel Rio Verde  para el sentido Norte",
				"labelAlias": "LBL_REGIME_EMERGENCIA_E1_AMB_02",
				"fans": 4,
				"incidentTypeId": "ImsIncidentType:23",
				"locationId": "Location:1002"
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
				},
				{
					"regimeId": 6,
					"stateIds": [8]	
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
				},
				{
					"regimeId": 6,
					"stateIds": [8]	
				}
			]
		},
		{
			"id": 3,
			"alias": "VENTILATION_MAINTENANCE",
			"description": "Config to calculate maintenance regime",
			"mode": 2,
			"stateMachineId": 1000,
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
					"stateIds": [3]	
				},
				{
					"regimeId": 4,
					"stateIds": [4]
				}
			]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

