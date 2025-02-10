-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(802, 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L2', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L2', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L2', NULL, 'img/synoptics/hospital_p2.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(80208400, 802, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(80203100, 802, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(80208900, 802, 'SYNOPTIC_LAYERS_CTROL_ACCESOS', 'LBL_SYNOPTIC_LAYERS_CTROL_ACCESOS', true),
(80204900, 802, 'SYNOPTIC_LAYERS_ASCENSORES', 'LBL_SYNOPTIC_LAYERS_ASCENSORES', true),
(80200020, 802, 'SYNOPTIC_LAYERS_DET_INCENDIOS', 'LBL_SYNOPTIC_LAYERS_DET_INCENDIOS', true),
(80205800, 802, 'SYNOPTIC_LAYERS_DET_DET_HUMOS', 'LBL_SYNOPTIC_LAYERS_DET_DET_HUMOS', true),
(80205900, 802, 'SYNOPTIC_LAYERS_DET_DET_PULSADOR', 'LBL_SYNOPTIC_LAYERS_DET_DET_PULSADOR', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(802, 80200020, 80205800),
(802, 80200020, 80205900);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(42, 802, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(43, 802, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(44, 802, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(4003, 802, 3, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench',false,104601, NULL,2,true, NULL,false,true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(80208400, 'Element:84:44', 020084, 'HOSP-ILUM-L2-P7', 1235, 363, false, NULL, true, false, NULL, true),
(80208400, 'Element:84:42', 020084, 'HOSP-ILUM-L2-P5', 1142, 99, false, NULL, true, false, NULL, true),
(80208400, 'Element:84:41', 020084, 'HOSP-ILUM-L2-P4', 873, 298, false, NULL, true, false, NULL, true),
(80208400, 'Element:84:40', 020084, 'HOSP-ILUM-L2-P3', 419, 475, false, NULL, true, false, NULL, true),
(80208400, 'Element:84:39', 020084, 'HOSP-ILUM-L2-P2', 485, 304, false, NULL, true, false, NULL, true),
(80208400, 'Element:84:43', 020084, 'HOSP-ILUM-L2-P6', 1141, 305, false, NULL, true, false, NULL, true),
(80208400, 'Element:84:38', 020084, 'HOSP-ILUM-L2-P1', 363, 77, false, NULL, true, false, NULL, true),
(80203100, 'Element:31:2', 010031, 'HOSP-FOT', 132, 48, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:7', 000049, 'HOSP-ELEV-CE', 1289, 409, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:2', 000049, 'HOSP-ELEV-CO', 510, 329, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:3', 000049, 'HOSP-ELEV-SO', 386, 509, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:6', 000049, 'HOSP-ELEV-NE', 1105, 244, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:4', 000049, 'HOSP-ELEV-NC', 909, 320, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:1', 000049, 'HOSP-ELEV-NO', 427, 207, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:5', 000049, 'HOSP-ELEV-SC', 578, 571, false, NULL, true, false, NULL, true),
(80205800, 'Element:58:19', 010058, 'HOSP-PUL-L2-MIN', 394, 317, false, NULL, true, false, NULL, true),
(80205800, 'Element:58:18', 010058, 'HOSP-PUL-L2-VAR', 409, 177, false, NULL, true, false, NULL, true),
(80205800, 'Element:58:20', 010058, 'HOSP-PUL-L2-CENT1', 630, 258, false, NULL, true, false, NULL, true),
(80205800, 'Element:58:21', 010058, 'HOSP-PUL-L2-CENT2', 909, 277, false, NULL, true, false, NULL, true),
(80205800, 'Element:58:24', 010058, 'HOSP-PUL-L2-GAS', 1168, 99, false, NULL, true, false, NULL, true),
(80205800, 'Element:58:25', 010058, 'HOSP-PUL-L2-PED', 1111, 177, false, NULL, true, false, NULL, true),
(80205800, 'Element:58:26', 010058, 'HOSP-PUL-L2-CAR', 1205, 355, false, NULL, true, false, NULL, true),
(80205800, 'Element:58:22', 010058, 'HOSP-PUL-L2-CENT3', 876, 360, false, NULL, true, false, NULL, true),
(80205800, 'Element:58:23', 010058, 'HOSP-PUL-L2-TOW', 608, 627, false, NULL, true, false, NULL, true),
(80205900, 'Element:59:27', 010059, 'HOSP-HUM-L2-GAS', 1181, 167, false, NULL, true, false, NULL, true),
(80205900, 'Element:59:28', 010059, 'HOSP-HUM-L2-PED', 1101, 207, false, NULL, true, false, NULL, true),
(80205900, 'Element:59:22', 010059, 'HOSP-HUM-L2-MIN', 327, 392, false, NULL, true, false, NULL, true),
(80205900, 'Element:59:21', 010059, 'HOSP-HUM-L2-VAR', 337, 167, false, NULL, true, false, NULL, true),
(80205900, 'Element:59:24', 010059, 'HOSP-HUM-L2-CENT2', 894, 234, false, NULL, true, false, NULL, true),
(80205900, 'Element:59:23', 010059, 'HOSP-HUM-L2-CENT1', 630, 135, false, NULL, true, false, NULL, true),
(80205900, 'Element:59:25', 010059, 'HOSP-HUM-L2-CENT3', 869, 429, false, NULL, true, false, NULL, true),
(80205900, 'Element:59:29', 010059, 'HOSP-HUM-L2-CAR', 1097, 399, false, NULL, true, false, NULL, true),
(80205900, 'Element:59:26', 010059, 'HOSP-HUM-L2-TOW', 837, 551, false, NULL, true, false, NULL, true);

