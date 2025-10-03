INSERT INTO ui.vo_enum_types(id, alias, description, enabled, visible) VALUES
(1, 'ColorSemaforo', 'ColorSemaforo', true, true),
(2, 'YesNo', 'YesNo', true, true),
(3, 'FullHalf', 'FullHalf', true, true),
(4, 'OpenedClosed', 'OpenedClosed', true, true),
(5, 'YesNo_presurization', 'YesNo_presurization', true, true),
(6, 'PASystemState', 'PASystemState', true, true),
(7, 'OpenClose_presurization', 'OpenClose_presurization', true, true),
(8, 'FanDirection', 'FanDirection', true, true),
(9, 'Normal', 'Racheado', true, true),
(10, 'tipo precipitacion', 'tipo precipitacion', true, true),
(11, 'Apagado_Encendido', 'Apagado_Encendido', true, true),
(12, 'Remoto_Local', 'Remoto_Local', true, true),
(13, 'Emergencia_general', 'Emergencia_general', true, true),
(14, 'ClosedOpened', 'ClosedOpened', true, true);








INSERT INTO ui.vo_enum_type_values(id, vo_enum_type_id, "key", value, "translate", image) VALUES
(1, 1, '7', 'LBL_VO_ENUM_TYPE_VALUES_APAGADO', TRUE, null),
(2, 1, '1', 'LBL_VO_ENUM_TYPE_VALUES_ROJO', TRUE, null),
(3, 1, '0', 'LBL_VO_ENUM_TYPE_VALUES_AMBAR', TRUE, null),
(4, 1, '4', 'LBL_VO_ENUM_TYPE_VALUES_VERDE', TRUE, null),

(5, 2, 'true', 'LBL_VO_ENUM_TYPE_VALUES_YES', TRUE, null),
(6, 2, 'false', 'LBL_VO_ENUM_TYPE_VALUES_NO', TRUE, null),

(7, 3, 'true', 'LBL_VO_ENUM_TYPE_VALUES_FULL', TRUE, null),
(8, 3, 'false', 'LBL_VO_ENUM_TYPE_VALUES_HALF', TRUE, null),

(9, 4, 'true', 'LBL_VO_ENUM_TYPE_VALUES_CLOSED', TRUE, null),
(10, 4, 'false', 'LBL_VO_ENUM_TYPE_VALUES_OPENED', TRUE, null),

(11, 5, 'true', 'LBL_VO_ENUM_TYPE_VALUES_YES_PRESURIZATION', TRUE, null),
(12, 5, 'false', 'LBL_VO_ENUM_TYPE_VALUES_NO_PRESURIZATION', TRUE, null),

(13, 6, '0', 'LBL_VO_ENUM_TYPE_VALUES_MEG_DISABLED_SOS', TRUE, null),
(14, 6, '255', 'LBL_VO_ENUM_TYPE_VALUES_MEG_ENABLED_SOS', TRUE, null),

(15, 7, 'false', 'LBL_VO_ENUM_TYPE_VALUES_CLOSED', TRUE, null),
(16, 7, 'true', 'LBL_VO_ENUM_TYPE_VALUES_OPENED', TRUE, null),

(17, 8, '0', 'LBL_VO_ENUM_TYPE_VALUES_PARADO', TRUE, null),
(18, 8, '1', 'LBL_VO_ENUM_TYPE_VALUES_DIRECTA', TRUE, null),
(19, 8, '2', 'LBL_VO_ENUM_TYPE_VALUES_INVERSA', TRUE, null),

(20, 9, '1', 'LBL_VO_ENUM_TYPE_VALUES_NORMAL', TRUE, null),
(21, 9, '2', 'LBL_VO_ENUM_TYPE_VALUES_RACHEADO', TRUE, null),

(22, 10, '0', 'LBL_VO_ENUM_TYPE_VALUES_NINGUNA', TRUE, null),
(23, 10, '1', 'LBL_VO_ENUM_TYPE_VALUES_LLUVIA', TRUE, null),
(24, 10, '2', 'LBL_VO_ENUM_TYPE_VALUES_LLUVIA_NIEVE', TRUE, null),
(25, 10, '3', 'LBL_VO_ENUM_TYPE_VALUES_NIEVE', TRUE, null),
(26, 10, '4', 'LBL_VO_ENUM_TYPE_VALUES_GRANIZO', TRUE, null),

(27, 11, 'false', 'LBL_VO_ENUM_TYPE_VALUES_ENCENDIDO', TRUE, null),
(28, 11, 'true', 'LBL_VO_ENUM_TYPE_VALUES_APAGADO', TRUE, null),

(29, 12, 'false', 'LBL_VO_ENUM_TYPE_VALUES_LOCAL', TRUE, null),
(30, 12, 'true', 'LBL_VO_ENUM_TYPE_VALUES_REMOTE', TRUE, null),

(31, 13, '1', 'LBL_VO_ENUM_TYPE_VALUES_EMERGENCY', TRUE, null),
(32, 13, '6', 'LBL_VO_ENUM_TYPE_VALUES_GENERAL', TRUE, null),

(33, 14, 'false', 'LBL_VO_ENUM_TYPE_VALUES_CLOSED', TRUE, null),
(34, 14, 'true', 'LBL_VO_ENUM_TYPE_VALUES_OPENED', TRUE, null);








INSERT INTO ui.vo_entity_enum_types(vo_entity_id, enum_type_id) VALUES
('ElementTypeParam:1:2:28', 9),
('ElementTypeParam:1:2:19', 10),

('ElementTypeParam:9:2:1', 8),
('ElementTypeParam:9:2:28', 2),
('ElementTypeParam:9:2:29', 2),

('ElementTypeParam:11:2:15', 6),
('ElementTypeParam:13:2:1', 1),

('ElementTypeParam:32:2:8', 5),
('ElementTypeParam:32:2:9', 5),

('ElementTypeParam:32:2:10', 7),

('ElementTypeParam:53:2:1', 1),

('ElementTypeParam:57:2:1', 4),
('ElementTypeParam:57:2:2', 14),

('ElementTypeParam:73:2:2', 11),
('ElementTypeParam:73:2:3', 12),


('ElementTypeParam:84:2:1', 4),
('ElementTypeParam:84:2:3', 13),

('ElementTypeParam:87:2:4', 3);







