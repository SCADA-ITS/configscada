INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 02010303, '[
	{
		"id": 1,
		"alias": "SWITCH OFF PANEL NR n",
		"from": 1,
		"to": 1,
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},	
	{
		"id": 2,
		"alias": "SWITCH OFF PANEL NR n-1",
		"from": 2,
		"to": 2,
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
		"id": 3,
		"alias": "SWITCH OFF PANEL NR n-2",
		"from": 3,
		"to": 3,
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
		"id": 4,
		"alias": "Del Prioritys",
		"from": 1,
		"to": 3,
		"command": {
			"commandElementTypeId": "CommandElementType:2:71",
			"commandElementValues": [{
						"value": null,
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					},
					{
						"value":"r3-0",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:2"
					}]		
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 03010303, '[
	{
		"id": 1,
		"alias": "PANEL NR n",
		"from": 1,
		"to": 1,
		"elementSubtypeId": "ElementSubtype:1:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":3,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10501,\"alternance\":0}],\"texts\":[]},{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10745,\"alternance\":0}],\"texts\":[]},{\"zone\":6,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10745,\"alternance\":0}],\"texts\":[]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 2,
		"alias": "PANEL NR n",
		"from": 1,
		"to": 1,
		"elementSubtypeId": "ElementSubtype:2:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":7,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10501,\"alternance\":0}],\"texts\":[]},{\"zone\":3,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10501,\"alternance\":0}],\"texts\":[]},{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10745,\"alternance\":0}],\"texts\":[]},{\"zone\":6,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10745,\"alternance\":0}],\"texts\":[]},{\"zone\":10,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10745,\"alternance\":0}],\"texts\":[]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 3,
		"alias": "PANEL NR n",
		"from": 1,
		"to": 1,
		"elementSubtypeId": "ElementSubtype:3:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":3,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10501,\"alternance\":0}],\"texts\":[]},{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10745,\"alternance\":0}],\"texts\":[]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "Del Prioritys",
		"from": 1,
		"to": 1,
		"command": {
			"commandElementTypeId": "CommandElementType:2:71",
			"commandElementValues": [{
						"value": null,
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					},
					{
						"value":"r3-0",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:2"
					}]		
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

