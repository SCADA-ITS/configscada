INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01010409, '[
	{
		"id": 1,
		"alias": "AFL - S001 - Aguas arriba",
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
		"alias": "CLV - S001 - Aguas arriba",
		"spathId": "Spath:107",
		"elementSubtypeId": "ElementSubtype:11:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"	
		}
	},
	{
		"id": 3,
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
		"id": 4,
		"alias": "PMVI - S039 - Aguas arriba",
		"spathId": "Spath:107",
		"elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"DISTURBIOS TUNEL\",\"alternance\":\"USE PROXIMA. SALIDA\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 5,
		"alias": "PMVE - S039 - Exterior",
		"spathId": "Spath:106",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"142\",\"alternance\":\"136\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"TUNEL\",\"alternance\":\"POSIBLES\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"CORTADO POR\",\"alternance\":\"RETENCIONES\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"DISTURBIOS\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 6,
		"alias": "PMVE - S039 - Exterior Ramal",
		"spathId": "Spath:109",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"142\",\"alternance\":\"136\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"TUNEL\",\"alternance\":\"POSIBLES\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"CORTADO POR\",\"alternance\":\"RETENCIONES\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"DISTURBIOS\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 7,
		"alias": "SEM - S003 - Exterior",
		"spathId": "Spath:106",
		"command": {
					"commandElementTypeId": "CommandElementType:3:13"
		}
	},
	{
		"id": 8,
		"alias": "SEM - S003 - Exterior Ramal",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:3:13"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 01020409, '[
	{
		"id": 1,
		"alias": "BAR - S001 - Bajar Barrera",
		"spathId": "Spath:106",
		"command": {
					"commandElementTypeId": "CommandElementType:1:8"
		}
	},	
	{
		"id": 2,
		"alias": "BAR - S001 - Bajar Barrera Ramal",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:1:8"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);