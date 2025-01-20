-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(910, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_4_NORTE', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_4_NORTE', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_4_NORTE', NULL, 'img/synoptics/unifilar_ilum_4_norte.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91005700, 910, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(91008400, 910, 'SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', 'LBL_SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(27, 910, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(28, 910, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91005700, 'Element:57:166', 00005702, 'INT-K02-ILU-NOR-4', 927, 220, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:177', 000057, 'INT-SOL-DER-1-ILU-NOR-4', 179, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:178', 000057, 'INT-SOL-DER-2-ILU-NOR-4', 348, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:179', 000057, 'INT-SOL-DER-3-ILU-NOR-4', 516, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:180', 000057, 'INT-SOL-DER-4-ILU-NOR-4', 685, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:181', 000057, 'INT-NUB-DER-1-ILU-NOR-4', 853, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:182', 000057, 'INT-NUB-DER-2-ILU-NOR-4', 1021, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:183', 000057, 'INT-CREP-DER-1-ILU-NOR-4', 1190, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:184', 000057, 'INT-CREP-DER-2-ILU-NOR-4', 1358, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:185', 000057, 'INT-EXT-ENT-ILU-NOR-4', 1526, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:186', 000057, 'INT-EXT-SAL-ILU-NOR-4', 1695, 743, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:176', 000057, 'INT-CREP-IZQ-2-ILU-NOR-4', 1695, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:175', 000057, 'INT-CREP-IZQ-1-ILU-NOR-4', 1526, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:174', 000057, 'INT-NOC-IZQ-ILU-NOR-4', 1358, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:173', 000057, 'INT-NUB-IZQ-2-ILU-NOR-4', 1190, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:172', 000057, 'INT-NUB-IZQ-1-ILU-NOR-4', 1021, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:171', 000057, 'INT-SOL-IZQ-4-ILU-NOR-4', 853, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:170', 000057, 'INT-SOL-IZQ-3-ILU-NOR-4', 685, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:169', 000057, 'INT-SOL-IZQ-2-ILU-NOR-4', 516, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:168', 000057, 'INT-SOL-IZQ-1-ILU-NOR-4', 348, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:167', 000057, 'INT-MANI-ILU-NOR-4', 179, 321, false, NULL, true, false, NULL, true),
(91005700, 'Element:57:165', 000057, 'INT-Q27-ILU-NOR-4', 937, 156, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:116', 00008401, 'SOL-DER-1-ILU-NOR-4', 173, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:117', 00008401, 'SOL-DER-2-ILU-NOR-4', 340, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:118', 00008401, 'SOL-DER-3-ILU-NOR-4', 509, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:119', 00008401, 'SOL-DER-4-ILU-NOR-4', 677, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:120', 00008401, 'NUB-DER-1-ILU-NOR-4', 846, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:121', 00008401, 'NUB-DER-2-ILU-NOR-4', 1014, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:122', 00008401, 'CREP-DER-1-ILU-NOR-4', 1182, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:123', 00008401, 'CREP-DER-2-ILU-NOR-4', 1351, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:124', 00008401, 'EXT-ENT-ILU-NOR-4', 1518, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:125', 00008401, 'EXT-SAL-ILU-NOR-4', 1687, 869, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:115', 00008401, 'CREP-IZQ-2-ILU-NOR-4', 1686, 447, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:114', 00008401, 'CREP-IZQ-1-ILU-NOR-4', 1519, 447, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:113', 00008401, 'NOC-IZQ-ILU-NOR-4', 1350, 447, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:112', 00008401, 'NUB-IZQ-2-ILU-NOR-4', 1183, 447, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:111', 00008401, 'NUB-IZQ-1-ILU-NOR-4', 1013, 447, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:110', 00008401, 'SOL-IZQ-4-ILU-NOR-4', 845, 447, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:109', 00008401, 'SOL-IZQ-3-ILU-NOR-4', 678, 447, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:108', 00008401, 'SOL-IZQ-2-ILU-NOR-4', 510, 447, false, NULL, true, false, NULL, true),
(91008400, 'Element:84:107', 00008401, 'SOL-IZQ-1-ILU-NOR-4', 340, 447, false, NULL, true, false, NULL, true);