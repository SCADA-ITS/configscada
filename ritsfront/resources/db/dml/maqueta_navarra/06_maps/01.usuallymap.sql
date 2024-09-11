INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(1, 'MAP', 'Map', 'LBL_MENU_ITEM_MAP', NULL, -1.650000, 42.816667, 9, 16, 9, 0, 39.8, -2.5, 45.8, null, null, true);

INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(1000, 'INCIDENT_REPORT', 'INCIDENT_REPORT', false, true, 'component/graphicIcon/graphicIconCustom.js', 70, 120, null, 'img/graphic_icon/incident_report.svg', 'component/graphicIcon/custom/incidentReport.js', null);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(2, NULL, 1, 'RADAR', 'RADAR', 'LBL_ELEMENT_TYPE_CAMERA', NULL, true),
(4, NULL, 1, 'SECC', 'SECC', 'LBL_ELEMENT_TYPE_SECTION', NULL, true),
(71, NULL, 1, 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true);

INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(1, 1000, 100401, 101301);

INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(6, 2,'Element:2:6', 2,'RM-RAD-001', -1.9017311745236636, 43.01375916591506, false, null, true, false, null, true ),
(7, 2,'Element:2:7', 2,'RM-RAD-002', -1.7942441187488551, 42.08257298179288, false, null, true, false, null, true ),
(8, 2,'Element:2:8', 2,'RM-RAD-003', -1.6194401610273894, 43.061277271785855, false, null, true, false, null, true ),
(9, 2,'Element:2:9', 2,'RM-RAD-004', -2.185011574528798,  42.915118005589925, false, null, true, false, null, true ),
(10, 2,'Element:2:10', 2,'RM-RAD-005', -1.635958218701191, 43.003896169817324, false, null, true, false, null, true ),
(11, 2,'Element:2:11', 2,'RM-RAD-006', -1.6701395033469013, 43.24607702001288,  false, null, true, false, null, true ),

(12, 4,'Element:4:1', 4,'ETD1-SEC1', -1.635958218701191, 43.00390401589307, false, null, true, false, null, true ),
(14, 4,'Element:4:3', 4,'ETD2-SEC1', -1.61727473219286, 43.04474750891408, false, null, true, false, null, true ),
(16, 4,'Element:4:5', 4,'ETD3-SEC1', -1.5948137745177775, 43.12624220385988, false, null, true, false, null, true ),
(18, 4,'Element:4:7', 4,'ETD4-SEC1', -1.6675353610219448, 43.16574064391471, false, null, true, false, null, true ),
(20, 4,'Element:4:9', 4,'ETD5-SEC1', -1.6762278186881505, 43.25350104774429, false, null, true, false, null, true ),

(71, 71,'Element:71:1', 71,'RM-PMV-001', -1.7029627033453338, 43.276149012514715, false, null, true, false, null, true ),
(72, 71,'Element:71:2', 71,'RM-PMV-002', -1.6141449610235126, 43.1356503997735, false, null, true, false, null, true ),
(73, 71,'Element:71:3', 71,'RM-PMV-003', -1.6156152456909012, 42.962597921264624, false, null, true, false, null, true );

INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color, z_index_front) VALUES
(12, 9, 10, 4, -1.135958218701191, 42.7190401589307, false, null, true, false, '#000000', null),
(14, 9, 10, 4, -1.135958218701191, 42.8990401589307, false, null, true, false, '#000000', null),
(16, 9, 10, 4, -1.135958218701191, 43.08624220385988, false, null, true, false, '#000000', null),
(18, 9, 10, 4, -1.135958218701191, 43.27574064391471, false, null, true, false, '#000000', null),
(20, 9, 10, 4, -1.135958218701191, 43.45350104774429, false, null, true, false, '#000000', null),

(71, 9, 10, 71, -1.7029627033453338, 43.276149012514715, false, null, true, false, '#000000', null),
(72, 9, 10, 71, -1.6141449610235126, 43.1356503997735, false, null, true, false, '#000000', null),
(73, 9, 10, 71, -1.6156152456909012, 42.962597921264624, false, null, true, false, '#000000', null),

(12, 11, 16, 401, -1.135958218701191, 42.7190401589307,  false, null, true, false, '#000000', null),
(14, 11, 16, 401, -1.135958218701191, 42.8990401589307, false, null, true, false, '#000000', null),
(16, 11, 16, 401, -1.135958218701191, 43.08624220385988, false, null, true, false, '#000000', null),
(18, 11, 16, 401, -1.135958218701191, 43.27574064391471, false, null, true, false, '#000000', null),
(20, 11, 16, 401, -1.135958218701191, 43.45350104774429, false, null, true, false, '#000000', null),

(71, 11, 16, 7101, -1.7029627033453338, 43.276149012514715, false, null, true, false, '#000000', null),
(72, 11, 16, 7101, -1.6141449610235126, 43.1356503997735, false, null, true, false, '#000000', null),
(73, 11, 16, 7101, -1.6156152456909012, 42.962597921264624, false, null, true, false, '#000000', null);
 
INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required,  context) VALUES
(0, 1, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, true, true),
(1, 1, 1, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, false, true),
(2, 1, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, true, true);

INSERT INTO ui.map_zones (id, map_id, name, description, label, icon, longitude, latitude, zoom) VALUES
(1,	1, 'Pamp N ', 'Pamplona Norte', 'Pamplona Norte', NULL, -1.6141449610235126, 43.1356503997735, 9),
(2, 1, 'Pamp S', 'Pamplona Sur', 'Pamplona Sur', NULL, -1.7942441187488551, 42.08257298179288, 9);

