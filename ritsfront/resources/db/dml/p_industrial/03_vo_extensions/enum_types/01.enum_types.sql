INSERT INTO ui.vo_enum_types(id, alias, description, enabled, visible) VALUES
(1, 'SiNo', 'SiNo', true, true);

INSERT INTO ui.vo_enum_type_values(id, vo_enum_type_id, "key", value, "translate", image) VALUES
(1, 1, 'true', 'Si', TRUE, null),
(2, 1, 'false', 'No', TRUE, null);

INSERT INTO ui.vo_entity_enum_types(vo_entity_id, enum_type_id) VALUES
('ElementTypeParam:3:2:7', 1),
('ElementTypeParam:3:2:8', 1),

('ElementTypeParam:4:2:7', 1),
('ElementTypeParam:4:2:8', 1);