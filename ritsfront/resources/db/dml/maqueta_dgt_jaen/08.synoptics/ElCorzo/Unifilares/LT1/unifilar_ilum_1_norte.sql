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
(91505700, 'Element:57:244', 00005702, 'INT-K02-ILU-NOR-1', 930, 221, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:243', 000057, 'INT-Q1-ILU-NOR-1', 937, 158, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:257', 000057, 'INT-SOL-DER-1-ILU-NOR-1', 89, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:258', 000057, 'INT-SOL-DER-2-ILU-NOR-1', 257, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:259', 000057, 'INT-SOL-DER-3-ILU-NOR-1', 427, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:260', 000057, 'INT-SOL-DER-4-ILU-NOR-1', 598, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:261', 000057, 'INT-NUB-DER-1-ILU-NOR-1', 767, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:262', 000057, 'INT-NUB-DER-2-ILU-NOR-1', 936, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:263', 000057, 'INT-NUB-DER-3-ILU-NOR-1', 1106, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:264', 000057, 'INT-CREP-DER-1-ILU-NOR-1', 1276, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:265', 000057, 'INT-CREP-DER-2-ILU-NOR-1', 1445, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:266', 000057, 'INT-CREP-DER-3-ILU-NOR-1', 1615, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:268', 000057, 'INT-EXT-SAL-ILU-NOR-1', 1786, 744, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:256', 000057, 'INT-CREP-IZQ-3-ILU-NOR-1', 1786, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:255', 000057, 'INT-CREP-IZQ-2-ILU-NOR-1', 1632, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:254', 000057, 'INT-CREP-IZQ-1-ILU-NOR-1', 1479, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:253', 000057, 'INT-NOC-IZQ-ILU-NOR-1', 1325, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:252', 000057, 'INT-NUB-IZQ-3-ILU-NOR-1', 1171, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:251', 000057, 'INT-NUB-IZQ-2-ILU-NOR-1', 1018, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:250', 000057, 'INT-NUB-IZQ-1-ILU-NOR-1', 865, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:249', 000057, 'INT-SOL-IZQ-4-ILU-NOR-1', 711, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:248', 000057, 'INT-SOL-IZQ-3-ILU-NOR-1', 557, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:247', 000057, 'INT-SOL-IZQ-2-ILU-NOR-1', 405, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:246', 000057, 'INT-SOL-IZQ-1-ILU-NOR-1', 251, 322, false, NULL, true, false, NULL, true),
(91505700, 'Element:57:245', 000057, 'INT-MANI-ILU-NOR-1', 89, 322, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:149', 00008401, 'SOL-IZQ-1-ILU-NOR-1', 243, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:150', 00008401, 'SOL-IZQ-2-ILU-NOR-1', 396, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:151', 00008401, 'SOL-IZQ-3-ILU-NOR-1', 549, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:152', 00008401, 'SOL-IZQ-4-ILU-NOR-1', 703, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:153', 00008401, 'NUB-IZQ-1-ILU-NOR-1', 857, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:154', 00008401, 'NUB-IZQ-2-ILU-NOR-1', 1010, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:155', 00008401, 'NUB-IZQ-3-ILU-NOR-1', 1164, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:156', 00008401, 'NOC-IZQ-ILU-NOR-1', 1317, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:157', 00008401, 'CREP-IZQ-1-ILU-NOR-1', 1470, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:158', 00008401, 'CREP-IZQ-2-ILU-NOR-1', 1624, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:159', 00008401, 'CREP-IZQ-3-ILU-NOR-1', 1779, 449, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:171', 00008401, 'EXT-SAL-ILU-NOR-1', 1778, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:169', 00008401, 'CREP-DER-3-ILU-NOR-1', 1608, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:168', 00008401, 'CREP-DER-2-ILU-NOR-1', 1437, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:167', 00008401, 'CREP-DER-1-ILU-NOR-1', 1267, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:166', 00008401, 'NUB-DER-3-ILU-NOR-1', 1099, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:165', 00008401, 'NUB-DER-2-ILU-NOR-1', 928, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:164', 00008401, 'NUB-DER-1-ILU-NOR-1', 759, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:163', 00008401, 'SOL-DER-4-ILU-NOR-1', 591, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:162', 00008401, 'SOL-DER-3-ILU-NOR-1', 418, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:161', 00008401, 'SOL-DER-2-ILU-NOR-1', 250, 870, false, NULL, true, false, NULL, true),
(91508400, 'Element:84:160', 00008401, 'SOL-DER-1-ILU-NOR-1', 81, 870, false, NULL, true, false, NULL, true);

