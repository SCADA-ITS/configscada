INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 1, 01010339, '[
	{
		"id": 1,
		"alias": "PMV - aguas arriba de la incidencia",
		"spathId": "Spath:100",
		"elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"317\",\"alternance\":\"317\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"POSIBLES\",\"alternance\": \"MODERE\", \"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"RETENCIONES\",\"alternance\": \"VELOCIDAD\", \"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 2,
		"alias": "PMV  - aguas arriba de la incidencia",
		"spathId": "Spath:100",
		"elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"317\",\"alternance\":\"317\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"POSIBLES\",\"alternance\": \"MODERE\", \"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"RETENCIONES\",\"alternance\": \"VELOCIDAD\", \"color\":\"#FFFFFF\"}]}, {\"zone\":3,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"317\",\"alternance\":\"317\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 3,
		"alias": "PMV - aguas abajo de la incidencia",
		"spathId": "Spath:101",
		"elementSubtypeId": "ElementSubtype:18:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"319\",\"alternance\":\"319\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"POSIBLES\",\"alternance\": \"MODERE\", \"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"RETENCIONES\",\"alternance\": \"VELOCIDAD\", \"color\":\"#FFFFFF\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	},
	{
		"id": 4,
		"alias": "PMV - aguas abajo de la incidencia",
		"spathId": "Spath:101",
		"elementSubtypeId": "ElementSubtype:22:71",
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"319\",\"alternance\":\"319\"}]},{\"zone\":2,\"alternance_ms\":5000,\"align\":\"center\",\"texts\":[{\"id\":1,\"value\":\"POSIBLES\",\"alternance\": \"MODERE\", \"color\":\"#FFFFFF\"},{\"id\":2,\"value\":\"RETENCIONES\",\"alternance\": \"VELOCIDAD\", \"color\":\"#FFFFFF\"}]}, {\"zone\":3,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":\"319\",\"alternance\":\"319\"}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

