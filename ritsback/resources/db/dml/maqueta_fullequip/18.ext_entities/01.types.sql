INSERT INTO static.ext_entity_types(ext_entity_type_id, alias, description, label_alias, label_description, archive_historical, cron_expression, config_params, enabled, visible, created_at, updated_at)
VALUES 
(1, 'C4', 'Incidencias generadas en sistema C4', 'LBL_EXT_ENTITY_TYPE_C4', 'LBL_EXT_ENTITY_TYPE_C4_DESC', true, '0 0/1 * * * ? *', '{
  "jdbc": {
    "url": "jdbc:postgresql://192.168.88.71:5430/rits",
    "username": "rits",
    "password": "rits"
  },
  "selectQuery": "SELECT id_accidente, referencia, fecha_ocurrencia, hora_ocurrencia, tipo_accidente, gravedad, causa_probable, condiciones_meteorologicas, numero_vehiculos, numero_personas, numero_heridos_leves, numero_heridos_graves, numero_fallecidos, codigo_via, pk, hm, sentido, utm_y, utm_x, observaciones, estado, fecha_cierre, hora_cierre FROM ext_entities.accidentes;",
  "extEntityParamsMapping": [ {"field": "referencia", "extEntityTypeParamId": "ExtEntityTypeParam:1:1"},
                {"field": "fecha_ocurrencia", "extEntityTypeParamId": "ExtEntityTypeParam:1:2"},
                {"field": "hora_ocurrencia", "extEntityTypeParamId": "ExtEntityTypeParam:1:3"},
                {"field": "gravedad", "extEntityTypeParamId": "ExtEntityTypeParam:1:4"},
                {"field": "causa_probable", "extEntityTypeParamId": "ExtEntityTypeParam:1:5"},
                {"field": "condiciones_meteorologicas", "extEntityTypeParamId": "ExtEntityTypeParam:1:6"},
                {"field": "numero_vehiculos", "extEntityTypeParamId": "ExtEntityTypeParam:1:7"},
                {"field": "numero_personas", "extEntityTypeParamId": "ExtEntityTypeParam:1:8"},
                {"field": "numero_heridos_leves", "extEntityTypeParamId": "ExtEntityTypeParam:1:9"},
                {"field": "pk", "extEntityTypeParamId": "ExtEntityTypeParam:1:10"},
                {"field": "numero_fallecidos", "extEntityTypeParamId": "ExtEntityTypeParam:1:11"},
                {"field": "codigo_via", "extEntityTypeParamId": "ExtEntityTypeParam:1:12"},
                {"field": "sentido", "extEntityTypeParamId": "ExtEntityTypeParam:1:13"},
                {"field": "observaciones", "extEntityTypeParamId": "ExtEntityTypeParam:1:14"},
                {"field": "estado", "extEntityTypeParamId": "ExtEntityTypeParam:1:15"},
                {"field": "fecha_cierre", "extEntityTypeParamId": "ExtEntityTypeParam:1:16"},
                {"field": "hora_cierre", "extEntityTypeParamId": "ExtEntityTypeParam:1:17"},
                {"field": "numero_heridos_graves", "extEntityTypeParamId": "ExtEntityTypeParam:1:18"}
              ],
  "extEntityFieldsMapping": [ {"srcField": "id_accidente", "dstField": "uid"},
                {"srcField": "referencia", "dstField": "alias"},
                {"srcField": "tipo_accidente", "dstField": "description"}
              ],
  "extEntitySubtypes": {
    "field": "tipo_accidente",
    "mapping": [ {"extEntitySubtypeId": "ExtEntitySubtype:1001", "value": "Colisión frontal"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1002", "value": "Colisión múltiple"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1003", "value": "Atropello"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1004", "value": "Salida de vía"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1005", "value": "Alcance trasero"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1006", "value": "Vuelco lateral"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1007", "value": "Incendio de vehículo"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1008", "value": "Colisión con animal"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1009", "value": "Caída de motocicleta"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1010", "value": "Colisión con obstáculo fijo"},
           {"extEntitySubtypeId": "ExtEntitySubtype:1011", "value": "Colisión lateral"}
    ]
  }
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);