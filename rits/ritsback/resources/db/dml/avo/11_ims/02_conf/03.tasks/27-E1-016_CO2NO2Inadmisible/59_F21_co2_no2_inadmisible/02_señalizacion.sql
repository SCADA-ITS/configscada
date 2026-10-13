INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01010599, '[
	{
		"id": 1,
		"alias": "AFL - S001",
		"spathId": "Spath:11",
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
		"id": 3,
		"alias": "PMVI - S040",
		"spathId": "Spath:11",
		"elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"ALTA CONCENTRACION\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "PMVE - S041",
		"spathId": "Spath:11",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"142\",\"alternance\":\"136\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"TUNEL CORTADO\",\"alternance\":\"POSIBLES\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"POR ALTA\",\"alternance\":\"RETENCIONES\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"CONCENTRACION\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 5,
		"alias": "SEM - S003",
		"spathId": "Spath:11",
		"command": {
					"commandElementTypeId": "CommandElementType:3:13"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

