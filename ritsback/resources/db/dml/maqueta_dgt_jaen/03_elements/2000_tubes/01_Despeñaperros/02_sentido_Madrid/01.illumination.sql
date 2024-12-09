INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
--Iluminación Despeñaperros sentido Madrid
(2000, 2, 1, 1,
'{
	"regimes": [{
			"id": 1,
			"alias": "EMERGENCIA",
			"description": "Regimen de emergencia para el tubo de Sentido Madrid",
			"labelAlias": "LBL_EMERGENCIA",
			"documentUrl": "",
			"configs": [{
				"id": 1,
				"commands": [
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:6"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:7"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:8"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:9"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:10"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:11"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:12"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:13"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:14"
                    }
				]	
			}]
		},
		{
			"id": 2,
			"alias": "SOLEADO",
			"description": "Regimen soleado para el tubo de Sentido Madrid",
			"labelAlias": "LBL_SOLEADO",
			"documentUrl": "",
			"configs": [{
				"id": 2,
				"commands": [
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:10"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:6"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:7"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:8"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:9"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:11"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:12"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:13"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:14"
                    }
				]	
			}]
		},
		{
			"id": 3,
			"alias": "CREPUSCULAR",
			"description": "Regimen crepuscular para el tubo de Sentido Madrid",
			"labelAlias": "LBL_CREPUSCULAR",
			"documentUrl": "",
			"configs": [{
				"id": 3,
				"commands": [
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:7"
                    },
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:13"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:6"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:8"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:9"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:10"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:11"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:12"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:14"
                    }
				]	
			}]
		},
		{
			"id": 4,
			"alias": "NUBLADO",
			"description": "Regimen nublado para el tubo de Sentido Madrid",
			"labelAlias": "LBL_NUBLADO",
			"documentUrl": "",
			"configs": [{
				"id": 4,
				"commands": [
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:11"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:6"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:7"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:8"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:9"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:10"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:12"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:13"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:14"
                    }
				]	
			}]
		},
		{
			"id": 5,
			"alias": "NOCTURNO",
			"description": "Regimen nocturno para el tubo de Sentido Madrid",
			"labelAlias": "LBL_NOCTURNO",
			"documentUrl": "",
			"configs": [{
				"id": 5,
				"commands": [
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:6"
                    },
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:12"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:7"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:8"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:9"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:10"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:11"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:13"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:14"
                    }
				]	
			}]
		}
	],
	"smConfigs": [
		{
			"id": 1,
			"alias": "ILUMINATION_SERVICE_MANUAL",
			"description": "Config to calculate service regime",
			"mode": 0,
			"stateMachineId": 101,
			"changeCurrentRegime": false,
			"smConfigsRegimes": [
				{
					"regimeId": 1,
					"stateIds": [2,3,4,5]	
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
				},
				{
					"regimeId": 5,
					"stateIds": [5]	
				}
			]
		},
		{
			"id": 2,
			"alias": "ILUMINATION_SERVICE_AUTO",
			"description": "Config to calculate service regime",
			"mode": 1,
			"stateMachineId": 101,
			"changeCurrentRegime": true,
			"smConfigsRegimes": [
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
				},
				{
					"regimeId": 5,
					"stateIds": [5]	
				}
			]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
