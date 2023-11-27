INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 01021129, '[
	{
		"id": 1,
		"alias": "BAR - S002 - Bajar Barrera",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:1:8"
		}
	},
	{
		"id": 2,
		"alias": "BAR - S002 - Bajar Barrera",
		"spathId": "Spath:106",
		"command": {
					"commandElementTypeId": "CommandElementType:1:8"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);