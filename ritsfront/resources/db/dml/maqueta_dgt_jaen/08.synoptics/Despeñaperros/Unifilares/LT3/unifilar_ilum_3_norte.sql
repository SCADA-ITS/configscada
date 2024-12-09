-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(906, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_3_NORTE', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_3_NORTE', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_3_NORTE', NULL, 'img/synoptics/unifilar_ilum_3_norte.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90605700, 906, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(90608400, 906, 'SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', 'LBL_SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(15, 906, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(16, 906, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90605700, 'Element:57:43', 00005702, 'INT-K02-ILU-NOR-3', 911, 217, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:56', 000057, 'INT-SOL-DER-1-ILU-NOR-3', 145, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:57', 000057, 'INT-SOL-DER-2-ILU-NOR-3', 306, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:58', 000057, 'INT-SOL-DER-3-ILU-NOR-3', 467, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:59', 000057, 'INT-SOL-DER-4-ILU-NOR-3', 628, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:60', 000057, 'INT-NUB-DER-1-ILU-NOR-3', 789, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:61', 000057, 'INT-NUB-DER-2-ILU-NOR-3', 950, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:62', 000057, 'INT-NUB-DER-3-ILU-NOR-3', 1111, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:63', 000057, 'INT-CREP-DER-1-ILU-NOR-3', 1272, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:64', 000057, 'INT-CREP-DER-2-ILU-NOR-3', 1434, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:65', 000057, 'INT-CREP-DER-3-ILU-NOR-3', 1595, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:66', 000057, 'INT-EXT-ILU-NOR-3', 1755, 743, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:55', 000057, 'INT-CREP-IZQ-3-ILU-NOR-3', 1804, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:54', 000057, 'INT-CREP-IZQ-2-ILU-NOR-3', 1643, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:53', 000057, 'INT-CREP-IZQ-1-ILU-NOR-3', 1482, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:52', 000057, 'INT-NOC-IZQ-ILU-NOR-3', 1321, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:51', 000057, 'INT-NUB-IZQ-3-ILU-NOR-3', 1160, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:50', 000057, 'INT-NUB-IZQ-2-ILU-NOR-3', 999, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:49', 000057, 'INT-NUB-IZQ-1-ILU-NOR-3', 838, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:48', 000057, 'INT-SOL-IZQ-4-ILU-NOR-3', 676, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:47', 000057, 'INT-SOL-IZQ-3-ILU-NOR-3', 515, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:46', 000057, 'INT-SOL-IZQ-2-ILU-NOR-3', 354, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:45', 000057, 'INT-SOL-IZQ-1-ILU-NOR-3', 193, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:44', 000057, 'INT-MANI-ILU-NOR-3', 38, 321, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:42', 000057, 'INT-Q27-ILU-NOR-3', 921, 157, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:95', 00008401, 'EXT-ILU-NOR-3', 1748, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:94', 00008401, 'CREP-DER-3-ILU-NOR-3', 1587, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:93', 00008401, 'CREP-DER-2-ILU-NOR-3', 1427, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:92', 00008401, 'CREP-DER-1-ILU-NOR-3', 1265, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:91', 00008401, 'NUB-DER-3-ILU-NOR-3', 1104, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:90', 00008401, 'NUB-DER-2-ILU-NOR-3', 944, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:89', 00008401, 'NUB-DER-1-ILU-NOR-3', 782, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:88', 00008401, 'SOL-DER-4-ILU-NOR-3', 621, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:87', 00008401, 'SOL-DER-3-ILU-NOR-3', 460, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:86', 00008401, 'SOL-DER-2-ILU-NOR-3', 300, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:85', 00008401, 'SOL-DER-1-ILU-NOR-3', 138, 869, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:84', 00008401, 'CREP-IZQ-3-ILU-NOR-3', 1797, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:83', 00008401, 'CREP-IZQ-2-ILU-NOR-3', 1635, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:82', 00008401, 'CREP-IZQ-1-ILU-NOR-3', 1475, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:81', 00008401, 'NOC-IZQ-ILU-NOR-3', 1314, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:80', 00008401, 'NUB-IZQ-3-ILU-NOR-3', 1153, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:79', 00008401, 'NUB-IZQ-2-ILU-NOR-3', 991, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:78', 00008401, 'NUB-IZQ-1-ILU-NOR-3', 831, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:77', 00008401, 'SOL-IZQ-4-ILU-NOR-3', 669, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:76', 00008401, 'SOL-IZQ-3-ILU-NOR-3', 508, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:75', 00008401, 'SOL-IZQ-2-ILU-NOR-3', 348, 447, false, NULL, true, false, NULL, true),
(90608400, 'Element:84:74', 00008401, 'SOL-IZQ-1-ILU-NOR-3', 186, 447, false, NULL, true, false, NULL, true);
