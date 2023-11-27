INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01050805, '[
	{
		"id": 1,
		"alias": "AFL_2A - S001 - Aguas arriba - Carriles afectados",
		"spathId": "Spath:100",
		"elementSubtypeId": "ElementSubtype:12:71",
		"lanes": [2,3],
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
		"alias": "AFL_2A - S002 - Aguas arriba - Carriles no afectados",
		"spathId": "Spath:100",
		"elementSubtypeId": "ElementSubtype:12:71",
		"lanes": [1],
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
		"alias": "AFL_4A - S001 - Aguas arriba - Carriles afectados desde el incidente hasta 1km antes",
		"spathId": "Spath:101",
		"elementSubtypeId": "ElementSubtype:12:71",
		"lanes": [1002, 1003],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":746}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "AFL_4A - S004 - Aguas arriba - Carriles afectados a partir de 1km del incidente",
		"spathId": "Spath:103",
		"elementSubtypeId": "ElementSubtype:12:71",
		"lanes": [1002, 1003],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":744}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 5,
		"alias": "AFL_4A - S002 - Aguas arriba - Carriles no afectados",
		"spathId": "Spath:100",
		"elementSubtypeId": "ElementSubtype:12:71",
		"lanes": [1001],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":745}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 6,
		"alias": "CLV - S001 - Aguas arriba - Carriles afectados desde el incidente hasta 1km antes",
		"spathId": "Spath:101",
		"elementSubtypeId": "ElementSubtype:11:71",
		"lanes": [2,3],
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
		"id": 7,
		"alias": "CLV - S001 - Aguas arriba - Carriles afectados 1km antes del incidente",
		"spathId": "Spath:103",
		"elementSubtypeId": "ElementSubtype:11:71",
		"lanes": [2,3],
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"	
		}
	},
	{
		"id": 8,
		"alias": "CLV - S030 - Aguas arriba - Carriles no afectados",
		"spathId": "Spath:100",
		"elementSubtypeId": "ElementSubtype:11:71",
		"lanes": [1],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":314}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 9,
		"alias": "CLV - S060 - Aguas abajo 500m después del incidente",
		"spathId": "Spath:105",
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
		"id": 10,
		"alias": "PMVI - S015 - Aguas arriba hasta 1.5km antes del incidente",
		"spathId": "Spath:102",
		"elementSubtypeId": "ElementSubtype:16:71",
		"lanes": [],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"USE PISTA\",\"alternance\":\"IZQUIERDA\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 11,
		"alias": "PMVI - S016 - Aguas arriba 1.5km antes del incidente",
		"spathId": "Spath:104",
		"elementSubtypeId": "ElementSubtype:16:71",
		"lanes": [],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"OBSTACULO PISTA\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 12,
		"alias": "PMVE - S014 - Exterior",
		"spathId": "Spath:106",
		"elementSubtypeId": "ElementSubtype:19:71",
		"lanes": [],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"141\",\"alternance\":\"141\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"PRECAUCION\",\"alternance\":\"PRECAUCION PISTA\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"OBSTACULO\",\"alternance\":\"CERRADA RESPETE\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"EN PISTA\",\"alternance\":\"SENALIZACION\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
