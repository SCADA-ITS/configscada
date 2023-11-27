-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(903, 'TREE_CT_3', 'TREE_CT_3', 'LBL_TREE_CT_3', NULL, 'img/synoptics/CT3.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90300006, 903, 'CONTROL', 'LBL_CONTROL', true),
(90302700, 903, 'PLC', 'LBL_PLC', true),
(90300007, 903, 'ELECTRICIDAD', 'LBL_ELECTRICIDAD', true),
(90303300, 903, 'TRAFO', 'LBL_TRAFO', true),
(90305600, 903, 'UNIFILAR', 'LBL_UNIFILAR', true),
(90305700, 903, 'CELDA_MT', 'LBL_CELDA_MT', true),
(90306500, 903, 'UPS', 'LBL_UPS', true),
(90307000, 903, 'ANALIZADOR_RED', 'LBL_ANALIZADOR_RED', true),
(90307500, 903, 'GE', 'LBL_GE', true),
(90300008, 903, 'DET_LAZO', 'LBL_DET_LAZO', true),
(90305800, 903, 'DET_HUMO', 'LBL_DET_HUMO', true),
(90305900, 903, 'PULSADOR', 'LBL_PULSADOR', true),
(90306000, 903, 'SIRENA', 'LBL_SIRENA', true),
(90306100, 903, 'CENTRAL_CT', 'LBL_CENTRAL_CT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(903, 90300006, 90302700),
(903, 90300007, 90303300),
(903, 90300007, 90305600),
(903, 90300007, 90305700),
(903, 90300007, 90306500),
(903, 90300007, 90307000),
(903, 90300007, 90307500),
(903, 90300008, 90305800),
(903, 90300008, 90305900),
(903, 90300008, 90306000),
(903, 90300008, 90306100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(6, 903, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(7, 903, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(8, 903, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90302700, 'Element:27:3', 000027, 'PLC_CT03', 1480, 63, false, NULL, true, false, NULL, true),
(90303300, 'Element:33:5', 000033, 'CT03-TR1-SE3', 756, 120, false, NULL, true, false, NULL, true),
(90305600, 'Element:56:5', 000056, '853.TFYA.1.SE3', 1233, 611, false, NULL, true, false, NULL, true),
(90305600, 'Element:56:6', 000056, '853.TUPS.1.SE3', 1152, 978, false, NULL, true, false, NULL, true),
(90305700, 'Element:57:607', 000057, 'CT03-CLI1-SE3', 694, 120, false, NULL, true, false, NULL, true),
(90305700, 'Element:57:608', 000057, 'CT03-CPF1-SE3', 664, 120, false, NULL, true, false, NULL, true),
(90306500, 'Element:65:3', 000065, 'CT03-UPS-001', 747, 708, false, NULL, true, false, NULL, true),
(90307000, 'Element:70:10', 000070, 'CT03-TUPS-001', 1212, 1008, false, NULL, true, false, NULL, true),
(90307000, 'Element:70:9', 000070, 'CT03-TFYA-001', 1203, 611, false, NULL, true, false, NULL, true),
(90307500, 'Element:75:1', 000075, 'CT02-GE-001', 1369, 303, false, NULL, true, false, NULL, true),
(90305800, 'Element:58:7', 000058, 'CT03-CCI-001.Z1PUL1', 422, 231, false, NULL, true, false, NULL, true),
(90305800, 'Element:58:6', 000058, 'CT03-CCI-001.Z3PUL1', 1136, 524, false, NULL, true, false, NULL, true),
(90305800, 'Element:58:5', 000058, 'CT03-CCI-001.Z1PUL2', 900, 737, false, NULL, true, false, NULL, true),
(90305900, 'Element:59:20', 000059, 'CT03-CCI-001.Z1DO3', 902, 466, false, NULL, true, false, NULL, true),
(90305900, 'Element:59:24', 000059, 'CT03-CCI-001.Z1DO1', 577, 204, false, NULL, true, false, NULL, true),
(90305900, 'Element:59:23', 000059, 'CT03-CCI-001.Z1DO2', 1079, 257, false, NULL, true, false, NULL, true),
(90305900, 'Element:59:22', 000059, 'CT03-CCI-001.Z3DT1', 1274, 257, false, NULL, true, false, NULL, true),
(90305900, 'Element:59:21', 000059, 'CT03-CCI-001.Z3DT2', 1472, 257, false, NULL, true, false, NULL, true),
(90305900, 'Element:59:19', 000059, 'CT03-CCI-001.Z1DO4', 1247, 767, false, NULL, true, false, NULL, true),
(90305900, 'Element:59:18', 000059, 'CT03-CCI-001.Z2DO1', 635, 794, false, NULL, true, false, NULL, true),
(90306000, 'Element:60:12', 000060, 'CT03-CCI-001.Z1SIR1', 422, 201, false, NULL, true, false, NULL, true),
(90306000, 'Element:60:10', 000060, 'CT03-CCI-001.Z2SIR1', 641, 602, false, NULL, true, false, NULL, true),
(90306000, 'Element:60:11', 000060, 'CT03-CCI-001.Z3SIR1', 1136, 554, false, NULL, true, false, NULL, true),
(90306000, 'Element:60:9', 000060, 'CT03-CCI-001.Z1SIR2', 900, 767, false, NULL, true, false, NULL, true),
(90306100, 'Element:61:1', 000061, 'CT01-CCI-001', 418, 300, false, NULL, true, false, NULL, true);

