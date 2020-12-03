INSERT INTO ui.module_types(id, "name", description, enabled, visible) VALUES
(1, 'Custom Window', 'Custom Window', true, true),
(2, 'Generic Grid Window', 'Generic Grid Window', true, true),
(3, 'Generic Map Window', 'Generic Map Window', true, true),
(4, 'Generic Synoptic Window', 'Generic Synoptic Window', true, true),
(5, 'Generic External App Window', 'Generic External App Window', true, true),
(6, 'Service', 'Service', true, true);

INSERT INTO ui.module_params(id, module_type_id, name, description, enabled, visible) VALUES
(1, 6, 'load_priority', 'load_priority', true, true),
(2, 6, 'config', 'config', true, true),
(3, 2, 'grid', 'grid id', true, true),
(4, 4, 'synopticId', 'synopticId', true, true);

INSERT INTO ui.window_params(id, "name", description, enabled, visible) VALUES
(1, 'hasMinButton', 'hasMinButton', true, true),
(2, 'hasMaxButton', 'hasMaxButton', true, true),
(3, 'isMaximized', 'isMaximized', true, true),
(4, 'heigth', 'heigth', true, true),
(5, 'width', 'width', true, true),
(6, 'x', 'x', true, true),
(7, 'y', 'y', true, true),
(8, 'allowMultipleInstances', 'allowMultipleInstances', true, true),
(9, 'image', 'image', true, true),
(10, 'icon', 'icon', true, true),
(11, 'color', 'color', true, true),
(12, 'label', 'label', true, true),
(13, 'resizable', 'resizable', true, true),
(14, 'externalizable', 'externalizable', true, true);


