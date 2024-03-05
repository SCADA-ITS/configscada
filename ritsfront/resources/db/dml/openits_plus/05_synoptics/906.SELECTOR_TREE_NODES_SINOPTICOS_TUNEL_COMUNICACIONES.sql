-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(906, 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_COMUNICACIONES', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_COMUNICACIONES', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_COMUNICACIONES', NULL, 'img/synoptics/Ausol_LQ_Tunel_Comm_Users.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90600009, 906, 'SYNOPTIC_LAYERS_COMUNICACIONES', 'LBL_SYNOPTIC_LAYERS_COMUNICACIONES', true),
(90600010, 906, 'SYNOPTIC_LAYERS_DAI', 'LBL_SYNOPTIC_LAYERS_DAI', true),
(90600011, 906, 'SYNOPTIC_LAYERS_MEGAFONIA', 'LBL_SYNOPTIC_LAYERS_MEGAFONIA', true),
(90607400, 906, 'SYNOPTIC_LAYERS_ERUS', 'LBL_SYNOPTIC_LAYERS_ERUS', true),
(90602700, 906, 'SYNOPTIC_LAYERS_PLCS', 'LBL_SYNOPTIC_LAYERS_PLCS', true),
(90601100, 906, 'SYNOPTIC_LAYERS_SOS', 'LBL_SYNOPTIC_LAYERS_SOS', true),
(90602600, 906, 'SYNOPTIC_LAYERS_SWITCHS', 'LBL_SYNOPTIC_LAYERS_SWITCHS', true),
(90602800, 906, 'SYNOPTIC_LAYERS_NAS', 'LBL_SYNOPTIC_LAYERS_NAS', true),
(90605200, 906, 'SYNOPTIC_LAYERS_SERVIDOR', 'LBL_SYNOPTIC_LAYERS_SERVIDOR', true),
(90600500, 906, 'SYNOPTIC_LAYERS_CENTRALES', 'LBL_SYNOPTIC_LAYERS_CENTRALES', true),
(90603000, 906, 'SYNOPTIC_LAYERS_RADIO', 'LBL_SYNOPTIC_LAYERS_RADIO', true),
(90601600, 906, 'SYNOPTIC_LAYERS_ZONAS', 'LBL_SYNOPTIC_LAYERS_ZONAS', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(906, 90600009, 90607400),
(906, 90600009, 90602700),
(906, 90600009, 90601100),
(906, 90600009, 90602600),
(906, 90600010, 90602800),
(906, 90600010, 90605200),
(906, 90600011, 90600500),
(906, 90600011, 90603000),
(906, 90600011, 90601600);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(15, 906, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(16, 906, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(17, 906, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90607400, 'Element:74:2', 000074, 'LQ-ERU-1-SLAVE', 1143, 905, false, NULL, true, false, NULL, true),
(90607400, 'Element:74:1', 000074, 'LQ-ERU-1-MASTER', 1203, 905, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:12', 000027, 'LQ-PLCSI-3MA', 1286, 955, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:9', 000027, 'LQ-PLC-1MA', 105, 475, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:8', 000027, 'LQ-PLC-1CA', 1296, 700, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:14', 000027, 'LQ-PLCL-1', 991, 700, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:6', 000027, 'LQ-PLCSI-5CA', 835, 700, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:7', 000027, 'LQ-PLCG-CA', 578, 700, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:5', 000027, 'LQ-PLCSI-4CA', 614, 144, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:11', 000027, 'LQ-PLCSI-2MA', 1089, 906, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:10', 000027, 'LQ-PLCSI-1MA', 1746, 446, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:13', 000027, 'LQ-PLCSI-4MA', 713, 460, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:1', 000027, 'LQ-PLC-CGBT', 1386, 144, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:2', 000027, 'LQ-PLCSI-1CA', 1203, 144, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:3', 000027, 'LQ-PLCSI-2CA', 1022, 144, false, NULL, true, false, NULL, true),
(90602700, 'Element:27:4', 000027, 'LQ-PLCSI-3CA', 813, 144, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:13', 00001100, 'LQ-SOSE-2MA', 1246, 700, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:12', 00001100, 'LQ-SOSE-1MA', 941, 700, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:11', 00001100, 'LQ-SOS-4MA', 785, 700, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:10', 00001100, 'LQ-SOS-3MA', 528, 700, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:9', 00001100, 'LQ-SOS-2MA', 341, 700, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:5', 00001100, 'LQ-SOS-5CA', 564, 144, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:8', 00001100, 'LQ-SOS-1MA', 1538, 700, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:7', 00001100, 'LQ-SOSE-2CA', 1553, 144, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:1', 00001100, 'LQ-SOS-1CA', 1336, 144, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:2', 00001100, 'LQ-SOS-2CA', 1153, 144, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:3', 00001100, 'LQ-SOS-3CA', 972, 144, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:4', 00001100, 'LQ-SOS-4CA', 763, 144, false, NULL, true, false, NULL, true),
(90601100, 'Element:11:6', 00001100, 'LQ-SOSE-1CA', 347, 144, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:11', 000026, 'LQ-SWE-CA', 105, 421, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:9', 000026, 'LQ-SWS-4MA', 1273, 649, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:8', 000026, 'LQ-SWS-3MA', 968, 649, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:7', 000026, 'LQ-SWS-2MA', 811, 649, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:6', 000026, 'LQ-SWS-1MA', 555, 649, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:5', 000026, 'LQ-SWS-5CA', 588, 193, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:12', 000026, 'LQ-SWL', 1746, 393, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:13', 000026, 'LQ-SWE-MA', 713, 410, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:10', 000026, 'LQ-SWG-CA', 1089, 852, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:1', 000026, 'LQ-SWS-1CA', 1361, 193, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:2', 000026, 'LQ-SWS-2CA', 1177, 193, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:3', 000026, 'LQ-SWS-3CA', 996, 193, false, NULL, true, false, NULL, true),
(90602600, 'Element:26:4', 000026, 'LQ-SWS-4CA', 787, 193, false, NULL, true, false, NULL, true),
(90602800, 'Element:28:1', 000028, 'LQ-NAS-1', 1203, 968, false, NULL, true, false, NULL, true),
(90605200, 'Element:52:1', 000052, 'LQ-DAI', 1089, 968, false, NULL, true, false, NULL, true),
(90600500, 'Element:5:1', 000005, 'LQ-DAC-1', 1143, 852, false, NULL, true, false, NULL, true),
(90603000, 'Element:30:1', 000030, 'LQ-MOD-1', 1203, 852, false, NULL, true, false, NULL, true),
(90601600, 'Element:16:5', 000016, 'LQ-MEG-1CA', 1474, 303, false, NULL, true, false, NULL, true),
(90601600, 'Element:16:6', 000016, 'LQ-MEG-2CA', 1238, 306, false, NULL, true, false, NULL, true),
(90601600, 'Element:16:7', 000016, 'LQ-MEG-3CA', 899, 305, false, NULL, true, false, NULL, true),
(90601600, 'Element:16:8', 000016, 'LQ-MEG-4CA', 572, 305, false, NULL, true, false, NULL, true),
(90601600, 'Element:16:4', 000016, 'LQ-MEG-4MA', 1318, 554, false, NULL, true, false, NULL, true),
(90601600, 'Element:16:3', 000016, 'LQ-MEG-3MA', 938, 554, false, NULL, true, false, NULL, true),
(90601600, 'Element:16:2', 000016, 'LQ-MEG-2MA', 550, 554, false, NULL, true, false, NULL, true),
(90601600, 'Element:16:1', 000016, 'LQ-MEG-1MA', 327, 554, false, NULL, true, false, NULL, true),
(90601600, 'Element:16:9', 000016, 'LQ-RADIO', 1276, 834, false, NULL, true, false, NULL, true);

