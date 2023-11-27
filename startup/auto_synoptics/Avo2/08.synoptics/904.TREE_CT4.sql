-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(904, 'TREE_CT4', 'TREE_CT4', 'LBL_TREE_CT4', NULL, 'img/synoptics/CT4.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90400009, 904, 'CONTROL', 'LBL_CONTROL', true),
(90402700, 904, 'PLC', 'LBL_PLC', true),
(90400010, 904, 'ELECTRICIDAD', 'LBL_ELECTRICIDAD', true),
(90403300, 904, 'TRAFO', 'LBL_TRAFO', true),
(90405600, 904, 'UNIFILAR', 'LBL_UNIFILAR', true),
(90405700, 904, 'CELDA_MT', 'LBL_CELDA_MT', true),
(90406500, 904, 'UPS', 'LBL_UPS', true),
(90407000, 904, 'ANALIZADOR_RED', 'LBL_ANALIZADOR_RED', true),
(90407500, 904, 'GE', 'LBL_GE', true),
(90400011, 904, 'DET_LAZO', 'LBL_DET_LAZO', true),
(90405800, 904, 'DET_HUMO', 'LBL_DET_HUMO', true),
(90405900, 904, 'PULSADOR', 'LBL_PULSADOR', true),
(90406000, 904, 'SIRENA', 'LBL_SIRENA', true),
(90406100, 904, 'CENTRAL_CT', 'LBL_CENTRAL_CT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(904, 90400009, 90402700),
(904, 90400010, 90403300),
(904, 90400010, 90405600),
(904, 90400010, 90405700),
(904, 90400010, 90406500),
(904, 90400010, 90407000),
(904, 90400010, 90407500),
(904, 90400011, 90405800),
(904, 90400011, 90405900),
(904, 90400011, 90406000),
(904, 90400011, 90406100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(9, 904, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(10, 904, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(11, 904, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90402700, 'Element:27:4', 000027, 'PLC_CT04', 1863, 397, false, NULL, true, false, NULL, true),
(90403300, 'Element:33:8', 00003300, 'CT04-TR3-SE4', 1088, 524, false, NULL, true, false, NULL, true),
(90403300, 'Element:33:7', 00003300, 'CT04-TR2-SE4', 1068, 524, false, NULL, true, false, NULL, true),
(90403300, 'Element:33:6', 00003300, 'CT04-TR1-SE4', 1048, 524, false, NULL, true, false, NULL, true),
(90405600, 'Element:56:8', 00005601, '853.TUPS.1.SE4', 515, 524, false, NULL, true, false, NULL, true),
(90405600, 'Element:56:7', 00005601, '853.TFYA.1.SE4', 411, 524, false, NULL, true, false, NULL, true),
(90406500, 'Element:65:4', 00006502, 'CT01-UPS-001', 78, 529, false, NULL, true, false, NULL, true),
(90407000, 'Element:70:12', 00007003, 'CT04-TD1-002', 1439, 524, false, NULL, true, false, NULL, true),
(90407000, 'Element:70:11', 00007003, 'CT04-TD1-001', 1397, 524, false, NULL, true, false, NULL, true),
(90407000, 'Element:70:14', 00007003, 'CT04-TCCMP-002', 699, 524, false, NULL, true, false, NULL, true),
(90407000, 'Element:70:13', 00007003, 'CT04-TCCMP-001', 609, 524, false, NULL, true, false, NULL, true),
(90407000, 'Element:70:16', 00007003, 'CT04-TUPS-001', 495, 524, false, NULL, true, false, NULL, true),
(90407000, 'Element:70:15', 00007003, 'CT04-TFYA-001', 391, 524, false, NULL, true, false, NULL, true),
(90407500, 'Element:75:5', 00007504, 'CT04-GE-001', 1624, 541, false, NULL, true, false, NULL, true),
(90407500, 'Element:75:6', 00007504, 'CT04-GE-002', 1829, 544, false, NULL, true, false, NULL, true),
(90405800, 'Element:58:10', 00005805, 'CT04-CCI-001.Z2PUL2', 987, 578, false, NULL, true, false, NULL, true),
(90405800, 'Element:58:9', 00005805, 'CT04-CCI-001.Z2PUL1', 480, 579, false, NULL, true, false, NULL, true),
(90405800, 'Element:58:8', 00005805, 'CT04-CCI-001.Z1PUL1', 119, 519, false, NULL, true, false, NULL, true),
(90405800, 'Element:58:11', 00005805, 'CT04-CCI-001.Z2PUL3', 1492, 582, false, NULL, true, false, NULL, true),
(90405900, 'Element:59:26', 00005906, 'CT04-CCI-001.Z2DO1', 374, 558, false, NULL, true, false, NULL, true),
(90405900, 'Element:59:25', 00005906, 'CT04-CCI-001.Z1DO1', 119, 559, false, NULL, true, false, NULL, true),
(90405900, 'Element:59:31', 00005906, 'CT04-CCI-001.Z3DT2', 1789, 566, false, NULL, true, false, NULL, true),
(90405900, 'Element:59:30', 00005906, 'CT04-CCI-001.Z3DT1', 1640, 575, false, NULL, true, false, NULL, true),
(90405900, 'Element:59:28', 00005906, 'CT04-CCI-001.Z2DO3', 1161, 557, false, NULL, true, false, NULL, true),
(90405900, 'Element:59:27', 00005906, 'CT04-CCI-001.Z2DO2', 783, 557, false, NULL, true, false, NULL, true),
(90405900, 'Element:59:29', 00005906, 'CT04-CCI-001.Z2DO4', 1478, 556, false, NULL, true, false, NULL, true),
(90406000, 'Element:60:17', 00006007, 'CT04-CCI-001.Z2SIR4', 1494, 556, false, NULL, true, false, NULL, true),
(90406000, 'Element:60:15', 00006007, 'CT04-CCI-001.Z2SIR2', 801, 557, false, NULL, true, false, NULL, true),
(90406000, 'Element:60:14', 00006007, 'CT04-CCI-001.Z2SIR1', 497, 557, false, NULL, true, false, NULL, true),
(90406000, 'Element:60:13', 00006007, 'CT04-CCI-001.Z1SIR1', 150, 559, false, NULL, true, false, NULL, true),
(90406000, 'Element:60:16', 00006007, 'CT04-CCI-001.Z2SIR3', 970, 559, false, NULL, true, false, NULL, true),
(90406100, 'Element:61:1', 00006108, 'CT01-CCI-001', 139, 519, false, NULL, true, false, NULL, true);

