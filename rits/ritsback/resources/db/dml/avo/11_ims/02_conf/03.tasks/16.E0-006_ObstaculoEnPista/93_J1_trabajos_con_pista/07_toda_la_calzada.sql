INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01060936, '[
	{
		"id": 1,
		"alias": "AFL - S001 - Aguas arriba - Desde el incidente hasta salida anterior",
		"spathId": "Spath:107",
		"elementSubtypeId": "ElementSubtype:12:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":746}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 2,
		"alias": "AFL - S002 - Aguas arriba - Desde la salida anterior hasta la boca del túnel",
		"spathId": "Spath:108",
		"elementSubtypeId": "ElementSubtype:12:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":745}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 3,
		"alias": "CLV - S001 - Aguas arriba - Desde el incidente hasta salida anterior",
		"spathId": "Spath:107",
		"elementSubtypeId": "ElementSubtype:11:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":314}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "CLV - S030 - Aguas arriba - Desde la salida anterior hasta la boca del túnel",
		"spathId": "Spath:108",
		"elementSubtypeId": "ElementSubtype:11:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
		"id": 5,
		"alias": "PMVI - S031 - Aguas arriba - Desde el incidente hasta salida anterior",
		"spathId": "Spath:107",
		"elementSubtypeId": "ElementSubtype:16:71",
		"lanes": [],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"OBSTACULO TUNEL\",\"alternance\":\"TRAFICO CORTADO\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 6,
		"alias": "PMVI - S032 - Aguas arriba - Desde la salida anterior hasta la boca del túnel",
		"spathId": "Spath:108",
		"elementSubtypeId": "ElementSubtype:16:71",
		"lanes": [],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"OBSTACULO TUNEL\",\"alternance\":\"USE PROX SALIDA\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 7,
		"alias": "PMVE - S014 - Exterior",
		"spathId": "Spath:106",
		"elementSubtypeId": "ElementSubtype:19:71",
		"lanes": [],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"142\",\"alternance\":\"136\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"TUNEL\",\"alternance\":\"POSIBLES\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"CORTADO POR\",\"alternance\":\"RETENCIONES\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"OBSTACULO\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
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
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"142\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"RAMAL CORTADO\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"OBSTACULO\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"EN PISTA\",\"color\":\"#FFFFFF\"}]}]",
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
	},
	{
		"id": 10,
		"alias": "SEM - S003 - Exterior",
		"spathId": "Spath:106",
		"command": {
					"commandElementTypeId": "CommandElementType:3:13"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 01260939, '[
	{
		"id": 1,
		"alias": "BAR - S002 - Exterior ramales de entrada",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:1:8"
		}
	},
	{
		"id": 2,
		"alias": "BAR - S002 - Exterior",
		"spathId": "Spath:106",
		"command": {
					"commandElementTypeId": "CommandElementType:1:8"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 01160939, '[
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
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
