INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4006, 2, 'LBL_EXT_ENTITY_WAZE_JAMS', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014006, 4006, 1, 1, true, false, false, false, 'id', 'id', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(024006, 4006, 4, 2, true, false, false, true, 'fecha publicacion', 'fecha publicacion', 'fecha_publicacion', false, true, null, false, false, 160, NULL, true, true, NULL, NULL, NULL, NULL),
(034006, 4006, 1, 3, true, true, false, false, null, null, 'circulacion', false, false, null, false, false, 45, NULL, true, true, NULL, NULL, NULL, NULL),
(044006, 4006, 1, 4, true, true, false, true, 'circulacion', 'circulacion', 'circulacion', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(054006, 4006, 1, 5, true, false, false, true, 'carretera', 'carretera', 'calle', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(064006, 4006, 1, 6, true, false, false, true, 'ciudad', 'ciudad', 'ciudad', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(074006, 4006, 1, 7, true, true, false, true, 'pais', 'pais', 'pais', false, false, null, false, false, 70, NULL, true, true, NULL, NULL, NULL, NULL),
(084006, 4006, 1, 8, true, false, false, true, 'velocidad', 'velocidad', 'velocidad_km_h', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(094006, 4006, 1, 9, true, false, false, true, 'distancia', 'distancia', 'distancia_m', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(104006, 4006, 1, 10, true, false, false, true, 'retraso', 'retraso', 'retraso_s', false, false, null, false, false, 70, NULL, true, true, NULL, NULL, NULL, NULL),
(114006, 4006, 1, 11, true, false, false, true, 'comienzo', 'comienzo', 'comienzo', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(124006, 4006, 1, 12, true, false, false, true, 'fin', 'fin', 'fin', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(134006, 4006, 1, 13, true, true, false, true, 'estado', 'estado', 'estado', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(144006, 4006, 4, 14, true, false, true, true, 'latitud', 'latitud', 'latitud', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(154006, 4006, 4, 15, true, false, true, true, 'longitud', 'longitud', 'longitud', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4006, 1, 'true'),
(4006, 2, 'true'),
(4006, 7, 'false'),
(4006, 8, 'true'),
(4006, 9, 'false'),
(4006, 10, 'true'),
(4006, 11, 'true'),
(4006, 12, 'true'),
(4006, 13, 'true'),
(4006, 14, 'false'),
(4006, 15, 'false'),
(4006, 17, 'true'),
(4006, 21, 'custom'),
(4006, 25, 'true'),
(4006, 26, 'true');


INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(034006, './img/ext_entity_type/waze_trafficjam_0.png', '(0) Libre', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_TRAFFIC_JAM_0'),
(034006, './img/ext_entity_type/waze_trafficjam_1.png', '(1) Velocidad moderada', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_TRAFFIC_JAM_1'),
(034006, './img/ext_entity_type/waze_trafficjam_2.png', '(2) Muy lenta', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_TRAFFIC_JAM_2'),
(034006, './img/ext_entity_type/waze_trafficjam_3.png', '(3) Paradas intermitentes', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_TRAFFIC_JAM_3'),
(034006, './img/ext_entity_type/waze_trafficjam_4.png', '(4) Paradas prolongadas', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_TRAFFIC_JAM_4'),
(034006, './img/ext_entity_type/waze_trafficjam_5.png', '(5) Detenida', false, false, false, true, 'LBL_EXT_ENTITY_SUBTYPE_TRAFFIC_JAM_5');

