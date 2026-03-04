INSERT INTO static.ext_entity_types(ext_entity_type_id, alias, description, label_alias, label_description, archive_historical, cron_expression, config_params, enabled, visible, created_at, updated_at) VALUES
(1, 'C4', 'Incidencias generadas en sistema C4', 'LBL_EXT_ENTITY_TYPE_C4', 'LBL_EXT_ENTITY_TYPE_C4_DESC', true, '0 0/1 * * * ? *', 
'{
	"jdbc": {
		"url": "jdbc:sqlserver://;serverName=192.168.88.159;databaseName=IncCarreteras",
		"username": "SA",
		"password": "Navarra0"
	},
	"selectQuery": "SELECT categoria, descripcion, fecha_actualizacion, nombre_carretera, pk, hm, tipo, afeccion, fecha, titulo, id_incidencia, fecha_modificacion, id_carretera, utm_x, utm_y, id_tipo, id_afeccion, color FROM IncCarreteras.dbo.BaseIVR;",
	"extEntityParamsMapping": [ {"field": "id_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:1:1"},
								{"field": "nombre_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:1:2"},
								{"field": "pk", "extEntityTypeParamId": "ExtEntityTypeParam:1:3"},
								{"field": "hm", "extEntityTypeParamId": "ExtEntityTypeParam:1:4"},
								{"field": "id_afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:1:5"},
								{"field": "afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:1:6"},
								{"field": "fecha_actualizacion", "extEntityTypeParamId": "ExtEntityTypeParam:1:7"},
								{"field": "color", "extEntityTypeParamId": "ExtEntityTypeParam:1:8"},
								{"field": "fecha", "extEntityTypeParamId": "ExtEntityTypeParam:1:9"},
								{"field": "categoria", "extEntityTypeParamId": "ExtEntityTypeParam:1:11"},
								{"field": "id_tipo", "extEntityTypeParamId": "ExtEntityTypeParam:1:12"},
								{"field": "descripcion", "extEntityTypeParamId": "ExtEntityTypeParam:1:13"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id_incidencia", "dstField": "uid"},
								{"srcField": "tipo", "dstField": "alias"},
								{"srcField": "descripcion", "dstField": "description"}
							],
	"extEntitySubtypes": {
		"field": "categoria",
		"mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:1001", "value": "Obras"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1002", "value": "Meteorológicas"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1003", "value": "Red Viaria"}
		]
	},
	"incidentReportMappings": [{
	    "extEntitySubtypeId": "ExtEntitySubtype:1003",
	    "imsIncidentTypeId": "ImsIncidentType:401",
	    "confirm": true
	  }]
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4, '112 Emergencias', 'Incidencias generadas en sistema 112 Emergencias', 'LBL_EXT_ENTITY_TYPE_112', 'LBL_EXT_ENTITY_TYPE_112_DESC', true, '10 0/1 * * * ? *', 
'{

	"host": "http://192.168.88.163:8100/TrafficIncidents",
	"extEntityParamsMapping": [ {"field": "location.text", "extEntityTypeParamId": "ExtEntityTypeParam:4:1"},
								{"field": "statusName", "extEntityTypeParamId": "ExtEntityTypeParam:4:2"},
								{"field": "creationTime", "extEntityTypeParamId": "ExtEntityTypeParam:4:5"},
								{"field": "typeOid", "extEntityTypeParamId": "ExtEntityTypeParam:4:6"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id", "dstField": "uid"},
								{"srcField": "typeName", "dstField": "alias"},
								{"srcField": "typeName", "dstField": "description"}
							],
	"extEntitySubtypes": {
		"field": "typeOid",
		"mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:4001", "value": "11020"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4001", "value": "11021"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4001", "value": "11022"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:4001", "value": "11080"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4001", "value": "11081"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4001", "value": "11082"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4001", "value": "11150"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4001", "value": "11160"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4001", "value": "11360"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4002", "value": "11510"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4002", "value": "11570"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4002", "value": "11640"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12000"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12001"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12010"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12020"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12030"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12040"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12050"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12060"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12070"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12080"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12090"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12100"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12110"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12120"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12130"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12350"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12360"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12370"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12410"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12420"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12430"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12440"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12510"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4003", "value": "12520"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:4004", "value": "18010"}
		]
	},
	"incidentReportMappings": [{
	    "extEntitySubtypeId": "ExtEntitySubtype:4001",
	    "imsIncidentTypeId": "ImsIncidentType:401",
	    "confirm": true
	  }]  
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, '112 - Resources', 'Recursos asiganados a una incidencia del 112', 'LBL_EXT_ENTITY_TYPE_112_RESOURCES', 'LBL_EXT_ENTITY_TYPE_112_RESOURCES_DESC', true, '30 0/1 * * * ? *', 
'{
	"host": "http://192.168.88.163:8100/TrafficIncidents",
	"extEntityParamsMapping": [ {"field": "avlTime", "extEntityTypeParamId": "ExtEntityTypeParam:5:1"},
								{"field": "statusName", "extEntityTypeParamId": "ExtEntityTypeParam:5:2"},
								{"field": "statusTime", "extEntityTypeParamId": "ExtEntityTypeParam:5:3"}
							],
	"extEntityFieldsMapping": [ {"srcField": "name", "dstField": "uid"},
								{"srcField": "agencyName", "dstField": "alias"},
								{"srcField": "stationName", "dstField": "description"}
							]
	}  
}',

