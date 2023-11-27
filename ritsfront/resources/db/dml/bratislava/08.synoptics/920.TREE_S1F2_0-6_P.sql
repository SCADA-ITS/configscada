-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(920, 'TREE_S1F2_0-6_P', 'TREE_S1F2_0-6_P', 'LBL_SYNOPTIC_S1F2_0-6_P', NULL, 'img/synoptics/Bratislava_VMS_S1F2_0-6_P.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92007100, 920, 'S1F2_0-6_P', 'LBL_SYNOPTIC_S1F2_0-6_P', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(51, 920, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(52, 920, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(53, 920, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(112, 920, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92007100, 'Element:71:103', 050071, 'PSJS400M', 494, 966, false, NULL, true, false, NULL, true),--se pide por parte del cliente borrar este equipo del scada
(92007100, 'Element:71:80', 040071, 'PSJS400B', 212, 966, false, NULL, true, false, NULL, true),--se pide por parte del cliente borrar este equipo del scada
(92007100, 'Element:71:120', 060071, 'NRSPS400BPS', 652, 966, false, NULL, true, false, NULL, true),--se pide por parte del cliente borrar este equipo del scada
(92007100, 'Element:71:47', 010071, 'NRLJS405P', 814, 160, false, NULL, true, false, NULL, true),
(92007100, 'Element:71:45', 010071, 'NRLJS404P', 1040, 88, false, NULL, true, false, NULL, true),
(92007100, 'Element:71:43', 010071, 'NRLJS403P', 813, 15, false, NULL, true, false, NULL, true);--se pide por parte del cliente borrar este equipo del scada

