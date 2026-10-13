INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 03050479, '[
	{
		"id": 1,
		"alias": "BAR - S001 - Subir Barrera",
		"spathId": "Spath:106",
		"command": {
					"commandElementTypeId": "CommandElementType:2:8"
		}
	},	
	{
		"id": 2,
		"alias": "BAR - S001 - Subir Barrera Ramal",
		"spathId": "Spath:109",
		"command": {
					"commandElementTypeId": "CommandElementType:2:8"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
