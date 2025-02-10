-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(909, 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CGBT', 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CGBT', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_UF_CGBT', NULL, 'img/synoptics/TUN_UF_CGBT.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90905700, 909, 'SYNOPTIC_LAYERS_INTERRUPTORES', 'LBL_SYNOPTIC_LAYERS_INTERRUPTORES', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(24, 909, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(25, 909, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(26, 909, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(4013, 909, 3, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench',false,104601, NULL,2,true, NULL,false,true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90905700, 'Element:57:39', 000057, 'TUN-INT-CS-EDIF-PCI', 1768, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:83', 000057, 'TUN-MAG-CSG-GAL', 1565, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:82', 000057, 'TUN-MAG-CS-ITS1', 1465, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:81', 000057, 'TUN-CIR-CSSAI', 1363, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:80', 000057, 'TUN-MAG-CSSAI', 1565, 710, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:38', 000057, 'TUN-MAG-GEN-CSSAI', 1163, 337, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:37', 000057, 'TUN-INT-CGBT-CSV-TSM', 1264, 337, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:40', 000057, 'TUN-INT-CGBT-CSV-TSC', 1365, 337, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:89', 000057, 'TUN-INT-GEN-EDIF-PCI', 1466, 337, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:36', 000057, 'TUN-INT-CGBT-BAT-COND', 1567, 337, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:41', 000057, 'TUN-MAG-CGBT-CSEDBT', 1668, 337, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:33', 000057, 'TUN-MAG-CGBT-SERV-AUX-GE', 827, 337, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:32', 000057, 'TUN-INT-CGBT-CSA-RED', 473, 337, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:34', 000057, 'TUN-INT-CGBT-COM-RED', 940, 206, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:35', 000057, 'TUN-INT-CGBT-COM-GRUPO', 1087, 234, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:31', 000057, 'TUN-MAG-CGBT', 655, 99, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:88', 000057, 'TUN-MAG-F2-EDIF-ITS', 971, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:87', 000057, 'TUN-MAG-F1-EDIF-ITS', 862, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:86', 000057, 'TUN-MAG-E1-EDIF-ITS', 753, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:85', 000057, 'TUN-MAG-A1-EDIF-ITS', 644, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:84', 000057, 'TUN-MAG-CS-EDIF-ITS', 808, 709, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:77', 000057, 'TUN-MAG-F2-CGBT', 422, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:76', 000057, 'TUN-MAG-F1-CGBT', 313, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:75', 000057, 'TUN-MAG-E1-CGBT', 204, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:74', 000057, 'TUN-MAG-A1-CGBT', 96, 859, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:73', 000057, 'TUN-MAG-CSEDBT', 259, 709, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:79', 000057, 'TUN-MAG-CGBT-MT', 143, 337, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:128', 000057, 'TUN-INT-CS-EDIF-ITS', 1667, 860, false, NULL, true, false, NULL, true);

