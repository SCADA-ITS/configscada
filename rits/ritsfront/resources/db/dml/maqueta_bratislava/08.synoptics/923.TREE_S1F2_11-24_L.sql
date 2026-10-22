-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(923, 'TREE_S1F2_11-24_L', 'TREE_S1F2_11-24_L', 'LBL_SYNOPTIC_S1F2_11-24_L', NULL, 'img/synoptics/Bratislava_VMS_S1F2_11-24_L.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92307100, 923, 'S1F2_11-24_L', 'LBL_SYNOPTIC_S1F2_11-24_L', true),
(92302300, 923, 'INCIDENT', 'LBL_INCIDENT', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(60, 923, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(61, 923, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(62, 923, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(115, 923, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92307100, 'Element:71:31', 030071, 'NRJS473L', 332, 617, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:35', 010071, 'NRJS469L', 117, 903, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:28', 010071, 'NRJS474L', 117, 548, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:14', 010071, 'NRJS482L', 332, 65, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:16', 010071, 'NRJS481L', 117, 136, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:18', 010071, 'NRJS480L', 332, 203, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:20', 010071, 'NRJS478L', 117, 273, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:22', 010071, 'NRJS477L', 332, 341, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:26', 010071, 'NRJS475L', 332, 479, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:24', 010071, 'NRJS476L', 117, 411, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:34', 030071, 'NRJS470L', 332, 839, false, NULL, true, false, NULL, true),
(92307100, 'Element:71:32', 020071, 'NRJS472L', 114, 730, false, NULL, true, false, NULL, true),
(92302300, 'Element:23:1', 000023, 'Imanager', 1789, 86, false, NULL, true, false, NULL, true);

