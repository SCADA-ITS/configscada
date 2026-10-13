INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01010489, '[
	{
		"id": 1,
		"alias": "CLV - S060",
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
		"alias": "PMVI - S026",
		"spathId": "Spath:11",
		"elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"PEATON EN TUNEL\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 3,
		"alias": "PMVE - S025",
		"spathId": "Spath:11",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"141\",\"alternance\":\"141\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"PRECAUCION\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"PEATON\",\"alternance\":\"PEATON\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"EN EL TUNEL\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "SEM - S002",
		"spathId": "Spath:11",
		"command": {
					"commandElementTypeId": "CommandElementType:2:13"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 01020489, '[
	{
		"id": 1,
		"alias": "Megafonía en el interior del túnel - Nivel-1",
		"spathId": "Spath:111",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"8,9,11,12,13,14,15,16,17,27,28,29,30,31,32,33,34\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"15,16\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]		
		}
	},
	{
		"id": 2,
		"alias": "Megafonía en el interior del túnel - Nivel-2",
		"spathId": "Spath:112",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"1,2,3,4,5,6,7,10,19,20,21,22,23,24,25,26,35,36,37,38,40\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"15,16\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);