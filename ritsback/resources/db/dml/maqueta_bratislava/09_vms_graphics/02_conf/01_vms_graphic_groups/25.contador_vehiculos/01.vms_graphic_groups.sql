INSERT INTO conf.vms_graphic_groups (graphic_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(25, 'Vms_contador_vehiculos', 'contador_vehiculos', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.vms_graphic_groups_elements (graphic_group_id, element_type_id, element_id, enabled, visible, created_at, updated_at) VALUES
(25, 71, 179, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 71, 180, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.vms_graphic_graphic_group_values (graphic_type_id, graphic_id, graphic_group_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 878, 25, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 881, 25, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




