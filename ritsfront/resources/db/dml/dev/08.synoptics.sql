
INSERT INTO ui.synoptics (id, "name", description, "label", icon, base_layer, exclusive_layers) VALUES
(1, 'synoptic_2', 'synoptic_2', 'LBL_synoptic_2', NULL, 'img/synoptics/tunel_test.svg', false);


INSERT INTO ui.synoptic_layers (id, base_layer, synoptic_id, "name", description, "label", icon, visible, active) VALUES
(2, null, 1, 'Tunnel Málaga', 'Tunel Málaga', 'LBL_TUNNEL_Málaga', NULL, true, NULL),
(3, null, 1, 'Tunnel Cádiz', 'Tunel Cádiz', 'LBL_TUNNEL_Cádiz', NULL, true, NULL),
(4, null, 1, 'Cameras Málaga', 'Cameras Málaga', 'LBL_CAMERAS', NULL, true, NULL),
(5, null, 1, 'Fans Málaga', 'Fans Málaga', 'LBL_FANS', NULL, true, NULL),
--(6, null, 1, 'VMS Málaga', 'VMS Málaga', 'LBL_VMS', NULL, true, NULL),
(7, null, 1, 'Traffic Section Málaga', 'Traffic Section Málaga', 'LBL_TRAFFIC_SECTION', NULL, true, NULL),
(8, null, 1, 'Cameras Cádiz', 'Cameras Cádiz', 'LBL_CAMERAS', NULL, true, NULL),
(9, null, 1, 'Fans Cádiz', 'Fans Cádiz', 'LBL_FANS', NULL, true, NULL),
(10, null, 1, 'VMS Cádiz', 'VMS Cádiz', 'LBL_VMS', NULL, true, NULL),
(11, null, 1, 'Traffic Section Cádiz', 'Traffic Section Cádiz', 'LBL_TRAFFIC_SECTION', NULL, true, NULL),
(12, null, 1, 'Cabinets Cádiz', 'Cabinets Cádiz', 'LBL_CABINETS', NULL, true, NULL),
(14, null, 1, 'Paneles', 'Paneles', 'LBL_PANEL', NULL, true, NULL),
(15, NULL, 1, 'Barreras', 'Barreras', 'LBL_BARRIER', NULL, true, NULL),
(16, NULL, 1, 'Semáforos', 'Semáforos', 'LBL_SEM', NULL, true, NULL),
(17, NULL, 1, 'Semáforo Ambar', 'Semáforo Ambar', 'LBL_SEM_AMBAR', NULL, true, NULL),
(18, NULL, 1, 'Indoor Anemometer', 'Indoor Anemometer', 'LBL_IN_ANEMOMETER', NULL, true, NULL),
(19, NULL, 1, 'Fire detector', 'Fire detector', 'LBL_FIRE_DETECTOR', NULL, true, NULL),
(20, NULL, 1, 'Anemometer', 'Anemometer', 'LBL_ANEMOMETER', NULL, true, NULL);




INSERT INTO ui.synoptic_layer_relationships (id, synoptic_id, parent_layer_id, child_layer_id) VALUES
(3, 1, 2, 4),
(4, 1, 2, 5),
--(5, 1, 2, 6),
(6, 1, 2, 7),
(7, 1, 3, 8),
(8, 1, 3, 9),
(9, 1, 3, 10),
(10, 1, 3, 11),
(11, 1, 3, 12),
(13, 1, 3, 14),
(14, 1, 3, 15),
(15, 1, 3, 16),
(16, 1, 3, 17),
(17, 1, 3, 18),
(18, 1, 3, 19),
(19, 1, 3, 20);


INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(1, 'element_type_1', 'element_type_1', false, false, '../data/drawFiles/GraphicElementRendererStatic.js', 80.0, 80.0, NULL, '', null, null),
(2, 'element_type_4', 'element_type_4', false, true, '../data/drawFiles/GraphicElementRendererTable.js', 10.0, 5.0, NULL, '', null, null),
(3, 'element_type_9', 'element_type_9', false, true, '../data/drawFiles/GraphicElementRendererSVG.js', 0.20049109, 0.20049109, 'SVG_9_FAN', '', null, null),
(4, 'element_type_36', 'element_type_36', false, true, '../data/drawFiles/GraphicElementRendererMatrix.js', 2.0, 2.0, NULL, '', null, null),
(5, 'element_type_59', 'element_type_59', false, true, '../data/drawFiles/GraphicElementRendererSVG.js', 1.0, 1.0, 'SVG_59_SMOKE_DETECTOR', '', null, null),
(6, 'element_type_81', 'element_type_81', false, true, '../data/drawFiles/GraphicElementRendererSVG.js', 100, 100, 'SVG_81_VIS', '', null, null),
(7, 'element_type_36_map', 'element_type_36_map', false, true, '../data/drawFiles/GraphicElementRendererMatrix.js', 200, 80, NULL, '', null, null),

