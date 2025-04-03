INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 03010101, '[
    {
        "id": 1,
        "alias": "Mensaje",
        "from": 0,
        "to": 100,
        "elementSubtypeId": "ElementSubtype:28:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
