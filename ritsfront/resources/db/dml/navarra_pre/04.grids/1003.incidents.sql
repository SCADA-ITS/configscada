delete from ui.grid_field_icons where grid_field_id in (100309,100310);
delete from ui.grid_fields where grid_id = 1003;

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
(100301, 1003, 2, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, NULL, false, false, 50, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(100302, 1003, 2, 2, true, false, false, false, '', '', 'incidentId', true, false, NULL, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100303, 1003, 1, 3, true, false, false, false, '', 'CONFIRM', 'confirm', false, false, NULL, false, false, 50, NULL, true, true, NULL, NULL, NULL, 'boolToString', NULL),
--(100304, 1003, 1, 4, true, false, true, false, 'LBL_GRID_FIELD_INCIDENT_LEVEL_LABEL_ALIAS', 'LBL_GRID_FIELD_INCIDENT_LEVEL_LABEL_ALIAS', 'incidentLevelLabelAlias', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100306, 1003, 1, 6, true, false, false, false, 'LBL_GRID_FIELD_INCIDENT_TYPE_ALIAS', 'LBL_GRID_FIELD_INCIDENT_TYPE_ALIAS', 'incidentTypeAlias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100305, 1003, 1, 5, true, false, true, false, 'alias', 'alias', 'alias', false, false, NULL, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100307, 1003, 4, 7, true, false, false, false, 'LBL_GRID_FIELD_GENERATED_AT', 'LBL_GRID_FIELD_GENERATED_AT', 'generatedAt', false, true, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100308, 1003, 1, 8, true, false, true, false, 'LBL_GRID_FIELD_INCIDENT_TYPE_DESCRIPTION', 'LBL_GRID_FIELD_INCIDENT_TYPE_DESCRIPTION', 'incidentTypeDescription', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100309, 1003, 1, 9, true, false, false, false, 'LBL_GRID_FIELD_AFFECTION_STRETCH', 'LBL_GRID_FIELD_AFFECTION_STRETCH', 'affectionStretchAlias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100310, 1003, 1, 10, true, false, true, false, 'LBL_GRID_FIELD_LOCATION', 'LBL_GRID_FIELD_LOCATION', 'locationAlias', false, false, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100311, 1003, 1, 11, true, false, false, false, 'LBL_GRID_FIELD_INCIDENT_STATE_LABEL_ALIAS', 'LBL_GRID_FIELD_INCIDENT_STATE_LABEL_ALIAS', 'incidentStateLabelAlias', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100312, 1003, 1, 12, true, false, true, false, 'LBL_GRID_FIELD_CURRENT_USER', 'LBL_GRID_FIELD_CURRENT_USER', 'currentUserName', false, false, NULL, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(100313, 1003, 1, 13, true, false, false, false, '', '', 'currentUserId', true, false, NULL, false, false, 50, NULL, true, false, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(100311, NULL, NULL, false, true, false, true, NULL),

(100303, './img/grid/confirmed.png', 'true', false, false, false, true, 'LBL_ICON_CONFIRMED'),
(100303, './img/grid/not_confirmed.png', 'false', false, false, false, true, 'LBL_ICON_NOT_CONFIRMED');