
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- P-MET-01
(702, 7, 101, 1, 'SEÑALIZACIÓN 1', 'Ejecutar planes de señalizacion', true, 1, true, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(703, 7, 1, 1, 'SEÑALIZACIÓN 2', 'Ejecutar plan automatico de restitución', true, 2, true, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(710, 7, 1, 1, 'SEÑALIZACIÓN 2 Manual', 'Ejecutar plan manual de restitución', true, 3, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(708, 7, null, 1, 'SEÑALIZACIÓN 3', null, true, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(709, 7, null, 1, 'SEÑALIZACIÓN 4', null, true, 2, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(704, 7, null, 3, 'Evacuation', 'Ejecutar plan automatico de restitución', true, 3, false, 'TaskTypeVentilation/EvacuationTask', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(706, 7, 2, 3, 'Arrancar ventilacion', 'Ejecutar plan automatico de restitución', true, 5, false, 'TaskTypeVentilation/StartVentilationTask', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(707, 7, 2, 3, 'Parar ventilacion', 'Ejecutar plan automatico de restitución', true, 6, false, 'TaskTypeVentilation/StopVentilationTask', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(711, 7, 2, 8, 'Enviar correo', 'Enviar correo electrónico', true, 7, false, 'SendMailTask', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(712, 7, 1, 1, 'SignallingsToSpath 4', null, false, 8, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(713, 7, 1, 1, 'SignallingsToSpath 5', null, false, 9, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(750, 7, 1, 1, 'SignallingsToSpath 6', null, false, 10, false, 'SendSignallingToStretch', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_tasks
(incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, "position", auto, on_change, delay, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Road impact 10
(714, 7, 1, 6, 'Tarea S-1-1 Road impact 10', 'Tarea S-1-1 Road impact 10', '', true, 10, false, null, null, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(715, 7, 1, 6, 'Tarea S-1-2 Road impact 10', 'Tarea S-1-2 Road impact 10', '', true, 11, false, null, null, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(716, 7, 2, 6, 'Tarea S-2-1 Road impact 10', 'Tarea S-2-1 Road impact 10', '', true, 12, false, null, null, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(717, 7, 2, 6, 'Tarea S-2-2 Road impact 10', 'Tarea S-2-1 Road impact 10', '', true, 13, false, null, null, true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Road impact 20
(718, 7, 1, 6, 'Tarea S-1-1 Road impact 20', 'Tarea S-1-1 Road impact 20', '', true, 14, false, null, null, true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(719, 7, 1, 6, 'Tarea S-1-2 Road impact 20', 'Tarea S-1-2 Road impact 20', '', true, 15, false, null, null, true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(720, 7, 2, 6, 'Tarea S-2-1 Road impact 20', 'Tarea S-2-1 Road impact 20', '', true, 16, false, null, null, true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(721, 7, 2, 6, 'Tarea S-2-2 Road impact 20', 'Tarea S-2-1 Road impact 20', '', true, 17, false, null, null, true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Road impact 30
(722, 7, 1, 6, 'Tarea S-1-1 Road impact 30', 'Tarea S-1-1 Road impact 30', '', true, 18, false, null, null, true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(723, 7, 1, 6, 'Tarea S-1-2 Road impact 30', 'Tarea S-1-2 Road impact 30', '', true, 19, false, null, null, true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(724, 7, 2, 6, 'Tarea S-2-1 Road impact 30', 'Tarea S-2-1 Road impact 30', '', true, 20, false, null, null, true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(725, 7, 2, 6, 'Tarea S-2-2 Road impact 30', 'Tarea S-2-1 Road impact 30', '', true, 21, false, null, null, true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, delay, enabled, visible, created_at, updated_at) VALUES
(701, 7, 1, 2, 'ILUMINACIÓN - I05', 'Verificación de la correcta iluminación de las bocas', true, 1, false, null, '70', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(705, 7, 2, 3, 'Extraction', 'Ejecutar plan automatico de restitución', true, 4, false, 'TaskTypeVentilation/ExtractionTask', '60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(1, 1, 712, '[
	{
		"id": 1,
		"alias": "Aguas abajo",
		"from": -1000,
		"to": -1,
		"lanes": [], 
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":81,\"alternance\":744}]},{\"zone\":2,\"alternance_ms\":5000,\"align\": \"left\",\"texts\":[{\"id\":1,\"value\":\"ACCIDENTE\",\"alternance\":\"REDUZCA\"},{\"id\":2,\"value\":\"A 3 KM EN\",\"alternance\":\"LA VELOCIDAD\"},{\"id\":3,\"value\":\"CARRIL DER.\",\"alternance\":\"USE CAR. IZD\"}]},{\"zone\":3,\"flashing_on_ms\":1000,\"flashing_off_ms\":1000,\"graphics\":[{\"id\":1,\"value\":85}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}, 
	{
		"id": 2,
		"alias": "Tramo afectado",
		"from": 0,
		"to": 0,
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":81,\"alternance\":744}]},{\"zone\":2,\"alternance_ms\":5000,\"align\": \"left\",\"texts\":[{\"id\":1,\"value\":\"ACCIDENTE 1\",\"alternance\":\"REDUZCA\"},{\"id\":2,\"value\":\"A 3 KM EN\",\"alternance\":\"LA VELOCIDAD\"},{\"id\":3,\"value\":\"CARRIL DER.\",\"alternance\":\"USE CAR. IZD\"}]},{\"zone\":3,\"flashing_on_ms\":1000,\"flashing_off_ms\":1000,\"graphics\":[{\"id\":1,\"value\":85}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}, 
	{
		"id": 3,
		"alias": "Aguas arriba",
		"from": 1,
		"to": 1000,
		"lanes": [1, 2],
		"command": {
					"commandElementTypeId": "CommandElementType:2:71",
					"commandElementValues": [{
						"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":81,\"alternance\":744}]},{\"zone\":2,\"alternance_ms\":5000,\"align\": \"left\",\"texts\":[{\"id\":1,\"value\":\"ACCIDENTE 2\",\"alternance\":\"REDUZCA\"},{\"id\":2,\"value\":\"A 3 KM EN\",\"alternance\":\"LA VELOCIDAD\"},{\"id\":3,\"value\":\"CARRIL DER.\",\"alternance\":\"USE CAR. IZD\"}]},{\"zone\":3,\"flashing_on_ms\":1000,\"flashing_off_ms\":1000,\"graphics\":[{\"id\":1,\"value\":85}]}]",
						"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
					}]	
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 1, 713, '[
	{
		"id": 1,
		"alias": "Aguas abajo",
		"from": -1,
		"to": 0,
		"elementSubtypeId": "ElementSubtype:1:53",
		"command": {
			"commandElementTypeId": "CommandElementType:5:53"
		}
	},
	{
		"id": 2,
		"alias": "Tramo afectado",
		"from": 0,
		"to": 0,
		"elementSubtypeId": "ElementSubtype:22:71",
		"command": {
			"commandElementTypeId": "CommandElementType:2:71",
			"commandElementValues": [
				{
					"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":81,\"alternance\":744}]},{\"zone\":2,\"alternance_ms\":5000,\"align\": \"left\",\"texts\":[{\"id\":1,\"value\":\"ACCIDENTE\",\"alternance\":\"REDUZCA\"},{\"id\":2,\"value\":\"A 3 KM EN\",\"alternance\":\"LA VELOCIDAD\"},{\"id\":3,\"value\":\"CARRIL DER.\",\"alternance\":\"USE CAR. IZD\"}]},{\"zone\":3,\"flashing_on_ms\":1000,\"flashing_off_ms\":1000,\"graphics\":[{\"id\":1,\"value\":85}]}]",
					"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
				}
			]
		}
	},
	{
		"id": 3,
		"alias": "Aguas arriba",
		"from": 0,
		"to": 1,
		"command": {
			"commandElementTypeId": "CommandElementType:2:53"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 1, 750, '[
	{
		"id": 1,
		"alias": "NR n",
		"from": 0,
		"to": 0,
		"command": {
			"commandElementTypeId": "CommandElementType:5:53"
		}
	},
	{
		"id": 2,
		"alias": "NR n-1",
		"from": 1,
		"to": 1,
		"command": {
			"commandElementTypeId": "CommandElementType:2:71",
			"commandElementValues": [
				{
					"value": "[{\"zone\":1,\"alternance_ms\":5000,\"graphics\":[{\"id\":1,\"value\":81,\"alternance\":744}]},{\"zone\":2,\"alternance_ms\":5000,\"align\": \"left\",\"texts\":[{\"id\":1,\"value\":\"ACCIDENTE\",\"alternance\":\"REDUZCA\"},{\"id\":2,\"value\":\"A 3 KM EN\",\"alternance\":\"LA VELOCIDAD\"},{\"id\":3,\"value\":\"CARRIL DER.\",\"alternance\":\"USE CAR. IZD\"}]},{\"zone\":3,\"flashing_on_ms\":1000,\"flashing_off_ms\":1000,\"graphics\":[{\"id\":1,\"value\":85}]}]",
					"commandElementTypeParamId": "CommandElementTypeParam:2:71:1"
				}
			]
		}
	},
	{
		"id": 3,
		"alias": "Nr n-2",
		"from": 2,
		"to": 2,
		"command": {
			"commandElementTypeId": "CommandElementType:2:53"
		}
	}
]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);











