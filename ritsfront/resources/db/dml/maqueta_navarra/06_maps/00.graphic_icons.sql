-- Elementos SCADA
INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render) VALUES
(2, 'CAM', 'CAM', false, false, 'component/graphicIcon/graphicIconDefault.js', 20, 20, null, 'img/graphic_icon/02_camera.svg', null),
(99, 'RADAR', 'RADAR', false, false, 'component/graphicIcon/graphicIconDefault.js', 20, 20, null, 'img/graphic_icon/02_camera_sanctioning2.svg', null);

-- Entidades externas
INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(1000, 'INCIDENT_REPORT', 'INCIDENT_REPORT', false, true, 'component/graphicIcon/graphicIconCustom.js', 70, 120, null, 'img/graphic_icon/incident_report.svg', 'component/graphicIcon/custom/incidentReport.js', null),
(010001, 'EXT_ENTITY_C4', 'EXT_ENTITY_WORKS', false, true, 'component/graphicIcon/graphicIconCustom.js', 40, 40, null, 'img/graphic_icon/ext_entity.svg', 'component/graphicIcon/custom/ext_entity.js', null);
