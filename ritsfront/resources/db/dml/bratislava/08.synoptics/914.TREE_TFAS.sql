-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(914, 'TREE_TFAS', 'TREE_TFAS', 'LBL_SYNOPTIC_TFAS', NULL, 'img/synoptics/Bratislava_ETD_TFA.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91405100, 914, 'TFAs', 'LBL_SYNOPTIC_TFAS', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(39, 914, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(40, 914, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(41, 914, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(106, 914, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91405100, 'Element:51:46', 000051, 'RNRJS05L-2+946', 524, 1022, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:45', 000051, 'RNRJS04L-2+256', 524, 985, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:44', 000051, 'SCJS03L-2+441', 524, 948, false, NULL, true, false, NULL, true), --se pide por parte del cliente borrar este equipo del scada
(91405100, 'Element:51:43', 000051, 'RNRJS03P-2+314', 524, 912, false, NULL, true, false, NULL, true),--se pide por parte del cliente borrar este equipo del scada
(91405100, 'Element:51:42', 000051, 'RNRJS02L-1+965', 524, 875, false, NULL, true, false, NULL, true),--se pide por parte del cliente borrar este equipo del scada
(91405100, 'Element:51:41', 000051, 'SCJS02P-1+896', 524, 838, false, NULL, true, false, NULL, true),--se pide por parte del cliente borrar este equipo del scada
(91405100, 'Element:51:52', 000051, 'RADPJS20P-0+402 VETVY PS', 697, 1023, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:51', 000051, 'RNRJS17P-8+678', 697, 986, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:50', 000051, 'RNRJS14P-7+894', 697, 949, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:49', 000051, 'RNRJS09L-4+289', 697, 912, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:48', 000051, 'RNRJS08L-3+803', 697, 874, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:47', 000051, 'RNRJS07L-3+515', 697, 837, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:73', 000051, 'RNRPK15L-5+994', 162, 457, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:72', 000051, 'RNRPK14L-5+653', 162, 421, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:71', 000051, 'RNRPK13L-5+582', 162, 384, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:70', 000051, 'RNRPK11L-4+607', 162, 347, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:65', 000051, 'RNRPK04L-1+065', 162, 163, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:66', 000051, 'RNRPK06L-1+923', 162, 200, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:67', 000051, 'RNRPK07L-2+597', 162, 237, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:68', 000051, 'RNRPK08L-2+853', 162, 273, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:69', 000051, 'RNRPK09L-4+153', 162, 310, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:63', 000051, 'RNRPK01L-0+412', 160, 54, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:62', 000051, 'RNRMP01-0+193', 160, 15, false, NULL, true, false, NULL, true),--se pide por parte del cliente borrar este equipo del scada
(91405100, 'Element:51:53', 000051, 'RNRSR01P-25+13', 847, 346, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:54', 000051, 'RNRSR03P-25+685', 847, 310, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:55', 000051, 'RNRSR05P-26+361', 847, 273, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:56', 000051, 'SCSR01P-26+58', 847, 237, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:57', 000051, 'RNRSR06P-27+015', 847, 200, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:58', 000051, 'RNRSR07L-27+113', 847, 164, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:59', 000051, 'RNRSR08P-28+328', 847, 127, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:60', 000051, 'SCSR02P-0+234 V4', 847, 91, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:61', 000051, 'SCSR03P-0+262 V3', 847, 54, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:21', 000051, 'RNRJS40P-20+451', 1271, 528, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:22', 000051, 'RNRJS41P-21+09', 1271, 492, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:23', 000051, 'RNRJS42P-21+608', 1271, 456, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:24', 000051, 'RNRJS43P-21+914', 1271, 419, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:25', 000051, 'RNRJS44P-22+606', 1271, 383, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:26', 000051, 'RNRJS45P-23+018', 1271, 347, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:27', 000051, 'RNRJS46P-23+406', 1271, 310, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:28', 000051, 'RNRJS47P-23+598', 1271, 274, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:40', 000051, 'SCJS05P-23+849', 1271, 238, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:29', 000051, 'RNRJS48P-24+27', 1271, 201, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:30', 000051, 'RNRJS49P-24+614', 1271, 165, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:11', 000051, 'RNRJS30P-14+062', 1492, 531, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:12', 000051, 'RNRJS31P-14+498', 1492, 494, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:13', 000051, 'RNRJS32P-15+274', 1492, 457, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:14', 000051, 'RNRJS33P-15+698', 1492, 419, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:15', 000051, 'RNRJS34P-16+363', 1492, 384, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:16', 000051, 'RNRJS35P-16+96', 1492, 347, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:17', 000051, 'RNRJS36P-17+52', 1492, 312, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:18', 000051, 'RNRJS37P-18+04', 1492, 276, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:19', 000051, 'RNRJS38P-19+015', 1492, 239, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:20', 000051, 'RNRJS39P-19+565', 1492, 202, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:39', 000051, 'RNRKD11L-14+157', 1723, 1006, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:38', 000051, 'RNRKD10L-12+359', 1723, 969, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:37', 000051, 'RNRKD09L-10+557', 1723, 933, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:36', 000051, 'RNRKD08L-8+767', 1723, 896, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:35', 000051, 'RNRKD03L-7+217', 1723, 860, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:34', 000051, 'RNRKD01L-6+347', 1723, 824, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:2', 000051, 'SCJS04P-9+272', 1723, 567, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:3', 000051, 'RNRJS22P-9+982', 1723, 530, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:4', 000051, 'RNRJS23P-10+328', 1723, 493, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:5', 000051, 'RNRJS24P-10+901', 1723, 458, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:6', 000051, 'RNRJS25P-11+101', 1723, 423, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:7', 000051, 'RNRJS26P-11+892', 1723, 386, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:8', 000051, 'RNRJS27L-12+095', 1723, 349, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:9', 000051, 'RNRJS28P-12+736', 1723, 312, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:10', 000051, 'RNRJS29P-13+316', 1723, 275, false, NULL, true, false, NULL, true),
(91405100, 'Element:51:64', 000051, 'RNRPK03L-0+832', 162, 113, false, NULL, true, false, NULL, true);

