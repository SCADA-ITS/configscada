INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 010201, '[
    {
        "id": 1,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
        "id": 2,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 2,
        "elementSubtypeId": "ElementSubtype:12:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
							"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":745}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},	
	{
        "id": 3,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
        "id": 4,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:11:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
							"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":321}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 050201, '[
    {
        "id": 1,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
        "id": 2,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 2,
        "elementSubtypeId": "ElementSubtype:12:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
							"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":745}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
        "id": 3,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
        "id": 4,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:11:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
							"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":321}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ponemos el modo mantenimiento en la ventilacion.
(5, 1, 020131, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--ilumninación
(3, 1, 030131, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


