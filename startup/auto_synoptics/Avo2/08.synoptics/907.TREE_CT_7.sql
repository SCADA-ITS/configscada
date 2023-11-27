-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(907, 'TREE_CT_7', 'TREE_CT_7', 'LBL_TREE_CT_7', NULL, 'img/synoptics/CT7.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90700018, 907, 'CONTROL', 'LBL_CONTROL', true),
(90702700, 907, 'PLC', 'LBL_PLC', true),
(90700019, 907, 'ELECTRICIDAD', 'LBL_ELECTRICIDAD', true),
(90703300, 907, 'TRAFO', 'LBL_TRAFO', true),
(90705600, 907, 'UNIFILAR', 'LBL_UNIFILAR', true),
(90705700, 907, 'CELDA_MT', 'LBL_CELDA_MT', true),
(90706500, 907, 'UPS', 'LBL_UPS', true),
(90707000, 907, 'ANALIZADOR_RED', 'LBL_ANALIZADOR_RED', true),
(90707500, 907, 'GE', 'LBL_GE', true),
(90700020, 907, 'DET_LAZO', 'LBL_DET_LAZO', true),
(90705800, 907, 'DET_HUMO', 'LBL_DET_HUMO', true),
(90705900, 907, 'PULSADOR', 'LBL_PULSADOR', true),
(90706000, 907, 'SIRENA', 'LBL_SIRENA', true),
(90706100, 907, 'CENTRAL_CT', 'LBL_CENTRAL_CT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(907, 90700018, 90702700),
(907, 90700019, 90703300),
(907, 90700019, 90705600),
(907, 90700019, 90705700),
(907, 90700019, 90706500),
(907, 90700019, 90707000),
(907, 90700019, 90707500),
(907, 90700020, 90705800),
(907, 90700020, 90705900),
(907, 90700020, 90706000),
(907, 90700020, 90706100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(18, 907, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(19, 907, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(20, 907, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90702700, 'Element:27:7', 000027, 'PLC_CT07', 1865, 5, false, NULL, true, false, NULL, true),
(90703300, 'Element:33:17', 000033, 'CT07-TR3-SE7', 1131, 928, false, NULL, true, false, NULL, true),
(90703300, 'Element:33:16', 000033, 'CT07-TR2-SE7', 1163, 928, false, NULL, true, false, NULL, true),
(90703300, 'Element:33:15', 000033, 'CT07-TR1-SE7', 1195, 928, false, NULL, true, false, NULL, true),
(90705600, 'Element:56:13', 000056, '853.TFYA.1.SE7', 444, 796, false, NULL, true, false, NULL, true),
(90705600, 'Element:56:14', 000056, '853.TUPS.1.SE7', 525, 103, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:623', 000057, 'CT07-CPF3-SE7', 1329, 928, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:622', 000057, 'CT07-CPF2-SE7', 1299, 928, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:621', 000057, 'CT07-CPF1-SE7', 1269, 928, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:620', 000057, 'CT07-CLI1-SE7', 1239, 928, false, NULL, true, false, NULL, true),
(90706500, 'Element:65:7', 000065, 'CT07-UPS-001', 1343, 167, false, NULL, true, false, NULL, true),
(90707000, 'Element:70:31', 000070, 'CT07-TCCMP-001', 868, 498, false, NULL, true, false, NULL, true),
(90707000, 'Element:70:32', 000070, 'CT07-TCCMP-002', 784, 928, false, NULL, true, false, NULL, true),
(90707000, 'Element:70:33', 000070, 'CT07-TFYA-001', 414, 796, false, NULL, true, false, NULL, true),
(90707000, 'Element:70:34', 000070, 'CT07-TUPS-001', 495, 103, false, NULL, true, false, NULL, true),
(90707000, 'Element:70:30', 000070, 'CT07-TD1-002', 1091, 498, false, NULL, true, false, NULL, true),
(90707000, 'Element:70:29', 000070, 'CT07-TD1-001', 1057, 498, false, NULL, true, false, NULL, true),
(90707500, 'Element:75:10', 000075, 'CT07-GE-002', 144, 751, false, NULL, true, false, NULL, true),
(90707500, 'Element:75:9', 000075, 'CT07-GE-001', 248, 750, false, NULL, true, false, NULL, true),
(90705800, 'Element:58:21', 000058, 'CT07-CCI-001.Z1PUL1', 1237, 422, false, NULL, true, false, NULL, true),
(90705800, 'Element:58:22', 000058, 'CT07-CCI-001.Z2PUL1', 1555, 932, false, NULL, true, false, NULL, true),
(90705800, 'Element:58:20', 000058, 'CT07-CCI-001.Z2PUL2', 389, 492, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:55', 000059, 'CT07-CCI-001.Z3DT1', 89, 674, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:62', 000059, 'CT07-CCI-001.Z1DO1', 1266, 270, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:61', 000059, 'CT07-CCI-001.Z1DO2', 1369, 270, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:60', 000059, 'CT07-CCI-001.Z2DO1', 1375, 674, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:59', 000059, 'CT07-CCI-001.Z2DO2', 972, 674, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:58', 000059, 'CT07-CCI-001.Z2DO4', 551, 291, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:57', 000059, 'CT07-CCI-001.Z2DO3', 671, 674, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:56', 000059, 'CT07-CCI-001.Z3DT2', 216, 674, false, NULL, true, false, NULL, true),
(90706000, 'Element:60:31', 000060, 'CT07-CCI-001.Z2SIR2', 389, 447, false, NULL, true, false, NULL, true),
(90706000, 'Element:60:30', 000060, 'CT07-CCI-001.Z3SIR1', 358, 238, false, NULL, true, false, NULL, true),
(90706000, 'Element:60:33', 000060, 'CT07-CCI-001.Z1SIR1', 1207, 422, false, NULL, true, false, NULL, true),
(90706000, 'Element:60:32', 000060, 'CT07-CCI-001.Z2SIR1', 1585, 932, false, NULL, true, false, NULL, true),
(90706100, 'Element:61:7', 00006109, 'CT07-CCI-001', 1142, 325, false, NULL, true, false, NULL, true);

