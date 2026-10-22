-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(910, 'TREE_EC_DAI', 'TREE_EC_DAI', 'LBL_TREE_EC_DAI', NULL, 'img/synoptics/Bratislava_EC_DAI.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91002500, 910, 'DAI', 'LBL_DAI', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(27, 910, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(28, 910, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(29, 910, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91002500, 'Element:25:183', 00002500, 'RAIDJSKD07', 386, 1037, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:182', 00002500, 'RAIDJSKD06', 386, 1001, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:181', 00002500, 'RAIDJSKD05', 386, 964, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:180', 00002500, 'RAIDJSKD04', 386, 928, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:179', 00002500, 'RAIDJSKD03', 386, 891, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:178', 00002500, 'RAIDJSKD02', 386, 854, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:177', 00002500, 'RAIDJSKD01', 386, 818, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:191', 00002500, 'RAIDJSKD15', 501, 1034, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:190', 00002500, 'RAIDJSKD14', 501, 998, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:189', 00002500, 'RAIDJSKD13', 501, 961, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:188', 00002500, 'RAIDJSKD12', 501, 924, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:187', 00002500, 'RAIDJSKD11', 501, 888, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:186', 00002500, 'RAIDJSKD10', 501, 851, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:185', 00002500, 'RAIDJSKD09', 501, 814, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:184', 00002500, 'RAIDJSKD08', 501, 778, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:199', 00002500, 'RAIDJSKD23', 620, 1037, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:198', 00002500, 'RAIDJSKD22', 620, 1000, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:197', 00002500, 'RAIDJSKD21', 620, 964, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:196', 00002500, 'RAIDJSKD20', 620, 927, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:195', 00002500, 'RAIDJSKD19', 620, 890, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:194', 00002500, 'RAIDJSKD18', 620, 854, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:193', 00002500, 'RAIDJSKD17', 620, 817, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:192', 00002500, 'RAIDJSKD16', 620, 780, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:206', 00002500, 'RAIDJSKD30', 734, 1037, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:205', 00002500, 'RAIDJSKD29', 734, 1000, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:204', 00002500, 'RAIDJSKD28', 734, 963, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:203', 00002500, 'RAIDJSKD27', 734, 927, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:202', 00002500, 'RAIDJSKD26', 734, 890, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:201', 00002500, 'RAIDJSKD25', 734, 853, false, NULL, true, false, NULL, true),
(91002500, 'Element:25:200', 00002500, 'RAIDJSKD24', 734, 817, false, NULL, true, false, NULL, true);

