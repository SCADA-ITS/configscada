-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(906, 'TREE_CT_6', 'TREE_CT_6', 'LBL_TREE_CT_6', NULL, 'img/synoptics/CT6.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90600015, 906, 'CONTROL', 'LBL_CONTROL', true),
(90602700, 906, 'PLC', 'LBL_PLC', true),
(90600016, 906, 'ELECTRICIDAD', 'LBL_ELECTRICIDAD', true),
(90603300, 906, 'TRAFO', 'LBL_TRAFO', true),
(90605600, 906, 'UNIFILAR', 'LBL_UNIFILAR', true),
(90605700, 906, 'CELDA_MT', 'LBL_CELDA_MT', true),
(90606500, 906, 'UPS', 'LBL_UPS', true),
(90607000, 906, 'ANALIZADOR_RED', 'LBL_ANALIZADOR_RED', true),
(90607500, 906, 'GE', 'LBL_GE', true),
(90600017, 906, 'DET_LAZO', 'LBL_DET_LAZO', true),
(90605800, 906, 'DET_HUMO', 'LBL_DET_HUMO', true),
(90605900, 906, 'PULSADOR', 'LBL_PULSADOR', true),
(90606000, 906, 'SIRENA', 'LBL_SIRENA', true),
(90606100, 906, 'CENTRAL_CT', 'LBL_CENTRAL_CT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(906, 90600015, 90602700),
(906, 90600016, 90603300),
(906, 90600016, 90605600),
(906, 90600016, 90605700),
(906, 90600016, 90606500),
(906, 90600016, 90607000),
(906, 90600016, 90607500),
(906, 90600017, 90605800),
(906, 90600017, 90605900),
(906, 90600017, 90606000),
(906, 90600017, 90606100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(15, 906, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(16, 906, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(17, 906, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90602700, 'Element:27:6', 00002700, 'PLC_CT06', 1878, 550, false, NULL, true, false, NULL, true),
(90603300, 'Element:33:14', 000033, 'CT06-TR3-SE6', 160, 613, false, NULL, true, false, NULL, true),
(90603300, 'Element:33:13', 000033, 'CT06-TR2-SE6', 192, 613, false, NULL, true, false, NULL, true),
(90603300, 'Element:33:12', 000033, 'CT06-TR1-SE6', 224, 613, false, NULL, true, false, NULL, true),
(90605600, 'Element:56:11', 00005601, '853.TFYA.1.SE6', 1406, 637, false, NULL, true, false, NULL, true),
(90605600, 'Element:56:12', 00005601, '853.TUPS.1.SE6', 1187, 637, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:617', 00005701, 'CT06-CPF1-SE6', 62, 623, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:618', 00005701, 'CT06-CPF2-SE6', 62, 603, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:616', 00005701, 'CT06-CLI1-SE6', 62, 557, false, NULL, true, false, NULL, true),
(90605700, 'Element:57:619', 00005701, 'CT06-CPF3-SE6', 62, 577, false, NULL, true, false, NULL, true),
(90606500, 'Element:65:6', 00006503, 'CT06-UPS-001', 1641, 550, false, NULL, true, false, NULL, true),
(90607000, 'Element:70:24', 000070, 'CT06-TD1-002', 975, 553, false, NULL, true, false, NULL, true),
(90607000, 'Element:70:23', 000070, 'CT06-TD1-001', 939, 553, false, NULL, true, false, NULL, true),
(90607000, 'Element:70:25', 000070, 'CT06-TCCMP-001', 1081, 553, false, NULL, true, false, NULL, true),
(90607000, 'Element:70:26', 000070, 'CT06-TCCMP-002', 1232, 553, false, NULL, true, false, NULL, true),
(90607000, 'Element:70:28', 000070, 'CT06-TUPS-001', 1157, 637, false, NULL, true, false, NULL, true),
(90607000, 'Element:70:27', 000070, 'CT06-TFYA-001', 1376, 637, false, NULL, true, false, NULL, true),
(90607500, 'Element:75:13', 00007505, 'CT06-GE-002', 432, 461, false, NULL, true, false, NULL, true),
(90607500, 'Element:75:12', 00007505, 'CT06-GE-001', 432, 501, false, NULL, true, false, NULL, true),
(90605800, 'Element:58:19', 00005806, 'CT06-CCI-001.Z1PUL1', 1648, 616, false, NULL, true, false, NULL, true),
(90605800, 'Element:58:18', 00005806, 'CT06-CCI-001.Z2PUL1', 1065, 590, false, NULL, true, false, NULL, true),
(90605800, 'Element:58:17', 00005806, 'CT06-CCI-001.Z3PUL1', 801, 590, false, NULL, true, false, NULL, true),
(90605800, 'Element:58:16', 00005806, 'CT06-CCI-001.Z5PUL1', 272, 633, false, NULL, true, false, NULL, true),
(90605900, 'Element:59:51', 00005907, 'CT06-CCI-001.Z2DO3', 1132, 590, false, NULL, true, false, NULL, true),
(90605900, 'Element:59:50', 00005907, 'CT06-CCI-001.Z3DO1', 921, 590, false, NULL, true, false, NULL, true),
(90605900, 'Element:59:49', 00005907, 'CT06-CCI-001.Z4DT1', 695, 452, false, NULL, true, false, NULL, true),
(90605900, 'Element:59:48', 00005907, 'CT06-CCI-001.Z4DT2', 486, 452, false, NULL, true, false, NULL, true),
(90605900, 'Element:59:47', 00005907, 'CT06-CCI-001.Z5DO1', 265, 590, false, NULL, true, false, NULL, true),
(90605900, 'Element:59:54', 00005907, 'CT06-CCI-001.Z1DO1', 1763, 590, false, NULL, true, false, NULL, true),
(90605900, 'Element:59:53', 00005907, 'CT06-CCI-001.Z2DO1', 1596, 590, false, NULL, true, false, NULL, true),
(90605900, 'Element:59:52', 00005907, 'CT06-CCI-001.Z2DO2', 1359, 590, false, NULL, true, false, NULL, true),
(90606000, 'Element:60:25', 00006008, 'CT06-CCI-001.Z4SIR1', 300, 610, false, NULL, true, false, NULL, true),
(90606000, 'Element:60:24', 00006008, 'CT06-CCI-001.Z5SIR1', 34, 590, false, NULL, true, false, NULL, true),
(90606000, 'Element:60:26', 00006008, 'CT06-CCI-001.Z3SIR1', 801, 610, false, NULL, true, false, NULL, true),
(90606000, 'Element:60:27', 00006008, 'CT06-CCI-001.Z2SIR2', 1065, 616, false, NULL, true, false, NULL, true),
(90606000, 'Element:60:28', 00006008, 'CT06-CCI-001.Z2SIR1', 1611, 616, false, NULL, true, false, NULL, true),
(90606000, 'Element:60:29', 00006008, 'CT06-CCI-001.Z1SIR1', 1878, 616, false, NULL, true, false, NULL, true),
(90606100, 'Element:61:1', 00006110, 'CT01-CCI-001', 1640, 589, false, NULL, true, false, NULL, true);

