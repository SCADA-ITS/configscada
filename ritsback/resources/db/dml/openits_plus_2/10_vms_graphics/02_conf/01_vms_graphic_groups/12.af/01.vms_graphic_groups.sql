INSERT INTO conf.vms_graphic_groups (graphic_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(12, 'af', 'af', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.vms_graphic_groups_elements (graphic_group_id, element_type_id, element_id, enabled, visible, created_at, updated_at) VALUES
(12, 71, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 71, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 71, 25, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 71, 26, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 71, 27, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 71, 28, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 71, 29, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 71, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.vms_graphic_graphic_group_values (graphic_type_id, graphic_id, graphic_group_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 745,  12, '236' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 746,  12, '237' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 743,  12, '238' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 744,  12, '239' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 
