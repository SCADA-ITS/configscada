-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(803, 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L3', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L3', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L3', NULL, 'img/synoptics/hospital_p3.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(80308400, 803, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(80303100, 803, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(80308900, 803, 'SYNOPTIC_LAYERS_CTROL_ACCESOS', 'LBL_SYNOPTIC_LAYERS_CTROL_ACCESOS', true),
(80304900, 803, 'SYNOPTIC_LAYERS_ASCENSORES', 'LBL_SYNOPTIC_LAYERS_ASCENSORES', true),
(80300021, 803, 'SYNOPTIC_LAYERS_DET_INCENDIOS', 'LBL_SYNOPTIC_LAYERS_DET_INCENDIOS', true),
(80305800, 803, 'SYNOPTIC_LAYERS_DET_DET_HUMOS', 'LBL_SYNOPTIC_LAYERS_DET_DET_HUMOS', true),
(80305900, 803, 'SYNOPTIC_LAYERS_DET_DET_PULSADOR', 'LBL_SYNOPTIC_LAYERS_DET_DET_PULSADOR', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(803, 80300021, 80305800),
(803, 80300021, 80305900);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(45, 803, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(46, 803, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(47, 803, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(80308400, 'Element:84:45', 020084, 'HOSP-ILUM-L3-P1', 450, 328, false, NULL, true, false, NULL, true),
(80308400, 'Element:84:48', 020084, 'HOSP-ILUM-L3-P4', 920, 328, false, NULL, true, false, NULL, true),
(80308400, 'Element:84:47', 020084, 'HOSP-ILUM-L3-P3', 920, 108, false, NULL, true, false, NULL, true),
(80308400, 'Element:84:49', 020084, 'HOSP-ILUM-L3-P5', 1010, 398, false, NULL, true, false, NULL, true),
(80308400, 'Element:84:46', 020084, 'HOSP-ILUM-L3-P2', 471, 688, false, NULL, true, false, NULL, true),
(80303100, 'Element:31:2', 010031, 'HOSP-FOT', 86, 21, false, NULL, true, false, NULL, true),
(80304900, 'Element:49:6', 000049, 'HOSP-ELEV-NE', 890, 268, false, NULL, true, false, NULL, true),
(80304900, 'Element:49:4', 000049, 'HOSP-ELEV-NC', 714, 347, false, NULL, true, false, NULL, true),
(80304900, 'Element:49:5', 000049, 'HOSP-ELEV-SC', 463, 618, false, NULL, true, false, NULL, true),
(80304900, 'Element:49:7', 000049, 'HOSP-ELEV-CE', 1027, 441, false, NULL, true, false, NULL, true),
(80305800, 'Element:58:32', 010058, 'HOSP-PUL-L3-NEU', 950, 138, false, NULL, true, false, NULL, true),
(80305800, 'Element:58:31', 010058, 'HOSP-PUL-L3-NEP', 890, 208, false, NULL, true, false, NULL, true),
(80305800, 'Element:58:29', 010058, 'HOSP-PUL-L3-CENT3', 510, 318, false, NULL, true, false, NULL, true),
(80305800, 'Element:58:28', 010058, 'HOSP-PUL-L3-CENT2', 722, 308, false, NULL, true, false, NULL, true),
(80305800, 'Element:58:33', 010058, 'HOSP-PUL-L3-OUT', 990, 431, false, NULL, true, false, NULL, true),
(80305800, 'Element:58:27', 010058, 'HOSP-PUL-L3-CENT1', 660, 355, false, NULL, true, false, NULL, true),
(80305800, 'Element:58:30', 010058, 'HOSP-PUL-L3-TOW', 503, 658, false, NULL, true, false, NULL, true),
(80305900, 'Element:59:35', 010059, 'HOSP-HUM-L3-NEU', 950, 178, false, NULL, true, false, NULL, true),
(80305900, 'Element:59:34', 010059, 'HOSP-HUM-L3-NEP', 890, 178, false, NULL, true, false, NULL, true),
(80305900, 'Element:59:31', 010059, 'HOSP-HUM-L3-CENT2', 722, 258, false, NULL, true, false, NULL, true),
(80305900, 'Element:59:32', 010059, 'HOSP-HUM-L3-CENT3', 510, 153, false, NULL, true, false, NULL, true),
(80305900, 'Element:59:30', 010059, 'HOSP-HUM-L3-CENT1', 670, 456, false, NULL, true, false, NULL, true),
(80305900, 'Element:59:36', 010059, 'HOSP-HUM-L3-OUT', 915, 449, false, NULL, true, false, NULL, true),
(80305900, 'Element:59:33', 010059, 'HOSP-HUM-L3-TOW', 630, 578, false, NULL, true, false, NULL, true);

