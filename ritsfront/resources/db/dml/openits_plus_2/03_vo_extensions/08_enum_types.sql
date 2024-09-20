INSERT INTO ui.vo_enum_types(id, alias, description, enabled, visible) VALUES
(2, 'LocalRemoto', 'LocalRemoto', true, true),
(3, 'ParadoActivado', 'ParadoActivado', true, true),
(4, 'ParadaArrancada', 'ParadaArrancada', true, true),
(5, 'SubidoBajado', 'SubidoBajado', true, true),
(6, 'ControlYesNo', 'ControlYesNo', true, true),
(7, 'BajadoSubido', 'BajadoSubido', true, true),
(8, 'DerivationYesNo', 'DerivationYesNo', true, true),
(9, 'FailYesNo', 'FailYesNo', true, true),
(10, 'CerradoAbierto', 'CerradoAbierto', true, true),
(11, 'PrecipitationTypes', 'PrecipitationTypes', true, true),
(12, 'ENABLED', 'ENABLED', true, true);


INSERT INTO ui.vo_enum_type_values(id, vo_enum_type_id, "key", value, "translate", image) VALUES
(3, 2, 'false', 'LBL_ETV_LOCAL', TRUE, null),
(4, 2, 'true', 'LBL_ETV_REMOTO', TRUE, null),

(5, 3, 'false', 'LBL_ETV_PARADO', TRUE, null),
(6, 3, 'true', 'LBL_ETV_ARRANCADO', TRUE, null),

(7, 4, 'false', 'LBL_ETV_PARADA', TRUE, null),
(8, 4, 'true', 'LBL_ETV_ARRANCADA', TRUE, null),

(9, 5, 'false', 'LBL_ETV_BAJADO', TRUE, null),
(10, 5, 'true', 'LBL_ETV_SUBIDO', TRUE, null),

(11, 6, 'true', 'LBL_ETV_WITH_CONTROL', TRUE, null),
(12, 6, 'false', 'LBL_ETV_WITHOUT_CONTROL', TRUE, null),

(13, 7, 'true', 'LBL_ETV_SUBIDO', TRUE, null),
(14, 7, 'false', 'LBL_ETV_BAJADO', TRUE, null),

(15, 8, 'false', 'LBL_ETV_WITHOUT_DERIVATION', TRUE, null),
(16, 8, 'true', 'LBL_ETV_WITH_DERIVATION', TRUE, null),

(17, 9, 'false', 'LBL_ETV_WITHOUT_FAIL', TRUE, null),
(18, 9, 'true', 'LBL_ETV_WITH_FAIL', TRUE, null),

(19, 10, 'false', 'LBL_ETV_CERRADA', TRUE, null),
(20, 10, 'true', 'LBL_ETV_ABIERTA', TRUE, null),

(21, 11, '0', 'LBL_WITHOUT_PRECIPITATION', TRUE, null),
(22, 11, '60', 'LBL_LIQUID_PRECIPITATION', TRUE, null),
(23, 11, '70', 'LBL_SOLID_PRECIPITATION', TRUE, null),

(24, 12, 'true', 'LBL_ETV_ENABLE_TRAVEL_TIME', TRUE, null),
(25, 12, 'false', 'LBL_ETV_DISABLE_TRAVEL_TIME', TRUE, null);



INSERT INTO ui.vo_entity_enum_types(vo_entity_id, enum_type_id) VALUES
('ElementTypeParam:84:2:1', 5);

