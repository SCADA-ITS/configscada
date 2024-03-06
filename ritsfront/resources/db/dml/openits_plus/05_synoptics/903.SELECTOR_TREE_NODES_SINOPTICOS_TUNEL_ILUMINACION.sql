-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(903, 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_ILUMINACION', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_ILUMINACION', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_ILUMINACION', NULL, 'img/synoptics/Ausol_LQ_Ilumination.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90305600, 903, 'SYNOPTIC_LAYERS_UNIFILARES', 'LBL_SYNOPTIC_LAYERS_UNIFILARES', true),
(90300008, 903, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(90308400, 903, 'SYNOPTIC_LAYERS_CIRCUITOS', 'LBL_SYNOPTIC_LAYERS_CIRCUITOS', true),
(90308500, 903, 'SYNOPTIC_LAYERS_DALI', 'LBL_SYNOPTIC_LAYERS_DALI', true),
(90303100, 903, 'SYNOPTIC_LAYERS_FOTOCELULA', 'LBL_SYNOPTIC_LAYERS_FOTOCELULA', true),
(90301400, 903, 'SYNOPTIC_LAYERS_LUMINANCIMENTRO', 'LBL_SYNOPTIC_LAYERS_LUMINANCIMENTRO', true),
(90301200, 903, 'SYNOPTIC_LAYERS_LUXOMETRO', 'LBL_SYNOPTIC_LAYERS_LUXOMETRO', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(903, 90300008, 90308400),
(903, 90300008, 90308500),
(903, 90300008, 90303100),
(903, 90300008, 90301400),
(903, 90300008, 90301200);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(6, 903, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(7, 903, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(8, 903, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90305600, 'Element:56:1', 000056, 'TUN-CS-ITS1', 1054, 978, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:13', 000084, 'TUN-CONT-A31-SM', 1569, 798, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:26', 000084, 'TUN-CONT-A36-SM', 1331, 740, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:25', 000084, 'TUN-CONT-A35-SM', 1227, 740, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:5', 000084, 'TUN-CONT-AA11-SM', 1440, 620, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:24', 000084, 'TUN-CONT-A34-SM', 1331, 620, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:23', 000084, 'TUN-CONT-A33-SM', 1216, 620, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:12', 000084, 'TUN-CONT-A30-SC', 1568, 158, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:17', 000084, 'TUN-CONT-A4-SC', 1291, 346, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:18', 000084, 'TUN-CONT-A6-SC', 1146, 346, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:15', 000084, 'TUN-CONT-A3-SC', 1291, 225, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:16', 000084, 'TUN-CONT-A5-SC', 1146, 225, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:1', 000084, 'TUN-CONT-AA1-SC', 998, 225, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:3', 000084, 'TUN-CONT-AA2-SC', 998, 346, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:10', 000084, 'TUN-CONT-AE9-SC', 864, 346, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:9', 000084, 'TUN-CONT-AE14-SM', 868, 618, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:8', 000084, 'TUN-CONT-AA20-SM', 711, 740, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:6', 000084, 'TUN-CONT-AA21-SM', 711, 620, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:20', 000084, 'TUN-CONT-A23-SM', 593, 620, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:22', 000084, 'TUN-CONT-A22-SM', 593, 740, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:14', 000084, 'TUN-CONT-A32-SM', 264, 799, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:21', 000084, 'TUN-CONT-A12-SM', 460, 740, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:19', 000084, 'TUN-CONT-A13-SM', 460, 620, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:4', 000084, 'TUN-CONT-AA16-SC', 460, 346, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:2', 000084, 'TUN-CONT-AA15-SC', 461, 225, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:11', 000084, 'TUN-CONT-A29-SC', 265, 155, false, NULL, true, false, NULL, true),
(90308400, 'Element:84:7', 000084, 'TUN-CONT-AA10-SM', 1440, 740, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:10', 000085, 'TUN-DALI-DER-4MA', 1220, 831, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:8', 010085, 'TUN-DALI-DER-3MA', 858, 831, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:3', 010085, 'TUN-DALI-DER-2MA', 608, 831, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:1', 010085, 'TUN-DALI-DER-1MA', 358, 831, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:11', 000085, 'TUN-DALI-IZQ-4MA', 1220, 565, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:12', 020085, 'TUN-DALI-IZQ-1CA', 1234, 436, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:5', 010085, 'TUN-DALI-IZQ-3MA', 858, 566, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:4', 010085, 'TUN-DALI-IZQ-2MA', 608, 566, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:2', 010085, 'TUN-DALI-IZQ-1MA', 358, 566, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:7', 020085, 'TUN-DALI-IZQ-3CA', 654, 436, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:9', 020085, 'TUN-DALI-IZQ-2CA', 944, 436, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:6', 020085, 'TUN-DALI-IZQ-4CA', 364, 436, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:13', 020085, 'TUN-DALI-DER-1CA', 1234, 170, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:14', 020085, 'TUN-DALI-DER-2CA', 944, 170, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:15', 020085, 'TUN-DALI-DER-3CA', 654, 170, false, NULL, true, false, NULL, true),
(90308500, 'Element:85:16', 020085, 'TUN-DALI-DER-4CA', 364, 170, false, NULL, true, false, NULL, true),
(90303100, 'Element:31:1', 000031, 'TUN-FOT-1MA', 1192, 947, false, NULL, true, false, NULL, true),
(90301400, 'Element:14:1', 000014, 'TUN-LUM-1CA', 158, 478, false, NULL, true, false, NULL, true),
(90301400, 'Element:14:2', 000014, 'TUN-LUM-1MA', 1671, 478, false, NULL, true, false, NULL, true),
(90301200, 'Element:12:1', 000012, 'TUN-LUX-1CA', 1344, 107, false, NULL, true, false, NULL, true),
(90301200, 'Element:12:2', 000012, 'TUN-LUX-2CA', 1314, 107, false, NULL, true, false, NULL, true),
(90301200, 'Element:12:4', 000012, 'TUN-LUX-4CA', 1162, 107, false, NULL, true, false, NULL, true),
(90301200, 'Element:12:3', 000012, 'TUN-LUX-3CA', 1241, 107, false, NULL, true, false, NULL, true),
(90301200, 'Element:12:8', 000012, 'TUN-LUX-4MA', 849, 858, false, NULL, true, false, NULL, true),
(90301200, 'Element:12:7', 000012, 'TUN-LUX-3MA', 576, 856, false, NULL, true, false, NULL, true),
(90301200, 'Element:12:6', 000012, 'TUN-LUX-2MA', 546, 856, false, NULL, true, false, NULL, true),
(90301200, 'Element:12:5', 000012, 'TUN-LUX-1MA', 473, 856, false, NULL, true, false, NULL, true);

