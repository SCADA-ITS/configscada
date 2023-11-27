-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(913, 'LBL_TREE_ALJ_4', 'LBL_TREE_ALJ_4', 'LBL_LBL_TREE_ALJ_4', NULL, 'img/synoptics/aljibe4.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91300028, 913, 'PCI', 'LBL_PCI', true),
(91302200, 913, 'VALVULAS', 'LBL_VALVULAS', true),
(91308600, 913, 'BOMBAS', 'LBL_BOMBAS', true),
(91308700, 913, 'TANQUE', 'LBL_TANQUE', true),
(91308800, 913, 'SENSORES', 'LBL_SENSORES', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(913, 91300028, 91302200),
(913, 91300028, 91308600),
(913, 91300028, 91308700),
(913, 91300028, 91308800);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(36, 913, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(37, 913, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(38, 913, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91302200, 'Element:22:24', 000022, 'VALV-DESCARGA-ALJ4-2', 1306, 823, false, NULL, true, false, NULL, true),
(91302200, 'Element:22:23', 000022, 'VALV-DESCARGA-ALJ4-1', 1307, 451, false, NULL, true, false, NULL, true),
(91302200, 'Element:22:21', 000022, 'VALV-PRUEBAS-ALJ4-1', 870, 154, false, NULL, true, false, NULL, true),
(91302200, 'Element:22:22', 000022, 'VALV-PRUEBAS-ALJ4-2', 870, 280, false, NULL, true, false, NULL, true),
(91302200, 'Element:22:20', 000022, 'VALV-OSY-ALJ4-2', 556, 822, false, NULL, true, false, NULL, true),
(91302200, 'Element:22:19', 000022, 'VALV-OSY-ALJ4-1', 558, 451, false, NULL, true, false, NULL, true),
(91308600, 'Element:86:7', 000086, 'CT07-BCI-001-BBA1', 819, 450, false, NULL, true, false, NULL, true),
(91308600, 'Element:86:8', 000086, 'CT07-BCI-001-BBA2', 819, 822, false, NULL, true, false, NULL, true),
(91308700, 'Element:87:4', 000087, 'TANK-ALJ-4', 141, 645, false, NULL, true, false, NULL, true),
(91308800, 'Element:88:16', 000088, 'ALJ4-DETROOM-PCI-1', 1611, 149, false, NULL, true, false, NULL, true),
(91308800, 'Element:88:12', 000088, 'ALJ4-DETPR-PCI-1', 1603, 635, false, NULL, true, false, NULL, true),
(91308800, 'Element:88:11', 000088, 'ALJ4-DETFLJ-PCI-2', 1458, 820, false, NULL, true, false, NULL, true),
(91308800, 'Element:88:10', 000088, 'ALJ4-DETFLJ-PCI-1', 1458, 449, false, NULL, true, false, NULL, true);

