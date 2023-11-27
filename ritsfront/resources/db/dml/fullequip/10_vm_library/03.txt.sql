/*INSERT INTO ui.vms_libraries(id, name, description, label, enabled, visible) VALUES
(03, 'Librería mensajes', null, 'LBL_LIB_VMS', true, true);

INSERT INTO ui.vms_library_nodes(id, vms_library_id, name, description, label, signaling, enabled, visible) VALUES
(03, 03, 'Libreria', null, 'Libreria', null, true, true),

(0301, 03, 'Alternantes', null, 'Alternantes', null, true, true),
(0302, 03, 'Fijos', null, 'Fijos', null, true, true);

INSERT INTO ui.vms_library_hierarchies(vms_library_id, node_parent_id, node_child_id, enabled, visible) VALUES
(03, 03, 0301, true, true),
(03, 03, 0302, true, true);


INSERT INTO ui.vms_library_element_subtypes(element_subtype_id, vms_library_id, enabled, visible) VALUES
('ElementSubtype:16:71', 03, true, true);*/