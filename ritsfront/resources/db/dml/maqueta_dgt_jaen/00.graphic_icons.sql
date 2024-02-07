-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios
-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras
-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)
-- Tipo del elemento --> 4 cifras
INSERT INTO ui.graphic_icons 
(id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(040009, 'JETFAN', 'JETFAN', false, true, 'component/graphicIcon/graphicIconCustom.js', 40, 42.250332, 'img/graphic_icon/09_jetfan.svg', 'component/graphicIcon/custom/jetfan.js',true),
(000013, 'SEM', 'SEM', false, true, 'component/graphicIcon/graphicIconCustom.js', 40, 40, 'img/graphic_icon/13_sem_3_grupos.svg', 'component/graphicIcon/custom/trafficLight.js',true),
(000018, 'ANEM', 'ANEM', false, true, 'component/graphicIcon/graphicIconCustom.js', 40, 50, 'img/graphic_icon/18_anemometro.svg', 'component/graphicIcon/custom/inAnemometer.js',true),
(000019, 'CO', 'CO', false, false, 'component/graphicIcon/graphicIconDefault.js', 40, 50.909107, 'img/graphic_icon/19_co.svg', 'null',true),
(000021, 'OPAC', 'OPAC', false, false, 'component/graphicIcon/graphicIconDefault.js', 40, 50.909107, 'img/graphic_icon/21_opac.svg', 'null',true),
(030032, 'PRESURIZACION', 'PRESURIZACION', false, false, 'component/graphicIcon/graphicIconDefault.js', 40, 40, 'img/graphic_icon/32_presurizacion_vent.svg', 'component/graphicIcon/custom/vms.js',true),
(110071, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 40, 40, 'img/graphic_icon/71_vms_clv_af.svg', 'component/graphicIcon/custom/vms.js',true),
(120071, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 40, 40, 'img/graphic_icon/71_vms_clv_af.svg', 'component/graphicIcon/custom/vms.js',true),
(180071, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 165.9552, 55.870712, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'component/graphicIcon/custom/vms.js',true),
(220071, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 300, 100,'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js', true),
(000089, 'GATE', 'GATE', false, false, 'component/graphicIcon/graphicIconDefault.js', 40, 40, 'img/graphic_icon/89_gate.svg', 'null',true);

