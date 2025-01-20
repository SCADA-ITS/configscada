-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(919, 'SELECTOR_TREE_NODES_SINOPTICO_LA_CANTERA_TUNNEL_COMUNICACIONES', 'SELECTOR_TREE_NODES_SINOPTICO_LA_CANTERA_TUNNEL_COMUNICACIONES', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_LA_CANTERA_TUNNEL_COMUNICACIONES', NULL, 'img/synoptics/tunel_la_cantera_comunicaciones.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91901100, 919, 'SYNOPTIC_LAYERS_SOS', 'LBL_SYNOPTIC_LAYERS_SOS', true),
(91902600, 919, 'SYNOPTIC_LAYERS_SWITCH', 'LBL_SYNOPTIC_LAYERS_SWITCH', true),
(91902700, 919, 'SYNOPTIC_LAYERS_PLC', 'LBL_SYNOPTIC_LAYERS_PLC', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(54, 919, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(55, 919, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(56, 919, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91901100, 'Element:11:47', 00001113, 'PSE-1-01-CA', 727, 963, false, NULL, true, false, NULL, true),
(91901100, 'Element:11:45', 00001113, 'PSI-1-01-CA', 1054, 963, false, NULL, true, false, NULL, true),
(91901100, 'Element:11:48', 00001113, 'PSE-1-02-CA', 1365, 963, false, NULL, true, false, NULL, true),
(91901100, 'Element:11:49', 00001113, 'PSE-2-01-CA', 1365, 112, false, NULL, true, false, NULL, true),
(91901100, 'Element:11:46', 00001113, 'PSI-2-01-CA', 1048, 112, false, NULL, true, false, NULL, true),
(91901100, 'Element:11:50', 00001113, 'PSE-2-02-CA', 727, 112, false, NULL, true, false, NULL, true),
(91902600, 'Element:26:45', 00002601, 'SWP-1-05-CA', 1462, 606, false, NULL, true, false, NULL, true),
(91902600, 'Element:26:46', 00002601, 'SWP-2-01-CA', 1462, 466, false, NULL, true, false, NULL, true),
(91902600, 'Element:26:44', 00002601, 'SWP-1-04-CA', 1084, 903, false, NULL, true, false, NULL, true),
(91902600, 'Element:26:47', 00002601, 'SWP-2-02-CA', 1078, 172, false, NULL, true, false, NULL, true),
(91902600, 'Element:26:48', 00002601, 'SWP-2-03-CA', 702, 511, false, NULL, true, false, NULL, true),
(91902600, 'Element:26:43', 00002601, 'SWP-1-03-CA', 439, 903, false, NULL, true, false, NULL, true),
(91902600, 'Element:26:42', 00002601, 'SWP-1-02-CA', 190, 903, false, NULL, true, false, NULL, true),
(91902600, 'Element:26:41', 00002601, 'SWP-1-01-CA', 16, 903, false, NULL, true, false, NULL, true),
(91902700, 'Element:27:33', 00002701, 'ES-06-CA', 1108, 112, false, NULL, true, false, NULL, true),
(91902700, 'Element:27:30', 00002701, 'ES-03-CA', 702, 575, false, NULL, true, false, NULL, true),
(91902700, 'Element:27:31', 00002701, 'ES-04-CA', 1114, 963, false, NULL, true, false, NULL, true),
(91902700, 'Element:27:29', 00002701, 'ES-02-CA', 439, 971, false, NULL, true, false, NULL, true),
(91902700, 'Element:27:28', 00002701, 'ES-01-CA', 16, 971, false, NULL, true, false, NULL, true),
(91902700, 'Element:27:32', 00002701, 'ES-05-CA', 1462, 536, false, NULL, true, false, NULL, true);

