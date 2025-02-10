-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(913, 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSV_MA', 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSV_MA', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_UF_CSV_MA', NULL, 'img/synoptics/TUN_UF_CSV_MA.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91305700, 913, 'SYNOPTIC_LAYERS_INTERRUPTORES', 'LBL_SYNOPTIC_LAYERS_INTERRUPTORES', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(36, 913, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(37, 913, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(38, 913, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(4017, 913, 3, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench',false,104601, NULL,2,true, NULL,false,true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91305700, 'Element:57:63', 000057, 'TUN-VENT-INT-VF4-MA', 714, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:62', 000057, 'TUN-VENT-INT-VF3-MA', 564, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:120', 000057, 'TUN-VENT-INT-VF2-MA', 415, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:61', 000057, 'TUN-VENT-INT-VF1-MA', 265, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:71', 000057, 'TUN-INT-CSV-1', 115, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:70', 000057, 'TUN-MAG-CSV-1', 939, 313, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:72', 000057, 'TUN-MAGDIF-RCAL-CV1', 1762, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:69', 000057, 'TUN-VENT-INT-VF10-MA', 1612, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:68', 000057, 'TUN-VENT-INT-VF9-MA', 1463, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:67', 000057, 'TUN-VENT-INT-VF8-MA', 1313, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:66', 000057, 'TUN-VENT-INT-VF7-MA', 1163, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:65', 000057, 'TUN-VENT-INT-VF6-MA', 1014, 443, false, NULL, true, false, NULL, true),
(91305700, 'Element:57:64', 000057, 'TUN-VENT-INT-VF5-MA', 863, 443, false, NULL, true, false, NULL, true);

