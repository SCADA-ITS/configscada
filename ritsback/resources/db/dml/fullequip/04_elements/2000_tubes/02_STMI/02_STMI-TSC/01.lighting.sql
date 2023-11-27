-- 
-- Table: conf.element_values.tube
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES

--Iluminación STMI Sentido Cádiz
(2000, 2, 1, 5,
'{
	"regimes": [{
			"id": 1,
			"alias": "SOLEADO_10",
			"description": "Regimen soleado al 10% para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_SOLEADO_10",
			"documentUrl": "",
			"configs": [{
				"id": 1,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 10}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 10}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:118"
					}
				]	
			}]
		},
		{
			"id": 2,
			"alias": "SOLEADO_20",
			"description": "Regimen soleado al 20% para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_SOLEADO_20",
			"documentUrl": "",
			"configs": [{
				"id": 2,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 20}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 20}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:118"
					}
				]		
			}]
		},
		{
			"id": 3,
			"alias": "SOLEADO_30",
			"description": "Regimen soleado al 30% para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_SOLEADO_30",
			"documentUrl": "",
			"configs": [{
				"id": 3,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value":  "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 30}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 30}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:118"
					}
				]		
			}]
		},
		{
			"id": 4,
			"alias": "SOLEADO_40",
			"description": "Regimen soleado al 40% para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_SOLEADO_40",
			"documentUrl": "",
			"configs": [{
				"id": 4,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 40}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 40}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:118"
					}
				]		
			}]
		},
		{
			"id": 5,
			"alias": "SOLEADO_50",
			"description": "Regimen soleado al 50% para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_SOLEADO_50",
			"documentUrl": "",
			"configs": [{
				"id": 5,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 50}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 50}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:118"
					}
				]		
			}]
		},
		{
			"id": 6,
			"alias": "SOLEADO_65",
			"description": "Regimen soleado al 65% para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_SOLEADO_65",
			"documentUrl": "",
			"configs": [{
				"id": 6,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 65}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 65}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:118"
					}
				]		
			}]
		},
		{
			"id": 7,
			"alias": "SOLEADO_85",
			"description": "Regimen soleado al 85% para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_SOLEADO_85",
			"documentUrl": "",
			"configs": [{
				"id": 7,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 85}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 85}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:118"
					}
				]		
			}]
		},
		{
			"id": 8,
			"alias": "SOLEADO_100",
			"description": "Regimen soleado al 100% para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_SOLEADO_100",
			"documentUrl": "",
			"configs": [{
				"id": 8,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 100}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 100}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:118"
					}
				]		
			}]
		},
		{
			"id": 9,
			"alias": "LBL_NOCTURNO",
			"description": "Regimen nocturno para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_NOCTURNO_100",
			"documentUrl": "",
			"configs": [{
				"id": 9,
				"commands": [{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:118"
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 100}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 100 },{\"id\": 1 , \"value\" : 100}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					}
				]		
			}]
		},
		{
			"id": 10,
			"alias": "LBL_NOCTURNO",
			"description": "Regimen nocturno para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_NOCTURNO_60",
			"documentUrl": "",
			"configs": [{
				"id": 10,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:118"
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 60 },{\"id\": 1 , \"value\" : 100}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 60 },{\"id\": 1 , \"value\" : 100}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					}
				]		
			}]
		},
		{
			"id": 11,
			"alias": "LBL_NOCTURNO",
			"description": "Regimen nocturno para el tubo de Sentido Cádiz",
			"labelAlias": "LBL_NOCTURNO_30",
			"documentUrl": "",
			"configs": [{
				"id": 11,
				"commands": [
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:121"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:122"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:109"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:110"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:113"
					},
					{
						"commandElementTypeId": "CommandElementType:1:84",
						"elementId": "Element:84:116"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:117"
					},
					{
						"commandElementTypeId": "CommandElementType:2:84",
						"elementId": "Element:84:118"
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:3",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 30 },{\"id\": 1 , \"value\" : 100}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
					},
					{
						"commandElementTypeId": "CommandElementType:2:85",
						"elementId": "Element:85:4",
						"commandElementValues": [{
							"value": "{\"groups\":[ {\"id\": 0 , \"value\" : 30 },{\"id\": 1 , \"value\" : 100}]}",
							"commandElementTypeParamId": "CommandElementTypeParam:1:85:1"
						}]	
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
			"stateMachineId": 301,
			"changeCurrentRegime": false,
			"smConfigsRegimes": [
				{
					"regimeId": 1,
					"stateIds": [9]	
				},
				{
					"regimeId": 2,
					"stateIds": [8]	
				},
				{
					"regimeId": 3,
					"stateIds": [7]	
				},
				{
					"regimeId": 4,
					"stateIds": [6]	
				},
				{
					"regimeId": 5,
					"stateIds": [5]	
				},
				{
					"regimeId": 6,
					"stateIds": [4]	
				},
				{
					"regimeId": 7,
					"stateIds": [3]	
				},
				{
					"regimeId": 8,
					"stateIds": [2]	
				},
				{
					"regimeId": 9,
					"stateIds": [99]	
				},
				{
					"regimeId": 10,
					"stateIds": [99]	
				},
				{
					"regimeId": 11,
					"stateIds": [99]	
				}
			]
		},
		{
			"id": 2,
			"alias": "ILUMINATION_SERVICE_AUTO",
			"description": "Config to calculate service regime",
			"mode": 1,
			"stateMachineId": 301,
			"changeCurrentRegime": true,
			"smConfigsRegimes": [
				{
					"regimeId": 1,
					"stateIds": [9]	
				},
				{
					"regimeId": 2,
					"stateIds": [8]	
				},
				{
					"regimeId": 3,
					"stateIds": [7]	
				},
				{
					"regimeId": 4,
					"stateIds": [6]	
				},
				{
					"regimeId": 5,
					"stateIds": [5]	
				},
				{
					"regimeId": 6,
					"stateIds": [4]	
				},
				{
					"regimeId": 7,
					"stateIds": [3]	
				},
				{
					"regimeId": 8,
					"stateIds": [2]	
				},
				{
					"regimeId": 10,
					"stateIds": [99]	
				}
			]
		}
	]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);