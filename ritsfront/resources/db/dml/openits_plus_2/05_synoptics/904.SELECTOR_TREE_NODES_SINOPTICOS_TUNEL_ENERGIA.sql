-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(904, 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_ENERGIA', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_ENERGIA', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_ENERGIA', NULL, 'img/synoptics/TUN_Energia.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90401100, 904, 'SYNOPTIC_LAYERS_SOS', 'LBL_SYNOPTIC_LAYERS_SOS', true),
(90408700, 904, 'SYNOPTIC_LAYERS_TANQUE', 'LBL_SYNOPTIC_LAYERS_TANQUE', true),
(90400012, 904, 'SYNOPTIC_LAYERS_ELECTRICIDAD', 'LBL_SYNOPTIC_LAYERS_ELECTRICIDAD', true),
(90407000, 904, 'SYNOPTIC_LAYERS_ANALIZADOR_RED', 'LBL_SYNOPTIC_LAYERS_ANALIZADOR_RED', true),
(90409300, 904, 'SYNOPTIC_LAYERS_GRUPO_TRASIEGO', 'LBL_SYNOPTIC_LAYERS_GRUPO_TRASIEGO', true),
(90407500, 904, 'SYNOPTIC_LAYERS_GRUPO_ELECTROGENO', 'LBL_SYNOPTIC_LAYERS_GRUPO_ELECTROGENO', true),
(90405700, 904, 'SYNOPTIC_LAYERS_MAGNETOTERMICO', 'LBL_SYNOPTIC_LAYERS_MAGNETOTERMICO', true),
(90406500, 904, 'SYNOPTIC_LAYERS_SAI', 'LBL_SYNOPTIC_LAYERS_SAI', true),
(90403300, 904, 'SYNOPTIC_LAYERS_TRAFOS', 'LBL_SYNOPTIC_LAYERS_TRAFOS', true),
(90405600, 904, 'SYNOPTIC_LAYERS_UNIFILARES', 'LBL_SYNOPTIC_LAYERS_UNIFILARES', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(904, 90400012, 90407000),
(904, 90400012, 90409300),
(904, 90400012, 90407500),
(904, 90400012, 90405700),
(904, 90400012, 90406500),
(904, 90400012, 90403300),
(904, 90400012, 90405600);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(9, 904, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(10, 904, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(11, 904, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90401100, 'Element:11:13', 000011, 'TUN-SOSE-2MA', 709, 724, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:11', 000011, 'TUN-SOS-4MA', 606, 708, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:10', 000011, 'TUN-SOS-3MA', 483, 708, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:9', 000011, 'TUN-SOS-2MA', 386, 708, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:8', 000011, 'TUN-SOS-1MA', 288, 709, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:12', 000011, 'TUN-SOSE-1MA', 192, 724, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:6', 000011, 'TUN-SOSE-1CA', 710, 493, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:1', 000011, 'TUN-SOS-1CA', 642, 507, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:2', 000011, 'TUN-SOS-2CA', 550, 508, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:3', 000011, 'TUN-SOS-3CA', 454, 508, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:4', 000011, 'TUN-SOS-4CA', 357, 508, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:5', 000011, 'TUN-SOS-5CA', 272, 507, false, NULL, true, false, NULL, true),
(90401100, 'Element:11:7', 000011, 'TUN-SOSE-2CA', 185, 508, false, NULL, true, false, NULL, true),
(90408700, 'Element:87:1', 000087, 'TUN-DEP-DIESEL-PCI', 1548, 172, false, NULL, true, false, NULL, true),
(90407000, 'Element:70:1', 000070, 'TUN-ARED-1', 1008, 368, false, NULL, true, false, NULL, true),
(90409300, 'Element:93:1', 000093, 'TUN-DEP-GE', 1478, 197, false, NULL, true, false, NULL, true),
(90407500, 'Element:75:1', 010075, 'TUN-GEL-1MA', 1363, 273, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:78', 020057, 'TUN-EKOR-RPG-UCT', 1136, 83, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:79', 000057, 'TUN-MAG-CGBT-MT', 1136, 228, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:35', 000057, 'TUN-INT-CGBT-COM-GRUPO', 1303, 347, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:34', 000057, 'TUN-INT-CGBT-COM-RED', 1208, 347, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:31', 000057, 'TUN-MAG-CGBT', 1136, 347, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:32', 000057, 'TUN-INT-CGBT-CSA-RED', 1073, 423, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:33', 000057, 'TUN-MAG-CGBT-SERV-AUX-GE', 1165, 423, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:41', 000057, 'TUN-MAG-CGBT-CSEDBT', 1704, 423, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:36', 000057, 'TUN-INT-CGBT-BAT-COND', 1595, 423, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:40', 000057, 'TUN-INT-CGBT-CSV-TSC', 1407, 423, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:89', 000057, 'TUN-INT-GEN-EDIF-PCI', 1501, 423, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:37', 000057, 'TUN-INT-CGBT-CSV-TSM', 1324, 423, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:38', 000057, 'TUN-MAG-GEN-CSSAI', 1250, 423, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:80', 000057, 'TUN-MAG-CSSAI', 1250, 648, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:39', 000057, 'TUN-INT-CS-EDIF-PCI', 1474, 716, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:84', 000057, 'TUN-MAG-CS-EDIF-ITS', 1361, 716, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:83', 000057, 'TUN-MAG-CSG-GAL', 1250, 716, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:82', 000057, 'TUN-MAG-CS-ITS1', 1143, 716, false, NULL, true, false, NULL, true),
(90405700, 'Element:57:81', 000057, 'TUN-CIR-CSSAI', 1028, 717, false, NULL, true, false, NULL, true),
(90406500, 'Element:65:1', 010065, 'TUN-SAI-1', 1247, 529, false, NULL, true, false, NULL, true),
(90403300, 'Element:33:1', 000033, 'TUN-TRAFO-MT-T154', 1126, 147, false, NULL, true, false, NULL, true),
(90405600, 'Element:56:3', 010056, 'TUN-CSA-RED', 1062, 493, false, NULL, true, false, NULL, true),
(90405600, 'Element:56:5', 010056, 'TUN-CSV-TSC', 1398, 495, false, NULL, true, false, NULL, true),
(90405600, 'Element:56:4', 010056, 'TUN-CSV-TSM', 1313, 495, false, NULL, true, false, NULL, true),
(90405600, 'Element:56:1', 010056, 'TUN-CS-ITS1', 1132, 792, false, NULL, true, false, NULL, true),
(90405600, 'Element:56:2', 010056, 'TUN-CSA-SAI', 1016, 791, false, NULL, true, false, NULL, true);

