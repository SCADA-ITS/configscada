-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(910, 'LBL_TREE_ALJ_1', 'LBL_TREE_ALJ_1', 'LBL_LBL_TREE_ALJ_1', NULL, 'img/synoptics/aljibe1.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91000025, 910, 'PCI', 'LBL_PCI', true),
(91002200, 910, 'VALVULAS', 'LBL_VALVULAS', true),
(91008600, 910, 'BOMBAS', 'LBL_BOMBAS', true),
(91008700, 910, 'TANQUE', 'LBL_TANQUE', true),
(91008800, 910, 'SENSORES', 'LBL_SENSORES', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(910, 91000025, 91002200),
(910, 91000025, 91008600),
(910, 91000025, 91008700),
(910, 91000025, 91008800);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(27, 910, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(28, 910, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(29, 910, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91002200, 'Element:22:6', 000022, 'VALV-DESCARGA-ALJ1-2', 1309, 818, false, NULL, true, false, NULL, true),
(91002200, 'Element:22:5', 000022, 'VALV-DESCARGA-ALJ1-1', 1309, 446, false, NULL, true, false, NULL, true),
(91002200, 'Element:22:3', 000022, 'VALV-PRUEBAS-ALJ1-1', 874, 150, false, NULL, true, false, NULL, true),
(91002200, 'Element:22:4', 000022, 'VALV-PRUEBAS-ALJ1-2', 873, 275, false, NULL, true, false, NULL, true),
(91002200, 'Element:22:2', 000022, 'VALV-OSY-ALJ1-2', 559, 818, false, NULL, true, false, NULL, true),
(91002200, 'Element:22:1', 000022, 'VALV-OSY-ALJ1-1', 559, 446, false, NULL, true, false, NULL, true),
(91008600, 'Element:86:1', 000086, '1ES2-BCI-001-BBA1', 822, 446, false, NULL, true, false, NULL, true),
(91008600, 'Element:86:2', 000086, '1ES2-BCI-001-BBA2', 823, 818, false, NULL, true, false, NULL, true),
(91008700, 'Element:87:1', 000087, 'TANK-ALJ-1', 144, 640, false, NULL, true, false, NULL, true),
(91008800, 'Element:88:13', 000088, 'ALJ1-DETROOM-PCI-1', 1611, 130, false, NULL, true, false, NULL, true),
(91008800, 'Element:88:3', 000088, 'ALJ1-DETPR-PCI-1', 1606, 635, false, NULL, true, false, NULL, true),
(91008800, 'Element:88:2', 000088, 'ALJ1-DETFLJ-PCI-2', 1458, 819, false, NULL, true, false, NULL, true),
(91008800, 'Element:88:1', 000088, 'ALJ1-DETFLJ-PCI-1', 1458, 450, false, NULL, true, false, NULL, true);

