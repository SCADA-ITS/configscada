INSERT INTO static.ext_entity_types(ext_entity_type_id, alias, description, label_alias, label_description, cron_expression, config_params, enabled, visible, created_at, updated_at) VALUES
(1, 'C4 - Obras', 'Incidencias generadas en sistema C4 - Obras', 'LBL_EXT_ENTITY_TYPE_C4_OBRAS', 'LBL_EXT_ENTITY_TYPE_C4_OBRAS_DESC', '0 0/5 * * * ? *', 
'{
	"jdbc": {
		"url": "jdbc:sqlserver://;serverName=192.168.88.159;databaseName=IncCarreteras",
		"username": "SA",
		"password": "Navarra0"
	},
	"selectQuery": "SELECT fecha_actualizacion, nombre_carretera, pk, hm, tipo, afeccion, fecha, titulo, id_incidencia, fecha_modificacion, id_carretera, utm_x, utm_y, id_tipo, id_afeccion, color FROM IncCarreteras.dbo.BaseIVR where id_categoria  = 1;"
	"extEntityParamsMapping": [ {"field": "id_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:1:1"},
								{"field": "nombre_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:1:2"},
								{"field": "pk", "extEntityTypeParamId": "ExtEntityTypeParam:1:3"},
								{"field": "hm", "extEntityTypeParamId": "ExtEntityTypeParam:1:4"},
								{"field": "id_afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:1:5"},
								{"field": "afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:1:6"},
								{"field": "fecha_actualizacion", "extEntityTypeParamId": "ExtEntityTypeParam:1:7"},
								{"field": "color", "extEntityTypeParamId": "ExtEntityTypeParam:1:8"},
								{"field": "fecha", "extEntityTypeParamId": "ExtEntityTypeParam:1:9"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id_incidencia", "dstField": "uid"},
								{"srcField": "tipo", "dstField": "alias"},
								{"srcField": "titulo", "dstField": "descripcion"},    
								{"srcField": "id_tipo", "dstField": "extEntitySubtypeId"}  
							] 
}
',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'C4 - Meteorológicas', 'Incidencias generadas en sistema C4 - Meteorológicas', 'LBL_EXT_ENTITY_TYPE_C4_WEATHER', 'LBL_EXT_ENTITY_TYPE_C4_WEATHER_DESC', '20 0/5 * * * ? *', 
'{
	"jdbc": {
		"url": "jdbc:sqlserver://;serverName=192.168.88.159;databaseName=IncCarreteras",
		"username": "SA",
		"password": "Navarra0"
	},
	"selectQuery": "SELECT fecha_actualizacion, nombre_carretera, pk, hm, tipo, afeccion, fecha, titulo, id_incidencia, fecha_modificacion, id_carretera, utm_x, utm_y, id_tipo, id_afeccion, color FROM IncCarreteras.dbo.BaseIVR where id_categoria  = 2;"
	"extEntityParamsMapping": [ {"field": "id_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:2:1"},
								{"field": "nombre_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:2:2"},
								{"field": "pk", "extEntityTypeParamId": "ExtEntityTypeParam:2:3"},
								{"field": "hm", "extEntityTypeParamId": "ExtEntityTypeParam:2:4"},
								{"field": "id_afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:2:5"},
								{"field": "afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:2:6"},
								{"field": "fecha_actualizacion", "extEntityTypeParamId": "ExtEntityTypeParam:2:7"},
								{"field": "color", "extEntityTypeParamId": "ExtEntityTypeParam:2:8"},
								{"field": "fecha", "extEntityTypeParamId": "ExtEntityTypeParam:2:9"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id_incidencia", "dstField": "uid"},
								{"srcField": "tipo", "dstField": "alias"},
								{"srcField": "titulo", "dstField": "descripcion"},    
								{"srcField": "id_tipo", "dstField": "extEntitySubtypeId"}  
							] 
}
',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'C4 - Red viaria', 'Incidencias generadas en sistema C4 - Red viaria', 'LBL_EXT_ENTITY_TYPE_C4_ROAD', 'LBL_EXT_ENTITY_TYPE_C4_ROAD_DESC', '40 0/5 * * * ? *', 
'{
	"jdbc": {
		"url": "jdbc:sqlserver://;serverName=192.168.88.159;databaseName=IncCarreteras",
		"username": "SA",
		"password": "Navarra0"
	},
	"selectQuery": "SELECT fecha_actualizacion, nombre_carretera, pk, hm, tipo, afeccion, fecha, titulo, id_incidencia, fecha_modificacion, id_carretera, utm_x, utm_y, id_tipo, id_afeccion, color FROM IncCarreteras.dbo.BaseIVR where id_categoria  = 3;"
	"extEntityParamsMapping": [ {"field": "id_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:3:1"},
								{"field": "nombre_carretera", "extEntityTypeParamId": "ExtEntityTypeParam:3:2"},
								{"field": "pk", "extEntityTypeParamId": "ExtEntityTypeParam:3:3"},
								{"field": "hm", "extEntityTypeParamId": "ExtEntityTypeParam:3:4"},
								{"field": "id_afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:3:5"},
								{"field": "afeccion", "extEntityTypeParamId": "ExtEntityTypeParam:3:6"},
								{"field": "fecha_actualizacion", "extEntityTypeParamId": "ExtEntityTypeParam:3:7"},
								{"field": "color", "extEntityTypeParamId": "ExtEntityTypeParam:3:8"},
								{"field": "fecha", "extEntityTypeParamId": "ExtEntityTypeParam:3:9"}
							],
	"extEntityFieldsMapping": [ {"srcField": "id_incidencia", "dstField": "uid"},
								{"srcField": "tipo", "dstField": "alias"},
								{"srcField": "titulo", "dstField": "descripcion"},    
								{"srcField": "id_tipo", "dstField": "extEntitySubtypeId"}  
							] 
}
',
true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
