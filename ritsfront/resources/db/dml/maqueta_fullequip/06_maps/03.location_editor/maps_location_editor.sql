INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode, custom_provider) VALUES 
(1000, 'MAPA PARA COMPONENTE MAPEDITOR', 'MAPA PARA COMPONENTE MAPEDITOR', 'LBL_MENU_ITEM_MAP', NULL, -3.71, 40.36, 0, 20, 0, -36.04, 46.26, 29.53, 24.05, null, 8, true,
  '{ 
    "map": {"enabled": false, "type": "WMS", "serverType": "geoserver", "url": "http://192.168.88.71:8700/geoserver/wms", "name": "osm"},
    "router": {"enabled": true, "type": "OpenRouteService", "url": "http://192.168.88.71:8701/ors"},
        "smartRoadInfo": {"enabled": true, "url": "http://192.168.88.71:8702/api"}
  }');