-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(910, 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSA', 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSA', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_UF_CSA', NULL, 'img/synoptics/TUN_UF_CSA.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91000015, 910, 'SYNOPTIC_LAYERS_ENERGIA', 'LBL_SYNOPTIC_LAYERS_ENERGIA', true),
(91005700, 910, 'SYNOPTIC_LAYERS_INTERRUPTORES', 'LBL_SYNOPTIC_LAYERS_INTERRUPTORES', true),
(91008400, 910, 'SYNOPTIC_LAYERS_CONTACTORES', 'LBL_SYNOPTIC_LAYERS_CONTACTORES', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(910, 91000015, 91005700),
(910, 91000015, 91008400);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(27, 910, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(28, 910, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(29, 910, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91005700, 'Element:57:17', 000057, 'TUN-MAG-CSA-RED', 945, 78, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:18', 000057, 'TUN-INT-CSA-RED', 338, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:9', 000057, 'TUN-MAGDIF-AA10-SM', 636, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:11', 000057, 'TUN-MAGDIF-AE14-SM', 801, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:12', 000057, 'TUN-MAGDIF-AE9-SC', 883, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:10', 000057, 'TUN-MAGDIF-AA20-SM', 719, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:8', 000057, 'TUN-MAGDIF-AA21-SM', 554, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:14', 000057, 'TUN-MAGDIF-A30-SC', 1212, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:13', 000057, 'TUN-MAGDIF-A29-SC', 1130, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:1', 000057, 'TUN-MAG-CSA-SAI', 966, 599, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:15', 000057, 'TUN-MAGDIF-A31-SM', 1295, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:20', 000057, 'TUN-MAGDIF-A5-SC', 540, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:27', 000057, 'TUN-MAGDIF-A33-SM', 1248, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:16', 000057, 'TUN-MAGDIF-A32-SM', 1378, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:21', 000057, 'TUN-MAGDIF-A4-SC', 641, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:30', 000057, 'TUN-MAGDIF-A36-SM', 1551, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:26', 000057, 'TUN-MAGDIF-A22-SM', 1147, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:28', 000057, 'TUN-MAGDIF-A34-SM', 1349, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:29', 000057, 'TUN-MAGDIF-A35-SM', 1450, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:2', 000057, 'TUN-INT-CSA-SAI', 61, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:3', 000057, 'TUN-MAGDIF-AA1-SC', 143, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:25', 000057, 'TUN-MAGDIF-A12-SM', 1045, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:19', 000057, 'TUN-MAGDIF-A3-SC', 439, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:5', 000057, 'TUN-MAGDIF-AA2-SC', 307, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:4', 000057, 'TUN-MAGDIF-AA15-SC', 225, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:6', 000057, 'TUN-MAGDIF-AA16-SC', 390, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:23', 000057, 'TUN-MAGDIF-A13-SM', 843, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:7', 000057, 'TUN-MAGDIF-AA11-SM', 472, 730, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:22', 000057, 'TUN-MAGDIF-A6-SC', 742, 206, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:24', 000057, 'TUN-MAGDIF-A23-SM', 945, 206, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:7', 000057, 'TUN-CONT-AA10-SM', 637, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:6', 000057, 'TUN-CONT-AA21-SM', 554, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:8', 000057, 'TUN-CONT-AA20-SM', 719, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:9', 000057, 'TUN-CONT-AE14-SM', 802, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:10', 000057, 'TUN-CONT-AE9-SC', 884, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:11', 000057, 'TUN-CONT-A29-SC', 1130, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:12', 000057, 'TUN-CONT-A30-SC', 1213, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:13', 000057, 'TUN-CONT-A31-SM', 1295, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:14', 000057, 'TUN-CONT-A32-SM', 1377, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:16', 000057, 'TUN-CONT-A5-SC', 540, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:23', 000057, 'TUN-CONT-A33-SM', 1248, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:17', 000057, 'TUN-CONT-A4-SC', 641, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:3', 000057, 'TUN-CONT-AA2-SC', 307, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:24', 000057, 'TUN-CONT-A34-SM', 1349, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:21', 000057, 'TUN-CONT-A12-SM', 1046, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:25', 000057, 'TUN-CONT-A35-SM', 1450, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:22', 000057, 'TUN-CONT-A22-SM', 1147, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:1', 000057, 'TUN-CONT-AA1-SC', 143, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:2', 000057, 'TUN-CONT-AA15-SC', 225, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:15', 000057, 'TUN-CONT-A3-SC', 439, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:18', 000057, 'TUN-CONT-A6-SC', 742, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:20', 000057, 'TUN-CONT-A23-SM', 945, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:26', 000057, 'TUN-CONT-A36-SM', 1551, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:19', 000057, 'TUN-CONT-A13-SM', 843, 308, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:4', 000057, 'TUN-CONT-AA16-SC', 389, 830, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:5', 000057, 'TUN-CONT-AA11-SM', 472, 830, false, NULL, true, false, NULL, true);

