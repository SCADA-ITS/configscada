-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(932, 'TREE_BRIGE', 'TREE_BRIGE', 'LBL_SYNOPTIC_BRIGE', NULL, 'img/synoptics/GALERIAS_PUENTE.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(93203900, 932, 'HORIZONTAL_ACCESS', 'LBL_HORIZONTAL_ACCESS', true),
(93204000, 932, 'PRESENCE_DETECTION', 'LBL_PRESENCE_DETECTION', true),
(93208900, 932, 'GATE', 'LBL_GATE', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(90, 932, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(91, 932, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(92, 932, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(124, 932, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(93203900, 'Element:39:11', 000039, 'Poklop_11', 1672, 476, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:10', 000039, 'Poklop_10', 1354, 476, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:9', 000039, 'Poklop_9', 1100, 476, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:8', 000039, 'Poklop_8', 977, 476, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:7', 000039, 'Poklop_7', 886, 476, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:6', 000039, 'Poklop_6', 737, 476, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:5', 000039, 'Poklop_5', 702, 476, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:4', 000039, 'Poklop_4', 665, 490, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:3', 000039, 'Poklop_3', 631, 476, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:2', 000039, 'Poklop_2', 505, 476, false, NULL, true, false, NULL, true),
(93203900, 'Element:39:1', 000039, 'Poklop_1', 207, 476, false, NULL, true, false, NULL, true),
(93204000, 'Element:40:8', 000040, 'Pohybove cidlo_8', 1685, 539, false, NULL, true, false, NULL, true),
(93204000, 'Element:40:7', 000040, 'Pohybove cidlo_7', 1089, 539, false, NULL, true, false, NULL, true),
(93204000, 'Element:40:6', 000040, 'Pohybove cidlo_6', 989, 539, false, NULL, true, false, NULL, true),
(93204000, 'Element:40:5', 000040, 'Pohybove cidlo_5', 853, 539, false, NULL, true, false, NULL, true),
(93204000, 'Element:40:4', 000040, 'Pohybove cidlo_4', 754, 539, false, NULL, true, false, NULL, true),
(93204000, 'Element:40:3', 000040, 'Pohybove cidlo_3', 620, 539, false, NULL, true, false, NULL, true),
(93204000, 'Element:40:2', 000040, 'Pohybove cidlo_2', 518, 539, false, NULL, true, false, NULL, true),
(93204000, 'Element:40:1', 000040, 'Pohybove cidlo_1', 193, 539, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:10', 000089, 'Dvere_10', 1739, 669, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:9', 000089, 'Dvere_9', 1703, 476, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:8', 000089, 'Dvere_8', 1067, 476, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:7', 000089, 'Dvere_7', 1011, 476, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:6', 000089, 'Dvere_6', 832, 476, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:5', 000089, 'Dvere_5', 774, 490, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:4', 000089, 'Dvere_4', 598, 476, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:3', 000089, 'Dvere_3', 540, 476, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:2', 000089, 'Dvere_2', 173, 476, false, NULL, true, false, NULL, true),
(93208900, 'Element:89:1', 000089, 'Dvere_1', 138, 669, false, NULL, true, false, NULL, true);

