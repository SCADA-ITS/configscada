-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(929, 'TREE_S4-S5_L', 'TREE_S4-S5_L', 'LBL_SYNOPTIC_S4-S5_L', NULL, 'img/synoptics/Bratislava_VMS_S4S5_L.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92907100, 929, 'S4-S5_L', 'LBL_SYNOPTIC_S4-S5_L', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(78, 929, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(79, 929, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(80, 929, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(121, 929, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92907100, 'Element:71:97', 050071, 'NRSKD05LM', 824, 490, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:98', 050071, 'NRSKD07LM', 1490, 100, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:75', 040071, 'NRSKD07LB', 1207, 100, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:74', 040071, 'NRSKD05LB', 541, 490, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:135', 080071, 'PDZDH22L', 1272, 844, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:134', 080071, 'PDZDH21L', 1207, 844, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:125', 070071, 'PDZTDH01L', 1207, 736, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:127', 070071, 'PDZTDH14L', 1617, 954, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:147', 140071, 'PDZDH10L', 1617, 415, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:156', 150071, 'PDZDH12L', 1617, 685, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:155', 150071, 'PDZDH11L', 1617, 550, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:154', 150071, 'PDZDH09L', 1617, 280, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:141', 13007100, 'PDZDH13L', 1752, 767, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:139', 080071, 'PDZDH26L', 1682, 835, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:138', 080071, 'PDZDH25L', 1617, 835, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:131', 080071, 'PDZKD04L', 1272, 421, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:130', 080071, 'PDZKD03L', 1207, 421, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:5', 010071, 'NRLKD11L', 1207, 618, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:4', 010071, 'NRLKD10L', 1207, 514, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:3', 010071, 'NRLKD09L', 1207, 307, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:2', 010071, 'NRLKD08L', 1207, 203, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:1', 020071, 'NRLKD01L', 541, 16, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:73', 020071, 'NRLKD03L', 541, 299, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:172', 230071, 'NRSKD01dL', 545, 119, false, NULL, true, false, NULL, true),
(92907100, 'Element:71:171', 230071, 'NRSKD01cL', 592, 119, false, NULL, true, false, NULL, true);

