INSERT INTO ui.vms_libraries(id, name, description, label, enabled, visible) VALUES
(01, 'Librería mensajes', null, 'LBL_LIB_VMS', true, true);

INSERT INTO ui.vms_library_nodes(id, vms_library_id, name, description, label, signaling, enabled, visible) VALUES
(01, 01, 'Libreria', null, 'Libreria', null, true, true),

(0101, 01, 'Precaución_accidente_a', null, 'Precaución_accidente_a', '[{"zone":1,"graphics":[{"id":1,"value":80}]},{"zone":2,"align": "left","texts":[{"id":1,"value":""},{"id":2,"value":"ACCIDENTE"},{"id":3,"value":"A X KM"}]},{"zone":3,"graphics":[{"id":1,"value":null}]}]', true, true),
(0102, 01, 'Retenciones_en', null, 'Retenciones_en', '[{"zone":1,"graphics":[{"id":1,"value":80}]},{"zone":2,"align": "left","texts":[{"id":1,"value":""},{"id":2,"value":"EN X KM"},{"id":3,"value":""}]},{"zone":3,"graphics":[{"id":1,"value":null}]}]', true, true),
(0103, 01, 'Vehículo_lento_en_', null, 'Vehículo_lento_en_túnel', '[{"zone":1,"graphics":[{"id":1,"value":80}]},{"zone":2,"align": "left","texts":[{"id":1,"value":"VEHICULO"},{"id":2,"value":"LENTO"},{"id":3,"value":"EN TUNEL"}]},{"zone":3,"graphics":[{"id":1,"value":null}]}]', true, true),
(0104, 01, 'Niebla', null, 'Niebla', '[{"zone":1,"graphics":[{"id":1,"value":82}]},{"zone":2,"align": "left","texts":[{"id":1,"value":"CON NIEBLA"},{"id":2,"value":"MODERE"},{"id":3,"value":"VELOCIDAD"}]},{"zone":3,"graphics":[{"id":1,"value":null}]}]', true, true);

INSERT INTO ui.vms_library_hierarchies(vms_library_id, node_parent_id, node_child_id, enabled, visible) VALUES
(01, 01, 0101, true, true),
(01, 01, 0102, true, true),
(01, 01, 0103, true, true),
(01, 01, 0104, true, true);

INSERT INTO ui.vms_library_element_subtypes (element_subtype_id, vms_library_id, enabled, visible) VALUES
('ElementSubtype:22:71', 1, True, True);

