INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(2, 1, 060201, '[
    {
        "id": 1,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:19:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":164,\"alternance\":164}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"CON LLUVIA\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"MODERE\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"VELOCIDAD\",\"alternance\":\"EN TUNEL\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
        "id": 2,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:16:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"PRECAUCION\",\"alternance\":\"\",\"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
        "id": 3,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:12:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
							"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":746}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
