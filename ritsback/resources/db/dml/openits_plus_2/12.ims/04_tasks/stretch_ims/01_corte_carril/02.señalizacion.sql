INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
--iluminacion 
(3, 1, 020101, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020101, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 030101, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 030101, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--ventilacion carril izquierdo
(5, 1, 040101, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--ventilacion carril derecho
(5, 1, 100101, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 020201, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 020201, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 030201, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--señalizacion carril derecho
(1, 1, 050101, '[
    {
        "id": 1,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":744,\"alternance\":66}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"CARRIL\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"DERECHO\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"CORTADO\",\"alternance\":\"EN TUNEL\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"}]}]",
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
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--señalizacion carril izquierdo
(1, 1, 080101, '[
    {
        "id": 1,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 2,
        "elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":743,\"alternance\":66}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"CARRIL\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"IZQUIERDO\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"CORTADO\",\"alternance\":\"EN TUNEL\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
        "id": 2,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 2,
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