(8, 'CAM', 'CAM', false, false, 'component/graphicIcon/graphicIconDefault.js', 80, 80, null, 'img/graphic_icon/02_camera.svg', null, null),
(9, 'FAN', 'FAN', false, true, 'component/graphicIcon/graphicIconCustom.js', 80, 80, null, 'img/graphic_icon/09_jetfan.svg', 'component/graphicIcon/custom/jetfan.js', null),
(10, 'AXIALFAN', 'AXIALFAN', false, true, 'component/graphicIcon/graphicIconCustom.js', 80, 80, null, 'img/graphic_icon/09_axialfan.svg', 'component/graphicIcon/custom/axialfan.js', null),
--(10, 'VMS', 'VMS', true, true, 'component/graphicIcon/graphicIconCustom.js', 200, 80, null, '', 'component/graphicIcon/custom/matrix.js', null),
(11, 'SECTION', 'SECTION', false, true, 'component/graphicIcon/graphicIconCustom.js', 200, 80, null, 'img/graphic_icon/section.svg', null, null),
(12, 'EC_SCN', 'EC_SCN', false, false, 'component/graphicIcon/graphicIconDefault.js', 80, 80, null, 'img/graphic_icon/25_electrical_cabinet.svg', null, null),
(14, 'VMS', 'VMS', false, true, 'component/graphicIcon/graphicIconCustom.js', 200, 80, null, 'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js', null),
(15, 'VMS_TEXT_ZOOM_MAP', 'VMS_TEXT_ZOOM_MAP', false, true, 'component/graphicIcon/graphicIconCustom.js', 100, 40, null, 'img/graphic_icon/38_vms_txt_2_lines.svg', 'component/graphicIcon/custom/vmsTxt.js', null),
(16, 'VMS_TEXT_ZOOM_MAP', 'VMS_TEXT_ZOOM_MAP', false, true, 'component/graphicIcon/graphicIconCustom.js', 300, 120, null, 'img/graphic_icon/38_vms_txt_2_lines.svg', 'component/graphicIcon/custom/vmsTxt.js', null),
(17, 'VMS_MAX_ZOOM_MAP', 'VMS_MAX_ZOOM_MAP', false, true, 'component/graphicIcon/graphicIconCustom.js', 100, 40, null, 'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js', null),
(18, 'VMS_MIN_ZOOM_MAP', 'VMS_MIN_ZOOM_MAP', false, true, 'component/graphicIcon/graphicIconCustom.js', 400, 160, null, 'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js', null),
(19, 'BARRIER', 'BARRIER', false, true, 'component/graphicIcon/graphicIconCustom.js', 80.0, 80.0, NULL, 'img/graphic_icon/08_barrier.svg', 'component/graphicIcon/custom/barrier.js', null),
(20, 'SEM', 'SEM', false, true, 'component/graphicIcon/graphicIconCustom.js', 80.0, 80.0, NULL, 'img/graphic_icon/13_sem_3_grupos.svg', 'component/graphicIcon/custom/trafficLight.js', null),
(21, 'SEM AMBAR', 'SEM AMBAR', false, true, 'component/graphicIcon/graphicIconCustom.js', 80.0, 80.0, NULL, 'img/graphic_icon/53_sem_2_grupos.svg', 'component/graphicIcon/custom/amberTrafficLight.js', null),
(22, 'SEM RED RED', 'SEM RED RED', false, true, 'component/graphicIcon/graphicIconCustom.js', 80.0, 80.0, NULL, 'img/graphic_icon/53_sem_2_grupos.svg', 'component/graphicIcon/custom/redTrafficLight.js', null),
(23, 'INTERNAL_ANEMOMETER', 'INTERNAL_ANEMOMETER', false, true, 'component/graphicIcon/graphicIconCustom.js', 80.0, 80.0, NULL, 'img/graphic_icon/18_anemometro.svg', 'component/graphicIcon/custom/inAnemometer.js', null),
(24, 'VMS 2', 'VMS 2', false, true, 'component/graphicIcon/graphicIconCustom.js', 200, 80, null, 'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js', null),
(25, 'VMS 2', 'VMS 2', false, true, 'component/graphicIcon/graphicIconCustom.js', 200, 80, null, 'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js', null),
(26, 'CO', 'CO', false, true, 'component/graphicIcon/graphicIconCustom.js', 80, 100, null, 'img/graphic_icon/19_co.svg', 'component/graphicIcon/custom/co.js', null),
(27, 'LUX', 'LUX', false, true, 'component/graphicIcon/graphicIconCustom.js', 80, 100, null, 'img/graphic_icon/12_lux.svg', 'component/graphicIcon/custom/lux.js', null),
(28, 'LUM', 'LUM', false, true, 'component/graphicIcon/graphicIconCustom.js', 80, 100, null, 'img/graphic_icon/14_lum.svg', 'component/graphicIcon/custom/lum.js', null),
(29, 'PA', 'PA', false, true, 'component/graphicIcon/graphicIconCustom.js', 80, 100, null, 'img/graphic_icon/16_pa_zone.svg', 'component/graphicIcon/custom/pa.js', null),
(30, 'NO', 'NO', false, true, 'component/graphicIcon/graphicIconCustom.js', 80, 100, null, 'img/graphic_icon/20_no.svg', 'component/graphicIcon/custom/no.js', null),
(31, 'OP', 'OP', false, true, 'component/graphicIcon/graphicIconCustom.js', 80, 100, null, 'img/graphic_icon/21_opac.svg', 'component/graphicIcon/custom/op.js', null),
--(32, 'VMS_128x64_1', 'VMS_128x64_1', false, true, 'component/graphicIcon/graphicIconCustom.js', 200, 80, null, 'img/graphic_icon/71_vms_128x64.svg', 'component/graphicIcon/custom/vms.js', null),
(33, 'FIRE_DET_1', 'FIRE_DET_1', false, true, 'component/graphicIcon/graphicIconCustom.js', 1200, 15, null, 'img/graphic_icon/15_fire.svg', null, false),
(34, 'PA_SYSTEM', 'PA_SYSTEM', false, false, 'component/graphicIcon/graphicIconDefault.js', 80, 80, null, 'img/graphic_icon/05_pa_system.svg', null, null),
(35, 'PA', 'PA', false, false, 'component/graphicIcon/graphicIconDefault.js', 80, 80, null, 'img/graphic_icon/16_pa_zone.svg', null, null),
(36, 'ZONE', 'ZONE', false, false, 'component/graphicIcon/graphicIconDefault.js', 80, 80, null, 'img/graphic_icon/07_zone.svg', null, null),

