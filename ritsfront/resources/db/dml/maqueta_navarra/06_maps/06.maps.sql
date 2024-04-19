INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(1, 'MAP', 'Map', 'LBL_MENU_ITEM_MAP', NULL, -1.650000, 42.816667, 8, 16, 8, 0, 39.8, -2.5, 45.8, null, null, false);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(2, NULL, 1, 'CCTV', 'CCTV', 'LBL_CCTV', NULL, true),
(4, NULL, 1, 'SECC', 'SECC', 'LBL_SECC', NULL, true),
(71, NULL, 1, 'PMV', 'PMV', 'LBL_PMV', NULL, true);

INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(6, 2,'Element:2:6', 2,'RM-RAD-001', -1.9017311745236636, 43.01375916591506, false, null, true, false, null, true ),
(7, 2,'Element:2:7', 2,'RM-RAD-002', -1.7942441187488551, 42.08257298179288, false, null, true, false, null, true ),
(8, 2,'Element:2:8', 2,'RM-RAD-003', -1.6194401610273894, 43.061277271785855, false, null, true, false, null, true ),
(9, 2,'Element:2:9', 2,'RM-RAD-004', -2.185011574528798,  42.915118005589925, false, null, true, false, null, true ),
(10, 2,'Element:2:10', 2,'RM-RAD-005', -1.635958218701191, 43.003896169817324, false, null, true, false, null, true ),
(11, 2,'Element:2:11', 2,'RM-RAD-006', -1.6701395033469013, 43.24607702001288,  false, null, true, false, null, true ),

(12, 4,'Element:4:1', 4,'ETD1-SEC1', -1.6510788785713089, 42.607738806509616, false, null, true, false, null, true ),
(13, 4,'Element:4:2', 4,'ETD1-SEC2', -1.630411410795678, 43.00375494028285, false, null, true, false, null, true ),
(14, 4,'Element:4:3', 4,'ETD2-SEC1', -1.6472558436321283, 42.56380866034218, false, null, true, false, null, true ),
(15, 4,'Element:4:4', 4,'ETD2-SEC2', -1.6146765581066007, 43.06145756262855,  false, null, true, false, null, true ),
(16, 4,'Element:4:5', 4,'ETD3-SEC1', -1.6692937234707363, 42.48532958892129, false, null, true, false, null, true ),
(17, 4,'Element:4:6', 4,'ETD3-SEC2', -1.6137694517046688, 43.13691089256202, false, null, true, false, null, true ),
(18, 4,'Element:4:7', 4,'ETD4-SEC1', -1.6463806626756317, 42.40170971948243, false, null, true, false, null, true ),
(19, 4,'Element:4:8', 4,'ETD4-SEC2', -1.908625478716921, 43.017907137853676, false, null, true, false, null, true ),
(20, 4,'Element:4:9', 4,'ETD5-SEC1', -1.6526049227632462, 42.30617151649789, false, null, true, false, null, true ),
(21, 4,'Element:4:10', 4,'ETD5-SEC2', -1.908625478716921, 43.017907137853676, false, null, true, false, null, true ),


   
 

(71, 71,'Element:71:1', 71,'RM-PMV-001', -1.8130090460643355, 42.039861784011684, false, null, true, false, null, true ),
(72, 71,'Element:71:2', 71,'RM-PMV-002', -1.635985419026829, 43.009106406994235, false, null, true, false, null, true ),
(73, 71,'Element:71:3', 71,'RM-PMV-003', -1.6757154415117126, 43.225952100365724, false, null, true, false, null, true );

/*INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color, z_index_front) VALUES
(1, 10, 13, 211, -70.311033, -23.78, false, null, true, false, '#000000', null),
(1, 14, 14, 211, -70.311033, -23.78, false, null, true, false, '#000000', null),
(1, 15, 15, 212, -70.311033, -23.785, false, null, true, false, '#000000', null),
(1, 16, 16, 212, -70.311033, -23.785, false, null, true, false, '#000000', null);*/

INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required,  context) VALUES
(0, 1, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, true, true),
(1, 1, 1, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, false, true),
(2, 1, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, true, true);

