-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(902, 'SELECTOR_TREE_NODES_SINOPTICO_LOCALES_TECNICOS', 'SELECTOR_TREE_NODES_SINOPTICO_LOCALES_TECNICOS', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_LOCALES_TECNICOS', NULL, 'img/synoptics/locales_tecnicos.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90202900, 902, 'SYNOPTIC_LAYERS_FIBROLASER_CT', 'LBL_SYNOPTIC_LAYERS_FIBROLASER_CT', true),
(90205600, 902, 'SYNOPTIC_LAYERS_UNIFILAR', 'LBL_SYNOPTIC_LAYERS_UNIFILAR', true),
(90206500, 902, 'SYNOPTIC_LAYERS_SAI', 'LBL_SYNOPTIC_LAYERS_SAI', true),
(90208900, 902, 'SYNOPTIC_LAYERS_GATE', 'LBL_SYNOPTIC_LAYERS_GATE', true),
(90207500, 902, 'SYNOPTIC_LAYERS_GE', 'LBL_SYNOPTIC_LAYERS_GE', true),
(90206100, 902, 'SYNOPTIC_LAYERS_FIRE_CT', 'LBL_SYNOPTIC_LAYERS_FIRE_CT', true),
(90208600, 902, 'SYNOPTIC_LAYERS_PUMP', 'LBL_SYNOPTIC_LAYERS_PUMP', true),
(90208700, 902, 'SYNOPTIC_LAYERS_TANK', 'LBL_SYNOPTIC_LAYERS_TANK', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(3, 902, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(4, 902, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(5, 902, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_element_type_commands
(element_type, synoptic_command_id, module_action_id, view_type_id) VALUES
('ElementType:56', 4, 200002, 2);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90202900, 'Element:29:1', 000029, 'CEN-LIST-DP1', 149, 917, false, NULL, true, false, NULL, true),
(90202900, 'Element:29:3', 000029, 'CEN-LIST-CA', 1289, 917, false, NULL, true, false, NULL, true),
(90202900, 'Element:29:4', 000029, 'CEN-LIST-CZ', 1290, 415, false, NULL, true, false, NULL, true),
(90202900, 'Element:29:2', 000029, 'CEN-LIST-DP2', 1195, 415, false, NULL, true, false, NULL, true),
(90205600, 'Element:56:9', 00005600, 'CGBT-DP4', 1220, 601, false, NULL, true, false, NULL, true),
(90205600, 'Element:56:3', 00005600, 'CGBT-DP2', 1195, 73, false, NULL, true, false, NULL, true),
(90205600, 'Element:56:6', 00005600, 'CGBT-DP3', 258, 601, false, NULL, true, false, NULL, true),
(90205600, 'Element:56:12', 00005600, 'CGBT-DP1', 286, 73, false, NULL, true, false, NULL, true),
(90206500, 'Element:65:4', 000065, 'SAI-S-DP1', 419, 759, false, NULL, true, false, NULL, true),
(90206500, 'Element:65:3', 000065, 'SAI-N-DP1', 419, 601, false, NULL, true, false, NULL, true),
(90206500, 'Element:65:2', 000065, 'SAI-S-DP2', 1778, 387, false, NULL, true, false, NULL, true),
(90206500, 'Element:65:1', 000065, 'SAI-N-DP2', 1601, 387, false, NULL, true, false, NULL, true),
(90206500, 'Element:65:8', 000065, 'SAI-S-CZ', 457, 270, false, NULL, true, false, NULL, true),
(90206500, 'Element:65:7', 000065, 'SAI-N-CZ', 334, 270, false, NULL, true, false, NULL, true),
(90206500, 'Element:65:6', 000065, 'SAI-S-CA', 1598, 790, false, NULL, true, false, NULL, true),
(90206500, 'Element:65:5', 000065, 'SAI-N-CA', 1475, 790, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:12', 00008900, 'PUE-CGBT-CT3', 416, 933, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:14', 00008900, 'PUE-MT-CT3', 806, 933, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:13', 00008900, 'PUE-GE3-CT3', 634, 933, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:9', 00008900, 'PUE-CGBT-CT2', 1061, 423, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:11', 00008900, 'PUE-MT-CT2', 1767, 237, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:10', 00008900, 'PUE-GE2-CT2', 1588, 237, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:16', 00008900, 'PUE-GE4-CT4', 1758, 933, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:15', 00008900, 'PUE-CGBT-CT4', 1061, 933, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:18', 00008900, 'PUE-MT-CT1', 806, 423, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:19', 00008900, 'PUE-GE1-CT1', 634, 423, false, NULL, true, false, NULL, true),
(90208900, 'Element:89:17', 00008900, 'PUE-CGBT-CT1', 59, 423, false, NULL, true, false, NULL, true),
(90207500, 'Element:75:4', 000075, 'GE1-CZ', 634, 244, false, NULL, true, false, NULL, true),
(90207500, 'Element:75:1', 000075, 'GE2-DP', 1588, 93, false, NULL, true, false, NULL, true),
(90207500, 'Element:75:3', 000075, 'GE4-CA', 1758, 736, false, NULL, true, false, NULL, true),
(90207500, 'Element:75:2', 000075, 'GE3-DP', 634, 739, false, NULL, true, false, NULL, true),
(90206100, 'Element:61:1', 000061, 'CEN-INC-DP1', 258, 917, false, NULL, true, false, NULL, true),
(90206100, 'Element:61:3', 000061, 'CEN-INC-CA', 1384, 917, false, NULL, true, false, NULL, true),
(90206100, 'Element:61:2', 000061, 'CEN-INC-DP2', 1384, 415, false, NULL, true, false, NULL, true),
(90206100, 'Element:61:4', 000061, 'CEN-INC-CZ', 267, 415, false, NULL, true, false, NULL, true),
(90208600, 'Element:86:4', 01008601, 'BOMB-JOCK-1-DP2', 1251, 307, false, NULL, true, false, NULL, true),
(90208600, 'Element:86:3', 00008601, 'BOMB-LLEN-1-DP2', 1374, 307, false, NULL, true, false, NULL, true),
(90208600, 'Element:86:2', 00008601, 'BOMB-PRIN-2-DP2', 1156, 307, false, NULL, true, false, NULL, true),
(90208600, 'Element:86:1', 00008601, 'BOMB-PRIN-1-DP2', 1061, 307, false, NULL, true, false, NULL, true),
(90208700, 'Element:87:2', 000087, 'DEP-PCI-2-DP2', 1251, 185, false, NULL, true, false, NULL, true),
(90208700, 'Element:87:1', 000087, 'DEP-PCI-1-DP2', 1374, 185, false, NULL, true, false, NULL, true);

