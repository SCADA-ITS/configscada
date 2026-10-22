-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(915, 'TREE_TFAS_3D', 'TREE_TFAS_3D', 'LBL_TREE_TFAS_3D', NULL, 'img/synoptics/Bratislava_ETD_TFA3D.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91506800, 915, 'TFAs3D', 'LBL_TFAs3D', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(42, 915, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(43, 915, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(44, 915, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91506800, 'Element:68:18', 000068, '3DSR01L-28+48', 273, 48, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:20', 000068, '3DPK01L-3+473', 476, 493, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:19', 000068, '3DPK01P-3+473', 235, 491, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:17', 000068, '3DSR01P-28+48', 654, 46, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:16', 000068, '3DJS02L-7+992', 228, 608, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:15', 000068, '3DJS02P-7+992', 477, 706, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:14', 000068, '3DJS01L-2+849', 283, 663, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:13', 000068, '3DJS01P-2+849', 314, 752, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:12', 000068, '3DJS05L-17+94', 477, 378, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:11', 000068, '3DJS05P-17+94', 747, 413, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:10', 000068, '3DJS04PL-14+264', 477, 436, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:9', 000068, '3DJS04PP-14+264', 747, 546, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:8', 000068, '3DJS04L-11+708', 413, 553, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:7', 000068, '3DJS03P-10+33', 891, 649, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:6', 000068, '3DKD01L-21+225', 891, 706, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:5', 000068, '3DKD01P-21+225', 550, 832, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:4', 000068, '3DDH01L-15+847', 1007, 785, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:3', 000068, '3DDH01P-15+847', 656, 897, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:2', 000068, '3DDH02P-26+416', 656, 971, false, NULL, true, false, NULL, true),
(91506800, 'Element:68:1', 000068, '3DDH03L-30+245', 1399, 980, false, NULL, true, false, NULL, true);

