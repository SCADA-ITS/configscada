INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01010299, '[
	{
		"id": 1,
		"alias": "CLV - S060",
		"from": -1000,
		"to": 1000,
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
		"alias": "PMVI - S007",
		"from": -1000,
		"to": 1000,
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
		"id": 3,
		"alias": "PMVE - S049",
		"from": -1000,
		"to": 1000,
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"graphics\":[{\"id\":1,\"value\":\"88\"}]},{\"zone\":2,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"MODERE\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"SU\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "SEM - S002",
		"from": -1000,
		"to": 1000,
		"command": {
					"commandElementTypeId": "CommandElementType:2:13"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);