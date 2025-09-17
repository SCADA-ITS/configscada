INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
--ilumninación
(3, 1, 01030101, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 01030101, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 1, 01040101, '[
    {
        "id": 1,
        "alias": "Semáforos",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:1:13",
        "command": {
                    "commandElementTypeId": "CommandElementType:3:13",
                    "commandElementValues": [{"commandElementTypeParamId": "CommandElementTypeParam:2:13:1"}]
                    }
    },
    {
        "id": 2,
        "alias": "Semáforos",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:2:13",
        "command": {
                    "commandElementTypeId": "CommandElementType:3:13",
                    "commandElementValues": [{"commandElementTypeParamId": "CommandElementTypeParam:2:13:1"}]
                    }
    },
	{
        "id": 5,
        "alias": "Aspa-Flecha",
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
	},
	{
		"id": 6,
		"alias": "CLV",
		"from": 1,
        "to": 1,
		"elementSubtypeId": "ElementSubtype:11:71",
		"command": {
					"commandElementTypeId": "CommandElementType:3:71",
					"commandElementValues": [{
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]
		}
	},
    {
        "id": 7,
        "alias": "PMV",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"TUNEL\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"CERRADO\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"\",\"alternance\":\"\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
    {
        "id": 8,
        "alias": "PMV",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"TUNEL\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"CERRADO\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"\",\"alternance\":\"\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"},{\"zone\":3,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
    {
        "id": 9,
        "alias": "STOP",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:26:71",
		"command": {
					"commandElementTypeId": "CommandElementType:4:71",
					"commandElementValues": [{
                        "commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
    {
        "id": 10,
        "alias": "PRESURIZACION",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:3:32",
        "command": {
                    "commandElementTypeId": "CommandElementType:1:32",
                    "commandElementValues": [{"commandElementTypeParamId": "CommandElementTypeParam:2:32:1"}]
                    }
    }
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



--RESTITUCION
--ventilación
--(5, 1, 03010101, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 03030101, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 1, 03020101, '[
    {
        "id": 1,
        "alias": "PRESURIZACION",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:3:32",
        "command": {
                    "commandElementTypeId": "CommandElementType:2:32",
                    "commandElementValues": [{"commandElementTypeParamId": "CommandElementTypeParam:2:32:1"}]
                    }
    }]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--CANCELACION DE INCIDENCIA
(1, 1, 101020101, '[
    {
        "id": 1,
        "alias": "PRESURIZACION",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:3:32",
        "command": {
                    "commandElementTypeId": "CommandElementType:2:32",
                    "commandElementValues": [{"commandElementTypeParamId": "CommandElementTypeParam:2:32:1"}]
                    }
    }]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
