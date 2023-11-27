INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(1, 'ausol', 'autopista del sol', 'LBL_MAP', NULL, -4.968995252952193, 36.513236353017206, 15, 18, 12, -5.496230111341672, 36.69560304914391, -4.286333943790312, 36.302938945943, null, 6, true);
--(2, 'AVO', 'v', 'LBL_MAP', NULL, -70.6508, -33.4373, 15, 18, 12, -70.8522, -33.3248, -70.3455, -33.7358, 'http://192.168.88.162:8100/geoserver/wms', 6, true);

INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(1, 1000, 100401, 101301);


INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(3, null, 1, 'Tunnel Málaga', 'Tunel Málaga', 'LBL_TUNNEL_Málaga', NULL, true),
(4, null, 1, 'Tunnel Cádiz', 'Tunel Cádiz', 'LBL_TUNNEL_Cádiz', NULL, true),
(5, null, 1, 'Cameras Málaga', 'Cameras Málaga', 'LBL_CAMERAS', NULL, true),
(6, null, 1, 'Fans Málaga', 'Fans Málaga', 'LBL_FANS', NULL, true),
--(7, null, 1, 'VMS Málaga', 'VMS Málaga', 'LBL_VMS', NULL, true),
(8, null, 1, 'Traffic Section Málaga', 'Traffic Section Málaga', 'LBL_TRAFFIC_SECTION', NULL, true),
(9, null, 1, 'Cameras Cádiz', 'Cameras Cádiz', 'LBL_CAMERAS', NULL, true),
(10, null, 1, 'Fans Cádiz', 'Fans Cádiz', 'LBL_FANS', NULL, true),
(11, null, 1, 'VMS Cádiz', 'VMS Cádiz', 'LBL_VMS', NULL, true),
(12, null, 1, 'Traffic Section Cádiz', 'Traffic Section Cádiz', 'LBL_TRAFFIC_SECTION', NULL, true),
(13, null, 1, 'Cabinets Cádiz', 'Cabinets Cádiz', 'LBL_CABINETS', NULL, true),
(14, null, 1, 'Semáforos', 'Semáforos', 'LBL_TRAFFIC_LIGHTS', NULL, true),
(15, null, 1, 'Paneles', 'Paneles', 'LBL_PANEL', NULL, true);


INSERT INTO ui.map_layer_relationships (id, map_id, parent_layer_id, child_layer_id) VALUES 
(3, 1, 3, 5),
(4, 1, 3, 6),
--(5, 1, 3, 7),
(6, 1, 3, 8),

(7, 1, 4, 9),
(8, 1, 4, 10),
(9, 1, 4, 11),
(10, 1, 4, 12),
(11, 1, 4, 13),
(12, 1, 4, 14),
(13, 1, 4, 15);


INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color, z_index_front, text_size, text_pos, selectable) VALUES 

(6, 5, 'Element:2:1', 8, 'LBL_CAMERA', -4.98797587550447, 36.513569058407754, false, null, true, true, '#000000', 1, null, null, true),
--(7, 6, 'Element:9:1', 9, 'LBL_FAN', -4.9419696064478496, 36.52350159628537, -4.929145070739907, 36.532511870463594, false, null, true, true, '#000000', null, null, null, true),
(7, 6, 'Element:9:1', 9, 'LBL_FAN', -4.9454945569876205, 36.52499439371091, false, null, true, true, '#000000', null, null, null, true),
(8, 6, 'Element:9:7', 9, 'LBL_FAN', null, null, false, null, true, true, '#000000', null, null, null, true),
(9, 6, 'Element:9:3', 9, 'LBL_FAN', -4.952613033026909, 36.52659761204406, false, null, true, true, '#000000', null, null, null, true),

(12, 10, 'Element:9:4', 9, 'LBL_FAN', -4.97195967951297, 36.52604078417512, false, null, true, true, '#000000', null, null, null, true),
(13, 10, 'Element:9:5', 9, 'LBL_FAN', -4.977530848447531, 36.52359069162212, false, null, true, true, '#000000', null, null, null, true),
(14, 10, 'Element:9:6', 9, 'LBL_FAN', -4.982076478717206, 36.519514457162245, false, null, true, true, '#000000', null, null, null, true),
(16, 13, 'Element:34:1', 12, 'LBL_EC_SCN', -4.9618201640539885, 36.531301721270324, false, null, true, true, '#000000', null, null, null, true),
--(18, 15, 'Element:71:1', 15, 'LBL_VMS', -4.994050334935018, 36.506571546155456, -4.99233637774394, 36.50582616335875, false, null, true, true, '#000000', null, null, null, true); -- 2 custom zooms. From 12 to 16 and from 17 to 18 zoom levels
(18, 15, 'Element:71:1', 15, 'LBL_VMS', -4.962065331099242, 36.522075027192656, false, null, true, true, '#000000', null, null, null, true),
(19, 12, 'Element:53:3', 22, 'LBL_TRAFFIC_LIGHT', -4.989869129194308,36.52089726444369, false, null, true, true, '#000000', null, null, null, true),
(20, 12, 'Element:53:4', 22, 'LBL_TRAFFIC_LIGHT', -4.953570957059859,36.533670749660274, false, null, true, true, '#000000', null, null, null, true);


INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color, z_index_front) VALUES
--(17, 12, 16, 15, -4.9922764300371325, 36.50964596148832, false, null, true, true, '#000000', null),
(18, 12, 16, 17, -4.962065331099242, 36.522075027192656, false, null, true, true, '#000000', null),
(18, 17, 18, 18, -4.955439212989665, 36.52249564066949, false, null, true, true, '#000000', null);


INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES

(3, 1, 2, 'LBL_CMD_ALARMS', 'mdi mdi-bell', false, 100501, NULL, 2, true, NULL, false, true),
(4, 1, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, 2, false, NULL, true, true),
(5, 1, 3, 'LBL_CMD_SIGNALLING', 'mdi mdi-cog', false, 101701, NULL, 2, true, NULL, false, true);


INSERT INTO ui.map_zones (id, map_id, name, description, label, icon, longitude, latitude, zoom) VALUES
(1,	1, 'Zone1', 'Zone1', 'LBL_ZONE1', NULL, -4.992385, 36.508022, 17),
(2, 1, 'Zone2', 'Zone2', 'LBL_ZONE2', NULL,	-4.9766035,	36.522976, 16),
(3,	1, 'Zone3',	'Zone3', 'LBL_ZONE3', NULL,	-4.9466314,	36.525143,	17);