(1000, 'INCIDENT_REPORT', 'INCIDENT_REPORT', false, true, 'component/graphicIcon/graphicIconCustom.js', 70, 120, null, 'img/graphic_icon/incident_report.svg', 'component/graphicIcon/custom/incidentReport.js', null),
(1001, 'EXT_ENTITY', 'EXT_ENTITY', false, true, 'component/graphicIcon/graphicIconCustom.js', 70, 120, null, 'img/graphic_icon/ext_entity.svg', 'component/graphicIcon/custom/ext_entity.js', null),
(010001, 'EXT_ENTITY_WORKS', 'EXT_ENTITY_WORKS', false, true, 'component/graphicIcon/graphicIconCustom.js', 40, 40, null, 'img/graphic_icon/ext_entity.svg', 'component/graphicIcon/custom/ext_entity.js', null),
(010002, 'EXT_ENTITY_WEATHER', 'EXT_ENTITY_WEATHER', false, true, 'component/graphicIcon/graphicIconCustom.js', 40, 40, null, 'img/graphic_icon/ext_entity.svg', 'component/graphicIcon/custom/ext_entity.js', null),
(010003, 'EXT_ENTITY_ROAD', 'EXT_ENTITY_ROAD', false, true, 'component/graphicIcon/graphicIconCustom.js', 40, 40, null, 'img/graphic_icon/ext_entity.svg', 'component/graphicIcon/custom/ext_entity.js', null);


INSERT INTO ui.synoptic_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, text_size, text_pos, selectable) VALUES

(6, 4, 'Element:2:1', 8, 'LBL_CAMERA', 100, 700, false, null, true, true, null, null, null, true),
(7, 4, 'Element:2:1', 8, 'LBL_CAMERA', 200, 700, false, null, true, true, null, null, null, true),
(8, 5, 'Element:9:2', 9, 'LBL_FAN', 300, 700, false, null, true, true, null, null, null, true),
(9, 5, 'Element:9:3', 9, 'LBL_FAN', 400, 700, false, null, true, true, null, null, null, true),
-- (10, 6, 'Element:36:1', 10, 'LBL_VMS', 600, 700, false, null, true, true, null),
(11, 7, 'Element:4:1', 11, 'LBL_SECTION', 900, 700, false, null, true, false, null, null, null, true),

