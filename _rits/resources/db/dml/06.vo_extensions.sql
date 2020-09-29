INSERT INTO ui.vo_entity_types(id, alias, description, enabled, visible) values
('ElementTypeUI', 'ElementTypeUI', 'ElementTypeUI', true, true),
('ElementUI', 'ElementUI', 'ElementUI', true, true);

INSERT INTO ui.vo_entity_fields (id, vo_entity_type_id, field, description, label_field, label_description, enabled, visible) VALUES
(1, 'ElementTypeUI', 'grid', 'grid', 'LBL_FIELD_GRID_ALIAS', 'LBL_FIELD_GRID_DESCRIPTION', true, true);

INSERT INTO ui.vo_entities(id, vo_entity_type_id, enabled, visible) VALUES
('ElementType:3', 'ElementTypeUI', true, true),
('ElementType:33', 'ElementTypeUI', true, true);

INSERT INTO ui.vo_entity_values(id, vo_entity_id, vo_entity_field_id, value) VALUES
(1, 'ElementType:3', 1, '2'),
(2, 'ElementType:33', 1, '3');
