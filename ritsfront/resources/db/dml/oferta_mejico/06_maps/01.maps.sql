INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(1, 'mapa', 'mapa', 'LBL_MENU_ITEM_MAP', NULL, -99.12621, 19.42645, 14, 15, 14, null, null, null, null, null, null, true);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(1, NULL, 1, 'Layer PMVs', 'Layer PMVs', 'LBL_ELEMENT_TYPE_PANEL', NULL, true),
(2, NULL, 1, 'Layer Cameras', 'Layer Cameras', 'LBL_ELEMENT_TYPE_CAMERA', NULL, true),
(3, NULL, 1, 'Layer Secciones', 'Layer Secciones', 'LBL_ELEMENT_TYPE_SECTION', NULL, true),
(4, NULL, 1, 'Layer EM', 'Layer EM', 'LBL_ELEMENT_TYPE_WS', NULL, true),
(5, NULL, 1, 'Layer SOS', 'Layer SOS', 'LBL_ELEMENT_TYPE_WEATHER_STATION', NULL, true);



INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES
(1, 1, 1, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(2, 1, 2, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(3, 1, 3, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

