-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(922, 'TREE_S1F2_6-11_P', 'TREE_S1F2_6-11_P', 'LBL_TREE_S1F2_6-11_P', NULL, 'img/synoptics/Bratislava_VMS_S1F2_6-11_P.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92207100, 922, 'S1F2_6-11_P', 'LBL_S1F2_6-11_P', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(57, 922, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(58, 922, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(59, 922, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92207100, 'Element:71:111', 050071, 'PSJS403PM', 585, 336, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:110', 050071, 'PSJS402PM', 585, 227, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:82', 040071, 'PSJS403PB', 303, 336, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:81', 040071, 'PSJS402PB', 303, 227, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:148', 140071, 'PDZJS05P', 1460, 306, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:143', 130071, 'PDZJS02Pb', 1071, 477, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:142', 130071, 'PDZJS01Pb', 991, 477, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:159', 150071, 'PDZJS03P', 989, 528, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:158', 150071, 'PDZJS02Pa', 1072, 361, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:157', 150071, 'PDZJS01Pa', 992, 360, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:160', 150071, 'PDZJS04P', 1460, 88, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:52', 010071, 'NRLJS408P', 72, 279, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:48', 010071, 'NRLJS406P', 568, 9, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:50', 010071, 'NRLJS407P', 568, 118, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:72', 010071, 'NRLJS410P', 568, 445, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:7', 010071, 'NRJS412P', 982, 192, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:11', 010071, 'NRJS415P', 1460, 415, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:13', 010071, 'NRJS416P', 1460, 524, false, NULL, true, false, NULL, true),
(92207100, 'Element:71:9', 010071, 'NRJS414P', 1460, 197, false, NULL, true, false, NULL, true);

