INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01011119, '[
	{
		"id": 1,
		"alias": "PMVI - S007",
		"spathId": "Spath:11",
		"elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 2,
		"alias": "PMVE - S026",
		"spathId": "Spath:11",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"92\",\"alternance\":\"141\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\" \",\"alternance\":\" \",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"MODERE\",\"alternance\":\"MODERE\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"VELOCIDAD\",\"alternance\":\"VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 3,
		"alias": "SEM - S002",
		"spathId": "Spath:11",
		"command": {
					"commandElementTypeId": "CommandElementType:2:13"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);