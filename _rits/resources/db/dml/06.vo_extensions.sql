INSERT INTO ui.vo_entity_types(id, alias, description, enabled, visible) values
('ElementTypeUI', 'ElementTypeUI', 'ElementTypeUI', true, true),
('ElementUI', 'ElementUI', 'ElementUI', true, true),
('LocationUI', 'LocationUI', 'LocationUI', true, true);

INSERT INTO ui.vo_entity_fields (id, vo_entity_type_id, field, description, label_field, label_description, enabled, visible) VALUES
(1, 'ElementTypeUI', 'grid', 'grid', 'LBL_FIELD_GRID_ALIAS', 'LBL_FIELD_GRID_DESCRIPTION', true, true),
(2, 'ElementTypeUI', 'default_svg', 'default_svg', 'LBL_FIELD_DEFAULT_SVG_ALIAS', 'LBL_FIELD_DEFAULT_SVG_DESCRIPTION', true, true),
(3, 'ElementTypeUI', 'scale_x', 'scale_x', 'LBL_FIELD_SCALE_X_ALIAS', 'LBL_FIELD_SCALE_X_DESCRIPTION', true, true),
(4, 'ElementTypeUI', 'scale_y', 'scale_y', 'LBL_FIELD_SCALE_Y_ALIAS', 'LBL_FIELD_SCALE_Y_DESCRIPTION', true, true),
(5, 'LocationUI', 'lat', 'lattitude_x', 'LBL_LATTITUDE_ALIAS', 'LBL_LATTITUDE_DESCRIPTION', true, true),
(6, 'LocationUI', 'lon', 'longitude', 'LBL_LONGITUDE_ALIAS', 'LBL_LONGITUDE_DESCRIPTION', true, true),
(7, 'LocationUI', 'coordinates', 'array of coordinates (field calculated TODO)', 'LBL_COORDINATES_ARRAY_ALIAS', 'LBL_COORDINATES_ARRAY_DESCRIPTION', true, true);

INSERT INTO ui.vo_entities(id, vo_entity_type_id, enabled, visible) VALUES
('ElementType:3', 'ElementTypeUI', true, true),
('ElementType:4', 'ElementTypeUI', true, true),
('ElementType:7', 'ElementTypeUI', true, true),
('ElementType:8', 'ElementTypeUI', true, true),
('ElementType:9', 'ElementTypeUI', true, true),
('ElementType:10', 'ElementTypeUI', true, true),
('ElementType:33', 'ElementTypeUI', true, true),
('Location:1', 'LocationUI', true, true),
('Location:2', 'LocationUI', true, true),
('Location:3', 'LocationUI', true, true),
('Location:4', 'LocationUI', true, true);

INSERT INTO ui.vo_entity_values(vo_entity_id, vo_entity_field_id, value) VALUES
('ElementType:3', 1, '2'),
('ElementType:3', 2, 'element_type_3.svg'),
('ElementType:3', 3, '10'),
('ElementType:3', 4, '10'),
('ElementType:33', 1, '3'),
('ElementType:33', 2, 'element_type_33.svg'),
('ElementType:33', 3, '10'),
('ElementType:33', 4, '10'),
('Location:1', 5, '40.50192872888957'),
('Location:1', 6, '-3.5075150487690823'),
('Location:2', 5, '40.50601573088965'),
('Location:2', 6, '-3.5115705015736878'),
('Location:3', 5, '40.49601573088965'),
('Location:3', 6, '-3.5015705015736878');

INSERT INTO ui.vo_entity_hierarchies(parent_id, child_id, "position") VALUES
('Location:4', 'Location:2', 1),
('Location:4', 'Location:3', 2);
