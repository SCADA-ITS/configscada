-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(907, 'TREE_EC_TFALOOPS', 'TREE_EC_TFALOOPS', 'LBL_TREE_EC_TFALOOPS', NULL, 'img/synoptics/Bratislava_EC_TFA_Loops.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90704500, 907, 'TFA_Loops', 'LBL_TFA_Loops', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(18, 907, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(19, 907, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(20, 907, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90704500, 'Element:45:18', 000045, 'RNRJS31P', 1121, 371, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:62', 000045, 'RNRMP01', 103, 14, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:63', 000045, 'RNRPK01L', 103, 41, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:65', 000045, 'RNRPK04L', 102, 123, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:66', 000045, 'RNRPK06L', 102, 150, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:67', 000045, 'RNRPK07L', 102, 177, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:68', 000045, 'RNRPK08L', 102, 205, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:69', 000045, 'RNRPK09L', 102, 233, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:70', 000045, 'RNRPK11L', 102, 260, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:71', 000045, 'RNRPK13L', 102, 288, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:73', 000045, 'RNRPK15L', 102, 342, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:72', 000045, 'RNRPK14L', 102, 315, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:61', 000045, 'SCSR03P', 638, 41, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:60', 000045, 'SCSR02P', 638, 67, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:59', 000045, 'RNRSR08P', 638, 94, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:58', 000045, 'RNRSR07L', 638, 123, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:57', 000045, 'RNRSR06P', 638, 151, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:56', 000045, 'SCSR01P', 638, 177, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:55', 000045, 'RNRSR05P', 638, 205, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:53', 000045, 'RNRSR01P', 638, 260, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:54', 000045, 'RNRSR03P', 638, 233, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:26', 000045, 'RNRJS39P', 1121, 152, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:25', 000045, 'RNRJS38P', 1121, 178, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:24', 000045, 'RNRJS37P', 1121, 205, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:23', 000045, 'RNRJS36P', 1121, 234, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:22', 000045, 'RNRJS35P', 1121, 262, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:21', 000045, 'RNRJS34P', 1121, 288, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:20', 000045, 'RNRJS33P', 1121, 316, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:19', 000045, 'RNRJS32P', 1121, 345, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:17', 000045, 'RNRJS30P', 1121, 398, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:15', 000045, 'RNRJS28P', 1295, 233, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:16', 000045, 'RNRJS29P', 1295, 207, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:42', 000045, 'RNRJS02L', 393, 659, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:46', 000045, 'RNRJS05L', 392, 770, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:45', 000045, 'RNRJS04L', 393, 741, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:44', 000045, 'SCJS03L', 393, 713, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:43', 000045, 'RNRJS03P', 393, 686, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:41', 000045, 'SCJS02P', 393, 631, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:48', 000045, 'RNRJS08L', 528, 659, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:52', 000045, 'RNRJS18P', 528, 770, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:51', 000045, 'RNRJS17P', 528, 741, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:50', 000045, 'RNRJS14P', 528, 713, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:49', 000045, 'RNRJS09L', 528, 686, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:47', 000045, 'RNRJS07L', 528, 632, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:2', 000045, 'RNRKD03L', 1295, 645, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:6', 000045, 'RNRKD11L', 1295, 754, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:5', 000045, 'RNRKD10L', 1295, 727, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:4', 000045, 'RNRKD09L', 1295, 698, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:3', 000045, 'RNRKD08L', 1295, 672, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:1', 000045, 'RNRKD01L', 1295, 617, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:8', 000045, 'SCJS04P', 1295, 424, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:9', 000045, 'RNRJS22P', 1295, 397, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:10', 000045, 'RNRJS23P', 1295, 371, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:12', 000045, 'RNRJS25P', 1295, 316, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:11', 000045, 'RNRJS24P', 1295, 343, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:13', 000045, 'RNRJS26P', 1295, 288, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:14', 000045, 'RNRJS27L', 1295, 259, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:32', 000045, 'RNRJS45P', 955, 261, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:31', 000045, 'RNRJS44P', 955, 288, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:30', 000045, 'RNRJS43P', 955, 317, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:29', 000045, 'RNRJS42P', 955, 343, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:35', 000045, 'RNRJS48P', 955, 152, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:40', 000045, 'SCJS05P', 955, 179, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:34', 000045, 'RNRJS47P', 955, 206, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:33', 000045, 'RNRJS46P', 955, 234, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:28', 000045, 'RNRJS41P', 955, 370, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:27', 000045, 'RNRJS40P', 955, 399, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:36', 000045, 'RNRJS49P', 955, 125, false, NULL, true, false, NULL, true);

