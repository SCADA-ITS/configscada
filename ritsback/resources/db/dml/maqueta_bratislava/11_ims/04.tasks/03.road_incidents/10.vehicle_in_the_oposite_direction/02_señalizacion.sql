INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01010310, '[
	{
		"id": 1,
		"alias": "PANEL NR n",
		"from": 1,
		"to": 1,
		"elementSubtypeId": "ElementSubtype:1:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10746,\"alternance\":0}],\"texts\":[]},{\"zone\":6,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10746,\"alternance\":0}],\"texts\":[]},{\"zone\":3,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10152,\"alternance\":0}],\"texts\":[]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					},
					{
						"value":"r10-1",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:2"
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
						"value": "[{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10746,\"alternance\":0}],\"texts\":[]},{\"zone\":6,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10746,\"alternance\":0}],\"texts\":[]},{\"zone\":10,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10746,\"alternance\":0}],\"texts\":[]},{\"zone\":3,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10152,\"alternance\":0}],\"texts\":[]},{\"zone\":7,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10152,\"alternance\":0}],\"texts\":[]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					},
					{
						"value":"r10-1",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:2"
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
						"value": "[{\"zone\":3,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10152,\"alternance\":0}],\"texts\":[]},{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"graphics\":[{\"id\":1,\"value\":10746,\"alternance\":0}],\"texts\":[]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					},
					{
						"value":"r10-1",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:2"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