(12, 9, 'Element:5:2', 34, 'LBL_PA_SYSTEM', 100, 200, false, null, true, true, null, null, null, true),
(13, 9, 'Element:16:6', 35, 'LBL_PA', 200, 200, false, null, true, true, null, null, null, true),
(37, 9, 'Element:96:4', 35, 'LBL_PA_GROUP', 300, 200, false, null, true, true, null, null, null, true),
(14, 9, 'Element:9:100', 10, 'LBL_FAN', 400, 200, false, null, true, true, null, null, null, true),
(15, 11, 'Element:4:2', 11, 'LBL_SECTION', 500, 200, false, null, true, false, null, null, null, true),
(16, 12, 'Element:34:1', 12, 'LBL_EC_SCN', 1000, 200, false, null, true, true, '#00ff00', null, null, true),

(18, 14, 'Element:71:1', 14, 'LBL_VMS', 900, 450, false, null, true, true, null, null, null, true),
(19, 15, 'Element:8:1', 19, 'LBL_BARRIER', 300, 500, false, NULL, true, true, NULL, null, null, true),
(20, 16, 'Element:13:1', 20, 'LBL_SEM', 100, 500, false, NULL, true, true, NULL, null, null, true),
(21, 17, 'Element:53:1', 21, 'LBL_SEM_AMBAR', 600, 500, false, NULL, true, true, NULL, null, null, true),
(22, 17, 'Element:2000:1', 36, 'LBL_TUNNEL', 500, 500, false, NULL, true, true, NULL, null, null, true),
-- (23, 18, 'Element:18:1', 23, 'LBL_IN_ANEMOMETER', 600, 500, false, NULL, true, true, NULL, null, null, true),
(24, 14, 'Element:71:2', 24, 'LBL_VMS', 1100, 450, false, NULL, true, true, NULL, null, null, true),
(25, 17, 'Element:53:3', 22, 'LBL_SEM_RED', 700, 500, false, NULL, true, true, NULL, null, null, true),
(26, 14, 'Element:71:2', 25, 'LBL_VMS', 1300, 450, false, NULL, true, true, NULL, null, null, true),
(27, 4, 'Element:19:1', 26, 'LBL_OC', 1000, 700, false, null, true, true, null, null, null, true),
(28, 4, 'Element:12:1', 27, 'LBL_LUX', 900, 700, false, null, true, true, null, null, null, true),
(29, 4, 'Element:14:1', 28, 'LBL_LUM', 800, 700, false, null, true, true, null, null, null, true),
(31, 4, 'Element:18:1', 23, 'LBL_ANEM', 600, 700, false, null, true, true, null, null, null, true),
(32, 4, 'Element:20:1', 30, 'LBL_NO', 1100, 700, false, null, true, true, null, null, null, true),
(33, 4, 'Element:21:1', 31, 'LBL_OP', 1200, 700, false, null, true, true, null, null, null, true),
--(34, 14, 'Element:71:3', 32, 'LBL_VMS_128x64', 1100, 200, false, NULL, true, true, NULL, null, null, true),
(35, 19, 'Element:15:1', 33, 'LBL_FIRE_DET', 100, 125, false, NULL, true, true, NULL, null, null, true),
(36, 17, 'Element:53:4', 22, 'LBL_SEM_RED', 800, 500, false, NULL, true, true, NULL, null, null, true);





INSERT INTO ui.synoptic_commands
(id, synoptic_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES

(3, 1, 2, 'LBL_CMD_ALARMS', 'mdi mdi-bell', false, 100501, NULL, 2, true, NULL, false, true),
(4, 1, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, 2, false, NULL, true, true),
(5, 1, 3, 'LBL_CMD_SIGNALLING', 'mdi mdi-cog', false, 101701, NULL, 2, true, NULL, false, true),
(6, 1, 4, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench', false, 104601, NULL, 2, true, NULL, true, true);

INSERT INTO ui.synoptic_element_type_commands (element_type, synoptic_command_id, module_action_id, view_type_id) VALUES
('ElementType:34', 4, 100501, 2),
--('ElementType:2001', 4, 102701, 2),
--('ElementType:2000', 4, 102702, 2),
('ElementType:8', 4, 200002, 2);
