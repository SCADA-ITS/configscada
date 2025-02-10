-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(801, 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L1', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L1', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L1', NULL, 'img/synoptics/hospital_p1.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(80108400, 801, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(80103100, 801, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(80108900, 801, 'SYNOPTIC_LAYERS_CTROL_ACCESOS', 'LBL_SYNOPTIC_LAYERS_CTROL_ACCESOS', true),
(80104900, 801, 'SYNOPTIC_LAYERS_ASCENSORES', 'LBL_SYNOPTIC_LAYERS_ASCENSORES', true),
(80100019, 801, 'SYNOPTIC_LAYERS_DET_INCENDIOS', 'LBL_SYNOPTIC_LAYERS_DET_INCENDIOS', true),
(80105800, 801, 'SYNOPTIC_LAYERS_DET_DET_HUMOS', 'LBL_SYNOPTIC_LAYERS_DET_DET_HUMOS', true),
(80105900, 801, 'SYNOPTIC_LAYERS_DET_DET_PULSADOR', 'LBL_SYNOPTIC_LAYERS_DET_DET_PULSADOR', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(801, 80100019, 80105800),
(801, 80100019, 80105900);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(39, 801, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(40, 801, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(41, 801, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(4002, 801, 3, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench',false,104601, NULL,2,true, NULL,false,true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(80108400, 'Element:84:33', 020084, 'HOSP-ILUM-L1-P5', 785, 298, false, NULL, true, false, NULL, true),
(80108400, 'Element:84:30', 020084, 'HOSP-ILUM-L1-P2', 610, 298, false, NULL, true, false, NULL, true),
(80108400, 'Element:84:36', 020084, 'HOSP-ILUM-L1-P8', 1149, 300, false, NULL, true, false, NULL, true),
(80108400, 'Element:84:34', 020084, 'HOSP-ILUM-L1-P6', 984, 586, false, NULL, true, false, NULL, true),
(80108400, 'Element:84:32', 020084, 'HOSP-ILUM-L1-P4', 785, 100, false, NULL, true, false, NULL, true),
(80108400, 'Element:84:29', 020084, 'HOSP-ILUM-L1-P1', 473, 70, false, NULL, true, false, NULL, true),
(80108400, 'Element:84:35', 020084, 'HOSP-ILUM-L1-P7', 1147, 88, false, NULL, true, false, NULL, true),
(80108400, 'Element:84:31', 020084, 'HOSP-ILUM-L1-P3', 533, 482, false, NULL, true, false, NULL, true),
(80108400, 'Element:84:37', 020084, 'HOSP-ILUM-L1-P9', 1320, 470, false, NULL, true, false, NULL, true),
(80108900, 'Element:89:4', 010089, 'HOSP-PTA-S', 1313, 507, false, NULL, true, false, NULL, true),
(80108900, 'Element:89:3', 010089, 'HOSP-PTA-N', 1314, 268, false, NULL, true, false, NULL, true),
(80108900, 'Element:89:5', 010089, 'HOSP-PTA-O', 627, 645, false, NULL, true, false, NULL, true),
(80103100, 'Element:31:2', 010031, 'HOSP-FOT', 234, 14, false, NULL, true, false, NULL, true),
(80104900, 'Element:49:1', 000049, 'HOSP-ELEV-NO', 528, 189, false, NULL, true, false, NULL, true),
(80104900, 'Element:49:6', 000049, 'HOSP-ELEV-NE', 1109, 240, false, NULL, true, false, NULL, true),
(80104900, 'Element:49:4', 000049, 'HOSP-ELEV-NC', 921, 317, false, NULL, true, false, NULL, true),
(80104900, 'Element:49:7', 000049, 'HOSP-ELEV-CE', 1291, 414, false, NULL, true, false, NULL, true),
(80104900, 'Element:49:3', 000049, 'HOSP-ELEV-SO', 502, 520, false, NULL, true, false, NULL, true),
(80104900, 'Element:49:2', 000049, 'HOSP-ELEV-CO', 623, 331, false, NULL, true, false, NULL, true),
(80104900, 'Element:49:5', 000049, 'HOSP-ELEV-SC', 660, 605, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:5', 010058, 'HOSP-PUL-L1-REH', 471, 170, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:10', 010058, 'HOSP-PUL-L1-TOW', 959, 414, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:4', 010058, 'HOSP-PUL-L1-ORTH', 539, 98, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:8', 010058, 'HOSP-PUL-L1-SPIC', 762, 244, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:7', 010058, 'HOSP-PUL-L1-ENT', 765, 173, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:9', 010058, 'HOSP-PUL-L1-CENT', 957, 268, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:11', 010058, 'HOSP-PUL-L1-HEON', 959, 495, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:17', 010058, 'HOSP-PUL-L1-OUT2', 1130, 716, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:15', 010058, 'HOSP-PUL-L1-EME', 1172, 240, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:6', 010058, 'HOSP-PUL-L1-VAR', 487, 302, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:14', 010058, 'HOSP-PUL-L1-OURE', 1122, 164, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:13', 010058, 'HOSP-PUL-L1-END', 1017, 524, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:16', 010058, 'HOSP-PUL-L1-OUT1', 896, 745, false, NULL, true, false, NULL, true),
(80105800, 'Element:58:12', 010058, 'HOSP-PUL-L1-RHE', 1011, 406, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:18', 010059, 'HOSP-HUM-L1-EME', 1178, 577, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:14', 010059, 'HOSP-HUM-L1-HEON', 903, 268, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:7', 010059, 'HOSP-HUM-L1-ORTH', 578, 28, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:20', 010059, 'HOSP-HUM-L1-OUT2', 1158, 793, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:8', 010059, 'HOSP-HUM-L1-REH', 393, 184, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:11', 010059, 'HOSP-HUM-L1-SPIC', 723, 259, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:16', 010059, 'HOSP-HUM-L1-END', 1176, 182, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:12', 010059, 'HOSP-HUM-L1-CENT', 782, 418, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:10', 010059, 'HOSP-HUM-L1-ENT', 716, 102, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:13', 010059, 'HOSP-HUM-L1-TOW', 734, 528, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:15', 010059, 'HOSP-HUM-L1-RHE', 1101, 137, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:17', 010059, 'HOSP-HUM-L1-OURE', 1208, 367, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:19', 010059, 'HOSP-HUM-L1-OUT1', 955, 793, false, NULL, true, false, NULL, true),
(80105900, 'Element:59:9', 010059, 'HOSP-HUM-L1-VAR', 444, 371, false, NULL, true, false, NULL, true);

