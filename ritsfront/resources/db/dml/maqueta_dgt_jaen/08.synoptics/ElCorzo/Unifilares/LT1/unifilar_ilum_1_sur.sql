-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(916, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUM_SUR_LT1', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUM_SUR_LT1', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUM_SUR_LT1', NULL, 'img/synoptics/unifilar_ilum_1_sur.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91605700, 916, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(91608400, 916, 'SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', 'LBL_SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(45, 916, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(46, 916, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91605700, 'Element:57:270', 5702, 'INT-K01-ILU-SUR-1', 928, 312, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:269', 57, 'INT-Q1-ILU-SUR-1', 938, 253, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:281', 57, 'INT-EXT-ENT-ILU-SUR-1', 1791, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:280', 57, 'INT-CREP-DER-1-ILU-SUR-1', 1620, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:279', 57, 'INT-NUB-DER-1-ILU-SUR-1', 1449, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:278', 57, 'INT-SOL-DER-2-ILU-SUR-1', 1278, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:277', 57, 'INT-SOL-DER-1-ILU-SUR-1', 1107, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:276', 57, 'INT-CREP-IZQ-1-ILU-SUR-1', 937, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:275', 57, 'INT-NOC-IZQ-ILU-SUR-1', 766, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:274', 57, 'INT-NUB-IZQ-ILU-SUR-1', 596, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:273', 57, 'INT-SOL-IZQ-2-ILU-SUR-1', 425, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:272', 57, 'INT-SOL-IZQ-1-ILU-SUR-1', 253, 417, false, NULL, true, false, NULL, true),
(91605700, 'Element:57:271', 57, 'INT-MANI-ILU-SUR-1', 83, 417, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:181', 00008401, 'EXT-ENT-ILU-SUR-1', 1785, 542, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:180', 00008401, 'CREP-DER-1-ILU-SUR-1', 1612, 542, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:179', 00008401, 'NUB-DER-1-ILU-SUR-1', 1441, 542, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:178', 00008401, 'SOL-DER-2-ILU-SUR-1', 1269, 542, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:177', 00008401, 'SOL-DER-1-ILU-SUR-1', 1101, 542, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:176', 00008401, 'CREP-IZQ-1-ILU-SUR-1', 929, 542, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:175', 00008401, 'NOC-IZQ-ILU-SUR-1', 757, 542, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:174', 00008401, 'NUB-IZQ-ILU-SUR-1', 588, 542, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:173', 00008401, 'SOL-IZQ-2-ILU-SUR-1', 418, 542, false, NULL, true, false, NULL, true),
(91608400, 'Element:84:172', 00008401, 'SOL-IZQ-1-ILU-SUR-1', 245, 542, false, NULL, true, false, NULL, true);

