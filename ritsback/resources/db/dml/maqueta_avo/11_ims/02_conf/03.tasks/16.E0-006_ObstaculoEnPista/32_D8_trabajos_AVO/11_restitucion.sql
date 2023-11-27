INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 03020329, '[
	{
		"id": 1,
		"alias": "Megafonía en túnel- Congestión",
		"spathId": "Spath:111",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:1:5",
					"commandElementValues": [{
						"value": "[\"8\",\"9\",\"12\",\"15\",\"30\",\"33\"]",
						"commandElementTypeParamId": "CommandElementTypeParam:1:5:1"
					}]		
		}
	},
	{
		"id": 2,
		"alias": "Megafonía en túnel- Congestión",
		"spathId": "Spath:112",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:1:5",
					"commandElementValues": [{
						"value": "[\"1\",\"21\",\"23\",\"24\",\"36\"]",
						"commandElementTypeParamId": "CommandElementTypeParam:1:5:1"
					}]		
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 03030329, '[
	{
		"id": 1,
		"alias": "Megafonía en túnel- Congestión",
		"spathId": "Spath:111",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:1:5",
					"commandElementValues": [{
						"value": "[\"8\",\"9\",\"11\",\"12\",\"13\",\"14\",\"15\",\"16\",\"17\",\"27\",\"28\",\"29\",\"30\",\"31\",\"32\",\"33\",\"34\"]",
						"commandElementTypeParamId": "CommandElementTypeParam:1:5:1"
					}]		
		}
	},
	{
		"id": 2,
		"alias": "Megafonía en túnel- Congestión",
		"spathId": "Spath:112",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:1:5",
					"commandElementValues": [{
						"value": "[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"7\",\"10\",\"19\",\"20\",\"21\",\"22\",\"23\",\"24\",\"25\",\"26\",\"35\",\"36\",\"37\",\"38\",\"40\"]",
						"commandElementTypeParamId": "CommandElementTypeParam:1:5:1"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 03040329, '[
	{
		"id": 1,
		"alias": "BAR - S002 - Exterior ramales de entrada",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:2:8"
		}
	},
	{
		"id": 2,
		"alias": "BAR - S002 - Exterior",
		"spathId": "Spath:11",
		"command": {
					"commandElementTypeId": "CommandElementType:2:8"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 03050329, '[
	{
		"id": 1,
		"alias": "BAR - S002 - Exterior ramales de entrada",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:2:8"
		}
	},
	{
		"id": 2,
		"alias": "BAR - S002 - Exterior",
		"spathId": "Spath:11",
		"command": {
					"commandElementTypeId": "CommandElementType:2:8"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
