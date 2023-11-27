INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 03020020, '[
	{
		"id": 1,
		"alias": "BAR - S001 - Exterior ramales de entrada",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:2:8"
		}
	},
	{
		"id": 2,
		"alias": "BAR - S001",
		"spathId": "Spath:106",
		"command": {
					"commandElementTypeId": "CommandElementType:2:8"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 03030020, '[
	{
		"id": 1,
		"alias": "Megafonía en túnel- Congestión",
		"spathId": "Spath:111",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:1:5",
					"commandElementValues": [{
						"value": "[\"1\",\"21\",\"23\",\"24\",\"36\"]",
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
						"value": "[\"2\",\"3\",\"4\",\"5\",\"6\",\"7\",\"10\",\"19\",\"20\",\"22\",\"25\",\"26\",\"35\",\"37\",\"38\",\"40\"]",
						"commandElementTypeParamId": "CommandElementTypeParam:1:5:1"
					}]		
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
