-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(912, 'LBL_TREE_ALJ_3', 'LBL_TREE_ALJ_3', 'LBL_LBL_TREE_ALJ_3', NULL, 'img/synoptics/aljibe3.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91200027, 912, 'PCI', 'LBL_PCI', true),
(91202200, 912, 'VALVULAS', 'LBL_VALVULAS', true),
(91208600, 912, 'BOMBAS', 'LBL_BOMBAS', true),
(91208700, 912, 'TANQUE', 'LBL_TANQUE', true),
(91208800, 912, 'SENSORES', 'LBL_SENSORES', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(912, 91200027, 91202200),
(912, 91200027, 91208600),
(912, 91200027, 91208700),
(912, 91200027, 91208800);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(33, 912, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(34, 912, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(35, 912, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91202200, 'Element:22:18', 000022, 'VALV-DESCARGA-ALJ3-2', 1307, 819, false, NULL, true, false, NULL, true),
(91202200, 'Element:22:17', 000022, 'VALV-DESCARGA-ALJ3-1', 1308, 447, false, NULL, true, false, NULL, true),
(91202200, 'Element:22:15', 000022, 'VALV-PRUEBAS-ALJ3-1', 872, 150, false, NULL, true, false, NULL, true),
(91202200, 'Element:22:16', 000022, 'VALV-PRUEBAS-ALJ3-2', 871, 275, false, NULL, true, false, NULL, true),
(91202200, 'Element:22:14', 000022, 'VALV-OSY-ALJ3-2', 557, 818, false, NULL, true, false, NULL, true),
(91202200, 'Element:22:13', 000022, 'VALV-OSY-ALJ3-1', 559, 447, false, NULL, true, false, NULL, true),
(91208600, 'Element:86:5', 000086, 'CT06-BCI-001-BBA1', 821, 446, false, NULL, true, false, NULL, true),
(91208600, 'Element:86:6', 000086, 'CT06-BCI-001-BBA2', 821, 818, false, NULL, true, false, NULL, true),
(91208700, 'Element:87:3', 000087, 'TANK-ALJ-3', 142, 641, false, NULL, true, false, NULL, true),
(91208800, 'Element:88:8', 000088, 'ALJ3-DETFLJ-PCI-2', 1458, 820, false, NULL, true, false, NULL, true),
(91208800, 'Element:88:15', 000088, 'ALJ3-DETROOM-PCI-1', 1602, 125, false, NULL, true, false, NULL, true),
(91208800, 'Element:88:9', 000088, 'ALJ3-DETPR-PCI-1', 1605, 635, false, NULL, true, false, NULL, true),
(91208800, 'Element:88:7', 000088, 'ALJ3-DETFLJ-PCI-1', 1458, 449, false, NULL, true, false, NULL, true);

