INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01010669, '[
	{
		"id": 1,
		"alias": "PMVI - S004",
		"spathId": "Spath:10",
		"elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"VIENTO\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 2,
		"alias": "PMVE - S002 - Todos los túneles",
		"spathId": "Spath:10",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"graphics\":[{\"id\":1,\"value\":\"38\"}]},{\"zone\":2,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"VIENTO\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"MODERE\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 3,
		"alias": "PMVE - S002 - Ramales",
		"spathId": "Spath:13",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"graphics\":[{\"id\":1,\"value\":\"38\"}]},{\"zone\":2,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"VIENTO\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"MODERE\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "PMVE - S002 - Bajada de la pirámide",
		"spathId": "Spath:12",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"graphics\":[{\"id\":1,\"value\":\"38\"}]},{\"zone\":2,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"VIENTO\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"MODERE\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);