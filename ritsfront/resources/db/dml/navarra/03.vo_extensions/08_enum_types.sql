INSERT INTO ui.vo_enum_types(id, alias, description, enabled, visible) VALUES
(2, 'NieveLluvia', 'NieveLluvia', true, true);


INSERT INTO ui.vo_enum_type_values(id, vo_enum_type_id, "key", value, "translate", image) VALUES
(2, 2, '0', 'Sin Precipitaciones', TRUE, null),
(3, 2, '1', 'Lluvia', TRUE, null),
(4, 2, '2', 'Nieve', TRUE, null);


INSERT INTO ui.vo_entity_enum_types(vo_entity_id, enum_type_id) VALUES
('ElementTypeParam:1:2:19', 2);
