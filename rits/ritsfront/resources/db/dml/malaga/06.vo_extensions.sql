
INSERT INTO ui.vo_entities(id, vo_entity_type_id, enabled, visible) VALUES
('ElementType:36', 'ElementTypeUI', true, true),
('Element:36:1', 'ElementUI', true, true),
('Element:36:2', 'ElementUI', true, true),
('ElementTypeParam:36:2:1', 'ElementTypeParamUI', true, true),

('ElementType:37', 'ElementTypeUI', true, true),
('ElementTypeParam:37:2:1', 'ElementTypeParamUI', true, true),

('ElementType:38', 'ElementTypeUI', true, true),
('CommandElementType:1:38', 'CommandElementTypeUI', true, true),
('CommandElementType:2:38', 'CommandElementTypeUI', true, true),
('CommandElementType:3:38', 'CommandElementTypeUI', true, true),
('CommandElementType:4:38', 'CommandElementTypeUI', true, true),
('CommandElementType:5:38', 'CommandElementTypeUI', true, true),

('ElementType:71', 'ElementTypeUI', true, true),
('ElementSubtype:12:71', 'ElementSubtypeUI', true, true),
('ElementSubtype:22:71', 'ElementSubtypeUI', true, true),
('Element:71:1', 'ElementUI', true, true),
('Element:71:2', 'ElementUI', true, true),
('Element:71:3', 'ElementUI', true, true),
('Element:71:4', 'ElementUI', true, true),
('Element:71:5', 'ElementUI', true, true),
('Element:71:6', 'ElementUI', true, true),
('Element:71:7', 'ElementUI', true, true),
('Element:71:8', 'ElementUI', true, true),
('Element:71:9', 'ElementUI', true, true),
('Element:71:10', 'ElementUI', true, true),
('Element:71:11', 'ElementUI', true, true),
('Element:71:12', 'ElementUI', true, true),
('Element:71:13', 'ElementUI', true, true),
('ElementTypeParam:71:1:3', 'ElementTypeParamUI', true, true),
('ElementTypeParam:71:2:4', 'ElementTypeParamUI', true, true),


('CommandElementType:2:71', 'CommandElementTypeUI', true, true),
('CommandElementType:3:71', 'CommandElementTypeUI', true, true);


INSERT INTO ui.vo_entity_values(vo_entity_id, vo_entity_field_id, value) VALUES
('ElementType:36', 1, '36'),
('ElementType:36', 2, 'graphic_icon/36_baliza_amarilla.svg'),
('ElementType:36', 14, 'grid/element_type_36.png'),

('ElementType:37', 1, '37'),
('ElementType:37', 2, 'graphic_icon/37_ipl.svg'),
('ElementType:37', 14, 'grid/element_type_37.png'),

('ElementType:38', 1, '38'),
('ElementType:38', 2, 'graphic_icon/38_bus_vao.svg'),
('ElementType:38', 14, 'grid/element_type_38.png'),
('CommandElementType:1:38', 53, 'icons/baliza_off.png'),
('CommandElementType:2:38', 53, 'icons/baliza_on_100.png'),
('CommandElementType:3:38', 53, 'icons/baliza_on_70.png'),
('CommandElementType:4:38', 53, 'icons/baliza_on_100f.png'),
('CommandElementType:5:38', 53, 'icons/baliza_on_70f.png'),


('CommandElementType:2:71', 39, 'true'),
('CommandElementType:3:71', 39, 'true'),
('ElementType:71', 1, '71'),
('ElementType:71', 2, 'graphic_icon/71_vms_dgt.svg'),
('ElementType:71', 14, 'grid/element_type_71.png'),
('ElementType:71', 23, '102601'),
('ElementType:71', 30, '102602'),

('ImsIncidentLevel:1', 36, 'true'),
('ImsIncidentLevel:1', 37, 'incident.mp3'),
('ImsIncidentLevel:2', 36, 'true'),
('ImsIncidentLevel:2', 37, 'incident.mp3'),
('ImsIncidentLevel:3', 36, 'true'),
('ImsIncidentLevel:3', 37, 'incident.mp3'),
('ImsIncidentLevel:4', 36, 'true'),
('ImsIncidentLevel:4', 37, 'incident.mp3'),

('AlarmLevel:5', 8, 'true'),
('AlarmLevel:5', 9, 'alarm.mp3');

INSERT INTO ui.vo_enum_types(id, alias, description, enabled, visible) VALUES
(1, 'BalizaState', 'BalizaState', true, true);

INSERT INTO ui.vo_enum_type_values(id, vo_enum_type_id, "key", value, "translate", image) VALUES
(1, 1, '1', 'LBL_ENUM_TYPE_CERRADO', TRUE, null),
(2, 1, '2', 'LBL_ENUM_TYPE_OPEN_100', TRUE, null),
(3, 1, '3', 'LBL_ENUM_TYPE_OPEN_70', TRUE, null),
(4, 1, '4', 'LBL_ENUM_TYPE_OPEN_100_FLASHING', TRUE, null),
(5, 1, '5', 'LBL_ENUM_TYPE_OPEN_70_FLASHING', TRUE, null);

INSERT INTO ui.vo_entity_enum_types(vo_entity_id, enum_type_id) VALUES
('ElementTypeParam:36:2:1', 1);