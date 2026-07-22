INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
--Iluminación El Corzo sentido Madrid
(2000, 2, 1, 5,
'{
	"regimes": [
		{
			"id": 2,
			"alias": "SOLEADO",
			"description": "Régimen soleado para el tubo de Sentido Madrid",
			"labelAlias": "LBL_SOLEADO",
			"documentUrl": "",
			"configs": [{
				"id": 2,
				"commands": [
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:29"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:30"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:31"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:32"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:33"
                    }
				]	
			}]
		},
		{
			"id": 3,
			"alias": "NUBLADO",
			"description": "Régimen nublado para el tubo de Sentido Madrid",
			"labelAlias": "LBL_NUBLADO",
			"documentUrl": "",
			"configs": [{
				"id": 3,
				"commands": [
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:30"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:29"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:31"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:32"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:33"
                    }
				]	
			}]
		},
		{
			"id": 4,
			"alias": "CREPUSCULAR",
			"description": "Régimen crepuscular para el tubo de Sentido Madrid",
			"labelAlias": "LBL_CREPUSCULAR",
			"documentUrl": "",
			"configs": [{
				"id": 4,
				"commands": [
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:32"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:29"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:30"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:31"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:33"
                    }
				]	
			}]
		},
		{
			"id": 5,
			"alias": "NOCTURNO",
			"description": "Régimen nocturno para el tubo de Sentido Madrid",
			"labelAlias": "LBL_NOCTURNO",
			"documentUrl": "",
			"configs": [{
				"id": 5,
				"commands": [
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:31"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:29"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:30"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:32"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:33"
                    }
				]	
			}]
		},
		{
			"id": 6,
			"alias": "EXTERIOR/NOCTURNO",
			"description": "Régimen exterior/nocturno para el tubo de Sentido Madrid",
			"labelAlias": "LBL_EXTERIOR_NOCTURNO",
			"documentUrl": "",
			"configs": [{
				"id": 6,
				"commands": [
                    {
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:31"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:29"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:30"
                    },
					{
                        "commandElementTypeId": "CommandElementType:1:84",
                        "elementId": "Element:84:32"
                    },
					{
                        "commandElementTypeId": "CommandElementType:2:84",
                        "elementId": "Element:84:33"
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
			"stateMachineId": 501,
			"changeCurrentRegime": false,
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
				},
				{
					"regimeId": 6,
					"stateIds": [6]	
				}
			]
		},
		{
			"id": 2,
			"alias": "ILUMINATION_SERVICE_AUTO",
			"description": "Config to calculate service regime",
			"mode": 1,
			"stateMachineId": 501,
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
				},
				{
					"regimeId": 6,
					"stateIds": [6]	
				}
			]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
