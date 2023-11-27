-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios
-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras
-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)
-- Tipo del elemento --> 4 cifras
INSERT INTO ui.graphic_icons 
(id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(01030001, 'ELEMENT_TYPE_WEATHER_STATION', 'ELEMENT_TYPE_WEATHER_STATION', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/01_weather_station.svg', 'null',false),

(01010002, 'ELEMENT_TYPE_CAMERA', 'ELEMENT_TYPE_CAMERA', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/02_camera_domo.svg', 'null',false),
(02020002, 'ELEMENT_TYPE_CAMERA', 'ELEMENT_TYPE_CAMERA', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/02_camera.svg', 'null',false),

(01020004, 'ELEMENT_TYPE_SECTION', 'ELEMENT_TYPE_SECTION', false, false, 'component/graphicIcon/graphicIconDefault.js', 100, 90, 'img/graphic_icon/04_section.svg', 'null',false),

(01010005, 'ELEMENT_TYPE_PA_SYSTEM', 'ELEMENT_TYPE_PA_SYSTEM', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/05_pa_system.svg', 'null',false),

(01020008, 'ELEMENT_TYPE_BARRIER', 'ELEMENT_TYPE_BARRIER', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/08_barrier.svg', 'null',false),

(01010009, 'ELEMENT_TYPE_JETFAN', 'ELEMENT_TYPE_JETFAN', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/09_jetfan.svg', 'null',false),

(01010011, 'ELEMENT_TYPE_SOS', 'ELEMENT_TYPE_SOS', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/11_sos.svg', 'null',false),

(01000012, 'ELEMENT_TYPE_LUX', 'ELEMENT_TYPE_LUX', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 80, 'img/graphic_icon/12_lux.svg', 'null',false),

(01000013, 'ELEMENT_TYPE_SEM', 'ELEMENT_TYPE_SEM', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/13_sem_3_grupos.svg', 'null',false),

(01000014, 'ELEMENT_TYPE_LUM', 'ELEMENT_TYPE_LUM', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 80, 'img/graphic_icon/14_lum.svg', 'null',false),

(01000015, 'ELEMENT_TYPE_FIRE_DET', 'ELEMENT_TYPE_FIRE_DET', false, false, 'component/graphicIcon/graphicIconDefault.js', 188, 20, 'img/graphic_icon/15_fire.svg', 'null',false),

(01000016, 'ELEMENT_TYPE_PA_ZONE', 'ELEMENT_TYPE_PA_ZONE', false, false, 'component/graphicIcon/graphicIconDefault.js', 125, 55, 'img/graphic_icon/16_pa_zone_width.svg', 'null',false),

(01000018, 'ELEMENT_TYPE_ANEM_INT', 'ELEMENT_TYPE_ANEM_INT', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/18_anemometro.svg', 'null',false),

(01000019, 'ELEMENT_TYPE_CO', 'ELEMENT_TYPE_CO', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 80, 'img/graphic_icon/19_co.svg', 'null',false),

(01000020, 'ELEMENT_TYPE_NO', 'ELEMENT_TYPE_NO', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 80, 'img/graphic_icon/20_no.svg', 'null',false),

(01000021, 'ELEMENT_TYPE_OPAC', 'ELEMENT_TYPE_OPAC', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 80, 'img/graphic_icon/21_opac.svg', 'null',false),

(01000026, 'ELEMENT_TYPE_SWITCH_COMM', 'ELEMENT_TYPE_SWITCH_COMM', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/26_switch.svg', 'null',false),

(01020027, 'ELEMENT_TYPE_PLC', 'ELEMENT_TYPE_PLC', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/27_plc.svg', 'null',false),

(01000028, 'ELEMENT_TYPE_NAS', 'ELEMENT_TYPE_NAS', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/28_nas.svg', 'null',false),

(01000030, 'ELEMENT_TYPE_RADIO', 'ELEMENT_TYPE_RADIO', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/30_radio.svg', 'null',false),

(01000031, 'ELEMENT_TYPE_PHOTOCELL', 'ELEMENT_TYPE_PHOTOCELL', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 70, 'img/graphic_icon/31_pho.svg', 'null',false),

(01020032, 'ELEMENT_TYPE_PRESURIZACION', 'ELEMENT_TYPE_PRESURIZACION', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/32_presurizacion_vent.svg', 'null',false),

(01000033, 'ELEMENT_TYPE_TRAFO', 'ELEMENT_TYPE_TRAFO', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/33_trafo.svg', 'null',false),

(01000070, 'ELEMENT_TYPE_ETD', 'ELEMENT_TYPE_ETD', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/51_etd.svg', 'null',false),

(01000052, 'ELEMENT_TYPE_DAI_SERVER', 'ELEMENT_TYPE_DAI_SERVER', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/52_dai_server.svg', 'null',false),

(01010053, 'ELEMENT_TYPE_SEM_2_LUCES', 'ELEMENT_TYPE_SEM_2_LUCES', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/53_sem_2_grupos.svg', 'null',false),
(02020053, 'ELEMENT_TYPE_SEM_2_LUCES', 'ELEMENT_TYPE_SEM_2_LUCES', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/53_sem_2_grupos.svg', 'null',false),

(01020055, 'ELEMENT_TYPE_CT', 'ELEMENT_TYPE_CT', false, false, 'component/graphicIcon/graphicIconDefault.js', 100, 100, 'img/graphic_icon/55_ct.svg', 'null',false),

(01000056, 'ELEMENT_TYPE_UNIFILAR', 'ELEMENT_TYPE_UNIFILAR', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/56_unifilar.svg', 'null',false),

(01000057, 'ELEMENT_TYPE_INTERRUPTOR', 'ELEMENT_TYPE_INTERRUPTOR', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/57_switch.svg', 'null',false),

(01000065, 'ELEMENT_TYPE_SAI', 'ELEMENT_TYPE_SAI', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/65_sai.svg', 'null',false),

(01010066, 'ELEMENT_TYPE_VANE', 'ELEMENT_TYPE_VANE', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 65.723999, 'img/graphic_icon/66_vane.svg', 'null',false),

(01010070, 'ELEMENT_TYPE_ANALIZADOR_RED', 'ELEMENT_TYPE_ANALIZADOR_RED', false, false, 'component/graphicIcon/graphicIconDefault.js', 35, 35, 'img/graphic_icon/70_analizador_red.svg', 'null',false),

(01180071, 'ELEMENT_TYPE_PANEL', 'ELEMENT_TYPE_PANEL', false, false, 'component/graphicIcon/graphicIconDefault.js', 300, 90, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'null',false),
(02220071, 'ELEMENT_TYPE_PANEL', 'ELEMENT_TYPE_PANEL', false, false, 'component/graphicIcon/graphicIconDefault.js', 300, 90, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'null',false),

(01000074, 'ELEMENT_TYPE_ERU', 'ELEMENT_TYPE_ERU', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/74_eru.svg', 'null',false),

(01010075, 'ELEMENT_TYPE_GE', 'ELEMENT_TYPE_GE', false, false, 'component/graphicIcon/graphicIconDefault.js', 100, 100, 'img/graphic_icon/75_ge.svg', 'null',false),
(02040031, 'ELEMENT_TYPE_GE', 'ELEMENT_TYPE_GE', false, false, 'component/graphicIcon/graphicIconDefault.js', 100, 100, 'img/graphic_icon/75_ge.svg', 'null',false),

(01000087, 'ELEMENT_TYPE_TANK', 'ELEMENT_TYPE_TANK', false, false, 'component/graphicIcon/graphicIconDefault.js', 65, 85, 'img/graphic_icon/87_tank.svg', 'null',false),

(01000089, 'ELEMENT_TYPE_GATE', 'ELEMENT_TYPE_GATE', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/89_gate.svg', 'null',false),

(01000093, 'ELEMENT_TYPE_TRANSFER_GROUP', 'ELEMENT_TYPE_TRANSFER_GROUP', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/93_transfer_group.svg', 'null',false),;

