-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(907, 'TREE_EC_TFALOOPS', 'TREE_EC_TFALOOPS', 'LBL_SYNOPTIC_EC_TFALOOPS', NULL, 'img/synoptics/Bratislava_EC_TFA_Loops.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90704500, 907, 'TFA_Loops', 'LBL_SYNOPTIC_EC_TFALOOPS', true),
(90702300, 907, 'INCIDENT', 'LBL_INCIDENT', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(18, 907, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(19, 907, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(20, 907, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(99, 907, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90704500, 'Element:45:18', 000045, 'RNRJS31P', 1495, 498, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:62', 000045, 'RNRMP01', 159, 24, false, NULL, true, false, NULL, true),--elemento a borrar pedido por el cliente
(90704500, 'Element:45:63', 000045, 'RNRPK01L', 159, 59, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:64', 000045, 'RNRPK03L', 159, 113, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:65', 000045, 'RNRPK04L', 159, 167, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:66', 000045, 'RNRPK06L', 159, 204, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:67', 000045, 'RNRPK07L', 158, 241, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:68', 000045, 'RNRPK08L', 158, 277, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:69', 000045, 'RNRPK09L', 158, 313, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:70', 000045, 'RNRPK11L', 158, 350, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:71', 000045, 'RNRPK13L', 158, 388, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:73', 000045, 'RNRPK15L', 158, 458, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:72', 000045, 'RNRPK14L', 158, 419, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:61', 000045, 'SCSR03P', 849, 58, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:60', 000045, 'SCSR02P', 849, 96, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:59', 000045, 'RNRSR08P', 849, 132, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:58', 000045, 'RNRSR07L', 849, 166, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:57', 000045, 'RNRSR06P', 849, 204, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:56', 000045, 'SCSR01P', 849, 240, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:55', 000045, 'RNRSR05P', 849, 279, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:53', 000045, 'RNRSR01P', 849, 347, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:54', 000045, 'RNRSR03P', 849, 312, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:26', 000045, 'RNRJS39P', 1495, 206, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:25', 000045, 'RNRJS38P', 1495, 245, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:24', 000045, 'RNRJS37P', 1495, 280, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:23', 000045, 'RNRJS36P', 1495, 318, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:22', 000045, 'RNRJS35P', 1495, 353, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:21', 000045, 'RNRJS34P', 1495, 389, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:20', 000045, 'RNRJS33P', 1495, 427, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:19', 000045, 'RNRJS32P', 1495, 463, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:17', 000045, 'RNRJS30P', 1495, 536, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:15', 000045, 'RNRJS28P', 1727, 318, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:16', 000045, 'RNRJS29P', 1727, 279, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:42', 000045, 'RNRJS02L', 552, 881, false, NULL, true, false, NULL, true),--elemento a borrar pedido por el cliente
(90704500, 'Element:45:46', 000045, 'RNRJS05L', 552, 1024, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:45', 000045, 'RNRJS04L', 552, 989, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:44', 000045, 'SCJS03L', 552, 953, false, NULL, true, false, NULL, true),--elemento a borrar pedido por el cliente
(90704500, 'Element:45:43', 000045, 'RNRJS03P', 552, 916, false, NULL, true, false, NULL, true),--elemento a borrar pedido por el cliente
(90704500, 'Element:45:41', 000045, 'SCJS02P', 552, 842, false, NULL, true, false, NULL, true),--elemento a borrar pedido por el cliente
(90704500, 'Element:45:48', 000045, 'RNRJS08L', 703, 881, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:52', 000045, 'RADPJS20P', 703, 1024, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:51', 000045, 'RNRJS17P', 703, 989, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:50', 000045, 'RNRJS14P', 703, 953, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:49', 000045, 'RNRJS09L', 703, 916, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:47', 000045, 'RNRJS07L', 703, 842, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:2', 000045, 'RNRKD03L', 1727, 866, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:6', 000045, 'RNRKD11L', 1727, 1009, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:5', 000045, 'RNRKD10L', 1727, 972, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:4', 000045, 'RNRKD09L', 1727, 936, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:3', 000045, 'RNRKD08L', 1727, 900, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:1', 000045, 'RNRKD01L', 1727, 825, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:8', 000045, 'SCJS04P', 1727, 571, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:9', 000045, 'RNRJS22P', 1727, 535, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:10', 000045, 'RNRJS23P', 1727, 500, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:12', 000045, 'RNRJS25P', 1727, 425, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:11', 000045, 'RNRJS24P', 1727, 461, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:13', 000045, 'RNRJS26P', 1727, 392, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:14', 000045, 'RNRJS27L', 1727, 353, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:32', 000045, 'RNRJS45P', 1273, 351, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:31', 000045, 'RNRJS44P', 1273, 389, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:30', 000045, 'RNRJS43P', 1273, 425, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:29', 000045, 'RNRJS42P', 1273, 461, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:35', 000045, 'RNRJS48P', 1273, 206, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:40', 000045, 'SCJS05P', 1273, 244, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:34', 000045, 'RNRJS47P', 1273, 279, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:33', 000045, 'RNRJS46P', 1273, 315, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:28', 000045, 'RNRJS41P', 1273, 499, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:27', 000045, 'RNRJS40P', 1273, 535, false, NULL, true, false, NULL, true),
(90704500, 'Element:45:36', 000045, 'RNRJS49P', 1273, 170, false, NULL, true, false, NULL, true),
(90702300, 'Element:23:1', 000023, 'Imanager', 1843, 26, false, NULL, true, false, NULL, true);

