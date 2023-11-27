-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(641, 'SYNOPTIC_COR_CT_PLANO', 'SYNOPTIC_COR_CT_PLANO', 'LBL_SYNOPTIC_COR_CT_PLANO', NULL, 'img/synoptics/Ausol_COR_CT_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(64105500, 641, 'LAYER_CT', 'LBL_LAYER_CT', true),
(64107500, 641, 'LAYER_GE', 'LBL_LAYER_GE', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(27, 641, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(28, 641, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(29, 641, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(64105500, 'Element:55:22', 01020055, 'COR-CITS-BM', 1569, 916, false, NULL, true, false, NULL, true),
(64105500, 'Element:55:23', 01020055, 'COR-CGBT-BM', 1689, 916, false, NULL, true, false, NULL, true),
(64105500, 'Element:55:21', 01020055, 'COR-CITS-BC', 290, 69, false, NULL, true, false, NULL, true),
(64105500, 'Element:55:20', 01020055, 'COR-CGBT-BC', 168, 69, false, NULL, true, false, NULL, true),
(64105500, 'Element:55:19', 01020055, 'COR-PCI-BC', 42, 136, false, NULL, true, false, NULL, true),
(64107500, 'Element:75:14', 01010075, 'COR-GEL-BM', 1809, 916, false, NULL, true, false, NULL, true),
(64107500, 'Element:75:13', 01010075, 'COR-GEL-BC', 42, 32, false, NULL, true, false, NULL, true);

