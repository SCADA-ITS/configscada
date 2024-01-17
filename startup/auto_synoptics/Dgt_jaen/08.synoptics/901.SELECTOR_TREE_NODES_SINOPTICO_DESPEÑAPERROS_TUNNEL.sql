-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(901, 'SELECTOR_TREE_NODES_SINOPTICO_DESPEÑAPERROS_TUNNEL', 'SELECTOR_TREE_NODES_SINOPTICO_DESPEÑAPERROS_TUNNEL', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_DESPEÑAPERROS_TUNNEL', NULL, 'img/synoptics/PLANTA_SCADA_tunel_DESPENAPERROS.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90100000, 901, 'SYNOPTIC_LAYERS_CONTROL_DE_ACCESSOS', 'LBL_SYNOPTIC_LAYERS_CONTROL_DE_ACCESSOS', true),
(90107111, 901, 'SYNOPTIC_LAYERS_PANELES_CLV', 'LBL_SYNOPTIC_LAYERS_PANELES_CLV', true),
(90107112, 901, 'SYNOPTIC_LAYERS_PANELES_AF', 'LBL_SYNOPTIC_LAYERS_PANELES_AF', true),
(90107118, 901, 'SYNOPTIC_LAYERS_PANELES_GT', 'LBL_SYNOPTIC_LAYERS_PANELES_GT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(901, 90100000, 90107111),
(901, 90100000, 90107112),
(901, 90100000, 90107118);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(0, 901, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(1, 901, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(2, 901, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90107111, 'Element:71:44', 110071, 'SV-2-05-DP-C', 334, 293, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:45', 110071, 'SV-2-05-DP-I', 334, 394, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:43', 110071, 'SV-2-05-DP-D', 334, 191, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:40', 110071, 'SV-2-04-DP-D', 662, 191, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:41', 110071, 'SV-2-04-DP-C', 662, 293, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:42', 110071, 'SV-2-04-DP-I', 662, 394, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:35', 110071, 'SV-2-02-DP-C', 1513, 292, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:39', 110071, 'SV-2-03-DP-I', 1209, 393, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:38', 110071, 'SV-2-03-DP-C', 1209, 292, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:37', 110071, 'SV-2-03-DP-D', 1209, 190, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:36', 110071, 'SV-2-02-DP-I', 1513, 393, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:34', 110071, 'SV-2-02-DP-D', 1513, 190, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:33', 110071, 'SV-2-01-DP-I', 1807, 392, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:32', 110071, 'SV-2-01-DP-C', 1807, 291, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:31', 110071, 'SV-2-01-DP-D', 1807, 190, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:57', 110071, 'SV-1-04-DP-I', 1209, 676, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:58', 110071, 'SV-1-05-DP-D', 1513, 871, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:59', 110071, 'SV-1-05-DP-C', 1513, 776, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:60', 110071, 'SV-1-05-DP-I', 1513, 676, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:56', 110071, 'SV-1-04-DP-C', 1209, 776, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:52', 110071, 'SV-1-03-DP-D', 662, 871, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:53', 110071, 'SV-1-03-DP-C', 662, 776, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:54', 110071, 'SV-1-03-DP-I', 662, 676, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:55', 110071, 'SV-1-04-DP-D', 1209, 871, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:51', 110071, 'SV-1-02-DP-I', 334, 677, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:49', 110071, 'SV-1-02-DP-D', 334, 872, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:50', 110071, 'SV-1-02-DP-C', 334, 777, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:46', 110071, 'SV-1-01-DP-D', 71, 872, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:48', 110071, 'SV-1-01-DP-I', 71, 677, false, NULL, true, false, NULL, true),
(90107111, 'Element:71:47', 110071, 'SV-1-01-DP-C', 71, 778, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:15', 120071, 'AF-2-05-DP-I', 334, 434, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:13', 120071, 'AF-2-05-DP-D', 334, 231, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:14', 120071, 'AF-2-05-DP-C', 334, 333, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:10', 120071, 'AF-2-04-DP-D', 662, 231, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:11', 120071, 'AF-2-04-DP-C', 662, 333, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:12', 120071, 'AF-2-04-DP-I', 662, 434, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:8', 120071, 'AF-2-03-DP-C', 1209, 332, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:9', 120071, 'AF-2-03-DP-I', 1209, 433, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:7', 120071, 'AF-2-03-DP-D', 1209, 230, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:4', 120071, 'AF-2-02-DP-D', 1513, 230, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:5', 120071, 'AF-2-02-DP-C', 1513, 332, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:3', 120071, 'AF-2-01-DP-I', 1807, 432, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:2', 120071, 'AF-2-01-DP-C', 1807, 331, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:1', 120071, 'AF-2-01-DP-D', 1807, 230, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:6', 120071, 'AF-2-02-DP-I', 1513, 433, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:30', 120071, 'AF-1-05-DP-I', 1513, 715, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:28', 120071, 'AF-1-05-DP-D', 1513, 911, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:27', 120071, 'AF-1-04-DP-I', 1209, 715, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:29', 120071, 'AF-1-05-DP-C', 1513, 816, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:23', 120071, 'AF-1-03-DP-C', 662, 816, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:22', 120071, 'AF-1-03-DP-D', 662, 911, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:24', 120071, 'AF-1-03-DP-I', 662, 715, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:25', 120071, 'AF-1-04-DP-D', 1209, 911, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:26', 120071, 'AF-1-04-DP-C', 1209, 816, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:19', 120071, 'AF-1-02-DP-D', 334, 912, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:20', 120071, 'AF-1-02-DP-C', 334, 817, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:21', 120071, 'AF-1-02-DP-I', 334, 717, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:17', 120071, 'AF-1-01-DP-C', 72, 817, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:18', 120071, 'AF-1-01-DP-I', 71, 716, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:16', 120071, 'AF-1-01-DP-D', 71, 911, false, NULL, true, false, NULL, true),
(90107118, 'Element:71:61', 180071, 'PMV-2-01-DP', 762, 298, false, NULL, true, false, NULL, true),
(90107118, 'Element:71:62', 180071, 'PMV-1-01-DP', 810, 784, false, NULL, true, false, NULL, true);

