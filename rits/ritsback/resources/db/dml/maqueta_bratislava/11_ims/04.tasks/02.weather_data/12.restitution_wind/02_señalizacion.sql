INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01010212, '[
	{
		"id": 1,
		"alias": "SWITCH OFF PANEL NR n",
		"from": 1,
		"to": 2,
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},	
	{
		"id": 2,
		"alias": "SWITCH OFF PANEL NR n-1",
		"from": 3,
		"to": 4,
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
		"id": 3,
		"alias": "SWITCH OFF PANEL NR n-2",
		"from": 5,
		"to": 6,
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
		"id": 4,
		"alias": "Del Prioritys",
		"from": 1,
		"to": 6,
		"command": {
			"commandElementTypeId": "CommandElementType:2:71",
			"commandElementValues": [{
						"value": null,
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					},
					{
						"value":"w12-0",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:2"
					}]		
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

