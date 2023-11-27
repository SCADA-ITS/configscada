-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(911, 'LBL_TREE_ALJ_2', 'LBL_TREE_ALJ_2', 'LBL_LBL_TREE_ALJ_2', NULL, 'img/synoptics/aljibe2.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91100026, 911, 'PCI', 'LBL_PCI', true),
(91102200, 911, 'VALVULAS', 'LBL_VALVULAS', true),
(91108600, 911, 'BOMBAS', 'LBL_BOMBAS', true),
(91108700, 911, 'TANQUE', 'LBL_TANQUE', true),
(91108800, 911, 'SENSORES', 'LBL_SENSORES', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(911, 91100026, 91102200),
(911, 91100026, 91108600),
(911, 91100026, 91108700),
(911, 91100026, 91108800);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(30, 911, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(31, 911, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(32, 911, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91102200, 'Element:22:8', 000022, 'VALV-OSY-ALJ2-2', 556, 824, false, NULL, true, false, NULL, true),
(91102200, 'Element:22:7', 000022, 'VALV-OSY-ALJ2-1', 556, 449, false, NULL, true, false, NULL, true),
(91102200, 'Element:22:12', 000022, 'VALV-DESCARGA-ALJ2-2', 1303, 823, false, NULL, true, false, NULL, true),
(91102200, 'Element:22:11', 000022, 'VALV-DESCARGA-ALJ2-1', 1303, 449, false, NULL, true, false, NULL, true),
(91102200, 'Element:22:10', 000022, 'VALV-PRUEBAS-ALJ2-2', 872, 277, false, NULL, true, false, NULL, true),
(91102200, 'Element:22:9', 000022, 'VALV-PRUEBAS-ALJ2-1', 872, 154, false, NULL, true, false, NULL, true),
(91108600, 'Element:86:4', 000086, 'CT04-BCI-001-BBA2', 820, 825, false, NULL, true, false, NULL, true),
(91108600, 'Element:86:3', 000086, 'CT04-BCI-001-BBA1', 820, 449, false, NULL, true, false, NULL, true),
(91108700, 'Element:87:2', 00008700, 'TANK-ALJ-2', 138, 642, false, NULL, true, false, NULL, true),
(91108800, 'Element:88:14', 000088, 'ALJ2-DETROOM-PCI-1', 1597, 120, false, NULL, true, false, NULL, true),
(91108800, 'Element:88:6', 000088, 'ALJ2-DETPR-PCI-1', 1606, 635, false, NULL, true, false, NULL, true),
(91108800, 'Element:88:5', 000088, 'ALJ2-DETFLJ-PCI-2', 1456, 823, false, NULL, true, false, NULL, true),
(91108800, 'Element:88:4', 000088, 'ALJ2-DETFLJ-PCI-1', 1456, 448, false, NULL, true, false, NULL, true);