true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(6, '112 - Vehicles', 'Vehículos asiganados a una incidencia del 112', 'LBL_EXT_ENTITY_TYPE_112_VEHICLES', 'LBL_EXT_ENTITY_TYPE_112_VEHICLES_DESC', true, '50 0/1 * * * ? *', 
'{
	"host": "http://192.168.88.163:8100/TrafficIncidents",
	"extEntityParamsMapping": [ {"field": "color", "extEntityTypeParamId": "ExtEntityTypeParam:6:1"}
							],
	"extEntityFieldsMapping": [ {"srcField": "plateNumber", "dstField": "uid"},
								{"srcField": "brand", "dstField": "alias"},
								{"srcField": "model", "dstField": "description"}
							]
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(7, 'Waze traffic alerts', 'Alertas de trafico de waze', 'LBL_EXT_ENTITY_TYPE_WAZE_TRAFFIC_ALERTS', 'LBL_EXT_ENTITY_TYPE_WAZE_TRAFFIC_ALERTS_DESC', true, '0/30 * * * * ? *', 
'{
	"url": "https://www.waze.com/row-partnerhub-api/partners/16934850034/waze-feeds/c46bd65e-1743-4e61-88da-311bab40b8ae?format=1&types=alerts",
	"reliability": 0,
	"confidence": 0,
	"extEntityParamsMapping": [ {"field": "pubMillis", "extEntityTypeParamId": "ExtEntityTypeParam:7:1"},
								{"field": "magvar", "extEntityTypeParamId": "ExtEntityTypeParam:7:2"},
								{"field": "street", "extEntityTypeParamId": "ExtEntityTypeParam:7:3"},
								{"field": "city", "extEntityTypeParamId": "ExtEntityTypeParam:7:4"},
								{"field": "country", "extEntityTypeParamId": "ExtEntityTypeParam:7:5"},
								{"field": "reportRating", "extEntityTypeParamId": "ExtEntityTypeParam:7:6"},
								{"field": "reliability", "extEntityTypeParamId": "ExtEntityTypeParam:7:7"},
								{"field": "confidence", "extEntityTypeParamId": "ExtEntityTypeParam:7:8"},
								{"field": "reportByMunicipalityUser", "extEntityTypeParamId": "ExtEntityTypeParam:7:9"}
							],
	"extEntityFieldsMapping": [ {"srcField": "uuid", "dstField": "uid"},
								{"srcField": "street", "dstField": "alias"},
								{"srcField": "reportDescription", "dstField": "description"}
							],
	"extEntitySubtypes": {
			"field": "type",
			"mapping": [{"extEntitySubtypeId": "ExtEntitySubtype:7001", "value": "ACCIDENT",
						 "extEntitySubtypes": {
							"field": "subtype",
							"mapping": [{"extEntitySubtypeId": "ExtEntitySubtype:7001", "value": "ACCIDENT_MINOR"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7002", "value": "ACCIDENT_MAJOR"}]
						 }
						},
						{"extEntitySubtypeId": "ExtEntitySubtype:7006", "value": "JAM",
						 "extEntitySubtypes": {
							"field": "subtype",
							"mapping": [{"extEntitySubtypeId": "ExtEntitySubtype:7003", "value": "JAM_MODERATE_TRAFFIC"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "JAM_HEAVY_TRAFFIC"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7005", "value": "JAM_STAND_STILL_TRAFFIC"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7006", "value": "JAM_LIGHT_TRAFFIC"}]
						 }
						},
						{"extEntitySubtypeId": "ExtEntitySubtype:7007", "value": "HAZARD",
						 "extEntitySubtypes": {
							"field": "subtype",
							"mapping": [{"extEntitySubtypeId": "ExtEntitySubtype:7007", "value": "HAZARD_ON_ROAD"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7008", "value": "HAZARD_ON_SHOULDER"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7009", "value": "HAZARD_WEATHER"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7010", "value": "HAZARD_ON_ROAD_OBJECT"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7011", "value": "HAZARD_ON_ROAD_POT_HOLE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7012", "value": "HAZARD_ON_ROAD_ROAD_KILL"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7013", "value": "HAZARD_ON_SHOULDER_CAR_STOPPED"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7014", "value": "HAZARD_ON_SHOULDER_ANIMALS"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7015", "value": "HAZARD_ON_SHOULDER_MISSING_SIGN"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7016", "value": "HAZARD_WEATHER_FOG"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7017", "value": "HAZARD_WEATHER_HAIL"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7018", "value": "HAZARD_WEATHER_HEAVY_RAIN"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7019", "value": "HAZARD_WEATHER_HEAVY_SNOW"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7020", "value": "HAZARD_WEATHER_FLOOD"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7021", "value": "HAZARD_WEATHER_MONSOON"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7022", "value": "HAZARD_WEATHER_TORNADO"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7023", "value": "HAZARD_WEATHER_HEAT_WAVE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7024", "value": "HAZARD_WEATHER_HURRICANE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7025", "value": "HAZARD_WEATHER_FREEZING_RAIN"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7026", "value": "HAZARD_ON_ROAD_LANE_CLOSED"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7027", "value": "HAZARD_ON_ROAD_OIL"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7028", "value": "HAZARD_ON_ROAD_ICE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7029", "value": "HAZARD_ON_ROAD_CONSTRUCTION"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7030", "value": "HAZARD_ON_ROAD_CAR_STOPPED"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7031", "value": "HAZARD_ON_ROAD_TRAFFIC_LIGHT_FAULT"}]
						 }
						},
						{"extEntitySubtypeId": "ExtEntitySubtype:7007", "value": "WEATHERHAZARD",
						 "extEntitySubtypes": {
							"field": "subtype",
							"mapping": [{"extEntitySubtypeId": "ExtEntitySubtype:7007", "value": "HAZARD_ON_ROAD"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7008", "value": "HAZARD_ON_SHOULDER"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7009", "value": "HAZARD_WEATHER"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7010", "value": "HAZARD_ON_ROAD_OBJECT"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7011", "value": "HAZARD_ON_ROAD_POT_HOLE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7012", "value": "HAZARD_ON_ROAD_ROAD_KILL"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7013", "value": "HAZARD_ON_SHOULDER_CAR_STOPPED"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7014", "value": "HAZARD_ON_SHOULDER_ANIMALS"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7015", "value": "HAZARD_ON_SHOULDER_MISSING_SIGN"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7016", "value": "HAZARD_WEATHER_FOG"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7017", "value": "HAZARD_WEATHER_HAIL"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7018", "value": "HAZARD_WEATHER_HEAVY_RAIN"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7019", "value": "HAZARD_WEATHER_HEAVY_SNOW"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7020", "value": "HAZARD_WEATHER_FLOOD"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7021", "value": "HAZARD_WEATHER_MONSOON"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7022", "value": "HAZARD_WEATHER_TORNADO"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7023", "value": "HAZARD_WEATHER_HEAT_WAVE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7024", "value": "HAZARD_WEATHER_HURRICANE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7025", "value": "HAZARD_WEATHER_FREEZING_RAIN"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7026", "value": "HAZARD_ON_ROAD_LANE_CLOSED"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7027", "value": "HAZARD_ON_ROAD_OIL"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7028", "value": "HAZARD_ON_ROAD_ICE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7029", "value": "HAZARD_ON_ROAD_CONSTRUCTION"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7030", "value": "HAZARD_ON_ROAD_CAR_STOPPED"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7031", "value": "HAZARD_ON_ROAD_TRAFFIC_LIGHT_FAULT"}]
						 }
						},
						{"extEntitySubtypeId": "ExtEntitySubtype:7032", "value": "MISC"},
						{"extEntitySubtypeId": "ExtEntitySubtype:7033", "value": "CONSTRUCTION"},
						{"extEntitySubtypeId": "ExtEntitySubtype:7034", "value": "ROAD_CLOSED",
						 "extEntitySubtypes": {
							"field": "subtype",
							"mapping": [{"extEntitySubtypeId": "ExtEntitySubtype:7034", "value": "ROAD_CLOSED_HAZARD"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7035", "value": "ROAD_CLOSED_CONSTRUCTION"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7036", "value": "ROAD_CLOSED_EVENT"}]
						 }
						}
		]
	},
	"incidentReportMappings": [{
	    "extEntitySubtypeId": "ExtEntitySubtype:7001",
	    "imsIncidentTypeId": "ImsIncidentType:401",
	    "confirm": true
	  },{
	    "extEntitySubtypeId": "ExtEntitySubtype:7002",
	    "imsIncidentTypeId": "ImsIncidentType:401",
	    "confirm": true
	  }]   
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 'Waze traffic jams', 'Atascos de trafico waze', 'LBL_EXT_ENTITY_TYPE_WAZE_TRAFFIC_JAMS', 'LBL_EXT_ENTITY_TYPE_WAZE_TRAFFIC_JAMS_DESC', true, '10/40 * * * * ? *', 
'{
	"url": "https://www.waze.com/row-partnerhub-api/partners/16934850034/waze-feeds/c46bd65e-1743-4e61-88da-311bab40b8ae?format=1&types=traffic",
	"level": 0,
	"extEntityParamsMapping": [ {"field": "pubMillis", "extEntityTypeParamId": "ExtEntityTypeParam:8:1"},
								{"field": "speedKMH", "extEntityTypeParamId": "ExtEntityTypeParam:8:2"},
								{"field": "length", "extEntityTypeParamId": "ExtEntityTypeParam:8:3"},
								{"field": "delay", "extEntityTypeParamId": "ExtEntityTypeParam:8:4"},
								{"field": "city", "extEntityTypeParamId": "ExtEntityTypeParam:8:5"},
								{"field": "country", "extEntityTypeParamId": "ExtEntityTypeParam:8:6"},
								{"field": "startNode", "extEntityTypeParamId": "ExtEntityTypeParam:8:7"},
								{"field": "endNode", "extEntityTypeParamId": "ExtEntityTypeParam:8:8"},
								{"field": "street", "extEntityTypeParamId": "ExtEntityTypeParam:8:9"}
							],
	"extEntityFieldsMapping": [ {"srcField": "uuid", "dstField": "uid"},
								{"srcField": "street", "dstField": "alias"}
							] ,
	"extEntitySubtypes": {
		"field": "level",
		"mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:8001", "value": "0"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:8002", "value": "1"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:8003", "value": "2"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:8004", "value": "3"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:8005", "value": "4"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:8006", "value": "5"}
		]
	} 
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 'Waze routes', 'Rutas creadas en waze', 'LBL_EXT_ENTITY_TYPE_WAZE_ROUTES', 'LBL_EXT_ENTITY_TYPE_WAZE_ROUTES_DESC', false, '20/50 * * * * ? *', 
'{
	"url": "https://www.waze.com/row-partnerhub-api/feeds-tvt/?id=11010904334",
	"level": 0,
	"extEntityParamsMapping": [ {"field": "length", "extEntityTypeParamId": "ExtEntityTypeParam:9:1"},
								{"field": "time", "extEntityTypeParamId": "ExtEntityTypeParam:9:2"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id", "dstField": "uid"},
								{"srcField": "name", "dstField": "alias"},
								{"srcField": "fromName", "dstField": "description"}
							] ,
	"extEntitySubtypes": {
		"field": "jamLevel",
		"mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:9001", "value": "0"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:9002", "value": "1"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:9003", "value": "2"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:9004", "value": "3"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:9005", "value": "4"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:9006", "value": "5"}
		]
	} 
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(10, 'Balizas V16', 'Balizas V16', 'LBL_EXT_ENTITY_TYPE_112', 'LBL_EXT_ENTITY_TYPE_112_DESC', true, '0 0/1 * * * ? *', 
'{

	"url": "http://192.168.88.163:8582/events",
	"extEntityParamsMapping": [ {"field": "lon", "extEntityTypeParamId": "ExtEntityTypeParam:10:1"},
								{"field": "lat", "extEntityTypeParamId": "ExtEntityTypeParam:10:2"}
							],
	"extEntityFieldsMapping": [ {"srcField": "actionid", "dstField": "uid"},
								{"srcField": "device_event_type_value", "dstField": "alias"},
								{"srcField": "device_event_type", "dstField": "description"}
							],
	"extEntitySubtypes": {
		"field": "device_event_type_value",
		"mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:10001", "value": "2"}
		]
	} 
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
