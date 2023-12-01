-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(933, 'TREE_D4-R7', 'TREE_D4-R7', 'LBL_TREE_D4-R7', NULL, 'img/synoptics/Bratislava_VMS_INCIDENT.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(93307101, 933, 'VMS', 'LBL_VMS', true),
(93307102, 933, 'VMS', 'LBL_VMS', true),
(93307103, 933, 'VMS', 'LBL_VMS', true),
(93302300, 933, 'INCIDENT', 'LBL_INCIDENT', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(125, 933, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(126, 933, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(127, 933, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(128, 933, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(93307101, 'Element:71:61', 01007100, 'NRLMP01P', 1653, 999, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:2', 01007100, 'NRLKD08L', 1401, 312, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:3', 01007100, 'NRLKD09L', 1401, 248, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:46', 01007100, 'NRLJS494L', 100, 816, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:36', 01007100, 'NRLJS497L', 100, 984, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:43', 01007100, 'NRLJS403P', 350, 912, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:44', 01007100, 'NRLJS495L', 100, 880, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:45', 01007100, 'NRLJS404P', 350, 848, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:47', 01007100, 'NRLJS405P', 350, 784, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:48', 01007100, 'NRLJS406P', 350, 690, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:50', 01007100, 'NRLJS407P', 350, 624, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:49', 01007100, 'NRLJS492L', 100, 689, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:51', 01007100, 'NRLJS491L', 100, 622, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:52', 01007100, 'NRLJS408P', 350, 560, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:53', 01007100, 'NRLJS490L', 100, 533, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:72', 01007100, 'NRLJS410P', 350, 497, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:6', 01007100, 'NRJS488L', 100, 417, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:7', 01007100, 'NRJS412P', 350, 390, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:8', 01007100, 'NRJS487L', 100, 351, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:9', 01007100, 'NRJS414P', 350, 328, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:10', 01007100, 'NRJS485L', 100, 287, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:11', 01007100, 'NRJS415P', 350, 262, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:12', 01007100, 'NRJS484L', 100, 221, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:13', 01007100, 'NRJS416P', 350, 196, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:17', 01007100, 'NRJS419P', 868, 1003, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:18', 01007100, 'NRJS480L', 615, 978, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:19', 01007100, 'NRJS420P', 868, 940, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:20', 01007100, 'NRJS478L', 615, 882, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:21', 01007100, 'NRJS422P', 868, 829, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:22', 01007100, 'NRJS477L', 615, 817, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:23', 01007100, 'NRJS423P', 868, 766, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:24', 01007100, 'NRJS476L', 615, 752, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:25', 01007100, 'NRJS424P', 868, 700, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:27', 01007100, 'NRJS425P', 868, 637, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:26', 01007100, 'NRJS475L', 615, 688, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:28', 01007100, 'NRJS474L', 615, 625, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:29', 01007100, 'NRJS426P_2', 995, 560, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:30', 01007100, 'NRJS426P', 868, 560, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:33', 01007100, 'NRJS428P', 868, 433, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:35', 01007100, 'NRJS469L', 615, 372, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:58', 01007100, 'NRSR434P', 868, 180, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:59', 01007100, 'NRSR464L', 615, 130, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:4', 01007100, 'NRLKD10L', 1401, 184, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:5', 01007100, 'NRLKD11L', 1401, 118, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:60', 01007100, 'NRSR463L', 615, 67, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:15', 01007100, 'NRJS418P', 350, 96, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:14', 01007100, 'NRJS482L', 100, 122, false, NULL, true, false, NULL, true),
(93307101, 'Element:71:16', 01007100, 'NRJS481L', 100, 58, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:32', 02007101, 'NRJS472L', 535, 498, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:63', 02007101, 'NRLPK08P', 1654, 842, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:65', 02007101, 'NRLPK11P', 1654, 769, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:68', 02007101, 'NRLPK15P', 1654, 669, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:69', 02007101, 'NRLPK17P', 1654, 605, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:62', 02007101, 'NRLPK05L', 1322, 906, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:64', 02007101, 'NRLPK10L', 1322, 832, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:66', 02007101, 'NRLPK12L', 1322, 749, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:67', 02007101, 'NRLPK13L', 1322, 685, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:70', 02007101, 'NRLPK18L', 1322, 579, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:1', 02007101, 'NRLKD01L', 1322, 441, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:73', 02007101, 'NRLKD03L', 1322, 377, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:71', 02007101, 'NRLPK19P', 1654, 541, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:54', 02007101, 'NRSR431P', 868, 318, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:55', 02007101, 'NRSR432P', 868, 256, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:56', 02007101, 'NRSR467L', 535, 257, false, NULL, true, false, NULL, true),
(93307102, 'Element:71:57', 02007101, 'NRSR466L', 535, 193, false, NULL, true, false, NULL, true),
(93307103, 'Element:71:31', 03007102, 'NRJS473L', 655, 562, false, NULL, true, false, NULL, true),
(93307103, 'Element:71:34', 03007102, 'NRJS470L', 655, 434, false, NULL, true, false, NULL, true),
(93302300, 'Element:23:1', 000023, 'Imanager', 1811, 66, false, NULL, true, false, NULL, true);

