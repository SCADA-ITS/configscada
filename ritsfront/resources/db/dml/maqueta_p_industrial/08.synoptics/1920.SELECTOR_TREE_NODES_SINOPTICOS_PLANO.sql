-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(1920, 'SELECTOR_TREE_NODES_SINOPTICOS_PLANO', 'SELECTOR_TREE_NODES_SINOPTICOS_PLANO', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_PLANO', NULL, 'img/synoptics/PI_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(192000200, 1920, 'SYNOPTIC_LAYER_CAMERAS', 'LBL_SYNOPTIC_LAYER_CAMERAS', true),
(192000400, 1920, 'SYNOPTIC_LAYER_ETDS', 'LBL_SYNOPTIC_LAYER_ETDS', true),
(192007100, 1920, 'SYNOPTIC_LAYER_PMVS', 'LBL_SYNOPTIC_LAYER_PMVS', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(0, 1920, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(1, 1920, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(2, 1920, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(192000200, 'Element:2:1', 000002,  '00.3-ASC-DOM-01', 123, 757, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:2', 000002,  '00.8-ASC-DOM-01', 442, 713, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:3', 000002,  '01.1-ECT-DOM-01', 592, 316, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:4', 000002,  '01.2-ASC-DOM-01', 618, 637, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:5', 000002,  '01.8-MR1-DOM-01', 719, 710, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:6', 000002,  '01.8-MR1-DOM-02', 736, 846, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:7', 000002,  '02.3-ASC-DOM-01', 785, 699, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:8', 000002,  '02.8-ASC-DOM-01', 910, 699, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:9', 000002,  '03.3-ASC-DOM-01', 1040, 699, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:10', 000002, '03.8-ASC-DOM-01', 1110, 704, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:11', 000002, '04.3-MR2-DOM-01', 1166, 691, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:12', 000002, '04.4-MR2-DOM-01', 1189, 823, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:13', 000002, '04.6-ASC-DOM-01', 1287, 775, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:14', 000002, '05.1-ASC-DOM-01', 1352, 736, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:15', 000002, '05.5-ASC-DOM-01', 1247, 643, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:16', 000002, '00.0-EBT-DOM-01', 1439, 696, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:17', 000002, '06.0-ASC-DOM-01', 1618, 421, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:18', 000002, '00.3-AAE-DOM-01', 1697, 646, false, NULL, true, false, NULL, true),
(192000400, 'Element:4:2', 000004, 'SEC-2', 870, 831, false, NULL, true, false, NULL, true),
(192000400, 'Element:4:1', 000004, 'SEC-1', 868, 592, false, NULL, true, false, NULL, true),
(192007100, 'Element:71:2', 000071, '05.1-DES-PMV-01', 987, 517, false, NULL, true, false, NULL, true),
(192007100, 'Element:71:1', 000071, '01.4-ASC-PMV-01', 425, 863, false, NULL, true, false, NULL, true);

