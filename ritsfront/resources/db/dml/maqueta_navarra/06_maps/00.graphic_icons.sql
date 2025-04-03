-- Elementos SCADA
INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render) VALUES
(2, 'CAM', 'CAM', false, true, 'component/graphicIcon/graphicIconCustom.js', 45, 45, null, 'img/graphic_icon/map_icon.svg', 'component/graphicIcon/custom/map_icon.js'),
(71, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 100, 80, null, 'img/graphic_icon/71_vms_incident.svg', 'component/graphicIcon/custom/vms.js'),
(99, 'RADAR', 'RADAR', false, true, 'component/graphicIcon/graphicIconCustom.js', 45, 45, null, 'img/graphic_icon/map_icon.svg', 'component/graphicIcon/custom/map_icon.js');

-- Entidades externas
INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(1000, 'INCIDENT_REPORT', 'INCIDENT_REPORT', false, true, 'component/graphicIcon/graphicIconCustom.js', 45, 45, null, 'img/graphic_icon/incident_report.svg', 'component/graphicIcon/custom/incidentReport.js', null),
(010001, 'EXT_ENTITY', 'EXT_ENTITY', false, true, 'component/graphicIcon/graphicIconCustom.js', 45, 45, null, 'img/graphic_icon/ext_entity.svg', 'component/graphicIcon/custom/ext_entity.js', null);
