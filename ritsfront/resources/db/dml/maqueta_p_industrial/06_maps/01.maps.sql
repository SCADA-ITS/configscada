INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(1, 'mapa', 'mapa', 'LBL_MENU_ITEM_MAP', NULL, -73.12, -36.8165, 14, 15, 14, -73.2, -36.85, -73.04, -36.78, null, null, true);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(1, NULL, 1, 'Layer PMVs', 'Layer PMVs', 'LBL_ELEMENT_TYPE_PANEL', NULL, true),
(2, NULL, 1, 'Layer Cameras', 'Layer Cameras', 'LBL_ELEMENT_TYPE_CAMERA', NULL, true),
(3, NULL, 1, 'Layer Secciones', 'Layer Secciones', 'LBL_ELEMENT_TYPE_SECTION', NULL, true);

INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, z_index_front, show_text, text_color,selectable) VALUES 
(1,  1, 'Element:2:1',  010002, '00.3-ASC-DOM-01', -73.11873227116453, -36.79392916826567, false, null, true, 1, false, '#000000',true),
(2,  1, 'Element:2:2',  010002, '00.8-ASC-DOM-01', -73.12047991534185, -36.79776917687944, false, null, true, 1, false, '#000000',true),
(3,  1, 'Element:2:3',  010002, '01.1-ECT-DOM-01', -73.11615064417732, -36.79896595260499, false, null, true, 1, false, '#000000',true),
(4,  1, 'Element:2:4',  010002, '01.2-ASC-DOM-01', -73.12516918650638, -36.80046200123361, false, null, true, 1, false, '#000000',true),
(5,  1, 'Element:2:5',  010002, '01.8-MR1-DOM-01', -73.12807945767094, -36.80415955294186, false, null, true, 1, false, '#000000',true),
(6,  1, 'Element:2:6',  010002, '01.8-MR1-DOM-02', -73.12768381349360, -36.80413237202204, false, null, true, 1, false, '#000000',true),
(7,  1, 'Element:2:7',  010002, '02.3-ASC-DOM-01', -73.12965418650640, -36.80840648115063, false, null, true, 1, false, '#000000',true),
(8,  1, 'Element:2:8',  010002, '02.8-ASC-DOM-01', -73.13148827116453, -36.81271785254932, false, null, true, 1, false, '#000000',true),
(9,  1, 'Element:2:9',  010002, '03.3-ASC-DOM-01', -73.13341264417733, -36.81701257601499, false, null, true, 1, false, '#000000',true),
(10, 1, 'Element:2:10', 010002, '03.8-ASC-DOM-01', -73.13532864417730, -36.82117705236560, false, null, true, 1, false, '#000000',true),
(11, 1, 'Element:2:11', 010002, '04.3-MR2-DOM-01', -73.13552472883546, -36.82569323954109, false, null, true, 1, false, '#000000',true),
(12, 1, 'Element:2:12', 010002, '04.4-MR2-DOM-01', -73.13533272883546, -36.82614753689318, false, null, true, 1, false, '#000000',true),
(13, 1, 'Element:2:13', 010002, '04.6-ASC-DOM-01', -73.13339690674740, -36.82835435954075, false, null, true, 1, false, '#000000',true),
(14, 1, 'Element:2:14', 010002, '05.1-ASC-DOM-01', -73.13018427116452, -36.83182966599265, false, null, true, 1, false, '#000000',true),
(15, 1, 'Element:2:15', 010002, '05.5-ASC-DOM-01', -73.12811891533951, -36.83485707553441, false, null, true, 1, false, '#000000',true),
(16, 1, 'Element:2:16', 010002, '00.0-EBT-DOM-01', -73.12578354242491, -36.83764778642686, false, null, true, 1, false, '#000000',true),
(17, 1, 'Element:2:17', 010002, '06.0-ASC-DOM-01', -73.12583836715976, -36.83924866536039, false, null, true, 1, false, '#000000',true),
(18, 1, 'Element:2:18', 010002, '00.3-AAE-DOM-01', -73.12811968855875, -36.83946659295810, false, null, true, 1, false, '#000000',true),
(19, 2, 'Element:71:1', 010071, '01.4-ASC-PMV-01', -73.12698872883546, -36.80235877780780, false, null, true, 1, false, '#000000',true),
(20, 2, 'Element:71:2', 010071, '05.1-DES-PMV-01', -73.13020791534186, -36.83182666598559, false, null, true, 1, false, '#000000',true),
(21, 3, 'Element:4:1',  010004, '01.8-MR1-ETD-01_SEC-1', -73.12807945767094, -36.80415955294186, false, null, true, 1, false, '#000000',true),
(22, 3, 'Element:4:2',  010004, '01.8-MR1-ETD-01_SEC-2', -73.12768381349360, -36.80413237202204, false, null, true, 1, false, '#000000',true);

INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color) VALUES
(5, 14, 15, 010002, -73.13007945767094, -36.80415955294186, false, null, true, false, '#00000'),
(6, 14, 15, 010002, -73.12568381349360, -36.80413237202204, false, null, true, false, '#00000'),
(11, 14, 15, 010002, -73.13752472883546, -36.82569323954109, false, null, true, false, '#00000'),
(12, 14, 15, 010002, -73.13333272883546, -36.82614753689318, false, null, true, false, '#00000'),
(14, 14, 15, 010002, -73.13218427116452, -36.83382966599265, false, null, true, false, '#00000'),
(19, 14, 15, 010071, -73.13699000000000, -36.79836000000000, false, null, true, false, '#00000'),
(20, 14, 15, 010071, -73.11820791534186, -36.83182666598559, false, null, true, false, '#00000'),
(21, 14, 15, 010004, -73.11957945767094, -36.80915955294186, false, null, true, false, '#00000'),
(22, 14, 15, 010004, -73.13768381349360, -36.80913237202204, false, null, true, false, '#00000');

INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES
(1, 1, 1, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(2, 1, 2, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(3, 1, 3, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

