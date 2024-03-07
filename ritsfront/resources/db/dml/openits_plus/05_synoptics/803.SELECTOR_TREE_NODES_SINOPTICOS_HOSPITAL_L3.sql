-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(803, 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L3', 'SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L3', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_HOSPITAL_L3', NULL, 'img/synoptics/hospital_p3.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(80308400, 803, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(80308900, 803, 'SYNOPTIC_LAYERS_CTROL_ACCESOS', 'LBL_SYNOPTIC_LAYERS_CTROL_ACCESOS', true),
(80304900, 803, 'SYNOPTIC_LAYERS_ASCENSORES', 'LBL_SYNOPTIC_LAYERS_ASCENSORES', true),
(80300021, 803, 'SYNOPTIC_LAYERS_DET_INCENDIOS', 'LBL_SYNOPTIC_LAYERS_DET_INCENDIOS', true),
(80305800, 803, 'SYNOPTIC_LAYERS_DET_DET_HUMOS', 'LBL_SYNOPTIC_LAYERS_DET_DET_HUMOS', true),
(80305900, 803, 'SYNOPTIC_LAYERS_DET_DET_PULSADOR', 'LBL_SYNOPTIC_LAYERS_DET_DET_PULSADOR', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(803, 80300021, 80305800),
(803, 80300021, 80305900);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(45, 803, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(46, 803, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(47, 803, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(80304900, 'Element:49:6', 000049, 'HOSP-ELEV-NE', 890, 268, false, NULL, true, false, NULL, true),
(80304900, 'Element:49:4', 000049, 'HOSP-ELEV-NC', 714, 347, false, NULL, true, false, NULL, true),
(80304900, 'Element:49:5', 000049, 'HOSP-ELEV-SC', 463, 618, false, NULL, true, false, NULL, true),
(80304900, 'Element:49:7', 000049, 'HOSP-ELEV-CE', 1027, 441, false, NULL, true, false, NULL, true);

