INSERT INTO conf.vms_graphic_groups (graphic_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(24, 'Vms_meteo_trafico', 'Meteo_Trafico', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.vms_graphic_groups_elements (graphic_group_id, element_type_id, element_id, enabled, visible, created_at, updated_at) VALUES
(24, 71, 177, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 71, 178, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.vms_graphic_graphic_group_values (graphic_type_id, graphic_id, graphic_group_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 859, 24, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 860, 24, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 878, 24, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 879, 24, '6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 880, 24, '5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 882, 24, '7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




