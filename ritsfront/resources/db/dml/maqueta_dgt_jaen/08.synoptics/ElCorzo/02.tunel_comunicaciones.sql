-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(920, 'SELECTOR_TREE_NODES_SINOPTICO_EL_CORZO_TUNNEL_COMUNICACIONES', 'SELECTOR_TREE_NODES_SINOPTICO_EL_CORZO_TUNNEL_COMUNICACIONES', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_EL_CORZO_TUNNEL_COMUNICACIONES', NULL, 'img/synoptics/tunel_el_corzo_comunicaciones.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92001100, 920, 'SYNOPTIC_LAYERS_SOS', 'LBL_SYNOPTIC_LAYERS_SOS', true),
(92002600, 920, 'SYNOPTIC_LAYERS_SWITCH', 'LBL_SYNOPTIC_LAYERS_SWITCH', true),
(92002700, 920, 'SYNOPTIC_LAYERS_PLC', 'LBL_SYNOPTIC_LAYERS_PLC', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(57, 920, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(58, 920, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(59, 920, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92001100, 'Element:11:52', 00001113, 'PSI-1-02-CZ', 761, 971, false, NULL, true, false, NULL, true),
(92001100, 'Element:11:51', 00001113, 'PSI-1-01-CZ', 490, 971, false, NULL, true, false, NULL, true),
(92001100, 'Element:11:53', 00001113, 'PSI-1-03-CZ', 1009, 971, false, NULL, true, false, NULL, true),
(92001100, 'Element:11:55', 00001113, 'PSE-1-01-CZ', 318, 971, false, NULL, true, false, NULL, true),
(92001100, 'Element:11:56', 00001113, 'PSE-1-02-CZ', 1270, 971, false, NULL, true, false, NULL, true),
(92001100, 'Element:11:57', 00001113, 'PSE-2-01-CZ', 1156, 111, false, NULL, true, false, NULL, true),
(92001100, 'Element:11:58', 00001113, 'PSE-2-02-CZ', 498, 111, false, NULL, true, false, NULL, true),
(92001100, 'Element:11:54', 00001113, 'PSI-2-01-CZ', 806, 111, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:49', 00002601, 'SWP-1-01-CZ', 523, 911, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:50', 00002601, 'SWP-1-02-CZ', 791, 911, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:51', 00002601, 'SWP-1-03-CZ', 1009, 911, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:52', 00002601, 'SWP-1-04-CZ', 1730, 615, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:53', 00002601, 'SWP-1-05-CZ', 1730, 472, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:54', 00002601, 'SWP-2-01-CZ', 1846, 171, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:55', 00002601, 'SWP-2-02-CZ', 1657, 171, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:56', 00002601, 'SWP-2-03-CZ', 1405, 171, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:57', 00002601, 'SWP-2-04-CZ', 1272, 171, false, NULL, true, false, NULL, true),
(92002600, 'Element:26:58', 00002601, 'SWP-2-05-CZ', 834, 171, false, NULL, true, false, NULL, true),
(92002700, 'Element:27:42', 00002701, 'ES-07-CZ', 1834, 542, false, NULL, true, false, NULL, true),
(92002700, 'Element:27:41', 00002701, 'ES-VEN-02-CZ', 1764, 542, false, NULL, true, false, NULL, true),
(92002700, 'Element:27:40', 00002701, 'ES-VEN-01-CZ', 1694, 542, false, NULL, true, false, NULL, true),
(92002700, 'Element:27:34', 00002701, 'ES-01-CZ', 550, 971, false, NULL, true, false, NULL, true),
(92002700, 'Element:27:35', 00002701, 'ES-02-CZ', 821, 971, false, NULL, true, false, NULL, true),
(92002700, 'Element:27:38', 00002701, 'ES-05-CZ', 1621, 542, false, NULL, true, false, NULL, true),
(92002700, 'Element:27:36', 00002701, 'ES-03-CZ', 1846, 109, false, NULL, true, false, NULL, true),
(92002700, 'Element:27:37', 00002701, 'ES-04-CZ', 1405, 109, false, NULL, true, false, NULL, true),
(92002700, 'Element:27:39', 00002701, 'ES-06-CZ', 866, 111, false, NULL, true, false, NULL, true);

