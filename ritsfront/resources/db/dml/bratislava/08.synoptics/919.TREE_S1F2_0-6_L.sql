-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(919, 'TREE_S1F2_0-6_L', 'TREE_S1F2_0-6_L', 'LBL_SYNOPTIC_S1F2_0-6_L', NULL, 'img/synoptics/Bratislava_VMS_S1F2_0-6_L.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91907100, 919, 'S1F2_0-6_L', 'LBL_SYNOPTIC_S1F2_0-6_L', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(48, 919, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(49, 919, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(50, 919, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(111, 919, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91907100, 'Element:71:109', 050071, 'PSJS495LM', 1129, 333, false, NULL, true, false, NULL, true),
(91907100, 'Element:71:108', 050071, 'PSJS496LM', 1128, 170, false, NULL, true, false, NULL, true),
(91907100, 'Element:71:95', 040071, 'PSJS495LB', 847, 333, false, NULL, true, false, NULL, true),
(91907100, 'Element:71:94', 040071, 'PSJS496LB', 846, 170, false, NULL, true, false, NULL, true),
(91907100, 'Element:71:36', 010071, 'NRLJS497L', 239, 486, false, NULL, true, false, NULL, true),--se pide por parte del cliente borrar este equipo del scada
(91907100, 'Element:71:46', 010071, 'NRLJS494L', 627, 246, false, NULL, true, false, NULL, true),
(91907100, 'Element:71:44', 010071, 'NRLJS495L', 626, 87, false, NULL, true, false, NULL, true);

