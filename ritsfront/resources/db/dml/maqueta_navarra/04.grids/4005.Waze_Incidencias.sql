INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4005, 2, 'LBL_EXT_ENTITY_WAZE_INCIDENTS', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014005, 4005, 1, 1, true, false, false, false, 'id', 'id', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(024005, 4005, 4, 2, true, false, false, true, 'fecha publicacion', 'fecha publicacion', 'fecha_publicacion', false, true, null, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL),
(034005, 4005, 1, 3, true, true, false, false, null, null, 'subtipo', false, false, null, false, false, 45, NULL, true, true, NULL, NULL, NULL, NULL),
(044005, 4005, 1, 4, true, true, false, true, 'subtipo', 'subtipo', 'subtipo', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(054005, 4005, 1, 5, true, false, false, true, 'description', 'description', 'description', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(064005, 4005, 1, 6, true, false, false, true, 'carretera', 'carretera', 'calle', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(074005, 4005, 1, 7, true, true, false, true, 'pais', 'pais', 'pais', false, false, null, false, false, 70, NULL, true, true, NULL, NULL, NULL, NULL),
(084005, 4005, 1, 8, true, false, false, true, 'localidad', 'localidad', 'localidad', false, true, null, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL),
(094005, 4005, 1, 9, true, true, false, true, 'estado', 'estado', 'estado', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4005, 1, 'true'),
(4005, 2, 'true'),
(4005, 7, 'false'),
(4005, 8, 'true'),
(4005, 9, 'false'),
(4005, 10, 'true'),
(4005, 11, 'true'),
(4005, 12, 'true'),
(4005, 13, 'true'),
(4005, 14, 'false'),
(4005, 15, 'false'),
(4005, 17, 'true'),
(4005, 21, 'custom'),
(4005, 25, 'true'),
(4005, 26, 'true');

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(034005, './img/ext_entity_type/waze_accident.png', 'Accidente menor', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_ACCIDENT'),
(034005, './img/ext_entity_type/waze_traffic_hazard.png', 'Peligro en carretera', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD'),
(034005, './img/ext_entity_type/waze_accident.png', 'Coche detenido', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD'),
(034005, './img/ext_entity_type/waze_construction.png', 'Obras', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_CONSTRUCTION'),
(034005, './img/ext_entity_type/waze_closed_road.png', 'Carril cerrado', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_ROAD_CLOSED'),
(034005, './img/ext_entity_type/waze_traffic_hazard.png', 'Objeto en carretera', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD'),
(034005, './img/ext_entity_type/waze_traffic_hazard.png', 'Semáforo averíado', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD'),
(034005, './img/ext_entity_type/waze_traffic_hazard.png', 'Coche en el arcén', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD'),
(034005, './img/ext_entity_type/waze_traffic_hazard.png', 'Bache en carretera', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD'),
(034005, './img/ext_entity_type/waze_closed_road.png', 'Carretera cortada', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_ROAD_CLOSED'),
(034005, './img/ext_entity_type/waze_weather_hazard.png', 'Hielo en carretera', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD_WEATHER'),
(034005, './img/ext_entity_type/waze_weather_hazard.png', 'Incidencia meteorológica', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD_WEATHER'),
(034005, './img/ext_entity_type/waze_weather_hazard.png', 'Inundación', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD_WEATHER'),
(034005, './img/ext_entity_type/waze_weather_hazard.png', 'Niebla', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD_WEATHER'),
(034005, './img/ext_entity_type/waze_weather_hazard.png', 'Nieve', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_HAZARD_WEATHER');


