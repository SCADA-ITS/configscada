-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(905, 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_VENTILACION', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_VENTILACION', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_VENTILACION', NULL, 'img/synoptics/Ausol_LQ_Ventilation.svg', false);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(12, 905, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(13, 905, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(14, 905, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

