-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(911, 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSB', 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSB', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_UF_CSB', NULL, 'img/synoptics/Ausol_LQ_UF_CSB.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91100016, 911, 'SYNOPTIC_LAYERS_ENERGIA', 'LBL_SYNOPTIC_LAYERS_ENERGIA', true),
(91105700, 911, 'SYNOPTIC_LAYERS_INTERRUPTORES', 'LBL_SYNOPTIC_LAYERS_INTERRUPTORES', true),
(91108400, 911, 'SYNOPTIC_LAYERS_CONTACTORES', 'LBL_SYNOPTIC_LAYERS_CONTACTORES', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(911, 91100016, 91105700),
(911, 91100016, 91108400);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(30, 911, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(31, 911, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(32, 911, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91105700, 'Element:57:107', 000057, 'LQ-MAGDIF-DET1-LAZO', 1839, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:106', 000057, 'LQ-MAGDIF-BF16', 1742, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:130', 000057, 'LQ-MAGDIF-CGBT-ITS1', 1644, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:119', 000057, 'LQ-MAGDIF-BF34', 1252, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:118', 000057, 'LQ-MAGDIF-BF30', 1154, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:117', 000057, 'LQ-MAGDIF-BF29', 1057, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:116', 000057, 'LQ-MAGDIF-BF28', 959, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:114', 000057, 'LQ-MAGDIF-BF26', 763, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:113', 000057, 'LQ-MAGDIF-BF25', 665, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:112', 000057, 'LQ-MAGDIF-BF23', 567, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:111', 000057, 'LQ-MAGDIF-BF22', 470, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:110', 000057, 'LQ-MAGDIF-BF21', 372, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:109', 000057, 'LQ-MAGDIF-BF20', 274, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:108', 000057, 'LQ-MAGDIF-METEO', 176, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:115', 000057, 'LQ-MAGDIF-BF27', 861, 729, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:105', 000057, 'LQ-MAGDIF-BF15', 1644, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:104', 000057, 'LQ-MAGDIF-BF14-5', 1448, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:103', 000057, 'LQ-MAGDIF-BF14-4', 1350, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:102', 000057, 'LQ-MAGDIF-BF14-3', 1252, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:101', 000057, 'LQ-MAGDIF-BF14-2', 1154, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:93', 000057, 'LQ-MAGDIF-BF11', 372, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:100', 000057, 'LQ-MAGDIF-BF13-1', 1056, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:99', 000057, 'LQ-MAGDIF-BF13-7', 959, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:98', 000057, 'LQ-MAGDIF-BF13-6', 861, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:97', 000057, 'LQ-MAGDIF-BF13-5', 763, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:96', 000057, 'LQ-MAGDIF-BF13-4', 665, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:95', 000057, 'LQ-MAGDIF-BF13-3', 567, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:94', 000057, 'LQ-MAGDIF-BF13-2', 470, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:92', 000057, 'LQ-MAGDIF-BF10', 274, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:91', 000057, 'LQ-INT-ITS1', 176, 206, false, NULL, true, false, NULL, true),
(91105700, 'Element:57:90', 000057, 'LQ-MAG-ITS1', 977, 78, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:28', 000057, 'LQ-CONT-BF16', 1742, 308, false, NULL, true, false, NULL, true),
(91108400, 'Element:84:27', 000057, 'LQ-CONT-BF15', 1644, 308, false, NULL, true, false, NULL, true);

