-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(910, 'TREE_EC_DAI', 'TREE_EC_DAI', 'LBL_SYNOPTIC_EC_DAI', NULL, 'img/synoptics/Bratislava_EC_DAI.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91000200, 910, 'DAI', 'LBL_SYNOPTIC_EC_DAI', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(27, 910, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(28, 910, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(29, 910, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(102, 910, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91000200, 'Element:2:256', 00000200, 'RAIDJSKD24', 723, 816, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:257', 00000200, 'RAIDJSKD25', 723, 852, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:258', 00000200, 'RAIDJSKD26', 723, 890, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:259', 00000200, 'RAIDJSKD27', 723, 927, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:260', 00000200, 'RAIDJSKD28', 723, 964, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:261', 00000200, 'RAIDJSKD29', 723, 1001, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:262', 00000200, 'RAIDJSKD30', 723, 1038, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:255', 00000200, 'RAIDJSKD23', 614, 1038, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:254', 00000200, 'RAIDJSKD22', 614, 1001, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:253', 00000200, 'RAIDJSKD21', 614, 964, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:252', 00000200, 'RAIDJSKD20', 614, 927, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:251', 00000200, 'RAIDJSKD19', 614, 890, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:250', 00000200, 'RAIDJSKD18', 614, 852, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:249', 00000200, 'RAIDJSKD17', 614, 816, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:248', 00000200, 'RAIDJSKD16', 614, 779, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:240', 00000200, 'RAIDJSKD08', 498, 779, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:241', 00000200, 'RAIDJSKD09', 498, 816, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:242', 00000200, 'RAIDJSKD10', 498, 852, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:243', 00000200, 'RAIDJSKD11', 498, 890, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:244', 00000200, 'RAIDJSKD12', 498, 927, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:245', 00000200, 'RAIDJSKD13', 498, 964, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:246', 00000200, 'RAIDJSKD14', 498, 1001, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:247', 00000200, 'RAIDJSKD15', 498, 1038, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:239', 00000200, 'RAIDJSKD07', 386, 1038, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:238', 00000200, 'RAIDJSKD06', 386, 1001, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:237', 00000200, 'RAIDJSKD05', 386, 964, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:236', 00000200, 'RAIDJSKD04', 386, 927, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:235', 00000200, 'RAIDJSKD03', 386, 890, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:234', 00000200, 'RAIDJSKD02', 386, 852, false, NULL, true, false, NULL, true),
(91000200, 'Element:2:233', 00000200, 'RAIDJSKD01', 386, 816, false, NULL, true, false, NULL, true);

