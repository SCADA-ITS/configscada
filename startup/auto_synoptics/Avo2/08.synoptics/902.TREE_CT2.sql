-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(902, 'TREE_CT2', 'TREE_CT2', 'LBL_TREE_CT2', NULL, 'img/synoptics/CT2.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90200003, 902, 'CONTROL', 'LBL_CONTROL', true),
(90202700, 902, 'PLC', 'LBL_PLC', true),
(90200004, 902, 'ELECTRICIDAD', 'LBL_ELECTRICIDAD', true),
(90203300, 902, 'TRAFO', 'LBL_TRAFO', true),
(90205600, 902, 'UNIFILAR', 'LBL_UNIFILAR', true),
(90205700, 902, 'CELDA_MT', 'LBL_CELDA_MT', true),
(90206500, 902, 'UPS', 'LBL_UPS', true),
(90207000, 902, 'ANALIZADOR_RED', 'LBL_ANALIZADOR_RED', true),
(90207500, 902, 'GE', 'LBL_GE', true),
(90200005, 902, 'DET_LAZO', 'LBL_DET_LAZO', true),
(90205800, 902, 'DET_HUMO', 'LBL_DET_HUMO', true),
(90205900, 902, 'PULSADOR', 'LBL_PULSADOR', true),
(90206000, 902, 'SIRENA', 'LBL_SIRENA', true),
(90206100, 902, 'CENTRAL_CT', 'LBL_CENTRAL_CT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(902, 90200003, 90202700),
(902, 90200004, 90203300),
(902, 90200004, 90205600),
(902, 90200004, 90205700),
(902, 90200004, 90206500),
(902, 90200004, 90207000),
(902, 90200004, 90207500),
(902, 90200005, 90205800),
(902, 90200005, 90205900),
(902, 90200005, 90206000),
(902, 90200005, 90206100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(3, 902, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(4, 902, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(5, 902, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90202700, 'Element:27:2', 000027, 'PLC_CT02', 1419, 60, false, NULL, true, false, NULL, true),
(90203300, 'Element:33:4', 000033, 'CT02-TR3-SE2', 1062, 977, false, NULL, true, false, NULL, true),
(90203300, 'Element:33:3', 000033, 'CT02-TR2-SE2', 997, 977, false, NULL, true, false, NULL, true),
(90203300, 'Element:33:2', 000033, 'CT02-TR1-SE2', 1029, 977, false, NULL, true, false, NULL, true),
(90205600, 'Element:56:4', 000056, '853.TUPS.1.SE2', 1168, 577, false, NULL, true, false, NULL, true),
(90205600, 'Element:56:3', 000056, '853.TFYA.1.SE2', 1405, 815, false, NULL, true, false, NULL, true),
(90205700, 'Element:57:603', 000057, 'CT02-CLI1-SE2', 1315, 988, false, NULL, true, false, NULL, true),
(90205700, 'Element:57:604', 000057, 'CT02-CPF1-SE2', 1285, 988, false, NULL, true, false, NULL, true),
(90205700, 'Element:57:605', 000057, 'CT02-CPF2-SE2', 1255, 988, false, NULL, true, false, NULL, true),
(90205700, 'Element:57:606', 000057, 'CT02-CPF3-SE2', 1225, 988, false, NULL, true, false, NULL, true),
(90206500, 'Element:65:2', 000065, 'CT02-UPS-001', 626, 517, false, NULL, true, false, NULL, true),
(90207000, 'Element:70:8', 000070, 'CT02-TCCMP-002', 426, 860, false, NULL, true, false, NULL, true),
(90207000, 'Element:70:7', 000070, 'CT02-TCCMP-001', 426, 827, false, NULL, true, false, NULL, true),
(90207000, 'Element:70:6', 000070, 'CT02-TD1-002', 724, 988, false, NULL, true, false, NULL, true),
(90207000, 'Element:70:5', 000070, 'CT02-TD1-001', 692, 988, false, NULL, true, false, NULL, true),
(90207000, 'Element:70:2', 000070, 'CT02-TUPS-001', 1228, 577, false, NULL, true, false, NULL, true),
(90207000, 'Element:70:1', 000070, 'CT02-TFYA-001', 1435, 875, false, NULL, true, false, NULL, true),
(90207500, 'Element:75:1', 000075, 'CT02-GE-001', 1220, 116, false, NULL, true, false, NULL, true),
(90207500, 'Element:75:2', 000075, 'CT02-GE-002', 1236, 314, false, NULL, true, false, NULL, true),
(90205800, 'Element:58:3', 000058, 'CT02-CCI-001.Z2PUL1', 889, 624, false, NULL, true, false, NULL, true),
(90205800, 'Element:58:4', 000058, 'CT02-CCI-001.Z3PUL1', 1022, 534, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:15', 000059, 'CT02-CCI-001.Z3DO3', 1284, 167, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:16', 000059, 'CT02-CCI-001.Z3DO4', 1103, 137, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:17', 000059, 'CT02-CCI-001.Z3DT1', 849, 133, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:14', 000059, 'CT02-CCI-001.Z3DT2', 849, 389, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:12', 000059, 'CT02-CCI-001.Z3DO2', 1107, 391, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:13', 000059, 'CT02-CCI-001.Z3DO1', 1288, 391, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:11', 000059, 'CT02-CCI-001.Z1DO2', 529, 586, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:10', 000059, 'CT02-CCI-001.Z1DO1', 742, 586, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:8', 000059, 'CT02-CCI-001.Z2DO6', 1065, 674, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:9', 000059, 'CT02-CCI-001.Z2DO5', 1282, 674, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:5', 000059, 'CT02-CCI-001.Z2DO4', 1278, 847, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:7', 000059, 'CT02-CCI-001.Z2DO3', 1067, 847, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:4', 000059, 'CT02-CCI-001.Z2DO2', 810, 847, false, NULL, true, false, NULL, true),
(90205900, 'Element:59:6', 000059, 'CT02-CCI-001.Z2DO1', 530, 847, false, NULL, true, false, NULL, true),
(90206000, 'Element:60:5', 000060, 'CT02-CCI-001.Z1SIR1', 644, 712, false, NULL, true, false, NULL, true),
(90206000, 'Element:60:4', 000060, 'CT02-CCI-001.Z2SIR2', 1375, 757, false, NULL, true, false, NULL, true),
(90206000, 'Element:60:3', 000060, 'CT02-CCI-001.Z2SIR1', 452, 963, false, NULL, true, false, NULL, true),
(90206000, 'Element:60:7', 000060, 'CT02-CCI-001.Z3SIR2', 1137, 55, false, NULL, true, false, NULL, true),
(90206000, 'Element:60:6', 000060, 'CT02-CCI-001.Z3SIR1', 1052, 534, false, NULL, true, false, NULL, true),
(90206100, 'Element:61:1', 000061, 'CT01-CCI-001', 847, 614, false, NULL, true, false, NULL, true);

