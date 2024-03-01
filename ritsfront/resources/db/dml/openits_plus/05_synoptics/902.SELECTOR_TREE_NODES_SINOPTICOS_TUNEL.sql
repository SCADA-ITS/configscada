-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(902, 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_TUNEL', NULL, 'img/synoptics/Ausol_LQ_General.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90200000, 902, 'SYNOPTIC_LAYERS_CONTROL_DE_ACCESSOS', 'LBL_SYNOPTIC_LAYERS_CONTROL_DE_ACCESSOS', true),
(90200001, 902, 'SYNOPTIC_LAYERS_DETECCION_INCENDIOS', 'LBL_SYNOPTIC_LAYERS_DETECCION_INCENDIOS', true),
(90200100, 902, 'SYNOPTIC_LAYERS_EM', 'LBL_SYNOPTIC_LAYERS_EM', true),
(90200002, 902, 'SYNOPTIC_LAYERS_ILUMINACION', 'LBL_SYNOPTIC_LAYERS_ILUMINACION', true),
(90205500, 902, 'SYNOPTIC_LAYERS_LOCALES_TECNICOS', 'LBL_SYNOPTIC_LAYERS_LOCALES_TECNICOS', true),
(90201100, 902, 'SYNOPTIC_LAYERS_POSTES_SOS', 'LBL_SYNOPTIC_LAYERS_POSTES_SOS', true),
(90200003, 902, 'SYNOPTIC_LAYERS_TRAFICO', 'LBL_SYNOPTIC_LAYERS_TRAFICO', true),
(90200004, 902, 'SYNOPTIC_LAYERS_VENTILACION', 'LBL_SYNOPTIC_LAYERS_VENTILACION', true),
(90200800, 902, 'SYNOPTIC_LAYERS_BARRERAS', 'LBL_SYNOPTIC_LAYERS_BARRERAS', true),
(90200200, 902, 'SYNOPTIC_LAYERS_CAMARAS', 'LBL_SYNOPTIC_LAYERS_CAMARAS', true),
(90207100, 902, 'SYNOPTIC_LAYERS_PANELES', 'LBL_SYNOPTIC_LAYERS_PANELES', true),
(90205300, 902, 'SYNOPTIC_LAYERS_SEMAFOROS_2GRUPOS', 'LBL_SYNOPTIC_LAYERS_SEMAFOROS_2GRUPOS', true),
(90201300, 902, 'SYNOPTIC_LAYERS_SEMAFOROS_3GRUPOS', 'LBL_SYNOPTIC_LAYERS_SEMAFOROS_3GRUPOS', true),
(90203200, 902, 'SYNOPTIC_LAYERS_PRESURIZACION', 'LBL_SYNOPTIC_LAYERS_PRESURIZACION', true),
(90208900, 902, 'SYNOPTIC_LAYERS_PUERTAS_EMERGENCIA', 'LBL_SYNOPTIC_LAYERS_PUERTAS_EMERGENCIA', true),
(90201600, 902, 'SYNOPTIC_LAYERS_ZONAS_INCENDIO', 'LBL_SYNOPTIC_LAYERS_ZONAS_INCENDIO', true),
(90203100, 902, 'SYNOPTIC_LAYERS_ZONAS_FOTOCELULAS', 'LBL_SYNOPTIC_LAYERS_ZONAS_FOTOCELULAS', true),
(90201200, 902, 'SYNOPTIC_LAYERS_ZONAS_LUMINANCIMETROS', 'LBL_SYNOPTIC_LAYERS_ZONAS_LUMINANCIMETROS', true),
(90201400, 902, 'SYNOPTIC_LAYERS_ZONAS_LUXOMETROS', 'LBL_SYNOPTIC_LAYERS_ZONAS_LUXOMETROS', true),
(90205100, 902, 'SYNOPTIC_LAYERS_ZONAS_ETDS', 'LBL_SYNOPTIC_LAYERS_ZONAS_ETDS', true),
(90200400, 902, 'SYNOPTIC_LAYERS_ZONAS_SECCIONES', 'LBL_SYNOPTIC_LAYERS_ZONAS_SECCIONES', true),
(90201800, 902, 'SYNOPTIC_LAYERS_ZONAS_ANEMOMETROS', 'LBL_SYNOPTIC_LAYERS_ZONAS_ANEMOMETROS', true),
(90201900, 902, 'SYNOPTIC_LAYERS_ZONAS_DETECTORES_CO', 'LBL_SYNOPTIC_LAYERS_ZONAS_DETECTORES_CO', true),
(90202000, 902, 'SYNOPTIC_LAYERS_ZONAS_DETECTORES_NO', 'LBL_SYNOPTIC_LAYERS_ZONAS_DETECTORES_NO', true),
(90202100, 902, 'SYNOPTIC_LAYERS_ZONAS_DETECTORES_OPAC', 'LBL_SYNOPTIC_LAYERS_ZONAS_DETECTORES_OPAC', true),
(90206600, 902, 'SYNOPTIC_LAYERS_ZONAS_VELETAS', 'LBL_SYNOPTIC_LAYERS_ZONAS_VELETAS', true),
(90200900, 902, 'SYNOPTIC_LAYERS_ZONAS_VENTILADORES', 'LBL_SYNOPTIC_LAYERS_ZONAS_VENTILADORES', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(902, 90200000, 90200800),
(902, 90200000, 90200200),
(902, 90200000, 90207100),
(902, 90200000, 90205300),
(902, 90200000, 90201300),
(902, 90200001, 90203200),
(902, 90200001, 90208900),
(902, 90200001, 90201600),
(902, 90200002, 90203100),
(902, 90200002, 90201200),
(902, 90200002, 90201400),
(902, 90200003, 90205100),
(902, 90200003, 90200400),
(902, 90200004, 90201800),
(902, 90200004, 90201900),
(902, 90200004, 90202000),
(902, 90200004, 90202100),
(902, 90200004, 90206600),
(902, 90200004, 90200900);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(3, 902, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(4, 902, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(5, 902, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90205500, 'Element:55:40', 000055, 'LQ-CBT', 1927, 556, false, NULL, true, false, NULL, true),
(90203200, 'Element:32:4', 000032, 'LQ-CUAD-PRES-GAL-MA', 604, 589, false, NULL, true, false, NULL, true),
(90203200, 'Element:32:3', 000032, 'LQ-CUAD-PRES-GAL-CA', 673, 519, false, NULL, true, false, NULL, true),
(90203100, 'Element:31:2', 000031, 'LQ-FOT-1MA', 1275, 846, false, NULL, true, false, NULL, true),
(90201400, 'Element:14:5', 000014, 'LQ-LUM-1CA', 1791, 554, false, NULL, true, false, NULL, true),
(90201400, 'Element:14:6', 000014, 'LQ-LUM-1MA', 74, 556, false, NULL, true, false, NULL, true),
(90201800, 'Element:18:7', 00001800, 'LQ-ANI-1CA', 1364, 234, false, NULL, true, false, NULL, true),
(90201800, 'Element:18:13', 00001800, 'LQ-ANI-3MA', 1508, 867, false, NULL, true, false, NULL, true),
(90201800, 'Element:18:12', 00001800, 'LQ-ANI-2MA', 927, 867, false, NULL, true, false, NULL, true),
(90201800, 'Element:18:11', 00001800, 'LQ-ANI-1MA', 480, 867, false, NULL, true, false, NULL, true),
(90201800, 'Element:18:9', 00001800, 'LQ-ANI-3CA', 694, 232, false, NULL, true, false, NULL, true),
(90201800, 'Element:18:8', 00001800, 'LQ-ANI-2CA', 913, 230, false, NULL, true, false, NULL, true),
(90201900, 'Element:19:9', 000019, 'LQ-CO-4MA', 1624, 127, false, NULL, true, false, NULL, true),
(90202000, 'Element:20:10', 000020, 'LQ-NO-2CA', 1130, 127, false, NULL, true, false, NULL, true),
(90202000, 'Element:20:12', 000020, 'LQ-NO-4CA', 486, 127, false, NULL, true, false, NULL, true),
(90202000, 'Element:20:13', 000020, 'LQ-NO-5CA', 289, 127, false, NULL, true, false, NULL, true),
(90202000, 'Element:20:14', 000020, 'LQ-NO-1MA', 507, 968, false, NULL, true, false, NULL, true),
(90202000, 'Element:20:9', 000020, 'LQ-NO-1CA', 1673, 127, false, NULL, true, false, NULL, true),
(90202000, 'Element:20:16', 000020, 'LQ-NO-3MA', 1052, 968, false, NULL, true, false, NULL, true),
(90202000, 'Element:20:15', 000020, 'LQ-NO-2MA', 806, 968, false, NULL, true, false, NULL, true),
(90202000, 'Element:20:17', 000020, 'LQ-NO-4MA', 1548, 968, false, NULL, true, false, NULL, true),
(90202000, 'Element:20:11', 000020, 'LQ-NO-3CA', 883, 127, false, NULL, true, false, NULL, true),
(90202100, 'Element:21:10', 000021, 'LQ-OPC-2CA', 1179, 127, false, NULL, true, false, NULL, true),
(90202100, 'Element:21:12', 000021, 'LQ-OPC-4CA', 535, 127, false, NULL, true, false, NULL, true),
(90202100, 'Element:21:13', 000021, 'LQ-OPC-5CA', 338, 127, false, NULL, true, false, NULL, true),
(90202100, 'Element:21:14', 000021, 'LQ-OPC-1MA', 556, 968, false, NULL, true, false, NULL, true),
(90202100, 'Element:21:9', 000021, 'LQ-OPC-1CA', 1722, 127, false, NULL, true, false, NULL, true),
(90202100, 'Element:21:16', 000021, 'LQ-OPC-3MA', 1101, 968, false, NULL, true, false, NULL, true),
(90202100, 'Element:21:15', 000021, 'LQ-OPC-2MA', 904, 968, false, NULL, true, false, NULL, true),
(90202100, 'Element:21:17', 000021, 'LQ-OPC-4MA', 1597, 968, false, NULL, true, false, NULL, true),
(90202100, 'Element:21:11', 000021, 'LQ-OPC-3CA', 932, 127, false, NULL, true, false, NULL, true);

