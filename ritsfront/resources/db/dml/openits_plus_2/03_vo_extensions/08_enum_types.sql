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
(12, 'ENABLED', 'ENABLED', true, true),
(13, 'Si/No', 'Si/No', true, true),
(14, 'Subida/Bajada', 'Subida/Bajada', true, true),
(15, 'SemaforosRAV', 'SemaforosRAV', true, true),
(16, 'Encendido/Apagado', 'Encendido/Apagado', true, true),
(17, 'Apagado/Encendido', 'Apagado/Encendido', true, true);


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

(13, 7, 'false', 'LBL_ETV_CERRADO', TRUE, null),
(14, 7, 'true', 'LBL_ETV_ABIERTO', TRUE, null),

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
(25, 12, 'false', 'LBL_ETV_DISABLE_TRAVEL_TIME', TRUE, null),

(26, 13, 'true', 'LBL_ETV_SI', TRUE, null),
(27, 13, 'false', 'LBL_ETV_NO', TRUE, null),

(28, 14, '1', 'LBL_ETV_SUBIDA', TRUE, null),
(29, 14, '2', 'LBL_ETV_BAJADA', TRUE, null),

(30, 15, '0', 'LBL_ETV_FLASHING_YELLOW', TRUE, null),
(31, 15, '1', 'LBL_ETV_RED', TRUE, null),
(32, 15, '2', 'LBL_ETV_YELLOW', TRUE, null),
(33, 15, '4', 'LBL_ETV_GREEN', TRUE, null),
(34, 15, '7', 'LBL_ETV_OFF', TRUE, null),

(35, 16, '0', 'LBL_ETV_APAGADO', TRUE, null),
(36, 16, '1', 'LBL_ETV_ENCENDIDO', TRUE, null);

INSERT INTO ui.vo_entity_enum_types(vo_entity_id, enum_type_id) VALUES
('ElementTypeParam:8:2:1', 14),

('ElementTypeParam:13:2:1', 15),

('ElementTypeParam:22:2:1', 10),

('ElementTypeParam:53:2:1', 16),

('ElementTypeParam:57:2:1', 7),

('ElementTypeParam:65:2:1', 13),
('ElementTypeParam:65:2:2', 13),

('ElementTypeParam:71:2:10', 12),

('ElementTypeParam:74:2:1', 13),

('ElementTypeParam:84:2:1', 5),

('ElementTypeParam:86:2:1', 13);

