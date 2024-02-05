INSERT INTO ui.vo_enum_types(id, alias, description, enabled, visible) VALUES
(1, 'ColorSemaforo', 'ColorSemaforo', true, true),
(2, 'YesNo', 'YesNo', true, true);



INSERT INTO ui.vo_enum_type_values(id, vo_enum_type_id, "key", value, "translate", image) VALUES
(1, 1, '7', 'LBL_VO_ENUM_TYPE_VALUES_APAGADO', TRUE, null),
(2, 1, '1', 'LBL_VO_ENUM_TYPE_VALUES_ROJO', TRUE, null),
(3, 1, '2', 'LBL_VO_ENUM_TYPE_VALUES_AMBAR', TRUE, null),
(4, 1, '4', 'LBL_VO_ENUM_TYPE_VALUES_VERDE', TRUE, null),

(5, 2, 'true', 'LBL_VO_ENUM_TYPE_VALUES_YES', TRUE, null),
(6, 2, 'false', 'LBL_VO_ENUM_TYPE_VALUES_NO', TRUE, null);

INSERT INTO ui.vo_entity_enum_types(vo_entity_id, enum_type_id) VALUES
('ElementTypeParam:13:2:1', 1),

('ElementTypeParam:9:2:28', 2),
('ElementTypeParam:9:2:29', 2);

