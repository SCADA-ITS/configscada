INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01010479, '[
	{
		"id": 1,
		"alias": "CLV - S030 - Aguas arriba de la incidencia",
		"spathId": "Spath:100",
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
		"id": 2,
		"alias": "CLV - S060 - Aguas abajo de la incidencia",
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
		"id": 3,
		"alias": "PMVI - S025 - Aguas arriba de la incidencia",
		"spathId": "Spath:100",
		"elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"ANIMAL EN TUNEL\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "PMVE - S023 - Exterior",
		"spathId": "Spath:106",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"141\",\"alternance\":\"141\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"PRECAUCION\",\"alternance\":\"ANIMAL EN TUNEL\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"ANIMAL EN TUNEL\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\" \",\"alternance\":\" \",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 5,
		"alias": "PMVE - S023 - Exterior Ramal",
		"spathId": "Spath:109",
		"elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"141\",\"alternance\":\"141\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"PRECAUCION\",\"alternance\":\"ANIMAL EN TUNEL\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"ANIMAL EN TUNEL\",\"alternance\":\"MODERE VELOCIDAD\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\" \",\"alternance\":\" \",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 6,
		"alias": "SEM - S002 - Exterior",
		"spathId": "Spath:106",
		"command": {
					"commandElementTypeId": "CommandElementType:2:13"
		}
	},
	{
		"id": 7,
		"alias": "SEM - S002 - Exterior Ramal",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:2:13"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 01020479, '[
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