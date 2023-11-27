-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(905, 'TREE_CT_5', 'TREE_CT_5', 'LBL_TREE_CT_5', NULL, 'img/synoptics/CT5.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90500012, 905, 'CONTROL', 'LBL_CONTROL', true),
(90502700, 905, 'PLC', 'LBL_PLC', true),
(90500013, 905, 'ELECTRICIDAD', 'LBL_ELECTRICIDAD', true),
(90503300, 905, 'TRAFO', 'LBL_TRAFO', true),
(90505600, 905, 'UNIFILAR', 'LBL_UNIFILAR', true),
(90505700, 905, 'CELDA_MT', 'LBL_CELDA_MT', true),
(90506500, 905, 'UPS', 'LBL_UPS', true),
(90507000, 905, 'ANALIZADOR_RED', 'LBL_ANALIZADOR_RED', true),
(90507500, 905, 'GE', 'LBL_GE', true),
(90500014, 905, 'DET_LAZO', 'LBL_DET_LAZO', true),
(90505800, 905, 'DET_HUMO', 'LBL_DET_HUMO', true),
(90505900, 905, 'PULSADOR', 'LBL_PULSADOR', true),
(90506000, 905, 'SIRENA', 'LBL_SIRENA', true),
(90506100, 905, 'CENTRAL_CT', 'LBL_CENTRAL_CT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(905, 90500012, 90502700),
(905, 90500013, 90503300),
(905, 90500013, 90505600),
(905, 90500013, 90505700),
(905, 90500013, 90506500),
(905, 90500013, 90507000),
(905, 90500013, 90507500),
(905, 90500014, 90505800),
(905, 90500014, 90505900),
(905, 90500014, 90506000),
(905, 90500014, 90506100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(12, 905, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(13, 905, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(14, 905, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90502700, 'Element:27:5', 000027, 'PLC_CT05', 1864, 8, false, NULL, true, false, NULL, true),
(90503300, 'Element:33:11', 000033, 'CT05-TR3-SE5', 281, 255, false, NULL, true, false, NULL, true),
(90503300, 'Element:33:10', 000033, 'CT05-TR2-SE5', 312, 255, false, NULL, true, false, NULL, true),
(90503300, 'Element:33:9', 000033, 'CT05-TR1-SE5', 344, 255, false, NULL, true, false, NULL, true),
(90505600, 'Element:56:9', 000056, '853.TFYA.1.SE5', 370, 132, false, NULL, true, false, NULL, true),
(90505600, 'Element:56:10', 000056, '853.TUPS.1.SE5', 917, 98, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:613', 000057, 'CT05-CPF1-SE5', 53, 319, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:614', 000057, 'CT05-CPF2-SE5', 53, 289, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:615', 000057, 'CT05-CPF3-SE5', 53, 259, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:612', 000057, 'CT05-CLI1-SE5', 53, 229, false, NULL, true, false, NULL, true),
(90506500, 'Element:65:5', 000065, 'CT05-UPS-001', 1301, 179, false, NULL, true, false, NULL, true),
(90507000, 'Element:70:18', 000070, 'CT05-TD1-002', 822, 344, false, NULL, true, false, NULL, true),
(90507000, 'Element:70:17', 000070, 'CT05-TD1-001', 747, 344, false, NULL, true, false, NULL, true),
(90507000, 'Element:70:20', 000070, 'CT05-TCCMP-002', 738, 231, false, NULL, true, false, NULL, true),
(90507000, 'Element:70:19', 000070, 'CT05-TCCMP-001', 921, 216, false, NULL, true, false, NULL, true),
(90507000, 'Element:70:21', 000070, 'CT05-TFYA-001', 340, 132, false, NULL, true, false, NULL, true),
(90507000, 'Element:70:22', 000070, 'CT05-TUPS-001', 887, 98, false, NULL, true, false, NULL, true),
(90507500, 'Element:75:7', 000075, 'CT05-GE-001', 981, 820, false, NULL, true, false, NULL, true),
(90507500, 'Element:75:8', 000075, 'CT05-GE-002', 979, 570, false, NULL, true, false, NULL, true),
(90505800, 'Element:58:13', 000058, 'CT05-CCI-001.Z2PUL3', 511, 364, false, NULL, true, false, NULL, true),
(90505800, 'Element:58:12', 000058, 'CT05-CCI-001.Z2PUL2', 70, 154, false, NULL, true, false, NULL, true),
(90505800, 'Element:58:15', 000058, 'CT05-CCI-001.Z2PUL4', 936, 382, false, NULL, true, false, NULL, true),
(90505800, 'Element:58:14', 000058, 'CT05-CCI-001.Z2PUL1', 1015, 101, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:41', 000059, 'CT05-CCI-001.Z1DO2', 1269, 138, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:42', 000059, 'CT05-CCI-001.Z1DO3', 1285, 285, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:45', 000059, 'CT05-CCI-001.Z3DT2', 1118, 781, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:40', 000059, 'CT05-CCI-001.Z1DO1', 1131, 156, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:43', 000059, 'CT05-CCI-001.Z1DO4', 1146, 300, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:39', 000059, 'CT05-CCI-001.Z2DO8', 946, 292, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:36', 000059, 'CT05-CCI-001.Z2DO7', 697, 317, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:38', 000059, 'CT05-CCI-001.Z2DO1', 936, 172, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:37', 000059, 'CT05-CCI-001.Z2DO2', 687, 190, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:35', 000059, 'CT05-CCI-001.Z2DO3', 427, 200, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:34', 000059, 'CT05-CCI-001.Z2DO6', 437, 330, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:33', 000059, 'CT05-CCI-001.Z2DO5', 141, 330, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:32', 000059, 'CT05-CCI-001.Z2DO4', 137, 206, false, NULL, true, false, NULL, true),
(90505900, 'Element:59:44', 000059, 'CT05-CCI-001.Z3DT1', 1095, 486, false, NULL, true, false, NULL, true),
(90506000, 'Element:60:19', 000060, 'CT05-CCI-001.Z2SIR3', 541, 364, false, NULL, true, false, NULL, true),
(90506000, 'Element:60:18', 000060, 'CT05-CCI-001.Z2SIR2', 103, 148, false, NULL, true, false, NULL, true),
(90506000, 'Element:60:22', 000060, 'CT05-CCI-001.Z2SIR1', 1016, 69, false, NULL, true, false, NULL, true),
(90506000, 'Element:60:21', 000060, 'CT05-CCI-001.Z2SIR4', 971, 384, false, NULL, true, false, NULL, true),
(90506000, 'Element:60:20', 000060, 'CT05-CCI-001.Z3SIR1', 999, 415, false, NULL, true, false, NULL, true),
(90506000, 'Element:60:23', 000060, 'CT05-CCI-001.Z1SIR1', 1053, 187, false, NULL, true, false, NULL, true),
(90506100, 'Element:61:1', 00006109, 'CT01-CCI-001', 1055, 148, false, NULL, true, false, NULL, true);

