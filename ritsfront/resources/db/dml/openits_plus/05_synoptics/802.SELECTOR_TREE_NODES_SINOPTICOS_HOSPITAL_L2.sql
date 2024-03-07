-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(802, 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L2', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L2', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L2', NULL, 'img/synoptics/hospital_p2.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(80208400, 802, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(80208900, 802, 'SYNOPTIC_LAYERS_CTROL_ACCESOS', 'LBL_SYNOPTIC_LAYERS_CTROL_ACCESOS', true),
(80204900, 802, 'SYNOPTIC_LAYERS_ASCENSORES', 'LBL_SYNOPTIC_LAYERS_ASCENSORES', true),
(80200020, 802, 'SYNOPTIC_LAYERS_DET_INCENDIOS', 'LBL_SYNOPTIC_LAYERS_DET_INCENDIOS', true),
(80205800, 802, 'SYNOPTIC_LAYERS_DET_DET_HUMOS', 'LBL_SYNOPTIC_LAYERS_DET_DET_HUMOS', true),
(80205900, 802, 'SYNOPTIC_LAYERS_DET_DET_PULSADOR', 'LBL_SYNOPTIC_LAYERS_DET_DET_PULSADOR', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(802, 80200020, 80205800),
(802, 80200020, 80205900);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(42, 802, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(43, 802, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(44, 802, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(80204900, 'Element:49:7', 000049, 'HOSP-ELEV-CE', 1031, 439, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:2', 000049, 'HOSP-ELEV-CO', 406, 353, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:3', 000049, 'HOSP-ELEV-SO', 306, 553, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:6', 000049, 'HOSP-ELEV-NE', 883, 266, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:4', 000049, 'HOSP-ELEV-NC', 729, 345, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:1', 000049, 'HOSP-ELEV-NO', 339, 229, false, NULL, true, false, NULL, true),
(80204900, 'Element:49:5', 000049, 'HOSP-ELEV-SC', 459, 613, false, NULL, true, false, NULL, true);

