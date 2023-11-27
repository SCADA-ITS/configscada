-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(908, 'TREE_CT8', 'TREE_CT8', 'LBL_TREE_CT8', NULL, 'img/synoptics/CT8.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90800021, 908, 'CONTROL', 'LBL_CONTROL', true),
(90802700, 908, 'PLC', 'LBL_PLC', true),
(90800022, 908, 'ELECTRICIDAD', 'LBL_ELECTRICIDAD', true),
(90803300, 908, 'TRAFO', 'LBL_TRAFO', true),
(90805600, 908, 'UNIFILAR', 'LBL_UNIFILAR', true),
(90805700, 908, 'CELDA_MT', 'LBL_CELDA_MT', true),
(90806500, 908, 'UPS', 'LBL_UPS', true),
(90807000, 908, 'ANALIZADOR_RED', 'LBL_ANALIZADOR_RED', true),
(90807500, 908, 'GE', 'LBL_GE', true),
(90800023, 908, 'DET_LAZO', 'LBL_DET_LAZO', true),
(90805800, 908, 'DET_HUMO', 'LBL_DET_HUMO', true),
(90805900, 908, 'PULSADOR', 'LBL_PULSADOR', true),
(90806000, 908, 'SIRENA', 'LBL_SIRENA', true),
(90806100, 908, 'CENTRAL_CT', 'LBL_CENTRAL_CT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(908, 90800021, 90802700),
(908, 90800022, 90803300),
(908, 90800022, 90805600),
(908, 90800022, 90805700),
(908, 90800022, 90806500),
(908, 90800022, 90807000),
(908, 90800022, 90807500),
(908, 90800023, 90805800),
(908, 90800023, 90805900),
(908, 90800023, 90806000),
(908, 90800023, 90806100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(21, 908, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(22, 908, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(23, 908, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90802700, 'Element:27:8', 000027, 'PLC_CT08', 1868, 395, false, NULL, true, false, NULL, true),
(90803300, 'Element:33:20', 000033, 'CT08-TR3-SE8', 1679, 472, false, NULL, true, false, NULL, true),
(90803300, 'Element:33:19', 000033, 'CT08-TR2-SE8', 1647, 472, false, NULL, true, false, NULL, true),
(90803300, 'Element:33:18', 000033, 'CT08-TR1-SE8', 1614, 472, false, NULL, true, false, NULL, true),
(90805600, 'Element:56:16', 00005602, '853.TFYA.1.SE8', 1034, 552, false, NULL, true, false, NULL, true),
(90805600, 'Element:56:15', 00005602, '853.TFYA.1.SE8', 1215, 498, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:627', 000057, 'CT08-CPF3-SE8', 1753, 593, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:626', 000057, 'CT08-CPF2-SE8', 1783, 593, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:625', 000057, 'CT08-CPF1-SE8', 1813, 593, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:624', 000057, 'CT08-CLI1-SE8', 1843, 593, false, NULL, true, false, NULL, true),
(90806500, 'Element:65:8', 000065, 'CT08-UPS-001', 119, 472, false, NULL, true, false, NULL, true),
(90807000, 'Element:70:39', 000070, 'CT08-TFYA-001', 1185, 508, false, NULL, true, false, NULL, true),
(90807000, 'Element:70:36', 000070, 'CT08-TD1-002', 1451, 514, false, NULL, true, false, NULL, true),
(90807000, 'Element:70:35', 000070, 'CT08-TD1-001', 1411, 514, false, NULL, true, false, NULL, true),
(90807000, 'Element:70:37', 000070, 'CT08-TCCMP-001', 1344, 477, false, NULL, true, false, NULL, true),
(90807000, 'Element:70:38', 000070, 'CT08-TCCMP-002', 1344, 565, false, NULL, true, false, NULL, true),
(90807000, 'Element:70:40', 000070, 'CT08-TUPS-001', 1004, 552, false, NULL, true, false, NULL, true),
(90807500, 'Element:75:11', 000075, 'CT08-GE-002', 669, 479, false, NULL, true, false, NULL, true),
(90805800, 'Element:58:24', 000058, 'CT08-CCI-001.Z3PUL1', 951, 584, false, NULL, true, false, NULL, true),
(90805800, 'Element:58:23', 000058, 'CT08-CCI-001.Z1PUL1', 249, 507, false, NULL, true, false, NULL, true),
(90805900, 'Element:59:64', 000059, 'CT08-CCI-001.Z1DO2', 217, 532, false, NULL, true, false, NULL, true),
(90805900, 'Element:59:63', 000059, 'CT08-CCI-001.Z1DO1', 89, 532, false, NULL, true, false, NULL, true),
(90805900, 'Element:59:65', 000059, 'CT08-CCI-001.Z2DT1', 423, 530, false, NULL, true, false, NULL, true),
(90805900, 'Element:59:66', 000059, 'CT08-CCI-001.Z2DT2', 761, 533, false, NULL, true, false, NULL, true),
(90805900, 'Element:59:67', 000059, 'CT08-CCI-001.Z3DO1', 955, 532, false, NULL, true, false, NULL, true),
(90805900, 'Element:59:68', 000059, 'CT08-CCI-001.Z3DO2', 1150, 532, false, NULL, true, false, NULL, true),
(90805900, 'Element:59:69', 000059, 'CT08-CCI-001.Z3DO3', 1344, 532, false, NULL, true, false, NULL, true),
(90805900, 'Element:59:70', 000059, 'CT08-CCI-001.Z3DO4', 1500, 533, false, NULL, true, false, NULL, true),
(90806000, 'Element:60:37', 000060, 'CT08-CCI-001.Z3SIR2', 1588, 533, false, NULL, true, false, NULL, true),
(90806000, 'Element:60:36', 000060, 'CT08-CCI-001.Z3SIR1', 921, 584, false, NULL, true, false, NULL, true),
(90806000, 'Element:60:35', 000060, 'CT08-CCI-001.Z2SIR1', 589, 584, false, NULL, true, false, NULL, true),
(90806000, 'Element:60:34', 000060, 'CT08-CCI-001.Z1SIR1', 279, 507, false, NULL, true, false, NULL, true),
(90806100, 'Element:61:8', 00006109, 'CT08-CCI-001', 261, 599, false, NULL, true, false, NULL, true);

