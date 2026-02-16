INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
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
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":744,\"alternance\":66}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"CARRIL\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"DERECHO\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"CORTADO\",\"alternance\":\"EN TUNEL\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
        "id": 2,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":744,\"alternance\":66}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"CARRIL\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"DERECHO\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"CORTADO\",\"alternance\":\"EN TUNEL\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"},{\"zone\":3,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":744,\"alternance\":66}],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},	
	{
        "id": 3,
        "alias": "PANEL NR n-1",
        "from": 2,
        "to": 2,
        "elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":722,\"alternance\":0}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"A 5 KM\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
        "id": 4,
        "alias": "PANEL NR n-1",
        "from": 2,
        "to": 2,
        "elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":722,\"alternance\":0}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"A 5 KM\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"},{\"zone\":3,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":722,\"alternance\":0}],\"align\":\"center\"}]",
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
        "to": 1,
        "elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":743,\"alternance\":66}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"CARRIL\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"IZQUIERDO\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"CORTADO\",\"alternance\":\"EN TUNEL\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
        "id": 2,
        "alias": "PANEL NR n",
        "from": 1,
        "to": 1,
        "elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":743,\"alternance\":66}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"CARRIL\",\"alternance\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"IZQUIERDO\",\"alternance\":\"PRECAUCION\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"CORTADO\",\"alternance\":\"EN TUNEL\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"},{\"zone\":3,\"alternance_ms\":3000,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":743,\"alternance\":66}],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},		
	{
        "id": 3,
        "alias": "PANEL NR n-1",
        "from": 2,
        "to": 2,
        "elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":723,\"alternance\":0}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"A 5 KM\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
        "id": 4,
        "alias": "PANEL NR n-1",
        "from": 2,
        "to": 2,
        "elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":723,\"alternance\":0}],\"align\":\"center\"},{\"zone\":2,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[{\"id\":1,\"value\":\"\",\"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"A 5 KM\",\"color\":\"#FFFFFF\"},{\"id\":3,\"value\":\"\",\"alternance\":\"\",\"color\":\"#FFFFFF\"}],\"graphics\":[],\"align\":\"center\"},{\"zone\":3,\"alternance_ms\":0,\"flashing_on_ms\":0,\"flashing_off_ms\":0,\"texts\":[],\"graphics\":[{\"id\":1,\"value\":723,\"alternance\":0}],\"align\":\"center\"}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}	
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
