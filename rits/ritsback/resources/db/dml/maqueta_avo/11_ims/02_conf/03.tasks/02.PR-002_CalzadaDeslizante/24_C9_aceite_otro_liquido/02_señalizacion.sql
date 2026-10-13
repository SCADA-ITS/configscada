INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01010249, '[
	{
		"id": 1,
		"alias": "CLV - S060 - Todos los túneles",
		"spathId": "Spath:11",
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
		"id": 2,
		"alias": "CLV - S060 - Ramales",
		"spathId": "Spath:14",
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
		"id": 3,
		"alias": "PMVI - S002",
		"spathId": "Spath:11",
		"elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"PISTA DESLIZANTE\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "PMVE - S002 - Todos los túneles",
		"spathId": "Spath:11",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"96\",\"alternance\":\"47\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"PISTA DESLIZANTE\",\"alternance\":\"PISTA DESLIZANTE\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"MODERE\",\"alternance\":\"MODERE\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"VELOCIDAD\",\"alternance\":\"VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 5,
		"alias": "PMVE - S002 - Ramales",
		"spathId": "Spath:14",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"96\",\"alternance\":\"47\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"PISTA DESLIZANTE\",\"alternance\":\"PISTA DESLIZANTE\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"MODERE\",\"alternance\":\"MODERE\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"VELOCIDAD\",\"alternance\":\"VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 6,
		"alias": "PMVE - S002 - Bajada de la pirámide",
		"spathId": "Spath:13",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"96\",\"alternance\":\"47\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"PISTA DESLIZANTE\",\"alternance\":\"PISTA DESLIZANTE\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"MODERE\",\"alternance\":\"MODERE\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"VELOCIDAD\",\"alternance\":\"VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 7,
		"alias": "SEM - S002 - Túneles",
		"spathId": "Spath:11",
		"command": {
					"commandElementTypeId": "CommandElementType:5:13"
		}
	},
	{
		"id": 8,
		"alias": "SEM - S002 - Ramales",
		"spathId": "Spath:14",
		"command": {
					"commandElementTypeId": "CommandElementType:5:13"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);