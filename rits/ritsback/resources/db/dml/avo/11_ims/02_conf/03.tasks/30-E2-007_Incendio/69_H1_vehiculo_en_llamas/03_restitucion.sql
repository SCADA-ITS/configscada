INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 03020699, '[
	{
		"id": 1,
		"alias": "Megafonía en la boca del túnel- Paso a vehículos de emergencia - Nivel-1",
		"spathId": "Spath:111",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"8,9,12,15,30,33\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"10\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]	
		}
	},
	{
		"id": 2,
		"alias": "Megafonía en el interior del túnel - Incendio - Nivel-1",
		"spathId": "Spath:111",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"11,13,14,16,17,27,28,29,31,32,34\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"11,12\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]		
		}
	},
	{
		"id": 3,
		"alias": "Megafonía en la boca del túnel- Paso a vehículos de emergencia - Nivel-2",
		"spathId": "Spath:112",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"1,21,23,24,36\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"10\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "Megafonía en el interior del túnel- Incendio - Nivel-2",
		"spathId": "Spath:112",
		"elementSubtypeId": "ElementSubtype:2:5",
		"command": {
					"commandElementTypeId": "CommandElementType:2:5",
					"commandElementValues": [{
						"value": "\"2,3,4,5,6,7,10,19,20,22,25,26,35,37,38,40\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1"
					},
					{
						"value": "\"11,12\"",
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2"
					}]		
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 03030699, '[
	{
		"id": 1,
		"alias": "BARRERAS ENTRADA - S002",
		"spathId": "Spath:106",
		"command": {
					"commandElementTypeId": "CommandElementType:2:8"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
