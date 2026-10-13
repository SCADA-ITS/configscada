INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01090043, '[
	{
		"id": 1,
		"alias": "AFL - S001 - Aguas arriba",
		"spathId": "Spath:100",
		"elementSubtypeId": "ElementSubtype:12:71",
		"lanes": [2],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":746}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 3,
		"alias": "CLV - S060 - Aguas arriba tronco",
		"spathId": "Spath:107",
		"elementSubtypeId": "ElementSubtype:11:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":317}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "CLV - S001 - Aguas arriba",
		"lanes": [2],
		"spathId": "Spath:100",
		"elementSubtypeId": "ElementSubtype:11:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"	
		}
	},
	{
		"id": 5,
		"alias": "CLV - S060 - Aguas abajo",
		"spathId": "Spath:110",
		"elementSubtypeId": "ElementSubtype:11:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":317}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 6,
		"alias": "PMVI - S031 - Aguas arriba tronco",
		"spathId": "Spath:107",
		"elementSubtypeId": "ElementSubtype:16:71",
		"lanes": [],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"VEHICULO EN\",\"alternance\":\"PROXIMA SALIDA\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 7,
		"alias": "PMVI - S032 - Aguas arriba - Desde la salida anterior hasta la boca del túnel",
		"spathId": "Spath:108",
		"elementSubtypeId": "ElementSubtype:16:71",
		"lanes": [],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"VEHICULO TUNEL\",\"alternance\":\"USE PROX SALIDA\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 8,
		"alias": "PMVE - S015 - Exterior ramales de entrada",
		"spathId": "Spath:109",
		"elementSubtypeId": "ElementSubtype:19:71",
		"lanes": [],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"142\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"RAMAL CORTADO\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"VEHICULO\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"EN PISTA\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 9,
		"alias": "SEM - S003 - Exterior ramales de entrada",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:3:13"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 01190043, '[
	{
		"id": 1,
		"alias": "Megafonía en la boca del túnel- Paso a vehículos de emergencia",
		"spathId": "Spath:111",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"8,9,12,15,30,33\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"10\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]	
		}
	},
	{
		"id": 2,
		"alias": "Megafonía en el interior del túnel- Corte carril izquierdo",
		"spathId": "Spath:111",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"11,13,14,16,17,27,28,29,31,32,34\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"4,9\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]		
		}
	},
	{
		"id": 3,
		"alias": "Megafonía en la boca del túnel- Paso a vehículos de emergencia",
		"spathId": "Spath:112",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"1,21,23,24,36\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"10\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "Megafonía en el interior del túnel- Corte carril izquierdo",
		"spathId": "Spath:112",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"2,3,4,5,6,7,10,19,20,22,25,26,35,37,38,40\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"4,9\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]		
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

