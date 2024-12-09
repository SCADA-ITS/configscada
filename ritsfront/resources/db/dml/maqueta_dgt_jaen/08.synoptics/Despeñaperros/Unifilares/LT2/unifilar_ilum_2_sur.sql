-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(904, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_SUR_DP2', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_SUR_DP2', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_SUR_DP2', NULL, 'img/synoptics/unifilar_ilum_sur_dp2.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90405700, 904, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(90408400, 904, 'SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', 'LBL_SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(12, 904, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(13, 904, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90405700, 'Element:57:2', 00005702, 'INT-K01-ILU-SUR-2', 935, 219, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:27', 000057, 'INT-EXT-ILU-SUR-2', 1792, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:26', 000057, 'INT-CREP-DER-4-ILU-SUR-2', 1637, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:25', 000057, 'INT-CREP-DER-3-ILU-SUR-2', 1481, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:24', 000057, 'INT-CREP-DER-2-ILU-SUR-2', 1327, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:23', 000057, 'INT-CREP-DER-1-ILU-SUR-2', 1172, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:22', 000057, 'INT-NUB-DER-3-ILU-SUR-2', 1017, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:21', 000057, 'INT-NUB-DER-2-ILU-SUR-2', 861, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:20', 000057, 'INT-NUB-DER-1-ILU-SUR-2', 706, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:19', 000057, 'INT-SOL-DER-4-ILU-SUR-2', 551, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:18', 000057, 'INT-SOL-DER-3-ILU-SUR-2', 395, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:17', 000057, 'INT-SOL-DER-2-ILU-SUR-2', 240, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:16', 000057, 'INT-SOL-DER-1-ILU-SUR-2', 85, 743, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:15', 000057, 'INT-CREP-IZQ-4-ILU-SUR-2', 1818, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:14', 000057, 'INT-CREP-IZQ-3-ILU-SUR-2', 1664, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:13', 000057, 'INT-CREP-IZQ-2-ILU-SUR-2', 1511, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:12', 000057, 'INT-CREP-IZQ-1-ILU-SUR-2', 1357, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:11', 000057, 'INT-NOC-IZQ-ILU-SUR-2', 1204, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:10', 000057, 'INT-NUB-IZQ-3-ILU-SUR-2', 1050, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:9', 000057, 'INT-NUB-IZQ-2-ILU-SUR-2', 897, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:8', 000057, 'INT-NUB-IZQ-1-ILU-SUR-2', 743, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:1', 000057, 'INT-Q26-ILU-SUR-2', 945, 157, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:7', 000057, 'INT-SOL-IZQ-4-ILU-SUR-2', 590, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:6', 000057, 'INT-SOL-IZQ-3-ILU-SUR-2', 436, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:5', 000057, 'INT-SOL-IZQ-2-ILU-SUR-2', 282, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:4', 000057, 'INT-SOL-IZQ-1-ILU-SUR-2', 129, 322, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:3', 000057, 'INT-MANI-ILU-SUR-2', 23, 322, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:62', 00008401, 'CONT-EXT-ILU-SUR-2', 1785, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:47', 00008401, 'CONT-CREP-ILU-SUR-2', 1351, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:48', 00008401, 'CONT-CREP-ILU-SUR-2', 1503, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:49', 00008401, 'CONT-CREP-ILU-SUR-2', 1657, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:50', 00008401, 'CONT-CREP-ILU-SUR-2', 1810, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:58', 00008401, 'CONT-CREP-ILU-SUR-2', 1165, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:59', 00008401, 'CONT-CREP-ILU-SUR-2', 1320, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:60', 00008401, 'CONT-CREP-ILU-SUR-2', 1475, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:61', 00008401, 'CONT-CREP-ILU-SUR-2', 1629, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:46', 00008401, 'CONT-NOC-ILU-SUR-2', 1197, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:43', 00008401, 'CONT-NUB-ILU-SUR-2', 736, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:44', 00008401, 'CONT-NUB-ILU-SUR-2', 889, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:45', 00008401, 'CONT-NUB-ILU-SUR-2', 1043, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:55', 00008401, 'CONT-NUB-ILU-SUR-2', 699, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:56', 00008401, 'CONT-NUB-ILU-SUR-2', 855, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:57', 00008401, 'CONT-NUB-ILU-SUR-2', 1010, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:39', 00008401, 'CONT-SOL-ILU-SUR-2', 122, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:40', 00008401, 'CONT-SOL-ILU-SUR-2', 276, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:41', 00008401, 'CONT-SOL-ILU-SUR-2', 429, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:42', 00008401, 'CONT-SOL-ILU-SUR-2', 583, 447, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:51', 00008401, 'CONT-SOL-ILU-SUR-2', 77, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:52', 00008401, 'CONT-SOL-ILU-SUR-2', 233, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:53', 00008401, 'CONT-SOL-ILU-SUR-2', 388, 869, false, NULL, true, false, NULL, true),
(90408400, 'Element:84:54', 00008401, 'CONT-SOL-ILU-SUR-2', 544, 869, false, NULL, true, false, NULL, true);
