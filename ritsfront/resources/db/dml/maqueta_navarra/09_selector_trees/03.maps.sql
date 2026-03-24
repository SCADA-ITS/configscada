INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(03, 'Mapas', 200, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, position, enabled, visible) VALUES
(0301, 03, 'Mapa completo', 'tree_map.png', 200, 1,true, true),
(0302, 03, 'Equipos', 'tree_equipment.png', 201, 2, true, true),
(0303, 03, 'Incidencias externas', 'ext_entity_type/ext_entity_112.png', 202, 2, true, true),
(0304, 03, 'Incidencias internas', 'incident_off.png', 203, 2, true, true),
(0305, 03, 'V16', 'ext_entity_type/baliza.png', 204, 2, true, true);
