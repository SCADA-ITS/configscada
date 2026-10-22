INSERT INTO ui.vms_libraries(id, name, description, label, enabled, visible) VALUES
(01, 'Librería mensajes', null, 'LBL_LIB_VMS', true, true);

INSERT INTO ui.vms_library_element_subtypes(element_subtype_id, vms_library_id, enabled, visible) VALUES
('ElementSubtype:19:71', 01, true, true);