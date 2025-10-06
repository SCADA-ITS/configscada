-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios
-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras
-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)
-- Tipo del elemento --> 4 cifras
INSERT INTO ui.graphic_icons 
(id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(000002, 'CAM_DOMO', 'CAM_DOMO', false, false, 'component/graphicIcon/graphicIconDefault.js', 48, 48, 'img/graphic_icon/02_camera_domo.svg', 'null',true),
(000003, 'DETECTOR', 'DETECTOR', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/03_detector.svg', 'null',true),
(000004, 'SECCION', 'SECCION', false, false, 'component/graphicIcon/graphicIconDefault.js', 94, 94, 'img/graphic_icon/04_section.svg', 'null',true),
(000071, 'PANEL', 'PANEL', false, true, 'component/graphicIcon/graphicIconCustom.js', 283, 94, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'component/graphicIcon/custom/vms.js',true),

(010002, 'CAM_DOMO', 'CAM_DOMO', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/02_camera_domo.svg', 'null',true),
(010004, 'SECCION', 'SECCION', false, false, 'component/graphicIcon/graphicIconDefault.js', 80, 80, 'img/graphic_icon/04_section.svg', 'null',true),
(010071, 'PANEL', 'PANEL', false, true, 'component/graphicIcon/graphicIconCustom.js', 200, 100, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'component/graphicIcon/custom/vms.js',true);