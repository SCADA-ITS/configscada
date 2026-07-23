-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios
-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras
-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)
-- Tipo del elemento --> 4 cifras
INSERT INTO ui.graphic_icons 
(id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(010002, 'CAMERA_DOMO', 'CAMERA_DOMO', false, false, 'component/graphicIcon/graphicIconDefault.js', 51.875, 51.875, 'img/graphic_icon/02_camera_domo.svg', 'null',true),
(020002, 'CAMERA_DAI', 'CAMERA_DAI', false, false, 'component/graphicIcon/graphicIconDefault.js', 51.875, 51.875, 'img/graphic_icon/02_camera.svg', 'null',true),
(000011, 'SOS', 'SOS', false, false, 'component/graphicIcon/graphicIconDefault.js', 51.875, 51.875, 'img/graphic_icon/11_sos.svg', 'null',true),
(000013, 'SEM', 'SEM', false, true, 'component/graphicIcon/graphicIconCustom.js', 51.875, 51.875, 'img/graphic_icon/13_sem_3_grupos.svg', 'component/graphicIcon/custom/trafficLight.js',true),
(020053, 'SEM_2_GRUPOS', 'SEM_2_GRUPOS', false, true, 'component/graphicIcon/graphicIconCustom.js', 49.799999, 49.799999, 'img/graphic_icon/53_sem_2_grupos.svg', 'component/graphicIcon/custom/redTrafficLight.js',true),
(000018, 'ANEM', 'ANEM', false, true, 'component/graphicIcon/graphicIconCustom.js', 51.875, 64.84375, 'img/graphic_icon/18_anemometro.svg', 'component/graphicIcon/custom/inAnemometer.js',true),
(000019, 'CO', 'CO', false, true, 'component/graphicIcon/graphicIconCustom.js', 51.875, 66.022789, 'img/graphic_icon/19_co.svg', 'component/graphicIcon/custom/co.js',true),
(000020, 'NO', 'NO', false, true, 'component/graphicIcon/graphicIconCustom.js', 51.875, 66.022789, 'img/graphic_icon/20_no.svg', 'component/graphicIcon/custom/no.js',true),
(000021, 'OPAC', 'OPAC', false, true, 'component/graphicIcon/graphicIconCustom.js', 51.875, 66.022751, 'img/graphic_icon/21_opac.svg', 'component/graphicIcon/custom/op.js',true),
(120071, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 62.25, 62.25, 'img/graphic_icon/71_vms_clv_af.svg', 'component/graphicIcon/custom/vms.js',true);

