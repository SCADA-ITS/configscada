-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(912, 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSV_CA', 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSV_CA', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_UF_CSV_CA', NULL, 'img/synoptics/Ausol_LQ_UF_CSV_CA.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91205700, 912, 'SYNOPTIC_LAYERS_INTERRUPTORES', 'LBL_SYNOPTIC_LAYERS_INTERRUPTORES', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(33, 912, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(34, 912, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(35, 912, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91205700, 'Element:57:54', 000057, 'LQ-VENT-INT-VF13-CA', 901, 242, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:55', 000057, 'LQ-VENT-INT-VF14-CA', 735, 242, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:56', 000057, 'LQ-VENT-INT-VF15-CA', 570, 242, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:57', 000057, 'LQ-VENT-INT-VF16-CA', 404, 242, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:59', 000057, 'LQ-INT-CSV-2', 239, 242, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:58', 000057, 'LQ-MAG-CSV-2', 984, 111, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:52', 000057, 'LQ-VENT-INT-VF11-CA', 1232, 242, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:53', 000057, 'LQ-VENT-INT-VF12-CA', 1067, 242, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:50', 000057, 'LQ-VENT-INT-VF9-CA', 1563, 242, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:51', 000057, 'LQ-VENT-INT-VF10-CA', 1398, 242, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:48', 000057, 'LQ-VENT-INT-VF7-CA', 545, 731, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:47', 000057, 'LQ-VENT-INT-VF6-CA', 694, 731, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:46', 000057, 'LQ-VENT-INT-VF5-CA', 844, 731, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:45', 000057, 'LQ-VENT-INT-VF4-CA', 993, 731, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:44', 000057, 'LQ-VENT-INT-VF3-CA', 1144, 731, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:43', 000057, 'LQ-VENT-INT-VF2-CA', 1293, 731, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:42', 000057, 'LQ-VENT-INT-VF1-CA', 1444, 731, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:60', 000057, 'LQ-MAGDIF-RCAL-CV2', 1593, 731, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:49', 000057, 'LQ-VENT-INT-VF8-CA', 395, 731, false, NULL, true, false, NULL, true);

