INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode, custom_provider) VALUES 
(2, 'MAP', 'Map', 'LBL_MENU_ITEM_MAP', NULL, -1.65, 42.7, 8, 20, 8, -4, 44, 1, 41.1, null, 7, true,
	'{ 
		"map": {"enabled": false, "type": "WMS", "serverType": "geoserver", "url": "http://192.168.88.161:8700/geoserver/wms", "name": "osm"},
		"router": {"enabled": true, "type": "OpenRouteService", "url": "http://192.168.88.161:8701/ors"},
        "smartRoadInfo": {"enabled": true, "url": "http://192.168.88.161:8702/api"}
	}');

INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(2, 1000, 100401, 101303);

INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required,  context) VALUES
(3, 1, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, true, true),
(4, 1, 1, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, false, true),
(5, 1, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, true, true);
