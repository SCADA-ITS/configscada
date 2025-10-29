INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode, custom_provider) VALUES 
(4, 'MAP', 'Map', 'LBL_MENU_ITEM_MAP', NULL, -1.65, 42.7, 8, 20, 8, -4, 44, 1, 41.1, null, 7, true,
	'{ 
		"map": {"enabled": false, "type": "WMS", "serverType": "geoserver", "url": "https://descontroltrafico.admon-cfnavarra.esgeoserver/wms", "name": "osm"},
		"router": {"enabled": true, "type": "OpenRouteService", "url": "https://descontroltrafico.admon-cfnavarra.es/ors"},
        "smartRoadInfo": {"enabled": true, "url": "https://descontroltrafico.admon-cfnavarra.es/api"}
	}');

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(04101, '{"enabled":true,"type":"FILE","fileType":"KML","fileName":"REFERE_Lin_Navarra.shp.kml","name":"Frontera Navarra"}', 4, 'Frontera Navarra', 'Frontera Navarra', 'Frontera Navarra', 'mdi mdi-google-maps', true);

	
INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(4, 1000, 100401, 101303);

