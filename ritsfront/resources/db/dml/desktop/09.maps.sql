INSERT INTO ui.maps
(id, "name", description, "label", icon, base_map_url, exclusive_layers)
VALUES(1, 'Map_1', 'Map_1', 'LBL_MAP_1', 'map_1.png', 'http://localhost', false);

INSERT INTO ui.map_layers(id, map_id, "name", description, "label", icon, visible) VALUES
(1, 1, 'map_1_layer_1', 'map_1_layer_1', 'LBL_MAP_1_LAYER_1', 'map_1_layer_1.png', true),
(2, 1, 'map_1_layer_2', 'map_1_layer_2', 'LBL_MAP_1_LAYER_2', 'map_1_layer_2.png', true),
(3, 1, 'map_1_layer_3', 'map_1_layer_3', 'LBL_MAP_1_LAYER_3', 'map_1_layer_3.png', true);

INSERT INTO ui.map_layer_relationships(map_id, parent_layer_id, child_layer_id) VALUES
(1, 1, 2);

INSERT INTO ui.map_layer_elements(layer_id, element_id, custom_graphic_equipment_id, "label", width, height, horizontal_flip) VALUES
(1, 'Element:33:1', 1, '', 200, 100, false),
(2, 'Element:3:1', NULL, NULL, 200, 100, false),
(3, 'Element:4:1', NULL, NULL, 200, 100, false);
