INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 03020339, '[
    {
        "id": 1,
        "alias": "PANEL NR n",
        "spathId": "Spath:100",
        "elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
        "id": 2,
        "alias": "PANEL NR n",
        "spathId": "Spath:100",
        "elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
        "id": 3,
        "alias": "PANEL NR n-1",
        "spathId": "Spath:101",
        "elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	},
	{
        "id": 4,
        "alias": "PANEL NR n-1",
        "spathId": "Spath:101",
        "elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
