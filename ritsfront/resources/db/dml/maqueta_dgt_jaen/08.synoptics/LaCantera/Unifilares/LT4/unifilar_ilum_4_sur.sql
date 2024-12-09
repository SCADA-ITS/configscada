-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(911, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_4_SUR', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_4_SUR', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_4_SUR', NULL, 'img/synoptics/unifilar_ilum_4_sur.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91105700, 911, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(91108400, 911, 'SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', 'LBL_SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(30, 911, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(31, 911, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91105700, 'Element:57:212', 000057, 'INT-EXT-SAL-ILU-SUR-4', 1785, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:211', 000057, 'INT-EXT-ENT-ILU-SUR-4', 1632, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:210', 000057, 'INT-CREP-DER-3-ILU-SUR-4', 1478, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:209', 000057, 'INT-CREP-DER-2-ILU-SUR-4', 1325, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:208', 000057, 'INT-CREP-DER-1-ILU-SUR-4', 1172, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:207', 000057, 'INT-NUB-DER-3-ILU-SUR-4', 1019, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:206', 000057, 'INT-NUB-DER-2-ILU-SUR-4', 864, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:205', 000057, 'INT-NUB-DER-1-ILU-SUR-4', 711, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:204', 000057, 'INT-SOL-DER-4-ILU-SUR-4', 558, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:203', 000057, 'INT-SOL-DER-3-ILU-SUR-4', 404, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:202', 000057, 'INT-SOL-DER-2-ILU-SUR-4', 251, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:201', 000057, 'INT-SOL-DER-1-ILU-SUR-4', 89, 743, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:200', 000057, 'INT-CREP-IZQ-3-ILU-SUR-4', 1785, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:199', 000057, 'INT-CREP-IZQ-2-ILU-SUR-4', 1632, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:198', 000057, 'INT-CREP-IZQ-1-ILU-SUR-4', 1478, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:197', 000057, 'INT-NOC-IZQ-ILU-SUR-4', 1325, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:196', 000057, 'INT-NUB-IZQ-3-ILU-SUR-4', 1172, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:195', 000057, 'INT-NUB-IZQ-2-ILU-SUR-4', 1019, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:194', 000057, 'INT-NUB-IZQ-1-ILU-SUR-4', 864, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:193', 000057, 'INT-SOL-IZQ-4-ILU-SUR-4', 711, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:192', 000057, 'INT-SOL-IZQ-3-ILU-SUR-4', 558, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:191', 000057, 'INT-SOL-IZQ-2-ILU-SUR-4', 404, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:190', 000057, 'INT-SOL-IZQ-1-ILU-SUR-4', 251, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:189', 000057, 'INT-MANI-ILU-SUR-4', 89, 321, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:187', 000057, 'INT-Q26-ILU-SUR-4', 937, 156, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:188', 00005702, 'INT-K01-ILU-SUR-4', 927, 217, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:148', 00008401, 'EXT-SAL-ILU-SUR-4', 1778, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:147', 00008401, 'EXT-ENT-ILU-SUR-4', 1624, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:146', 00008401, 'CREP-DER-3-ILU-SUR-4', 1471, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:145', 00008401, 'CREP-DER-2-ILU-SUR-4', 1318, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:144', 00008401, 'CREP-DER-1-ILU-SUR-4', 1164, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:143', 00008401, 'NUB-DER-3-ILU-SUR-4', 1011, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:142', 00008401, 'NUB-DER-2-ILU-SUR-4', 857, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:141', 00008401, 'NUB-DER-1-ILU-SUR-4', 703, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:140', 00008401, 'SOL-DER-4-ILU-SUR-4', 550, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:139', 00008401, 'SOL-DER-3-ILU-SUR-4', 396, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:138', 00008401, 'SOL-DER-2-ILU-SUR-4', 244, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:137', 00008401, 'SOL-DER-1-ILU-SUR-4', 82, 869, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:136', 00008401, 'CREP-IZQ-3-ILU-SUR-4', 1778, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:135', 00008401, 'CREP-IZQ-2-ILU-SUR-4', 1625, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:134', 00008401, 'CREP-IZQ-1-ILU-SUR-4', 1471, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:133', 00008401, 'NOC-IZQ-ILU-SUR-4', 1317, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:132', 00008401, 'NUB-IZQ-3-ILU-SUR-4', 1164, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:131', 00008401, 'NUB-IZQ-2-ILU-SUR-4', 1011, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:130', 00008401, 'NUB-IZQ-1-ILU-SUR-4', 858, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:129', 00008401, 'SOL-IZQ-4-ILU-SUR-4', 704, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:128', 00008401, 'SOL-IZQ-3-ILU-SUR-4', 551, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:127', 00008401, 'SOL-IZQ-2-ILU-SUR-4', 397, 447, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:126', 00008401, 'SOL-IZQ-1-ILU-SUR-4', 243, 447, false, NULL, true, false, NULL, true);
