INSERT INTO conf.vms_graphic_groups (graphic_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(14, 'RED_BEACON', 'RED_BEACON', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.vms_graphic_groups_elements (graphic_group_id, element_type_id, element_id, enabled, visible, created_at, updated_at) VALUES
(14, 71, 146, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 71, 147, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 71, 148, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 71, 149, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.vms_graphic_graphic_group_values (graphic_type_id, graphic_id, graphic_group_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 873, 14, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



