-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios
-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras
-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)
-- Tipo del elemento --> 4 cifras
INSERT INTO ui.graphic_icons (id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(010002, 'ANPR', 'ANPR', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/02_camera.svg', 'null',true),
(020002, 'DAI', 'DAI', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/02_camera_domo.svg', 'null',true),
(030002, 'DOMO', 'DOMO', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/02_camera_domo.svg', 'null',true),
(040002, 'CAM_SANCTIONING', 'CAM_SANCTIONING', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/02_camera_sanctioning2.svg', 'null',true),
(000025, 'ELECTRICAL_CABINET_PLC_SOL', 'ELECTRICAL_CABINET_PLC_SOL', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/25_electrical_cabinet_plc_sol.svg', 'null',true),
(010025, 'ELECTRICAL_CABINET_SOL', 'ELECTRICAL_CABINET_SOL', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/25_electrical_cabinet_sol.svg', 'null',true),
(020025, 'ELECTRICAL_CABINET_TOTEM', 'ELECTRICAL_CABINET_TOTEM', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/25_electrical_cabinet_totem.svg', 'null',true),
(030025, 'ELECTRICAL_CABINET_PLC', 'ELECTRICAL_CABINET_PLC', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/25_electrical_cabinet_plc.svg', 'null',true),
(000052, 'DAI_SERVER', 'DAI_SERVER', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/52_dai_server.svg', 'null',true),
(000069, 'RDIF', 'RDIF', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/69_rdif.svg', 'null',true),
(190071, 'VMS', 'VMS', false, false, 'component/graphicIcon/graphicIconDefault.js', 160, 55, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'null',true),
(000095, 'SYNC_SERVER', 'SYNC_SERVER', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/95_sync_server.svg', 'null',true),

--MAPS
(2, 'CAM', 'CAM', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/02_camera.svg', null,true),
(201, 'CAM', 'CAM', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/02_camera.svg', null,true),

(211, 'CAM_DOMO_1', 'CAM_DOMO_1', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/02_camera_domo.svg', null,true),
(212, 'CAM_DOMO_1', 'CAM_DOMO_1', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/02_camera_domo.svg', null,true),

(22, 'CAM_SANCTIONING', 'CAM_SANCTIONING', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/02_camera_sanctioning2.svg', null,true),
(202, 'CAM_SANCTIONING', 'CAM_SANCTIONING', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/02_camera_sanctioning2.svg', null,true),

(252, 'EC', 'EC', false, false, 'component/graphicIcon/graphicIconDefault.js',30, 30, 'img/graphic_icon/25_electrical_cabinet_plc.svg', null,true),
(2520, 'EC', 'EC', false, false, 'component/graphicIcon/graphicIconDefault.js',50, 50, 'img/graphic_icon/25_electrical_cabinet_plc.svg', null,true),
(253, 'EC', 'EC', false, false, 'component/graphicIcon/graphicIconDefault.js',30, 30, 'img/graphic_icon/25_electrical_cabinet_plc_sol.svg', null,true),
(2530, 'EC', 'EC', false, false, 'component/graphicIcon/graphicIconDefault.js',50, 50, 'img/graphic_icon/25_electrical_cabinet_plc_sol.svg', null,true),
(254, 'EC', 'EC', false, false, 'component/graphicIcon/graphicIconDefault.js',30, 30, 'img/graphic_icon/25_electrical_cabinet_sol.svg', null,true),
(2540, 'EC', 'EC', false, false, 'component/graphicIcon/graphicIconDefault.js',50, 50, 'img/graphic_icon/25_electrical_cabinet_sol.svg', null,true),
(255, 'EC', 'EC', false, false, 'component/graphicIcon/graphicIconDefault.js',30, 30, 'img/graphic_icon/25_electrical_cabinet_totem.svg', null,true),
(2550, 'EC', 'EC', false, false, 'component/graphicIcon/graphicIconDefault.js',50, 50, 'img/graphic_icon/25_electrical_cabinet_totem.svg', null,true),

(520, 'DAI', 'DAI', false, false, 'component/graphicIcon/graphicIconDefault.js', 50, 50, 'img/graphic_icon/52_dai_server.svg', null,true),

(71, 'PANEL GR_TXT', 'PANEL GR_TXT', false, true, 'component/graphicIcon/graphicIconCustom.js', 200, 60, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'component/graphicIcon/custom/vms.js',true),

(950, 'SYNC_SERVER', 'SYNC_SERVER', false, false, 'component/graphicIcon/graphicIconDefault.js', 30, 30, 'img/graphic_icon/95_sync_server.svg', null,true);

