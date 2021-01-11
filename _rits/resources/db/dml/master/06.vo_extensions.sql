INSERT INTO ui.vo_entity_types(id, alias, description, enabled, visible) values
('ElementTypeUI', 'ElementTypeUI', 'ElementTypeUI', true, true),
('ElementUI', 'ElementUI', 'ElementUI', true, true),
('LocationUI', 'LocationUI', 'LocationUI', true, true),
('AlarmLevelUI', 'AlarmLevelUI', 'AlarmLevelUI', true, true);

INSERT INTO ui.vo_entity_fields (id, vo_entity_type_id, field, description, label_field, label_description, enabled, visible) VALUES
(1, 'ElementTypeUI', 'grid', 'grid', 'LBL_FIELD_GRID_ALIAS', 'LBL_FIELD_GRID_DESCRIPTION', true, true),
(2, 'ElementTypeUI', 'default_svg', 'default_svg', 'LBL_FIELD_DEFAULT_SVG_ALIAS', 'LBL_FIELD_DEFAULT_SVG_DESCRIPTION', true, true),
(3, 'ElementTypeUI', 'scale_x', 'scale_x', 'LBL_FIELD_SCALE_X_ALIAS', 'LBL_FIELD_SCALE_X_DESCRIPTION', true, true),
(4, 'ElementTypeUI', 'scale_y', 'scale_y', 'LBL_FIELD_SCALE_Y_ALIAS', 'LBL_FIELD_SCALE_Y_DESCRIPTION', true, true),
(5, 'LocationUI', 'lat', 'lattitude_x', 'LBL_LATTITUDE_ALIAS', 'LBL_LATTITUDE_DESCRIPTION', true, true),
(6, 'LocationUI', 'lon', 'longitude', 'LBL_LONGITUDE_ALIAS', 'LBL_LONGITUDE_DESCRIPTION', true, true),
(7, 'LocationUI', 'coordinates', 'array of coordinates (field calculated TODO)', 'LBL_COORDINATES_ARRAY_ALIAS', 'LBL_COORDINATES_ARRAY_DESCRIPTION', true, true),
(8, 'AlarmLevelUI', 'notification', 'show notification', 'LBL_NOTIFICATION_ALIAS', 'LBL_NOTIFICATION_DESCRIPTION', true, true),
(9, 'AlarmLevelUI', 'sound', 'sound to play', 'LBL_PLAY_SOUND_ALIAS', 'LBL_PLAY_SOUND_DESCRIPTION', true, true);

