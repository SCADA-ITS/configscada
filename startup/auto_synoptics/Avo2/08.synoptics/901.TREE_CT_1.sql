-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(901, 'TREE_CT_1', 'TREE_CT_1', 'LBL_TREE_CT_1', NULL, 'img/synoptics/CT1.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90100000, 901, 'CONTROL', 'LBL_CONTROL', true),
(90102700, 901, 'PLC', 'LBL_PLC', true),
(90100001, 901, 'ELECTRICIDAD', 'LBL_ELECTRICIDAD', true),
(90103300, 901, 'TRAFO', 'LBL_TRAFO', true),
(90105600, 901, 'UNIFILAR', 'LBL_UNIFILAR', true),
(90105700, 901, 'CELDA_MT', 'LBL_CELDA_MT', true),
(90106500, 901, 'UPS', 'LBL_UPS', true),
(90107000, 901, 'ANALIZADOR_RED', 'LBL_ANALIZADOR_RED', true),
(90107500, 901, 'GE', 'LBL_GE', true),
(90100002, 901, 'DET_LAZO', 'LBL_DET_LAZO', true),
(90105800, 901, 'DET_HUMO', 'LBL_DET_HUMO', true),
(90105900, 901, 'PULSADOR', 'LBL_PULSADOR', true),
(90106000, 901, 'SIRENA', 'LBL_SIRENA', true),
(90106100, 901, 'CENTRAL_CT', 'LBL_CENTRAL_CT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(901, 90100000, 90102700),
(901, 90100001, 90103300),
(901, 90100001, 90105600),
(901, 90100001, 90105700),
(901, 90100001, 90106500),
(901, 90100001, 90107000),
(901, 90100001, 90107500),
(901, 90100002, 90105800),
(901, 90100002, 90105900),
(901, 90100002, 90106000),
(901, 90100002, 90106100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(0, 901, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(1, 901, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(2, 901, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90102700, 'Element:27:1', 000027, 'PLC_CT01', 1285, 50, false, NULL, true, false, NULL, true),
(90103300, 'Element:33:1', 000033, 'CT01-TR1-SE1', 731, 266, false, NULL, true, false, NULL, true),
(90105600, 'Element:56:2', 000056, '853.TUPS.1.SE1', 1215, 761, false, NULL, true, false, NULL, true),
(90105600, 'Element:56:1', 000056, '853.TFYA.1.SE1', 1030, 564, false, NULL, true, false, NULL, true),
(90105700, 'Element:57:602', 000057, 'CT01-CPF1-SE1', 598, 265, false, NULL, true, false, NULL, true),
(90105700, 'Element:57:601', 000057, 'CT01-CLI1-SE1', 598, 235, false, NULL, true, false, NULL, true),
(90106500, 'Element:65:1', 000065, 'CT01-UPS-001', 1233, 653, false, NULL, true, false, NULL, true),
(90107000, 'Element:70:3', 000070, 'CT01-TFYA-001', 1090, 564, false, NULL, true, false, NULL, true),
(90107000, 'Element:70:4', 000070, 'CT01-TUPS-001', 1275, 761, false, NULL, true, false, NULL, true),
(90107500, 'Element:75:3', 000075, 'CT01-GE-001', 1205, 215, false, NULL, true, false, NULL, true),
(90105800, 'Element:58:2', 000058, 'CT01-CCI-001.Z3PUL1', 950, 233, false, NULL, true, false, NULL, true),
(90105800, 'Element:58:1', 000058, 'CT01-CCI-001.Z1PUL1', 590, 827, false, NULL, true, false, NULL, true),
(90105900, 'Element:59:3', 000059, 'CT01-CCI-001.Z2DT1', 1130, 195, false, NULL, true, false, NULL, true),
(90105900, 'Element:59:2', 000059, 'CT01-CCI-001.Z3DO1', 807, 323, false, NULL, true, false, NULL, true),
(90105900, 'Element:59:1', 000059, 'CT01-CCI-001.Z1DO1', 925, 767, false, NULL, true, false, NULL, true),
(90106000, 'Element:60:2', 000060, 'CT01-CCI-001.Z3SIR1', 950, 191, false, NULL, true, false, NULL, true),
(90106000, 'Element:60:1', 000060, 'CT01-CCI-001.Z1SIR1', 590, 646, false, NULL, true, false, NULL, true),
(90106100, 'Element:61:1', 000061, 'CT01-CCI-001', 584, 715, false, NULL, true, false, NULL, true);

