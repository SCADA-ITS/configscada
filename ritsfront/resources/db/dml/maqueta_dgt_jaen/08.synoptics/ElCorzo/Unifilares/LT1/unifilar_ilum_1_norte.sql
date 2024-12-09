-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(915, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUM_NOR_LT1', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUM_NOR_LT1', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUM_NOR_LT1', NULL, 'img/synoptics/unifilar_ilum_1_norte.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91505700, 915, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(91508400, 915, 'SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', 'LBL_SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(42, 915, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(43, 915, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91505700, 'Element:57:243', 000057, 'INT-Q1-ILU-NOR-1', 937, 157, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:257', 000057, 'INT-SOL-DER-1-ILU-NOR-1', 88, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:258', 000057, 'INT-SOL-DER-2-ILU-NOR-1', 250, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:259', 000057, 'INT-SOL-DER-3-ILU-NOR-1', 404, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:260', 000057, 'INT-SOL-DER-4-ILU-NOR-1', 557, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:261', 000057, 'INT-NUB-DER-1-ILU-NOR-1', 710, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:262', 000057, 'INT-NUB-DER-2-ILU-NOR-1', 864, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:263', 000057, 'INT-NUB-DER-3-ILU-NOR-1', 1018, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:264', 000057, 'INT-CREP-DER-1-ILU-NOR-1', 1171, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:265', 000057, 'INT-CREP-DER-2-ILU-NOR-1', 1324, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:266', 000057, 'INT-CREP-DER-3-ILU-NOR-1', 1478, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:267', 000057, 'INT-EXT-ENT-ILU-NOR-1', 1631, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:268', 000057, 'INT-EXT-SAL-ILU-NOR-1', 1785, 743, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:256', 000057, 'INT-CREP-IZQ-3-ILU-NOR-1', 1785, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:255', 000057, 'INT-CREP-IZQ-2-ILU-NOR-1', 1631, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:254', 000057, 'INT-CREP-IZQ-1-ILU-NOR-1', 1478, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:253', 000057, 'INT-NOC-IZQ-ILU-NOR-1', 1324, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:252', 000057, 'INT-NUB-IZQ-3-ILU-NOR-1', 1171, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:251', 000057, 'INT-NUB-IZQ-2-ILU-NOR-1', 1017, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:250', 000057, 'INT-NUB-IZQ-1-ILU-NOR-1', 864, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:249', 000057, 'INT-SOL-IZQ-4-ILU-NOR-1', 710, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:248', 000057, 'INT-SOL-IZQ-3-ILU-NOR-1', 557, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:247', 000057, 'INT-SOL-IZQ-2-ILU-NOR-1', 404, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:246', 000057, 'INT-SOL-IZQ-1-ILU-NOR-1', 250, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:245', 000057, 'INT-MANI-ILU-NOR-1', 88, 321, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:244', 00005702, 'INT-K02-ILU-NOR-1', 929, 220, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:149', 00008401, 'SOL-IZQ-1-ILU-NOR-1', 242, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:150', 00008401, 'SOL-IZQ-2-ILU-NOR-1', 396, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:151', 00008401, 'SOL-IZQ-3-ILU-NOR-1', 549, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:152', 00008401, 'SOL-IZQ-4-ILU-NOR-1', 703, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:153', 00008401, 'NUB-IZQ-1-ILU-NOR-1', 857, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:154', 00008401, 'NUB-IZQ-2-ILU-NOR-1', 1010, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:155', 00008401, 'NUB-IZQ-3-ILU-NOR-1', 1164, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:156', 00008401, 'NOC-IZQ-ILU-NOR-1', 1317, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:157', 00008401, 'CREP-IZQ-1-ILU-NOR-1', 1470, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:158', 00008401, 'CREP-IZQ-2-ILU-NOR-1', 1624, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:159', 00008401, 'CREP-IZQ-3-ILU-NOR-1', 1779, 448, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:171', 00008401, 'EXT-SAL-ILU-NOR-1', 1778, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:170', 00008401, 'EXT-ENT-ILU-NOR-1', 1625, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:169', 00008401, 'CREP-DER-3-ILU-NOR-1', 1472, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:168', 00008401, 'CREP-DER-2-ILU-NOR-1', 1317, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:167', 00008401, 'CREP-DER-1-ILU-NOR-1', 1163, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:166', 00008401, 'NUB-DER-3-ILU-NOR-1', 1011, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:165', 00008401, 'NUB-DER-2-ILU-NOR-1', 856, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:164', 00008401, 'NUB-DER-1-ILU-NOR-1', 703, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:163', 00008401, 'SOL-DER-4-ILU-NOR-1', 551, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:162', 00008401, 'SOL-DER-3-ILU-NOR-1', 396, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:161', 00008401, 'SOL-DER-2-ILU-NOR-1', 244, 869, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:160', 00008401, 'SOL-DER-1-ILU-NOR-1', 81, 869, false, NULL, true, false, NULL, true);
