-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(914, 'LBL_TREE_S1_T1', 'LBL_TREE_S1_T1', 'LBL_LBL_TREE_S1_T1', NULL, 'img/synoptics/S1_T1.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91400029, 914, 'CTRL_ACCESOS', 'LBL_CTRL_ACCESOS', true),
(91400800, 914, 'BARRERA', 'LBL_BARRERA', true),
(91401300, 914, 'SEM_3GRUPOS', 'LBL_SEM_3GRUPOS', true),
(91405300, 914, 'SEM_2GRUPOS', 'LBL_SEM_2GRUPOS', true),
(91407100, 914, 'PMV', 'LBL_PMV', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(914, 91400029, 91400800),
(914, 91400029, 91401300),
(914, 91400029, 91405300),
(914, 91400029, 91407100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(39, 914, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(40, 914, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(41, 914, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91400800, 'Element:8:2', 000008, 'ICEP-BAR-001', 1416, 419, false, NULL, true, false, NULL, true),
(91400800, 'Element:8:1', 000008, 'ICEO-BAR-001', 1147, 654, false, NULL, true, false, NULL, true),
(91400800, 'Element:8:3', 000008, 'IVEN-BAR-001', 491, 535, false, NULL, true, false, NULL, true),
(91401300, 'Element:13:6', 000013, 'S1TS-SEM-003', 1893, 679, false, NULL, true, false, NULL, true),
(91401300, 'Element:13:2', 000013, 'ICEP-SEM-001', 1443, 484, false, NULL, true, false, NULL, true),
(91401300, 'Element:13:4', 000013, 'S1TS-SEM-001', 1891, 510, false, NULL, true, false, NULL, true),
(91401300, 'Element:13:1', 000013, 'ICEO-SEM-001', 1121, 679, false, NULL, true, false, NULL, true),
(91401300, 'Element:13:3', 000013, 'IVEN-SEM-001', 260, 510, false, NULL, true, false, NULL, true),
(91405300, 'Element:53:2', 000053, 'ICEO-SEM-RR-BAR-001', 1147, 679, false, NULL, true, false, NULL, true),
(91405300, 'Element:53:3', 000053, 'ICEP-SEM-RR-BAR-001', 1441, 419, false, NULL, true, false, NULL, true),
(91405300, 'Element:53:1', 000053, 'IVEN-SEM-RR-BAR-001', 491, 510, false, NULL, true, false, NULL, true),
(91407100, 'Element:71:387', 190071, 'ICEO-PMV-001', 621, 636, false, NULL, true, false, NULL, true),
(91407100, 'Element:71:57', 190071, 'ICEP-PMV-001', 1282, 58, false, NULL, true, false, NULL, true),
(91407100, 'Element:71:56', 190071, 'IVEN-PMV-001', 68, 580, false, NULL, true, false, NULL, true);

