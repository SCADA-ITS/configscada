-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(602, 'SYNOPTIC_CH_COMMS', 'SYNOPTIC_CH_COMMS', 'LBL_SYNOPTIC_CH_COMMS', NULL, 'img/synoptics/Ausol_CH_Tunel_Comms.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(60200000, 602, 'LAYER_MEG', 'LBL_LAYER_MEG', true),
(60200500, 602, 'LAYER_MEG_CENTRAL', 'LBL_LAYER_MEG_CENTRAL', true),
(60201600, 602, 'LAYER_MEG_ZONE', 'LBL_LAYER_MEG_ZONE', true),
(60203000, 602, 'LAYER_MEG_RADIO', 'LBL_LAYER_MEG_RADIO', true),
(60200001, 602, 'LAYER_COMMS', 'LBL_LAYER_COMMS', true),
(60201100, 602, 'LAYER_COMMS_SOS', 'LBL_LAYER_COMMS_SOS', true),
(60202600, 602, 'LAYER_COMMS_SWITCH', 'LBL_LAYER_COMMS_SWITCH', true),
(60202700, 602, 'LAYER_COMMS_PLC', 'LBL_LAYER_COMMS_PLC', true),
(60207400, 602, 'LAYER_COMMS_ERU', 'LBL_LAYER_COMMS_ERU', true),
(60200002, 602, 'LAYER_DAI', 'LBL_LAYER_DAI', true),
(60202800, 602, 'LAYER_DAI_NAS', 'LBL_LAYER_DAI_NAS', true),
(60205200, 602, 'LAYER_DAI_SERVER', 'LBL_LAYER_DAI_SERVER', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(602, 60200000, 60200500),
(602, 60200000, 60201600),
(602, 60200000, 60203000),
(602, 60200001, 60201100),
(602, 60200001, 60202600),
(602, 60200001, 60202700),
(602, 60200001, 60207400),
(602, 60200002, 60202800),
(602, 60200002, 60205200);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(3, 602, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(4, 602, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(5, 602, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(60200500, 'Element:5:5', 01010005, 'CH-DAC-1', 1710, 312, false, NULL, true, false, NULL, true),
(60201600, 'Element:16:46', 01000016, 'CH-RADIO', 1614, 22, false, NULL, true, false, NULL, true),
(60201600, 'Element:16:45', 01000016, 'CH-MEG-4CA', 499, 393, false, NULL, true, false, NULL, true),
(60201600, 'Element:16:44', 01000016, 'CH-MEG-3CA', 839, 393, false, NULL, true, false, NULL, true),
(60201600, 'Element:16:43', 01000016, 'CH-MEG-2CA', 1167, 393, false, NULL, true, false, NULL, true),
(60201600, 'Element:16:42', 01000016, 'CH-MEG-1CA', 1390, 393, false, NULL, true, false, NULL, true),
(60201600, 'Element:16:41', 01000016, 'CH-MEG-4MA', 1137, 704, false, NULL, true, false, NULL, true),
(60201600, 'Element:16:40', 01000016, 'CH-MEG-3MA', 825, 704, false, NULL, true, false, NULL, true),
(60201600, 'Element:16:39', 01000016, 'CH-MEG-2MA', 486, 704, false, NULL, true, false, NULL, true),
(60201600, 'Element:16:38', 01000016, 'CH-MEG-1MA', 259, 704, false, NULL, true, false, NULL, true),
(60203000, 'Element:30:3', 01000030, 'CH-MOD-1', 1710, 249, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:37', 01010011, 'CH-SOSE-2MA', 1416, 977, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:42', 01010011, 'CH-SOS-3MA', 1138, 962, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:41', 01010011, 'CH-SOS-2MA', 854, 964, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:40', 01010011, 'CH-SOS-1MA', 591, 963, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:39', 01010011, 'CH-SOSE-1MA', 282, 977, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:38', 01010011, 'CH-SOSE-2CA', 284, 133, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:45', 01010011, 'CH-SOS-3CA', 583, 144, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:44', 01010011, 'CH-SOS-2CA', 875, 144, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:43', 01010011, 'CH-SOS-1CA', 1100, 149, false, NULL, true, false, NULL, true),
(60201100, 'Element:11:36', 01010011, 'CH-SOSE-1CA', 1414, 132, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:49', 01000026, 'CH-SWG-CA', 849, 553, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:98', 01000026, 'CH-SWE-BM', 1693, 914, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:48', 01000026, 'CH-SWS-3MA', 1165, 912, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:47', 01000026, 'CH-SWS-2MA', 877, 914, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:46', 01000026, 'CH-SWS-1MA', 616, 913, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:45', 01000026, 'CH-SWS-3CA', 607, 194, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:44', 01000026, 'CH-SWS-2CA', 899, 194, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:43', 01000026, 'CH-SWS-1CA', 1123, 200, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:51', 01000026, 'CH-SWL', 1710, 383, false, NULL, true, false, NULL, true),
(60202600, 'Element:26:50', 01000026, 'CH-SWE-BC', 69, 833, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:56', 01020027, 'CH-PLCG-CA', 899, 553, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:55', 01020027, 'CH-PLCSI-3MA', 1188, 962, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:54', 01020027, 'CH-PLCSI-2MA', 904, 964, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:53', 01020027, 'CH-PLCSI-1MA', 641, 963, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:52', 01020027, 'CH-PLCSI-3CA', 633, 144, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:51', 01020027, 'CH-PLCSI-2CA', 925, 144, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:50', 01020027, 'CH-PLCSI-1CA', 1150, 149, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:57', 01020027, 'CH-PLCL-1', 1846, 249, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:58', 01020027, 'CH-PLC-CGBT', 1712, 640, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:59', 01020027, 'CH-PLCE-BM', 1693, 822, false, NULL, true, false, NULL, true),
(60202700, 'Element:27:60', 01020027, 'CH-PLCE-BC', 69, 754, false, NULL, true, false, NULL, true),
(60207400, 'Element:74:14', 01000074, 'CH-ERU-1-SLAVE', 1846, 313, false, NULL, true, false, NULL, true),
(60207400, 'Element:74:13', 01000074, 'CH-ERU-1-MASTER', 1796, 313, false, NULL, true, false, NULL, true),
(60202800, 'Element:28:6', 01000028, 'CH-NAS-1', 1846, 382, false, NULL, true, false, NULL, true),
(60205200, 'Element:52:5', 01000052, 'CH-DAI', 1795, 249, false, NULL, true, false, NULL, true);

