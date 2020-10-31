INSERT INTO ui.synoptics(id, "name", description, "label", icon, base_layer, exclusive_layers) VALUES
(1, 'Synoptic_1', 'Synoptic_1', 'LBL_SYNOPTIC_1', 'synoptic_1.png', 'synoptic_1.svg', false);

INSERT INTO ui.synoptic_layers(id, synoptic_id, "name", description, "label", icon, visible) VALUES
(1, 1, 'synoptic_1_layer_1', 'synoptic_1_layer_1', 'LBL_SYNOPTIC_1_LAYER_1', 'synoptic_1_layer_1.png', true),
(2, 1, 'synoptic_1_layer_2', 'synoptic_1_layer_2', 'LBL_SYNOPTIC_1_LAYER_2', 'synoptic_1_layer_2.png', true),
(3, 1, 'synoptic_1_layer_3', 'synoptic_1_layer_3', 'LBL_SYNOPTIC_1_LAYER_3', 'synoptic_1_layer_3.png', true);

INSERT INTO ui.synoptic_layer_relationships(synoptic_id, parent_layer_id, child_layer_id) VALUES
(1, 1, 2);

INSERT INTO ui.synoptic_layer_elements(layer_id, element_id, custom_graphic_equipment_id, "label", x, y, width, height, horizontal_flip) values
(1, 'Element:33:1', 1, 'Element_33_1', 1, 2, 100, 200, false),
(2, 'Element:3:1', NULL, 'Element_3_1', 1, 2, 100, 200, false);
