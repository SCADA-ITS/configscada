INSERT INTO conf.vms_graphic_groups (graphic_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(7, 'Vms_temp', 'Temperature', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.vms_graphic_groups_elements (graphic_group_id, element_type_id, element_id, enabled, visible, created_at, updated_at) VALUES
(7, 71, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 71, 124, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 71, 125, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 71, 126, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 71, 127, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.vms_graphic_graphic_group_values (graphic_type_id, graphic_id, graphic_group_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 859, 7, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 860, 7, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



