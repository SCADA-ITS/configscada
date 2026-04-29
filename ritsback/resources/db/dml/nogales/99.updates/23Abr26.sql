INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(56,4,'Element:4:5',4,'NO-S1S3-SEC-OP',-71.407570, -32.739106, false,null,true,false,null,true),
(57,4,'Element:4:6',4,'NO-S1S3-SEC-PO',-71.407570, -32.739106, false,null,true,false,null,true);

INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color, z_index_front) VALUES
(56, 10, 13, 4, -71.407, -32.759106, false, null, true, false, '#000000', null),
(57, 10, 13, 4, -71.387, -32.719106, false, null, true, false, '#000000', null),

(56, 14, 14, 4, -71.407, -32.749106, false, null, true, false, '#000000', null),
(57, 14, 14, 4, -71.407, -32.729106, false, null, true, false, '#000000', null),

(56, 15, 15, 4, -71.407, -32.749106, false, null, true, false, '#000000', null),
(57, 15, 15, 4, -71.407, -32.729106, false, null, true, false, '#000000', null),

(56, 16, 16, 401, -71.407, -32.749106, false, null, true, false, '#000000', null),
(57, 16, 16, 401, -71.407, -32.729106, false, null, true, false, '#000000', null);

UPDATE conf.alarm_configs SET alarm_level_id = 4, enabled = true, treatment = True WHERE element_type_id = 3 AND alarm_config_id = 10004; --LBL_ALARM_CONFIGS_ALARM_DETECTOR_NO_CONNECTION
UPDATE conf.alarm_configs SET alarm_level_id = 5, enabled = True WHERE element_type_id = 54 AND alarm_config_id = 010054;
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True WHERE element_type_id = 54 AND alarm_config_id = 020054;
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True WHERE element_type_id = 54 AND alarm_config_id = 030054;
UPDATE conf.alarm_configs SET alarm_level_id = 3, enabled = True WHERE element_type_id = 54 AND alarm_config_id = 040054;

update master.i18n_labels set translation = 'ETD PAT' where label = 'LBL_ELEMENT_TYPE_ATC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Estación de toma de datos PAT' where label = 'LBL_ELEMENT_TYPE_ATC_DESC' and locale_code = 'es_CL';

