-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios
-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras
-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)
-- Tipo del elemento --> 4 cifras
INSERT INTO ui.graphic_icons 
(id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(000002, 'CAM_DOMO', 'CAM_DOMO', false, false, 'component/graphicIcon/graphicIconDefault.js', 15, 15, 'img/graphic_icon/02_camera_domo.svg', 'null',true),
(000004, 'SECCION', 'SECCION', false, false, 'component/graphicIcon/graphicIconDefault.js', 25, 25, 'img/graphic_icon/04_section.svg', 'null',true),
(000071, 'PANEL', 'PANEL', false, false, 'component/graphicIcon/graphicIconDefault.js', 75, 25, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'null',true);

