INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(1, 'mapa', 'mapa', 'LBL_MENU_ITEM_MAP', NULL, -4.956077777777778, 36.46929444444445, 11, 16, 10, -6.962095, 34.525937, -2.962095, 38.525937, null, null, true);

INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(1, 1000, 100401, 101301);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(1, NULL, 1, 'Layer Paneles', 'Layer Paneles', 'LBL_ELEMENT_TYPE_PANEL', NULL, true);

INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, z_index_front, show_text, text_color,selectable) VALUES 
(11, 1, 'Element:71:1', 22171, 'RV-PMV-5MA', -4.9716611111111115, 36.52565555555555, false, null, true, 1, true, '#000000',true);

INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES
(1, 1, 1, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(2, 1, 2, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(3, 1, 3, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(7, 1, 4, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench', false, 104601, NULL, 2, true, NULL, false, true);


