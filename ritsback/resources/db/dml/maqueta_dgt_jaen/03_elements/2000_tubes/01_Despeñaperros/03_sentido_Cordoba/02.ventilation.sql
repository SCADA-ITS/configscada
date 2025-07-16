INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2000, 1, 1, 2,
'{	
	"windDirectionId": "ElementTypeParam:18:2:1",
	"measuresValidityPeriod_s": 60,
	"periodTime_s": 60,
	"ventilationRegimes": [
        {
            "id": 1,
            "alias": "REGIMEN_NORMAL",
            "description": "Régimen normal túnel Despeñaperros para el sentido Madrid",
            "labelAlias": "LBL_REGIME_0",
            "fans": 0
        },
        {
            "id": 2,
            "alias": "REGIMEN_ALTO",
            "description": "Régimen alto túnel Despeñaperros para el sentido Madrid",
            "labelAlias": "LBL_REGIME_1",
            "fans": 4
        },
        {
            "id": 3,
            "alias": "REGIMEN_ALTO_ALTO",
            "description": "Régimen alto-alto túnel Despeñaperros para el sentido Madrid",
            "labelAlias": "LBL_REGIME_2",
            "fans": 8
        },
        {
            "id": 4,
            "alias": "REGIMEN_MAXIMO",
            "description": "Régimen máximo túnel Despeñaperros para el sentido Madrid",
            "labelAlias": "LBL_REGIME_3",
            "fans": 16
        }
    ],
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
					"stateIds": [2]	
				},
				{
					"regimeId": 2,
					"stateIds": [3]	
				},
				{
					"regimeId": 3,
					"stateIds": [4]	
				},
				{
					"regimeId": 4,
					"stateIds": [5]	
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
					"stateIds": [2]	
				},
				{
					"regimeId": 2,
					"stateIds": [3]	
				},
				{
					"regimeId": 3,
					"stateIds": [4]	
				},
				{
					"regimeId": 4,
					"stateIds": [5]	
				}
			]
		}
    ]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);