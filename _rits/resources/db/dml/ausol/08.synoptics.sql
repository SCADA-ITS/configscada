/*INSERT INTO ui.synoptics(id, "name", description, "label", icon, base_layer, exclusive_layers) VALUES
(1, 'Synoptic_Lineal', 'Synoptic_Lineal', 'LBL_SYNOPTIC_LINEAL', 'synoptic_1.png', 'metro.svg', false),
(2, 'Synoptic_Global', 'Synoptic_Global', 'LBL_SYNOPTIC_GLOBAL', 'synoptic_1.png', 'global_v0.1.svg', false),
(3, 'Synoptic_CSA', 'Synoptic_CSA', 'LBL_UNIFILAR_CSA', 'synoptic_1.png', 'UNIFlLAR-RIO-VERDE-CABLE-CSA_v3.svg', false),
(4, 'Synoptic_CSB', 'Synoptic_CSB', 'LBL_UNIFILAR_CSB', 'synoptic_1.png', 'UNIFlLAR RIO VERDE CABLE - CSB_v3_1.svg', false),
(5, 'Synoptic_CSV', 'Synoptic_CSV', 'LBL_UNIFILAR_CSV', 'synoptic_1.png', 'UNIFlLAR RIO VERDE CABLE - CSV - VENTILACION_v2.svg', false),
(6, 'Synoptic_CGBT', 'Synoptic_CGBT', 'LBL_UNIFILAR_CGBT', 'synoptic_1.png', 'UNIFlLAR RIO VERDE CABLE - CGBT_v3_1.svg', false);



INSERT INTO ui.synoptic_layers(id, synoptic_id, "name", description, "label", icon, visible) VALUES
(1, 1, 'synoptic_lineal_layer_1', 'synoptic_lineal_layer_1', 'LBL_SYNOPTIC_LINEAL_LAYER_1', 'synoptic_1_layer_1.png', true),
(2, 2, 'synoptic_global_layer_1', 'synoptic_global_layer_1', 'LBL_SYNOPTIC_GLOBAL_LAYER_1', 'synoptic_1_layer_1.png', true),
(3, 2, 'synoptic_global_layer_2', 'synoptic_global_layer_2', 'LBL_SYNOPTIC_GLOBAL_LAYER_2', 'synoptic_1_layer_1.png', true),
(4, 2, 'synoptic_global_layer_3', 'synoptic_global_layer_3', 'LBL_SYNOPTIC_GLOBAL_LAYER_3', 'synoptic_1_layer_1.png', true),
(5, 2, 'synoptic_global_layer_4', 'synoptic_global_layer_4', 'LBL_SYNOPTIC_GLOBAL_LAYER_4', 'synoptic_1_layer_1.png', true),
(6, 2, 'synoptic_global_layer_5', 'synoptic_global_layer_5', 'LBL_SYNOPTIC_GLOBAL_LAYER_5', 'synoptic_1_layer_1.png', true),
(7, 2, 'synoptic_global_layer_6', 'synoptic_global_layer_6', 'LBL_SYNOPTIC_GLOBAL_LAYER_6', 'synoptic_1_layer_1.png', true);



INSERT INTO ui.synoptic_layer_relationships(synoptic_id, parent_layer_id, child_layer_id) VALUES
(1, 1, 1);

INSERT INTO ui.synoptic_layer_elements(layer_id, element_id, custom_graphic_equipment_id, "label", x, y, width, height, horizontal_flip) values
(1, 'Element:7:2', 1, 'Element_7_2', 1, 2, 1920, 1080, false);*/
