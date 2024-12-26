INSERT INTO static.ext_entity_types(ext_entity_type_id, alias, description, label_alias, label_description, archive_historical, cron_expression, config_params, enabled, visible, created_at, updated_at) VALUES
(1, 'C4 - Obras', 'Incidencias generadas en sistema C4 - Obras', 'LBL_EXT_ENTITY_TYPE_C4_WORKS', 'LBL_EXT_ENTITY_TYPE_C4_WORKS_DESC', true, '0 0/1 * * * ? *', 
'{
	"jdbc": {
		"url": "jdbc:sqlserver://;serverName=192.168.88.159;databaseName=IncCarreteras",
		"username": "SA",
		"password": "Navarra0"
	},
	"selectQuery": "SELECT categoria, fecha_actualizacion, nombre_carretera, pk, hm, tipo, afeccion, fecha, titulo, id_incidencia, fecha_modificacion, id_carretera, utm_x, utm_y, id_tipo, id_afeccion, color FROM IncCarreteras.dbo.BaseIVR where id_categoria  = 1;",
	"extEntityParamsMapping": [ {"field": "id_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:1:1"},
								{"field": "nombre_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:1:2"},
								{"field": "pk", "extEntityTypeParamId": "ExtEntityTypeParam:1:3"},
								{"field": "hm", "extEntityTypeParamId": "ExtEntityTypeParam:1:4"},
								{"field": "id_afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:1:5"},
								{"field": "afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:1:6"},
								{"field": "fecha_actualizacion", "extEntityTypeParamId": "ExtEntityTypeParam:1:7"},
								{"field": "color", "extEntityTypeParamId": "ExtEntityTypeParam:1:8"},
								{"field": "fecha", "extEntityTypeParamId": "ExtEntityTypeParam:1:9"},
								{"field": "categoria", "extEntityTypeParamId": "ExtEntityTypeParam:1:11"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id_incidencia", "dstField": "uid"},
								{"srcField": "tipo", "dstField": "alias"},
								{"srcField": "titulo", "dstField": "description"}
							],
	"extEntitySubtypes": {
		"field": "id_tipo",
		"mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:1001", "value": "5"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1002", "value": "6"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1003", "value": "10"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:1004", "value": "14"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1005", "value": "17"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1006", "value": "20"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1007", "value": "21"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1008", "value": "22"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1009", "value": "23"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1010", "value": "24"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1011", "value": "25"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1012", "value": "34"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1013", "value": "35"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1014", "value": "37"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1015", "value": "38"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1016", "value": "41"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1017", "value": "42"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1018", "value": "43"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:1019", "value": "45"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1020", "value": "46"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1021", "value": "47"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1022", "value": "49"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1023", "value": "51"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1024", "value": "52"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1025", "value": "56"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:1026", "value": "57"}
		]
	} 
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 'C4 - Meteorológicas', 'Incidencias generadas en sistema C4 - Meteorológicas', 'LBL_EXT_ENTITY_TYPE_C4_WEATHER', 'LBL_EXT_ENTITY_TYPE_C4_WEATHER_DESC', true, '20 0/1 * * * ? *', 
'{
	"jdbc": {
		"url": "jdbc:sqlserver://;serverName=192.168.88.159;databaseName=IncCarreteras",
		"username": "SA",
		"password": "Navarra0"
	},
	"selectQuery": "SELECT categoria, fecha_actualizacion, nombre_carretera, pk, hm, tipo, afeccion, fecha, titulo, id_incidencia, fecha_modificacion, id_carretera, utm_x, utm_y, id_tipo, id_afeccion, color FROM IncCarreteras.dbo.BaseIVR where id_categoria  = 2;",
	"extEntityParamsMapping": [ {"field": "id_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:2:1"},
								{"field": "nombre_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:2:2"},
								{"field": "pk", "extEntityTypeParamId": "ExtEntityTypeParam:2:3"},
								{"field": "hm", "extEntityTypeParamId": "ExtEntityTypeParam:2:4"},
								{"field": "id_afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:2:5"},
								{"field": "afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:2:6"},
								{"field": "fecha_actualizacion", "extEntityTypeParamId": "ExtEntityTypeParam:2:7"},
								{"field": "color", "extEntityTypeParamId": "ExtEntityTypeParam:2:8"},
								{"field": "fecha", "extEntityTypeParamId": "ExtEntityTypeParam:2:9"},
								{"field": "categoria", "extEntityTypeParamId": "ExtEntityTypeParam:2:11"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id_incidencia", "dstField": "uid"},
								{"srcField": "tipo", "dstField": "alias"},
								{"srcField": "titulo", "dstField": "description"}
							],
	"extEntitySubtypes": {
		"field": "id_tipo",
		"mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:2001", "value": "1"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2002", "value": "2"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2003", "value": "6"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:2004", "value": "8"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2005", "value": "14"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2006", "value": "17"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2007", "value": "20"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2008", "value": "21"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2009", "value": "23"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2010", "value": "25"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2011", "value": "35"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2012", "value": "36"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2013", "value": "39"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2014", "value": "52"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2015", "value": "54"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:2016", "value": "58"}
		]
	}  
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 'C4 - Red viaria', 'Incidencias generadas en sistema C4 - Red viaria', 'LBL_EXT_ENTITY_TYPE_C4_ROAD', 'LBL_EXT_ENTITY_TYPE_C4_ROAD_DESC', true, '40 0/1 * * * ? *', 
'{
	"jdbc": {
		"url": "jdbc:sqlserver://;serverName=192.168.88.159;databaseName=IncCarreteras",
		"username": "SA",
		"password": "Navarra0"
	},
	"selectQuery": "SELECT categoria, fecha_actualizacion, nombre_carretera, pk, hm, tipo, afeccion, fecha, titulo, id_incidencia, fecha_modificacion, id_carretera, utm_x, utm_y, id_tipo, id_afeccion, color FROM IncCarreteras.dbo.BaseIVR where id_categoria  = 3;",
	"extEntityParamsMapping": [ {"field": "id_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:3:1"},
								{"field": "nombre_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:3:2"},
								{"field": "pk", "extEntityTypeParamId": "ExtEntityTypeParam:3:3"},
								{"field": "hm", "extEntityTypeParamId": "ExtEntityTypeParam:3:4"},
								{"field": "id_afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:3:5"},
								{"field": "afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:3:6"},
								{"field": "fecha_actualizacion", "extEntityTypeParamId": "ExtEntityTypeParam:3:7"},
								{"field": "color", "extEntityTypeParamId": "ExtEntityTypeParam:3:8"},
								{"field": "fecha", "extEntityTypeParamId": "ExtEntityTypeParam:3:9"},
								{"field": "categoria", "extEntityTypeParamId": "ExtEntityTypeParam:3:11"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id_incidencia", "dstField": "uid"},
								{"srcField": "tipo", "dstField": "alias"},
								{"srcField": "titulo", "dstField": "description"}
							],
	"extEntitySubtypes": {
		"field": "id_tipo",
		"mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:3001", "value": "2"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3002", "value": "3"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3003", "value": "4"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:3004", "value": "5"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3005", "value": "6"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3006", "value": "7"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3007", "value": "8"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3008", "value": "9"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3009", "value": "10"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3010", "value": "11"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3011", "value": "12"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3012", "value": "13"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3013", "value": "14"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3014", "value": "15"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3015", "value": "16"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3016", "value": "18"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3017", "value": "19"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3018", "value": "20"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:3019", "value": "21"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3020", "value": "22"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3021", "value": "23"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3022", "value": "25"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3023", "value": "26"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3024", "value": "27"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3025", "value": "29"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3026", "value": "30"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3027", "value": "35"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3028", "value": "36"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3029", "value": "44"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3030", "value": "48"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3031", "value": "50"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3032", "value": "52"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3033", "value": "54"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3034", "value": "58"}
		]
	}  
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4, '112 Emergencias', 'Incidencias generadas en sistema 112 Emergencias', 'LBL_EXT_ENTITY_TYPE_112', 'LBL_EXT_ENTITY_TYPE_112_DESC', true, '0 * * * * ? *', 
'{

	"host": "http://192.168.88.163:8100/TrafficIncidents",
	"extEntityParamsMapping": [ {"field": "location.text", "extEntityTypeParamId": "ExtEntityTypeParam:4:1"},
								{"field": "statusName", "extEntityTypeParamId": "ExtEntityTypeParam:4:2"},
								{"field": "creationTime", "extEntityTypeParamId": "ExtEntityTypeParam:4:5"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id", "dstField": "uid"},
								{"srcField": "typeName", "dstField": "alias"},
								{"srcField": "typeName", "dstField": "description"}
							],
	"extEntitySubtypes": {
		"field": "typeOid",
		"mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:3001", "value": "11020"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3002", "value": "11021"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3003", "value": "11022"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:3004", "value": "11080"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3005", "value": "11081"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3006", "value": "11082"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3007", "value": "11150"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3008", "value": "11160"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3009", "value": "11360"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3010", "value": "11510"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3011", "value": "11570"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3012", "value": "11640"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3013", "value": "12000"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3014", "value": "12001"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3015", "value": "12010"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3016", "value": "12020"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3017", "value": "12030"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3018", "value": "12040"},					
					 {"extEntitySubtypeId": "ExtEntitySubtype:3019", "value": "12050"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3020", "value": "12060"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3021", "value": "12070"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3022", "value": "12080"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3023", "value": "12090"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3024", "value": "12100"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3025", "value": "12110"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3026", "value": "12120"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3027", "value": "12130"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3028", "value": "12350"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3029", "value": "12360"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3030", "value": "12370"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3031", "value": "12410"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3032", "value": "12420"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3033", "value": "12430"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3034", "value": "12440"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3035", "value": "12510"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3036", "value": "12520"},
					 {"extEntitySubtypeId": "ExtEntitySubtype:3037", "value": "18010"}
		]
	}  
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, '112 - Resources', 'Recursos asiganados a una incidencia del 112', 'LBL_EXT_ENTITY_TYPE_112_RESOURCES', 'LBL_EXT_ENTITY_TYPE_112_RESOURCES_DESC', true, '20 * * * * ? *', 
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
(6, '112 - Vehicles', 'Vehículos asiganados a una incidencia del 112', 'LBL_EXT_ENTITY_TYPE_112_VEHICLES', 'LBL_EXT_ENTITY_TYPE_112_VEHICLES_DESC', true, '40 * * * * ? *', 
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

(7, 'Waze traffic alerts', 'Alertas de trafico de waze', 'LBL_EXT_ENTITY_TYPE_WAZE_TRAFFIC_ALERTS', 'LBL_EXT_ENTITY_TYPE_WAZE_TRAFFIC_ALERTS_DESC', true, '0 0/2 * * * ? *', 
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
								{"srcField": "subtype", "dstField": "alias"},
								{"srcField": "reportDescription", "dstField": "description"}
							],
	"extEntitySubtypes": {
			"field": "type",
			"mapping": [{"extEntitySubtypeId": "ExtEntitySubtype:7001", "value": "ACCIDENT"},
						{"extEntitySubtypeId": "ExtEntitySubtype:7002", "value": "JAM"},
						{"extEntitySubtypeId": "ExtEntitySubtype:7003", "value": "HAZARD"},
						{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "WEATHERHAZARD"},	
						{"extEntitySubtypeId": "ExtEntitySubtype:7003", "value": "WEATHERHAZARD / HAZARD",	
							"field": "subtype",
							"mapping": [{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_FOG"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_HAIL"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_HEAVY_RAIN"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_HEAVY_SNOW"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_FLOOD"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_MONSOON"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_TORNADO"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_HEAT_WAVE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_HURRICANE"},
										{"extEntitySubtypeId": "ExtEntitySubtype:7004", "value": "HAZARD_WEATHER_FREEZING_RAIN"}]
						},					
						{"extEntitySubtypeId": "ExtEntitySubtype:7005", "value": "MISC"},
						{"extEntitySubtypeId": "ExtEntitySubtype:7006", "value": "CONSTRUCTION"},
						{"extEntitySubtypeId": "ExtEntitySubtype:7007", "value": "ROAD_CLOSED"}
		]
	}  
}',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(8, 'Waze traffic jams', 'Atascos de trafico waze', 'LBL_EXT_ENTITY_TYPE_WAZE_TRAFFIC_JAMS', 'LBL_EXT_ENTITY_TYPE_WAZE_TRAFFIC_JAMS_DESC', true, '50 0/2 * * * ? *', 
'{
	"url": "https://www.waze.com/row-partnerhub-api/partners/16934850034/waze-feeds/c46bd65e-1743-4e61-88da-311bab40b8ae?format=1&types=traffic",
	"level": 1,
	"extEntityParamsMapping": [ {"field": "pubMillis", "extEntityTypeParamId": "ExtEntityTypeParam:8:1"},
								{"field": "speedKMH", "extEntityTypeParamId": "ExtEntityTypeParam:8:2"},
								{"field": "length", "extEntityTypeParamId": "ExtEntityTypeParam:8:3"},
								{"field": "delay", "extEntityTypeParamId": "ExtEntityTypeParam:8:4"},
								{"field": "city", "extEntityTypeParamId": "ExtEntityTypeParam:8:5"},
								{"field": "country", "extEntityTypeParamId": "ExtEntityTypeParam:8:6"},
								{"field": "startNode", "extEntityTypeParamId": "ExtEntityTypeParam:8:7"},
								{"field": "endNode", "extEntityTypeParamId": "ExtEntityTypeParam:8:8"}
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
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);