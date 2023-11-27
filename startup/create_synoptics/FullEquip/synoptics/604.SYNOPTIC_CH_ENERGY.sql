-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(604, 'SYNOPTIC_CH_ENERGY', 'SYNOPTIC_CH_ENERGY', 'LBL_SYNOPTIC_CH_ENERGY', NULL, 'img/synoptics/Ausol_CH_Energia.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(60400006, 604, 'LAYER_PCI', 'LBL_LAYER_PCI', true),
(60408700, 604, 'LAYER_PCI_TANK', 'LBL_LAYER_PCI_TANK', true),
(60409300, 604, 'LAYER_PCI_TRANSFER_GROUP', 'LBL_LAYER_PCI_TRANSFER_GROUP', true),
(60400007, 604, 'LAYER_ELECTRICITY', 'LBL_LAYER_ELECTRICITY', true),
(60403300, 604, 'LAYER_ELECTRICITY_TRAFO', 'LBL_LAYER_ELECTRICITY_TRAFO', true),
(60405600, 604, 'LAYER_ELECTRICITY_UF', 'LBL_LAYER_ELECTRICITY_UF', true),
(60405700, 604, 'LAYER_ELECTRICITY_MAGDIG', 'LBL_LAYER_ELECTRICITY_MAGDIG', true),
(60406500, 604, 'LAYER_ELECTRICITY_SAI', 'LBL_LAYER_ELECTRICITY_SAI', true),
(60407000, 604, 'LAYER_ELECTRICITY_NETWORK_ANALYZER', 'LBL_LAYER_ELECTRICITY_NETWORK_ANALYZER', true),
(60407500, 604, 'LAYER_ELECTRICITY_GE', 'LBL_LAYER_ELECTRICITY_GE', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(604, 60400006, 60408700),
(604, 60400006, 60409300),
(604, 60400007, 60403300),
(604, 60400007, 60405600),
(604, 60400007, 60405700),
(604, 60400007, 60406500),
(604, 60400007, 60407000),
(604, 60400007, 60407500);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(9, 604, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(10, 604, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(11, 604, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(60408700, 'Element:87:20', 01000087, 'CH-DEP-TRASIEGO', 1586, 178, false, NULL, true, false, NULL, true),
(60409300, 'Element:93:7', 01000093, 'CH-DEP-GE', 1490, 197, false, NULL, true, false, NULL, true),
(60403300, 'Element:33:4', 01000033, 'CH-TRAFO-MT-T154', 1146, 146, false, NULL, true, false, NULL, true),
(60405600, 'Element:56:34', 01000056, 'CH-CSV-TSC', 1420, 497, false, NULL, true, false, NULL, true),
(60405600, 'Element:56:33', 01000056, 'CH-CSV-TSM', 1333, 497, false, NULL, true, false, NULL, true),
(60405600, 'Element:56:29', 01000056, 'CH-CSA-RED', 1084, 498, false, NULL, true, false, NULL, true),
(60405600, 'Element:56:32', 01000056, 'CH-CSB-ITS2', 1208, 795, false, NULL, true, false, NULL, true),
(60405600, 'Element:56:31', 01000056, 'CH-CSB-ITS1', 1097, 793, false, NULL, true, false, NULL, true),
(60405600, 'Element:56:28', 01000056, 'CH-CSA-SAI', 981, 792, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:1075', 01000057, 'CH-CONT-CONM-GRUPO-CGBT', 1323, 350, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:1074', 01000057, 'CH-CONT-CONM-RED-CGBT', 1229, 350, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:1095', 01000057, 'CH-EKOR-RPG-UCT', 1157, 87, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:526', 01000057, 'CH-INT-CGBT-MT', 1157, 231, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:433', 01000057, 'CH-INT-CGBT', 1156, 350, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:534', 01000057, 'CAS-MAGDIF-AE12-SC', 1094, 426, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:435', 01000057, 'CH-MAGDIF-SER-AUX-GE-CGBT', 1185, 426, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:441', 01000057, 'CH-MAGDIF-CS-BT-CGBT', 1724, 426, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:440', 01000057, 'CH-INT-BAT-COND-CGBT', 1616, 425, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:439', 01000057, 'CH-INT-CS-PCI-CGBT', 1522, 426, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:438', 01000057, 'CH-INT-CSV-SC-CGBT', 1428, 426, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:437', 01000057, 'CH-INT-CSV-SM-CGBT', 1345, 426, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:436', 01000057, 'CH-INT-CS-SAI-CGBT', 1270, 426, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:475', 01000057, 'CH-INT-CS-SAI', 1270, 650, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:476', 01000057, 'CH-MAGDIF-CSA-SAI-CS-SAI', 991, 719, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:477', 01000057, 'CH-MAGDIF-CS-ITS1-SAI', 1106, 719, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:478', 01000057, 'CH-MAGDIF-CS-ITS2-SAI', 1216, 719, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:481', 01000057, 'CH-MAGDIF-CS-PCI-SAI', 1550, 719, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:480', 01000057, 'CH-MAGDIF-CS-ITS-SAI', 1437, 719, false, NULL, true, false, NULL, true),
(60405700, 'Element:57:479', 01000057, 'CH-MAGDIF-CSG-GAL-SAI', 1325, 719, false, NULL, true, false, NULL, true),
(60406500, 'Element:65:5', 01000065, 'CH-SAI-1', 1261, 531, false, NULL, true, false, NULL, true),
(60407000, 'Element:70:5', 01010070, 'CH-ARED-1', 1029, 373, false, NULL, true, false, NULL, true),
(60407500, 'Element:75:5', 01010075, 'CH-GEL-1MA', 1376, 264, false, NULL, true, false, NULL, true);

