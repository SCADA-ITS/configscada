-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios
-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras
-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)
-- Tipo del elemento --> 4 cifras
INSERT INTO ui.graphic_icons 
(id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(010002, 'CAM_DOMO', 'CAM_DOMO', false, false, 'component/graphicIcon/graphicIconDefault.js', 37.987198, 38.57143, 'img/graphic_icon/02_camera_domo.svg', 'null',true),
(020002, 'CAM_DAI', 'CAM_DAI', false, false, 'component/graphicIcon/graphicIconDefault.js', 37.651199, 38.243572, 'img/graphic_icon/02_camera.svg', 'null',true),
(010009, 'VENTILADORES', 'VENTILADORES', false, true, 'component/graphicIcon/graphicIconCustom.js', 36.681599, 38.57143, 'img/graphic_icon/09_jetfan.svg', 'component/graphicIcon/custom/jetfan.js',true),
(010011, 'SOS', 'SOS', false, false, 'component/graphicIcon/graphicIconDefault.js', 38.400002, 38.57143, 'img/graphic_icon/11_sos.svg', 'null',true),
(000013, 'SEM', 'SEM', false, true, 'component/graphicIcon/graphicIconCustom.js', 38.400002, 38.57143, 'img/graphic_icon/13_sem_3_grupos.svg', 'component/graphicIcon/custom/trafficLight.js',true),
(110071, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 39.792, 39.555, 'img/graphic_icon/71_vms_af_clv.svg', 'component/graphicIcon/custom/vms.js',true),
(120071, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 39.3792, 39.555, 'img/graphic_icon/71_vms_af_clv.svg', 'component/graphicIcon/custom/vms.js',true),
(180071, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 165.9552, 55.870712, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'component/graphicIcon/custom/vms.js',true),
(01000900, 'VENTILADORES', 'VENTILADORES', false, true, 'component/graphicIcon/graphicIconCustom.js', 36.9888, 38.899284, 'img/graphic_icon/09_jetfan.svg', 'component/graphicIcon/custom/jetfan.js',true),
(00001301, 'SEM', 'SEM', false, true, 'component/graphicIcon/graphicIconCustom.js', 40.195198, 40.374641, 'img/graphic_icon/13_sem_3_grupos.svg', 'component/graphicIcon/custom/trafficLight.js',true),
(00001302, 'SEM', 'SEM', false, true, 'component/graphicIcon/graphicIconCustom.js', 39.705601, 39.882858, 'img/graphic_icon/13_sem_3_grupos.svg', 'component/graphicIcon/custom/trafficLight.js',true),
(01001103, 'SOS', 'SOS', false, false, 'component/graphicIcon/graphicIconDefault.js', 38.073601, 38.243572, 'img/graphic_icon/11_sos.svg', 'null',true),
(01001104, 'SOS', 'SOS', false, false, 'component/graphicIcon/graphicIconDefault.js', 39.052799, 39.227142, 'img/graphic_icon/11_sos.svg', 'null',true),
(01001105, 'SOS', 'SOS', false, false, 'component/graphicIcon/graphicIconDefault.js', 38.726398, 38.899284, 'img/graphic_icon/11_sos.svg', 'null',true),
(02000206, 'CAM_DAI', 'CAM_DAI', false, false, 'component/graphicIcon/graphicIconDefault.js', 38.6208, 39.227142, 'img/graphic_icon/02_camera.svg', 'null',true),
(02000207, 'CAM_DAI', 'CAM_DAI', false, false, 'component/graphicIcon/graphicIconDefault.js', 37.9776, 38.57143, 'img/graphic_icon/02_camera.svg', 'null',true),
(02000208, 'CAM_DAI', 'CAM_DAI', false, false, 'component/graphicIcon/graphicIconDefault.js', 38.294399, 38.899284, 'img/graphic_icon/02_camera.svg', 'null',true);

