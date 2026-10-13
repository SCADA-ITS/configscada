-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Cada nodo del árbol donde esté organizado el sinoptico --> 2 cifras
-- Orden del sinoptico dentro del nodo final --> 2 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(0201, 'TREE_TUNEL_STMI_SYNOPTIC', 'TREE_TUNEL_STMI_SYNOPTIC', 'LBL_TREE_TUNEL_STMI_SYNOPTIC', NULL, 'img/synoptics/Ausol_STMI_General.svg', false);

INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(020101, 0201, 'CCTV', 'LBL_CCTV', true),
(02010100202, 0201, 'CAM', 'LBL_CAM', true),
(020102, 0201, 'CTRL_ACCESOS', 'LBL_CTRL_ACCESOS', true),
(020102008, 0201, 'BAR', 'LBL_BAR', true),
(020102071, 0201, 'PMV', 'LBL_PMV', true),
(020102013, 0201, 'SEM', 'LBL_SEM', true),
(020103, 0201, 'INCENDIOS', 'LBL_INCENDIOS', true),
(020103016, 0201, 'ZONES', 'LBL_ZONES', true),
(020104, 0201, 'ILUMINACION', 'LBL_ILUMINACION', true),
(020104014, 0201, 'LUM', 'LBL_LUM', true),
(020104012, 0201, 'LUX', 'LBL_LUX', true),
(020104031, 0201, 'FOTOCELULA', 'LBL_FOTOCELULA', true),
(020104029, 0201, 'LOC_TEC', 'LBL_LOC_TEC', true),
(020105, 0201, 'METEO', 'LBL_METEO', true),
(020105001, 0201, 'EM', 'LBL_EM', true),
(020106, 0201, 'POS_SOS', 'LBL_POS_SOS', true),
(020106011, 0201, 'SOS', 'LBL_SOS', true),
(020106053, 0201, 'SEM', 'LBL_SEM', true),
(020107, 0201, 'DAT_TRAF', 'LBL_DAT_TRAF', true),
(020107051, 0201, 'ETD', 'LBL_ETD', true),
(020107004, 0201, 'SECCIONES', 'LBL_SECCIONES', true),
(020108, 0201, 'VENTILACION', 'LBL_VENTILACION', true),
(020108018, 0201, 'ANEM', 'LBL_ANEM', true),
(020108019, 0201, 'CO', 'LBL_CO', true),
(020108020, 0201, 'NO', 'LBL_NO', true),
(020108021, 0201, 'OPAC', 'LBL_OPAC', true),
(020108022, 0201, 'VALV', 'LBL_VALV', true),
(020108009, 0201, 'VNT', 'LBL_VNT', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(0201, 020101, 02010100202),
(0201, 020102, 020102008),
(0201, 020102, 020102071),
(0201, 020102, 020102013),
(0201, 020103, 020103016),
(0201, 020104, 020104014),
(0201, 020104, 020104012),
(0201, 020104, 020104031),
(0201, 020105, 020105001),
(0201, 020106, 020106011),
(0201, 020107, 020107051),
(0201, 020107, 020107004),
(0201, 020108, 020108018),
(0201, 020108, 020108019),
(0201, 020108, 020108020),
(0201, 020108, 020108021),
(0201, 020108, 020108022),
(0201, 020108, 020108009);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(0, 0201, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(1, 0201, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(2, 0201, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios
-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras
-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)
-- Tipo del elemento --> 4 cifras
INSERT INTO ui.graphic_icons 
(id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(01010002, 'CAM_DOMO', 'CAM_DOMO', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/02_camera_domo.svg', 'null',false),
(02020002, 'CAM_DAI', 'CAM_DAI', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/02_camera.svg', 'null',false),
(01020004, 'Seccion', 'Seccion', false, true, 'component/graphicIcon/graphicIconCustom.js', 100, 100, 'img/graphic_icon/04_section.svg', 'component/graphicIcon/custom/section.js',false),
(01000008, 'BAR', 'BAR', false, true, 'component/graphicIcon/graphicIconCustom.js', 50, 50, 'img/graphic_icon/08_barrier.svg', 'component/graphicIcon/custom/barrier.js',false),
(01010011, 'SOS', 'SOS', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/11_sos.svg', 'null',false),
(01000013, 'SEM', 'SEM', false, true, 'component/graphicIcon/graphicIconCustom.js', 50, 50, 'img/graphic_icon/13_sem_3_grupos.svg', 'component/graphicIcon/custom/trafficLight.js',false),
(01000031, 'FOTOCELULA', 'FOTOCELULA', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 70, 'img/graphic_icon/31_pho.svg', 'null',false),
(01000051, 'ETD', 'ETD', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/51_etd.svg', 'null',false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(02010100202, 'Element:2:35', 02020002, 'STMI-TV-2MA', 1567, 966, false, NULL, true, false, NULL, true),
(02010100202, 'Element:2:34', 02020002, 'STMI-TV-1MA', 285, 966, false, NULL, true, false, NULL, true),
(02010100202, 'Element:2:36', 01010002, 'STMI-TVD-1MA', 29, 539, false, NULL, true, false, NULL, true),
(02010100202, 'Element:2:31', 01010002, 'STMI-TVD-1CA', 1802, 539, false, NULL, true, false, NULL, true),
(02010100202, 'Element:2:33', 01010002, 'STMI-TV-2CA', 285, 110, false, NULL, true, false, NULL, true),
(02010100202, 'Element:2:32', 01010002, 'STMI-TV-1CA', 1567, 110, false, NULL, true, false, NULL, true),
(020102008, 'Element:8:9', 01010002, 'STMI-BAR-1MA', 214, 864, false, NULL, true, false, NULL, true),
(020102008, 'Element:8:10', 01010002, 'STMI-BAR-2MA', 214, 663, false, NULL, true, false, NULL, true),
(020102008, 'Element:8:12', 01010002, 'STMI-BAR-2CA', 1657, 406, false, NULL, true, false, NULL, true),
(020102008, 'Element:8:11', 01010002, 'STMI-BAR-1CA', 1657, 226, false, NULL, true, false, NULL, true),
(020102013, 'Element:13:6', 01010002, 'STMI-SMF-RAV-1MA', 148, 864, false, NULL, true, false, NULL, true),
(020102013, 'Element:13:5', 01010002, 'STMI-SMF-RAV-1CA', 1769, 226, false, NULL, true, false, NULL, true),
(020104031, 'Element:31:1', 01000031, 'STMI-FOT-1CA', 79, 539, false, NULL, true, false, NULL, true),
(020106011, 'Element:11:20', 01010002, 'STMI-SOSE-2MA', 1769, 966, false, NULL, true, false, NULL, true),
(020106011, 'Element:11:22', 01010002, 'STMI-SOS-1MA', 877, 966, false, NULL, true, false, NULL, true),
(020106011, 'Element:11:17', 01010002, 'STMI-SOSE-1MA', 151, 966, false, NULL, true, false, NULL, true),
(020106011, 'Element:11:18', 01010002, 'STMI-SOSE-2CA', 151, 110, false, NULL, true, false, NULL, true),
(020106011, 'Element:11:21', 01010002, 'STMI-SOS-1CA', 877, 110, false, NULL, true, false, NULL, true),
(020106011, 'Element:11:19', 01010002, 'STMI-SOSE-1CA', 1769, 110, false, NULL, true, false, NULL, true),
(020107051, 'Element:51:6', 01010002, 'STMI-ETD-1MA', 129, 539, false, NULL, true, false, NULL, true),
(020107051, 'Element:51:5', 01010002, 'STMI-ETD-1CA', 1699, 539, false, NULL, true, false, NULL, true),
(020107004, 'Element:4:10', 01020004, 'STMI-ETD-1-SEC-2MA-OUT', 1455, 749, false, NULL, true, false, NULL, true),
(020107004, 'Element:4:11', 01020004, 'STMI-ETD-2-SEC-1MA-IN', 562, 749, false, NULL, true, false, NULL, true),
(020107004, 'Element:4:9', 01020004, 'STMI-ETD-1-SEC-1CA-IN', 1312, 292, false, NULL, true, false, NULL, true),
(020107004, 'Element:4:12', 01020004, 'STMI-ETD-2-SEC-2CA-OUT', 245, 292, false, NULL, true, false, NULL, true);

