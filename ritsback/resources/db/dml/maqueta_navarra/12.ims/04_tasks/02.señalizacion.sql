INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
--señalizacion carril derecho
(1, 1, 030101, '[
    {
        "id": 1,
        "alias": "PMV-01",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":744,\"alternance\":66}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"CARRIL\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"DERECHO\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"CORTADO\",\"alternance\":\"EN TUNEL\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
