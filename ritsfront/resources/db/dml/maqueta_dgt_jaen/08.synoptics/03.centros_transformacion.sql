-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(918, 'SELECTOR_TREE_NODES_SINOPTICO_CENTROS_TRANSFORMACION', 'SELECTOR_TREE_NODES_SINOPTICO_CENTROS_TRANSFORMACION', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_CENTROS_TRANSFORMACION', NULL, 'img/synoptics/centros_transformacion.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91805700, 918, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(51, 918, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(52, 918, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(53, 918, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91805700, 'Element:57:292', 00005704, 'INT-L3-4-CGMT', 1815, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:290', 00005704, 'INT-L3-3-CGMT', 1696, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:291', 00005705, 'SEC-L3-3-CGMT', 1659, 453, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:289', 00005705, 'SEC-L3-2-CGMT', 1488, 453, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:288', 00005704, 'INT-L3-2-CGMT', 1523, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:287', 00005704, 'INT-L3-1-CGMT', 1404, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:286', 00005704, 'INT-L2-3-CGMT', 1271, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:285', 00005705, 'SEC-L2-2-CGMT', 1112, 453, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:284', 00005704, 'INT-L2-2-CGMT', 1148, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:283', 00005704, 'INT-L2-1-CGMT', 1022, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:301', 00005705, 'SEC-L1-6-CGMT', 806, 453, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:299', 00005705, 'SEC-L1-5-CGMT', 635, 455, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:300', 00005704, 'INT-L1-6-CGMT', 842, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:298', 00005704, 'INT-L1-5-CGMT', 668, 337, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:297', 00005704, 'INT-L1-4-CGMT', 510, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:295', 00005704, 'INT-L1-3-CGMT', 247, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:296', 00005705, 'SEC-L1-3-CGMT', 211, 455, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:294', 00005704, 'INT-L1-2-CGMT', 127, 336, false, NULL, true, false, NULL, true),
(91805700, 'Element:57:293', 000057, 'INT-L1-1-CGMT', 60, 336, false, NULL, true, false, NULL, true);

